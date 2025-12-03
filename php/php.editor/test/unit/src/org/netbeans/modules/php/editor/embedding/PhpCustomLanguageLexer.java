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
package org.netbeans.modules.php.editor.embedding;

import org.netbeans.api.lexer.Token;
import org.netbeans.modules.php.editor.embedding.PHPCustomLanguageEmbeddingTest.PhpTextTokenId;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.netbeans.spi.lexer.TokenFactory;

/**
 *
 * @author bhaidu
 */
public class PhpCustomLanguageLexer implements Lexer<PhpTextTokenId> {

    private final LexerInput input;
    private final TokenFactory<PhpTextTokenId> factory;
    private boolean embeddedPhpState = true;

    public PhpCustomLanguageLexer(LexerRestartInfo<PhpTextTokenId> info) {
        this.input = info.input();
        this.factory = info.tokenFactory();
    }

    @Override
    public Token<PhpTextTokenId> nextToken() {
        if (input.read() == LexerInput.EOF) {
            return null;
        }

        input.read();

        if (input.readText().toString().startsWith("{{")) {
            embeddedPhpState = true;
            return factory.createToken(PhpTextTokenId.ANY);
        }

        if (embeddedPhpState && readUntil("}}")) {
            input.backup(2);
            embeddedPhpState = false;
            return factory.createToken(PhpTextTokenId.PHP);
        }

        return factory.createToken(PhpTextTokenId.ANY);
    }

    private boolean readUntil(String condition) {
        int read;

        while ((read = input.read()) != LexerInput.EOF && !input.readText().toString().endsWith(condition));

        return read != LexerInput.EOF;
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
        
    }

}
