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
package org.netbeans.modules.javascript2.vue.editor;

import javax.swing.text.Document;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.javascript2.editor.spi.DeclarationFinder;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.javascript2.lexer.api.JsTokenId;
import org.netbeans.modules.javascript2.lexer.api.LexUtilities;
import org.netbeans.modules.parsing.api.Source;

/**
 * 
 * TODO 
 * check if necessary
 */
@DeclarationFinder.Registration(priority = 13)
public class VueJsDeclarationFinder implements DeclarationFinder {

    @Override
    public DeclarationLocation findDeclaration(ParserResult info, int caretOffset) {
       return DeclarationLocation.NONE;
    }

    @Override
    public OffsetRange getReferenceSpan(Document doc, int caretOffset) {
        final OffsetRange[] value = new OffsetRange[1];
        value[0] = OffsetRange.NONE;
        
        doc.render(new Runnable() {

            @Override
            public void run() {
                TokenSequence<? extends JsTokenId> ts = LexUtilities.getJsTokenSequence(doc, caretOffset);
                if (ts != null) {
                    ts.move(caretOffset);
                    if (ts.moveNext() && ts.token().id() == JsTokenId.STRING) {
                        ts.move(caretOffset);
                        ts.moveNext();
                        String path = ts.token().text().toString();
                        int start = ts.offset();
                        int end = ts.offset() + path.length();
                        value[0] = new OffsetRange(start, end);
                    }
                }
            }
        });
        return value[0];
    }
    
}
