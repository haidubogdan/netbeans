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
package org.netbeans.modules.languages.bash;

import org.netbeans.api.lexer.Language;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import static org.netbeans.modules.languages.bash.BashLanguage.MIME_TYPE;
import org.netbeans.modules.languages.bash.lexer.BashLexer;
import org.netbeans.modules.languages.bash.lexer.BashTokenId;
import org.netbeans.modules.languages.bash.lexer.BashTokenId.BashLanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

@MIMEResolver.ExtensionRegistration(
        extension = {"sh", "SH"},
        displayName = "Bash",
        mimeType = MIME_TYPE,
        position = 199
)

@ActionReferences({
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.PasteAction"),
            position = 500,
            separatorAfter = 600
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 700
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 800,
            separatorAfter = 900
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 1000,
            separatorAfter = 1100
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1200,
            separatorAfter = 1300
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1400
    ),
    @ActionReference(
            path = "Loaders/application/x-sh/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1500
    ),
    @ActionReference(
            path = "Editors/application/x-sh/Popup",
            id = @ActionID(category = "Refactoring", id = "org.netbeans.modules.refactoring.api.ui.WhereUsedAction"),
            position = 1600
    ),})

@LanguageRegistration(mimeType = MIME_TYPE, useMultiview = true)
public class BashLanguage extends DefaultLanguageConfig{
    
    public static final String MIME_TYPE = "application/x-sh"; //NOI18N
    
    @NbBundle.Messages("Source=&Source")
    @MultiViewElement.Registration(displayName = "#Source",
            iconBase = "org/netbeans/modules/languages/bash/resources/sh_file_16.png",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "bash.source",
            mimeType = MIME_TYPE,
            position = 2)
    public static MultiViewEditorElement createMultiViewEditorElement(Lookup context) {
        return new MultiViewEditorElement(context);
    }

    public BashLanguage() {
        super();
    }

    @Override
    public Language<BashTokenId> getLexerLanguage() {
        return language;
    }

    @Override
    public String getDisplayName() {
        return "Bash"; // NOI18N
    }

    @Override
    public String getPreferredExtension() {
        return "bash"; // NOI18N
    }

    @Override
    public boolean isIdentifierChar(char c) {
        return super.isIdentifierChar(c) || c == '_' || c == '.' || c == '-' || c == '@'; // NOI18N
    }

    @Override
    public String getLineCommentPrefix() {
        return "#"; // NOI18N
    }
    
    private static final Language<BashTokenId> language
            = new BashLanguageHierarchy() {

                @Override
                protected String mimeType() {
                    return MIME_TYPE;
                }

                @Override
                protected Lexer<BashTokenId> createLexer(LexerRestartInfo<BashTokenId> info) {
                    return new BashLexer(info);
                }

            }.language();
}
