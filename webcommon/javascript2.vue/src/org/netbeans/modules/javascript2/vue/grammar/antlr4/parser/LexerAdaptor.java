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
package org.netbeans.modules.javascript2.vue.grammar.antlr4.parser;

import org.netbeans.modules.javascript2.vue.grammar.antlr4.coloring.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;

/**
 *
 * @author bogdan
 */
public abstract class LexerAdaptor extends Lexer {

    private boolean attrQuoteOpened = false;

    public LexerAdaptor(CharStream input) {
        super(input);
    }

    @Override
    public void reset() {
        attrQuoteOpened = false;
        super.reset();
    }

    public void setAttrQuoteState(boolean state) {
        attrQuoteOpened = state;
    }

    public boolean getAttrQuoteState() {
        return attrQuoteOpened;
    }

}
