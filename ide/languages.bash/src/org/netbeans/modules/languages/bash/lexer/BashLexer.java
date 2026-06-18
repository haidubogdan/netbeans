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
package org.netbeans.modules.languages.bash.lexer;

import org.netbeans.api.lexer.Token;
import org.netbeans.modules.languages.bash.grammar.antlr4.coloring.BashAntlrColoringLexer;
import static org.netbeans.modules.languages.bash.grammar.antlr4.coloring.BashAntlrColoringLexer.*;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.netbeans.spi.lexer.antlr4.AbstractAntlrLexerBridge;

public class BashLexer extends AbstractAntlrLexerBridge<BashAntlrColoringLexer, BashTokenId> {
    public BashLexer(LexerRestartInfo<BashTokenId> info) {
        super(info, BashAntlrColoringLexer::new);
    }

    @Override
    public Object state() {
        return new State(lexer);
    }

    @Override
    protected Token<BashTokenId> mapToken(org.antlr.v4.runtime.Token antlrToken) {
        return switch (antlrToken.getType()) {
            case COMMENT -> groupToken(BashTokenId.COMMENT, COMMENT); 
            case BASH_KEYWORD -> groupToken(BashTokenId.KEYWORD, BASH_KEYWORD);
            case COMMAND_OPTION->token(BashTokenId.COMMAND_OPTION); 
            case IDENTIFIER -> groupToken(BashTokenId.IDENTIFIER, IDENTIFIER);
            case STRING -> token(BashTokenId.STRING);
            case HEREDOC_START -> token(BashTokenId.HEREDOC_START);
            case HEREDOC_END -> token(BashTokenId.HEREDOC_END);
            case NUMBER -> token(BashTokenId.NUMBER);
            case ASSIGN_OPERATOR -> token(BashTokenId.ASSIGN_OPERATOR);     
            case OPERATOR -> token(BashTokenId.OPERATOR);  
            case DELIMITER -> token(BashTokenId.DELIMITER);      
            case SEPARATOR -> token(BashTokenId.SEPARATOR);  
            case SEMICOLON -> token(BashTokenId.SEMICOLON);
            case VARIABLE -> token(BashTokenId.VARIABLE);
            case DOLLAR -> token(BashTokenId.DOLLAR);    
            case WS -> groupToken(BashTokenId.WS, WS);    
            case NL -> groupToken(BashTokenId.WS, NL);
            default -> groupToken(BashTokenId.ERROR, ERROR);
        };
    }

    private static class State extends AbstractAntlrLexerBridge.LexerState<BashAntlrColoringLexer> {
        final boolean interpolationKeyAdded;

        public State(BashAntlrColoringLexer lexer) {
            super(lexer);
            this.interpolationKeyAdded = lexer.keyTokenAdded();
        }

        @Override
        public void restore(BashAntlrColoringLexer lexer) {
            super.restore(lexer);
            lexer.setInterpolationKeyAddedState(interpolationKeyAdded);
        }
    }
}
