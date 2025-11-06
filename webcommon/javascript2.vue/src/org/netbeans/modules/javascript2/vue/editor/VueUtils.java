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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bogdan
 */
public class VueUtils {

    static final Set<String> EVENT_HANDLER_NAMES = new HashSet<>();

    static {
        // See http://www.w3.org/TR/html401/interact/scripts.html
        EVENT_HANDLER_NAMES.add("load"); // NOI18N
        EVENT_HANDLER_NAMES.add("unload"); // NOI18N
        EVENT_HANDLER_NAMES.add("click"); // NOI18N
        EVENT_HANDLER_NAMES.add("dblclick"); // NOI18N
        EVENT_HANDLER_NAMES.add("mousedown"); // NOI18N
        EVENT_HANDLER_NAMES.add("mouseup"); // NOI18N
        EVENT_HANDLER_NAMES.add("mouseover"); // NOI18N
        EVENT_HANDLER_NAMES.add("mousemove"); // NOI18N
        EVENT_HANDLER_NAMES.add("mouseout"); // NOI18N
        EVENT_HANDLER_NAMES.add("focus"); // NOI18N
        EVENT_HANDLER_NAMES.add("blur"); // NOI18N
        EVENT_HANDLER_NAMES.add("keypress"); // NOI18N
        EVENT_HANDLER_NAMES.add("keydown"); // NOI18N
        EVENT_HANDLER_NAMES.add("keyup"); // NOI18N
        EVENT_HANDLER_NAMES.add("submit"); // NOI18N
        EVENT_HANDLER_NAMES.add("reset"); // NOI18N
        EVENT_HANDLER_NAMES.add("select"); // NOI18N
        EVENT_HANDLER_NAMES.add("change"); // NOI18N
        EVENT_HANDLER_NAMES.add("drag"); // NOI18N
        EVENT_HANDLER_NAMES.add("drop"); // NOI18N
        EVENT_HANDLER_NAMES.add("input"); // NOI18N
        EVENT_HANDLER_NAMES.add("update"); // NOI18N
    }

    public static boolean isVueDirective(String attributeName) {
        return attributeName.startsWith("v-") //NOI18N
                || attributeName.startsWith(":") //NOI18N
                || attributeName.startsWith("[") //NOI18N
                || attributeName.contains(".") //NOI18N
                || EVENT_HANDLER_NAMES.contains(attributeName);
    }
}
