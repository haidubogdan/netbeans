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
package org.netbeans.modules.javascript2.vue.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.html.editor.api.gsf.HtmlParserResult;
import org.netbeans.modules.html.editor.lib.api.elements.Attribute;
import org.netbeans.modules.html.editor.lib.api.elements.AttributeFilter;
import org.netbeans.modules.html.editor.lib.api.elements.Element;
import org.netbeans.modules.html.editor.lib.api.elements.OpenTag;
import org.netbeans.modules.parsing.api.Snapshot;

/**
 *
 * @author bogdan
 */
public class VueModel {

    private static final Map<HtmlParserResult, VueModel> INSTANCES
            = new WeakHashMap<>();

    /**
     * Gets cached angular model for the given parser result.
     *
     * @param result
     */
    @NonNull
    public static synchronized VueModel getModel(HtmlParserResult result) {
        VueModel model = INSTANCES.get(result);
        if (model == null) {
            model = new VueModel(result);
            INSTANCES.put(result, model);
        }
        return model;
    }

    /**
     * Maps html elements to ng attributes.
     */
    private Map<OpenTag, Collection<Attribute>> elements2attributes = new HashMap<>();

    /**
     * All ng attributes.
     *
     * XXX maybe use attribute -> container element map.
     */
    private Collection<Attribute> attributes = new ArrayList<>();

    private VueModel(final HtmlParserResult result) {
        Iterator<Element> elementsIterator = result.getSyntaxAnalyzerResult().getElementsIterator();
        while (elementsIterator.hasNext()) {
            Element element = elementsIterator.next();
            switch (element.type()) {
                case OPEN_TAG:
                    OpenTag ot = (OpenTag) element;
                    for (Attribute ngAttr : ot.attributes(new AttributeFilter() {
                        @Override
                        public boolean accepts(Attribute attribute) {
                            //the data-bind attribute can contain custom directives which we do not have any metadata for
                            //so the data-bind attribute is always considered as a knockout regardless the content, 
                            //at least until we have some custom directives metadata facility.
                            return false;
                        }
                    })) {
                        Collection<Attribute> attrs = elements2attributes.get(ot);
                        if (attrs == null) {
                            attrs = new ArrayList<>();
                            elements2attributes.put(ot, attrs);
                        }
                        attrs.add(ngAttr);
                        attributes.add(ngAttr);
                    }
            }
        }
    }

    /**
     * Gets a list of all angular attributes in the page.
     */
    @NonNull
    public Collection<Attribute> getBindings() {
        return attributes;
    }

    /**
     * Checks whether the parser result contains any knockout code.
     *
     * TODO - check for the ko.applyBindings() presence instead?
     */
    public boolean containsKnockout() {
        return !getBindings().isEmpty();
    }
}
