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

import javax.swing.text.BadLocationException;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.bracesmatching.api.BracesMatchingTestUtils;
import org.netbeans.modules.php.api.util.FileUtils;
import org.netbeans.modules.php.editor.PHPTestBase;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.BracesMatcherFactory;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;

/**
 *
 * @author bhaidu
 */
public class EmbeddedBraceMatchingTest extends PHPCustomLanguageEmbeddingTest {

    public EmbeddedBraceMatchingTest(String testName) {
        super(testName);
    }

    
    public void testFindMatching2() throws Exception {
        BracesMatcherFactory factory = MimeLookup.getLookup(FileUtils.PHP_MIME_TYPE).lookup(BracesMatcherFactory.class);
        
        String testText = "{{(TestClass^:)}}";
        int caretPos = testText.indexOf('^');
        testText = testText.substring(0, caretPos) + testText.substring(caretPos+1);
        BaseDocument doc = getDocument(testText);
        
        MatcherContext context = BracesMatchingTestUtils.createMatcherContext(doc, caretPos, false, 1);
        BracesMatcher matcher = factory.createMatcher(context);
        
        int [] origin = null, matches = null;
        try {
            origin = matcher.findOrigin();
            matches = matcher.findMatches();
        } catch (InterruptedException ex) {
        }
    }
}
