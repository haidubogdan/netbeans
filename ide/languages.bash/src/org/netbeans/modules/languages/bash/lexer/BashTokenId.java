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

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageHierarchy;

public enum BashTokenId implements TokenId {
    COMMENT("comment"),
    COMMAND_OPTION("command_option"),
    KEYWORD("keyword"),
    IDENTIFIER("identifier"),
    STRING("string"),
    NUMBER("number"),
    DELIMITER("delimiter"),
    ASSIGN_OPERATOR("operator"),
    OPERATOR("operator"),
    SEPARATOR("separator"),
    SEMICOLON("separator"),
    DOLLAR("dollar"),
    VARIABLE("variable"),
    HEREDOC_START("heredoc-guard"),
    HEREDOC_END("heredoc-guard"),
    WS("whitespace"),
    ERROR("error");
    private final String primaryCategory;

    BashTokenId(String category) {
        this.primaryCategory = category;
    }

    @Override

    public String primaryCategory() {
        return primaryCategory;
    }

    public static abstract class BashLanguageHierarchy extends LanguageHierarchy<BashTokenId> {

        @Override
        protected Collection<BashTokenId> createTokenIds() {
            return EnumSet.allOf(BashTokenId.class);
        }

        @Override
        protected LanguageEmbedding<? extends TokenId> embedding(Token<BashTokenId> token,
                LanguagePath languagePath, InputAttributes inputAttributes) {

            return null;
        }
    }
}
