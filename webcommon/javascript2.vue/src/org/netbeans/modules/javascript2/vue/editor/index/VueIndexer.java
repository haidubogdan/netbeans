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
package org.netbeans.modules.javascript2.vue.editor.index;

import java.io.IOException;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.html.editor.api.gsf.HtmlExtension;
import org.netbeans.modules.javascript2.model.api.Model;
import org.netbeans.modules.javascript2.vue.editor.VueLanguage;
import static org.netbeans.modules.javascript2.vue.editor.VueLanguage.MIME_TYPE;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;

/**
 *
 * @author bhaidu
 */
public class VueIndexer extends EmbeddingIndexer {

    @Override
    protected void index(Indexable indexable, Parser.Result parserResult, Context context) {
        String parserName = parserResult.getClass().getName();
        
        if (!(parserResult instanceof ParserResult)) {
            return;
        }
        
        if (parserName.endsWith("JsParserResult")) {
            Model model = Model.getModel((ParserResult) parserResult, false);
            return;
        }
        int x =1;
    }

    @MimeRegistrations({
        @MimeRegistration(mimeType = VueLanguage.MIME_TYPE, service = EmbeddingIndexerFactory.class, position = 501),
        @MimeRegistration(mimeType = "text/html", service = EmbeddingIndexerFactory.class, position = 502)}
    )
    public static final class Factory extends EmbeddingIndexerFactory {

        public static final String NAME = "vuejs"; // NOI18N
        public static final int VERSION = 1;
        private static final int PRIORITY = 221;

        @Override
        public EmbeddingIndexer createIndexer(Indexable indexable, Snapshot snapshot) {
            if (isIndexable(indexable, snapshot)) {
                return new VueIndexer();
            } else {
                return null;
            }
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> deleted, Context context) {
            try {
                IndexingSupport is = IndexingSupport.getInstance(context);
                for (Indexable i : deleted) {
                    is.removeDocuments(i);
                }
            } catch (IOException ioe) {
            }
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> dirty, Context context) {

        }

        @Override
        public String getIndexerName() {
            return NAME;
        }

        @Override
        public int getIndexVersion() {
            return VERSION;
        }

        private boolean isIndexable(Indexable indexable, Snapshot snapshot) {
            return snapshot.getSource().getFileObject().getExt().equals("vue");
        }

        @Override
        public int getPriority() {
            return PRIORITY;
        }
    }
}
