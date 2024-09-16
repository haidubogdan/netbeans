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
package org.netbeans.modules.php.blade.syntax.antlr4.v10;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author bogdan
 */
public abstract class ColoringLexerAdaptor extends Lexer {

    private int _currentRuleType = Token.INVALID_TYPE;
    private int roundParenBalance = 0;

    public ColoringLexerAdaptor(CharStream input) {
        super(input);
    }

    public int getCurrentRuleType() {
        return _currentRuleType;
    }

    public void setCurrentRuleType(int ruleType) {
        this._currentRuleType = ruleType;
    }

    @Override
    public Token emit() {
        return super.emit();
    }

    @Override
    public void reset() {
        setCurrentRuleType(Token.INVALID_TYPE);
        super.reset();
    }

    /**
     * eager check to see if the character position in a line is at the start
     *
     * @return
     */
    public boolean IsNewLineOrStart() {
        return this._tokenStartCharPositionInLine <= 2;
    }

    public boolean peekNextChar(char peekChar) {
        return (char) this._input.LA(1) == peekChar;
    }

    public boolean peekNextChars(char peekChar, int number) {
        for (int i = 1; i < number; i++) {
            if ((char) this._input.LA(i) != peekChar) {
                return false;
            }
        }
        return true;
    }

    public void lookForDirectiveArg() {
        this.setType(BladeAntlrColoringLexer.DIRECTIVE);
        if (this._input.LA(1) == '(') {
            this.roundParenBalance = 0;
            this.mode(BladeAntlrColoringLexer.INSIDE_PHP_EXPRESSION);
        }
    }

    public void lookForCustomDirectiveArg() {
        if (this._input.LA(1) == '(') {
            this.setType(BladeAntlrColoringLexer.D_CUSTOM);
            this.roundParenBalance = 0;
            this.mode(BladeAntlrColoringLexer.INSIDE_PHP_EXPRESSION);
        } else {
            this.setType(BladeAntlrColoringLexer.D_UNKNOWN);
        }
    }

    public void handleOpenRoundParen() {
        this.setType(BladeAntlrColoringLexer.PHP_TOKEN);
        this.roundParenBalance++;
    }

    public void handleEndRoundParen() {
        this.setType(BladeAntlrColoringLexer.PHP_TOKEN);
        this.roundParenBalance--;
        if (this.roundParenBalance < 0) {
            this.roundParenBalance = 0;
        }
        if (this.roundParenBalance == 0) {
            this.mode(DEFAULT_MODE);
        }
    }

    //blade coloring lexer
    public void consumeEscapedEchoToken() {
        if (this._input.LA(1) == '}' && this._input.LA(2) == '}') {
            this.setType(BladeAntlrColoringLexer.BLADE_PHP_ECHO_EXPR);
        } else {
            this.more();
        }
    }

    //blade coloring lexer
    public void consumeNotEscapedEchoToken() {
        if (this._input.LA(1) == '!' && this._input.LA(2) == '!' && this._input.LA(3) == '}') {
            this.setType(BladeAntlrColoringLexer.BLADE_PHP_ECHO_EXPR);
        } else {
            this.more();
        }
    }

    public void consumeExprToken() {
        if (this._input.LA(1) == ':' && this._input.LA(2) != ':') {
            this.setType(BladeAntlrColoringLexer.PHP_EXPRESSION);
        } else {
            this.more();
        }
    }

}

