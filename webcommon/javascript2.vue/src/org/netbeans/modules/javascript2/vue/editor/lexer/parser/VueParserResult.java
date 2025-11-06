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
package org.netbeans.modules.javascript2.vue.editor.lexer.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.Token;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.javascript2.vue.grammar.antlr4.parser.VueAntlrLexer;
import org.netbeans.modules.javascript2.vue.grammar.antlr4.parser.VueAntlrParser;
import org.netbeans.modules.javascript2.vue.grammar.antlr4.parser.VueAntlrParserBaseListener;
import org.netbeans.modules.parsing.api.Snapshot;

/**
 *
 * @author bogdan
 */
public class VueParserResult extends ParserResult {

    private volatile boolean finished = false;
    //offset ranges for coloring highlight
    private final List<OffsetRange> vueDirectiveLocations = new ArrayList<>();
    private final List<OffsetRange> vueTagsLocations = new ArrayList<>();

    public VueParserResult(final Snapshot snapshot) {
        super(snapshot);
    }

    protected VueAntlrParser createParser(Snapshot snapshot) {
        CharStream cs = CharStreams.fromString(String.valueOf(snapshot.getText()));
        VueAntlrLexer lexer = new VueAntlrLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        VueAntlrParser ret = new VueAntlrParser(tokens);
        ret.removeErrorListener(ConsoleErrorListener.INSTANCE);
        return ret;
    }

    public VueParserResult get() {
        if (!finished) {
            VueAntlrParser parser = createParser(getSnapshot());
            parser.setBuildParseTree(false);
            parser.addParseListener(new VueElementHighlightsLocationListener());
            evaluateParser(parser);
            finished = true;
        }

        return this;
    }

    protected void evaluateParser(VueAntlrParser parser) {
        parser.file();
    }

    @Override
    protected void invalidate() {

    }

    @Override
    public List<? extends org.netbeans.modules.csl.api.Error> getDiagnostics() {
        return Collections.emptyList();
    }

    private class VueElementHighlightsLocationListener extends VueAntlrParserBaseListener {

        @Override
        public void exitVueDirective(VueAntlrParser.VueDirectiveContext ctx) {
            Token vueDirective = ctx.start;
            if (vueDirective != null) {
                vueDirectiveLocations.add(new OffsetRange(vueDirective.getStartIndex(), vueDirective.getStopIndex() + 1));
            }
        }

        @Override
        public void exitVueInterpolation(VueAntlrParser.VueInterpolationContext ctx) {
            Token vueOpenTag = ctx.open_tag;
            if (vueOpenTag != null) {
                vueTagsLocations.add(new OffsetRange(vueOpenTag.getStartIndex(), vueOpenTag.getStopIndex() + 1));
            }
            
            Token vueCloseTag = ctx.close_tag;
            if (vueCloseTag != null) {
                vueTagsLocations.add(new OffsetRange(vueCloseTag.getStartIndex(), vueCloseTag.getStopIndex() + 1));
            }
        }
    }

    public List<OffsetRange> getVueDirectiveLocations() {
        return vueDirectiveLocations;
    }
    
    public List<OffsetRange> getVueTagsLocations() {
        return vueTagsLocations;
    }
}
