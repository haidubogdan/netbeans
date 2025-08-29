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
package org.netbeans.modules.docker.execution.project.ui;

import java.text.Collator;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.modules.docker.execution.project.ConfigManager;

public class DockerConfigComboBoxModel extends DefaultComboBoxModel<String> {

    private final ConfigManager manager;

    public DockerConfigComboBoxModel(ConfigManager manager) {
        this.manager = manager;
        Set<String> alphaConfigs = new TreeSet<>(getComparator());
        alphaConfigs.addAll(manager.configurationNames());
        for (String config : manager.configurationNames()) {
            this.addElement(config);
        }
    }

    private Comparator<String> getComparator() {
        return new Comparator<String>() {
            Collator coll = Collator.getInstance();

            @Override
            public int compare(String s1, String s2) {
                String lbl1 = manager.configurationFor(s1).getDisplayName();
                String lbl2 = manager.configurationFor(s2).getDisplayName();
                return coll.compare(lbl1, lbl2);
            }
        };
    }

}
