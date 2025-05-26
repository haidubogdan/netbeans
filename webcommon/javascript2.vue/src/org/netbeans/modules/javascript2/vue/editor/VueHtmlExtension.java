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

import org.netbeans.api.editor.mimelookup.MimeRegistration;

import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.modules.html.editor.api.gsf.HtmlExtension;
import org.netbeans.modules.html.editor.api.gsf.HtmlParserResult;
import org.netbeans.modules.html.editor.lib.api.HtmlSource;
import org.netbeans.modules.html.editor.lib.api.elements.Attribute;

/**
 *
 * @author bogdan
 */
@MimeRegistrations({
    @MimeRegistration(mimeType = "text/x-vue", service = HtmlExtension.class),})
public class VueHtmlExtension extends HtmlExtension {

    @Override
    public boolean isApplicationPiece(HtmlParserResult result) {
        return true;
    }

    @Override
    public boolean isCustomAttribute(Attribute attribute, HtmlSource source) {
        String attributeName = attribute.unqualifiedName().toString();
        return VueUtils.isVueDirective(attributeName);
    }
}
