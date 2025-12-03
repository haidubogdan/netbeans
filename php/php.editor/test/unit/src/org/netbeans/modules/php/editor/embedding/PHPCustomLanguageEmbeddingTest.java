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

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.lib.lexer.test.TestLanguageProvider;
import org.netbeans.modules.csl.api.test.CslTestBase;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.php.editor.csl.PHPLanguage;
import org.netbeans.modules.php.editor.lexer.PHPTokenId;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author bhaidu
 */
public class PHPCustomLanguageEmbeddingTest extends CslTestBase {

    public static final String PHP_MIME_TEST = "text/x-php-test";

    public PHPCustomLanguageEmbeddingTest(String testName) {
        super(testName);
        TestLanguageProvider.register(getPreferredLanguage().getLexerLanguage());
        TestLanguageProvider.register(new PHPLanguage().getLexerLanguage());
    }

    @LanguageRegistration(
            mimeType = PHP_MIME_TEST,
            useMultiview = true
    )
    public static class TestLanguage extends DefaultLanguageConfig {

        @Override
        public org.netbeans.api.lexer.Language getLexerLanguage() {
            return new Lang(PHP_MIME_TEST).language();
        }

        @Override
        public String getDisplayName() {
            return "test php language";
        }

    }

    public static enum PhpTextTokenId implements TokenId {

        ANY(null, null),
        PHP(null, null);

        private final String fixedText;

        private final String primaryCategory;

        PhpTextTokenId(String fixedText, String primaryCategory) {
            this.fixedText = fixedText;
            this.primaryCategory = primaryCategory;
        }

        @Override
        public String primaryCategory() {
            return primaryCategory;
        }

        public String fixedText() {
            return fixedText;
        }
    } // End of TestTokenId

    private static final class Lang extends LanguageHierarchy<PhpTextTokenId> {

        private final String mimeType;

        public Lang(String mimeType) {
            this.mimeType = mimeType;
        }

        @Override
        protected Lexer<PhpTextTokenId> createLexer(LexerRestartInfo<PhpTextTokenId> info) {
            return new PhpCustomLanguageLexer(info);
        }

        @Override
        protected Collection<PhpTextTokenId> createTokenIds() {
            return EnumSet.allOf(PhpTextTokenId.class);
        }

        @Override
        public String mimeType() {
            return mimeType;
        }
        
        @Override
        protected LanguageEmbedding<?> embedding(Token<PhpTextTokenId> token,
                LanguagePath languagePath, InputAttributes inputAttributes) {
            if (token.id().equals(PhpTextTokenId.PHP)) {
                return LanguageEmbedding.create(PHPTokenId.languageInPHP(), 0, 0);
            }

            return null; // No embedding
        }
    }

    @ServiceProvider(service = MIMEResolver.class)
    public static class PhpTestFileResolver extends MIMEResolver {

        public static final String FILE_EXT = "php.test"; //NOI18N

        public PhpTestFileResolver() {
            super(PHP_MIME_TEST);
        }

        @CheckForNull
        @Override
        public String findMIMEType(@NonNull final FileObject fo) {
            if (fo.getNameExt().endsWith(FILE_EXT)) {
                return PHP_MIME_TEST;
            }

            return null;
        }
    }
    
    @Override
    protected DefaultLanguageConfig getPreferredLanguage() {
        return new TestLanguage();
    }

    @Override
    protected String getPreferredMimeType() {
        return PHP_MIME_TEST;
    }
}
