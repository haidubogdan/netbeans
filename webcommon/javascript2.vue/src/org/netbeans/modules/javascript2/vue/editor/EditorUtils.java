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

import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.javascript2.lexer.api.JsTokenId;
import org.netbeans.modules.javascript2.lexer.api.LexUtilities;
import org.netbeans.modules.parsing.api.Snapshot;

/**
 *
 * @author bhaidu
 */
public class EditorUtils {
public static enum CodeCompletionContext {

        /**
         * in configuration object after baseUrl: ''
         */
        CONFIG_BASE_URL_VALUE,
        /**
         * Define file path in paths object in the configuration object
         */
        CONFIG_PATHS_VALUE,
        /**
         * name of properties in the configuration object
         */
        CONFIG_PROPERTY_NAME,
        /**
         * names and paths of modules in require, requirejs, define etc.
         */
        REQUIRE_MODULE,
        /**
         * other
         */
        UNKNOWN
    };

    public static boolean isFileReference(Snapshot snapshot, int offset) {
        CodeCompletionContext context = findContext(snapshot, offset);
        return false;
    }
    
    public static CodeCompletionContext findContext(final Snapshot snapshot, final int offset) {
        TokenSequence<? extends JsTokenId> ts = LexUtilities.getJsTokenSequence(snapshot, offset);
        if (ts == null) {
            return CodeCompletionContext.UNKNOWN;
        }
        
        return CodeCompletionContext.UNKNOWN;
    }
    
}
