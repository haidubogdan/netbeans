/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.php.blade.editor.completion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.netbeans.editor.BaseDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.api.CodeCompletionHandler2;
import org.netbeans.modules.csl.api.CodeCompletionResult;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.Documentation;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ParameterInfo;
import org.netbeans.modules.csl.spi.DefaultCompletionResult;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.csl.spi.support.CancelSupport;
import org.netbeans.modules.php.blade.csl.elements.DirectiveElement;
import org.netbeans.modules.php.blade.csl.elements.ElementType;
import org.netbeans.modules.php.blade.csl.elements.NamedElement;
import org.netbeans.modules.php.blade.csl.elements.PhpFunctionElement;
import org.netbeans.modules.php.blade.csl.elements.TagElement;
import org.netbeans.modules.php.blade.editor.EditorStringUtils;
import org.netbeans.modules.php.blade.editor.completion.BladeCompletionProposal.CompletionRequest;
import org.netbeans.modules.php.blade.editor.directives.CustomDirectives;
import org.netbeans.modules.php.blade.editor.lexer.BladeLexerUtils;
import org.netbeans.modules.php.blade.editor.parser.BladeParserResult;
import org.netbeans.modules.php.blade.editor.path.BladePathUtils;
import org.netbeans.modules.php.blade.editor.preferences.ModulePreferences;
import org.netbeans.modules.php.blade.project.ProjectUtils;
import org.netbeans.modules.php.blade.syntax.BladeTags;
import org.netbeans.modules.php.blade.syntax.BladeVariables;
import org.netbeans.modules.php.blade.syntax.annotation.Directive;
import org.netbeans.modules.php.blade.syntax.annotation.Tag;
import org.netbeans.modules.php.blade.syntax.antlr4.v10.BladeAntlrLexer;
import org.netbeans.modules.php.blade.syntax.antlr4.v10.BladeAntlrUtils;
import static org.netbeans.modules.php.blade.syntax.antlr4.v10.BladeAntlrLexer.*;
import static org.netbeans.modules.php.blade.syntax.antlr4.v10.BladeAntlrParser.CONTENT_TAG_OPEN;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.netbeans.spi.lexer.antlr4.AntlrTokenSequence;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author bogdan
 */
public class BladeCompletionHandler implements CodeCompletionHandler2 {

    private static final Logger LOGGER = Logger.getLogger(BladeCompletionHandler.class.getName());

    @Override
    public CodeCompletionResult complete(CodeCompletionContext completionContext) {
        if (CancelSupport.getDefault().isCancelled()) {
            return CodeCompletionResult.NONE;
        }
        long startTime = System.currentTimeMillis();
        BaseDocument doc = (BaseDocument) completionContext.getParserResult().getSnapshot().getSource().getDocument(false);

        if (doc == null) {
            return CodeCompletionResult.NONE;
        }

        int offset = completionContext.getCaretOffset();

        if (offset < 1) {
            return CodeCompletionResult.NONE;
        }

        BladeParserResult parserResult = (BladeParserResult) completionContext.getParserResult();

        final List<CompletionProposal> completionProposals = new ArrayList<>();

        Token currentToken = BladeAntlrUtils.getToken(doc, offset - 1);

        if (currentToken == null) {
            return CodeCompletionResult.NONE;
        }

        String prefix = currentToken.getText();

        if (prefix == null) {
            return CodeCompletionResult.NONE;
        }

        String tokenText = currentToken.getText();
        FileObject fo = completionContext.getParserResult().getSnapshot().getSource().getFileObject();
        //D_UNKNOWN_ATTR_ENC hack to fix completion not triggered in html embedded text
        if (tokenText.startsWith("@") // NOI18N
                && currentToken.getType() != D_UNKNOWN_ATTR_ENC) {
            completeDirectives(completionProposals, completionContext, fo, currentToken);
        } else {
            if (prefix.length() == 1) {
                return CodeCompletionResult.NONE;
            }
            switch (currentToken.getType()) {
                case PHP_IDENTIFIER, PHP_NAMESPACE_PATH ->
                    PhpCodeCompletionService.completePhpCode(completionProposals, parserResult, offset, prefix);
                case PHP_EXPRESSION ->
                    completePhpSnippet(completionProposals, offset, currentToken);
                case PHP_VARIABLE ->
                    completeScopedVariables(completionProposals, completionContext, parserResult, currentToken);
                case BL_PARAM_STRING ->
                    completeDirectiveIdentifier(completionProposals, completionContext, fo, currentToken);
                case CONTENT_TAG_OPEN, RAW_TAG_OPEN -> {
                    //{{ | {!!
                    if (!ModulePreferences.isAutoTagCompletionEnabled()) {
                        completeBladeTags(completionProposals, completionContext, currentToken);
                    }
                }
            }
        }

        if (completionProposals.isEmpty()) {
            return CodeCompletionResult.NONE;
        }

        long time = System.currentTimeMillis() - startTime;
        if (time > 2000) {
            LOGGER.info(String.format("complete() with results took %d ms", time)); // NOI18N
        }
        return new DefaultCompletionResult(completionProposals, false);
    }

    /**
     * proxy completion using the original php code completion service
     *
     * @param completionProposals
     * @param offset
     * @param currentToken
     */
    private void completePhpSnippet(final List<CompletionProposal> completionProposals,
            int offset, Token currentToken) {
        PhpCodeCompletionService phpCodeCompletion = new PhpCodeCompletionService();
        for (CompletionProposal proposal : phpCodeCompletion.getCompletionProposal(offset, currentToken)) {
            String proposalPrefix = proposal.getInsertPrefix();
            if (proposalPrefix.startsWith(phpCodeCompletion.prefix)) {
                completionProposals.add(proposal);
            }
        }
    }

    private void completeScopedVariables(final List<CompletionProposal> completionProposals,
            CodeCompletionContext completionContext, BladeParserResult parserResult, Token currentToken) {
        String variablePrefix = currentToken.getText();
        Set<String> scopedVariables = parserResult.findLoopVariablesForScope(completionContext.getCaretOffset());
        FileObject fo = completionContext.getParserResult().getSnapshot().getSource().getFileObject();

        if (scopedVariables != null && !scopedVariables.isEmpty()) {
            CompletionRequest request = new CompletionRequest();
            request.anchorOffset = completionContext.getCaretOffset() - variablePrefix.length();
            request.carretOffset = completionContext.getCaretOffset();
            request.prefix = variablePrefix;
            if (BladeVariables.LOOP_VAR.startsWith(variablePrefix)) {
                String variableName = BladeVariables.LOOP_VAR;
                NamedElement variableElement = new NamedElement(variableName, fo, ElementType.VARIABLE);
                completionProposals.add(new BladeCompletionProposal.BladeVariableItem(variableElement, request, variableName));
            }
            for (String variableName : scopedVariables) {
                if (variableName.startsWith(variablePrefix)) {
                    NamedElement variableElement = new NamedElement(variableName, fo, ElementType.VARIABLE);
                    completionProposals.add(new BladeCompletionProposal.VariableItem(variableElement, request, variableName));
                }
            }
        }
    }

    /**
     * BLADES
     *
     * @param completionProposals
     * @param completionContext
     * @param parserResult
     * @param currentToken
     */
    private void completeBladeTags(final List<CompletionProposal> completionProposals,
            CodeCompletionContext completionContext, Token currentToken) {
        String tagStart = currentToken.getText();

        CompletionRequest request = completionRequest(tagStart, completionContext.getCaretOffset());
        BladeTags tagsContainer = new BladeTags();
        Tag[] tags = tagsContainer.getTags();
        for (Tag tag : tags) {
            if (tag.openTag().startsWith(tagStart)) {
                TagElement tagElement = new TagElement(tag.closeTag());
                completionProposals.add(new BladeCompletionProposal.BladeTag(tagElement, request, tag));
            }
        }
    }

    private void completeDirectives(final List<CompletionProposal> completionProposals,
            CodeCompletionContext completionContext, FileObject fo, Token currentToken) {
        String prefix = currentToken.getText();
        DirectiveCompletionList completionList = new DirectiveCompletionList();

        CompletionRequest request = completionRequest(prefix, completionContext.getCaretOffset());

        for (Directive directive : completionList.getDirectives()) {
            String directiveName = directive.name();
            if (directiveName.startsWith(prefix)) {
                DirectiveElement directiveEl = new DirectiveElement(directiveName, fo);

                if (directive.params()) {
                    completionProposals.add(new BladeCompletionProposal.DirectiveWithArg(directiveEl, request, directive));
                    if (!directive.endtag().isEmpty()) {
                        completionProposals.add(new BladeCompletionProposal.BlockDirectiveWithArg(directiveEl, request, directive));
                    }
                } else {

                    completionProposals.add(new BladeCompletionProposal.InlineDirective(directiveEl, request, directive));
                    if (!directive.endtag().isEmpty()) {
                        completionProposals.add(new BladeCompletionProposal.BlockDirective(directiveEl, request, directive));
                    }
                }
            }
        }

        Project project = ProjectUtils.getMainOwner(fo);
        CustomDirectives.getInstance(project).filterAction(new CustomDirectives.FilterCallback() {
            @Override
            public void filterDirectiveName(CustomDirectives.CustomDirective directive, FileObject file) {
                DirectiveElement directiveEl = new DirectiveElement(directive.name, file);
                if (directive.name.startsWith(prefix)) {
                    CompletionRequest request = completionRequest(prefix, completionContext.getCaretOffset());
                    completionProposals.add(
                            new BladeCompletionProposal.CustomDirective(
                                    directiveEl,
                                    request,
                                    directive.name
                            ));
                }
            }
        });
    }

    private void completeDirectiveIdentifier(final List<CompletionProposal> completionProposals,
            CodeCompletionContext completionContext, FileObject fo, Token currentToken) {
        String pathName = EditorStringUtils.stripSurroundingQuotes(currentToken.getText());

        AntlrTokenSequence tokens;
        try {
            String docText = fo.asText();
            tokens = new AntlrTokenSequence(new BladeAntlrLexer(CharStreams.fromString(docText)));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return;
        }

        tokens.seekTo(currentToken.getStopIndex());
        List<Integer> tokensStop = Arrays.asList(new Integer[]{HTML, BL_COMMA, BL_PARAM_CONCAT_OPERATOR});
        List<Integer> tokensMatch = BladeLexerUtils.TOKENS_WITH_IDENTIFIABLE_PARAM;
        Token directiveToken = BladeAntlrUtils.findBackward(tokens, tokensMatch, tokensStop);
        if (directiveToken == null) {
            return;
        }

        int caretOffset = completionContext.getCaretOffset() + 2; //quote  & braket
        String prefix = currentToken.getText();

        switch (directiveToken.getType()) {
            case D_EXTENDS, D_INCLUDE, D_INCLUDE_IF, D_INCLUDE_WHEN, D_INCLUDE_UNLESS, D_EACH -> {
                int lastDotPos;

                if (pathName.endsWith(".")) {
                    lastDotPos = pathName.length();
                } else {
                    lastDotPos = pathName.lastIndexOf(".");
                }
                int pathOffset;

                if (lastDotPos > 0) {
                    int dotFix = pathName.endsWith(".") ? 0 : 1;
                    pathOffset = caretOffset - pathName.length() + lastDotPos + dotFix;
                } else {
                    pathOffset = caretOffset - pathName.length();
                }
                CompletionRequest request = completionRequest(prefix, caretOffset);
                List<FileObject> childrenFiles = BladePathUtils.getParentChildrenFromPrefixPath(fo, pathName);
                for (FileObject file : childrenFiles) {
                    String pathFileName = file.getName();
                    if (!file.isFolder()) {
                        pathFileName = pathFileName.replace(".blade", "");
                    }
                    completeBladePath(completionProposals, request, pathFileName, file);
                }
                return;
            }
//            case D_SECTION, D_HAS_SECTION ->
//                completeYieldIdFromIndex(completionProposals, pathName, fo, caretOffset);
//            case D_PUSH, D_PUSH_IF, D_PREPEND ->
//                completeStackIdFromIndex(completionProposals, pathName, fo, caretOffset);
        }
    }

    private void completeBladePath(final List<CompletionProposal> completionProposals,
            CompletionRequest request, String bladePath, FileObject originFile) {

        String filePath = originFile.getPath();
        NamedElement directiveEl = new NamedElement(bladePath, originFile);
        completionProposals.add(
                new BladeCompletionProposal.BladePath(
                        directiveEl,
                        request,
                        bladePath,
                        originFile.isFolder()
                ));
    }
//
//    private void addYieldIdCompletionItem(final List<CompletionProposal> completionProposals,
//            String identifier, FileObject fo,
//            int caretOffset) {
//
//        String filePath = fo.getPath();
//        int viewsPos = filePath.indexOf("/views/"); // NOI18N
//
//        CompletionItem item = CompletionUtilities.newCompletionItemBuilder(identifier)
//                .iconResource(getReferenceIcon(BladePhpCompletionProvider.CompletionType.YIELD_ID))
//                .startOffset(caretOffset)
//                .leftHtmlText(identifier)
//                .rightHtmlText(filePath.substring(viewsPos, filePath.length()))
//                .sortPriority(1)
//                .build();
//        resultSet.addItem(item);
//    }
//
//    private void addAssetPathCompletionItem(final List<CompletionProposal> completionProposals,
//            String preview, String info,
//            int caretOffset, BladePhpCompletionProvider.CompletionType type) {
//        CompletionItem item = CompletionUtilities.newCompletionItemBuilder(preview)
//                .iconResource(getReferenceIcon(type))
//                .startOffset(caretOffset)
//                .leftHtmlText(preview)
//                .rightHtmlText(info)
//                .sortPriority(1)
//                .build();
//        resultSet.addItem(item);
//    }

    @Override
    public String document(ParserResult pr, ElementHandle eh) {
        return null;
    }

    @Override
    public ElementHandle resolveLink(String string, ElementHandle eh) {
        return null;
    }

    @Override
    public String getPrefix(ParserResult info, int offset, boolean upToOffset) {
        return null;
    }

    @Override
    public CodeCompletionHandler.QueryType getAutoQuery(JTextComponent component, String typedText) {
        if (typedText.length() == 0) {
            return CodeCompletionHandler.QueryType.NONE;
        }

        if (typedText.startsWith("@")) { // NOI18N
            return CodeCompletionHandler.QueryType.ALL_COMPLETION;
        }

        char lastChar = typedText.charAt(typedText.length() - 1);

        return switch (lastChar) {
            case '\n' ->
                CodeCompletionHandler.QueryType.STOP;
            default ->
                CodeCompletionHandler.QueryType.ALL_COMPLETION;
        };
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String resolveTemplateVariable(String string, ParserResult pr, int i, String string1, Map map) {
        return null;
    }

    @Override
    public Set<String> getApplicableTemplates(Document dcmnt, int i, int i1) {
        return Collections.emptySet();
    }

    @Override
    public ParameterInfo parameters(ParserResult pr, int i, CompletionProposal cp) {
        return new ParameterInfo(new ArrayList<>(), 0, 0);
    }

    /**
     * used also for tooltip in blade mime context
     *
     * @param parserResult
     * @param elementHandle
     * @param cancel
     * @return
     */
    @Override
    public Documentation documentElement(ParserResult parserResult, ElementHandle elementHandle, Callable<Boolean> cancel) {
        Documentation result = null;
        if (elementHandle instanceof PhpFunctionElement phpFunctionElement) {
            return TooltipDoc.generateFunctionDoc(phpFunctionElement);
        } else if (elementHandle instanceof DirectiveElement) {
            return result;
        } else if (elementHandle instanceof NamedElement namedElement) {
            return TooltipDoc.generateDoc(namedElement);
        }
        return result;
    }

    public static CompletionRequest completionRequest(String prefix, int offset) {
        CompletionRequest request = new CompletionRequest();
        request.anchorOffset = offset - prefix.length();
        request.carretOffset = offset;
        request.prefix = prefix;

        return request;
    }
}
