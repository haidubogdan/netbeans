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
package org.netbeans.modules.docker.execution.project;

import java.text.Collator;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.netbeans.modules.docker.execution.project.ConfigManager.Configuration;

public class ConfigManagerUtils {

    public static Map<String, Map<String, String>> createEmptyConfigs() {
        Map<String, Map<String, String>> configs = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1 != null ? (s2 != null ? s1.compareTo(s2) : 1) : (s2 != null ? -1 : 0);
            }
        });
        // the default config has to be there even if it is not used
        configs.put(null, null);
        return configs;
    }

    public static Comparator<Configuration> getConfigurationComparator() {
        return new Comparator<Configuration>() {
            Collator coll = Collator.getInstance();

            @Override
            public int compare(Configuration c1, Configuration c2) {
                String lbl1 = c1.getDisplayName();
                String lbl2 = c2.getDisplayName();
                return coll.compare(lbl1, lbl2);
            }
        };
    }
}
