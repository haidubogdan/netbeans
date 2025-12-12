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

public class DockerConfigComboBoxModel1 extends DefaultComboBoxModel<String> {

    public DockerConfigComboBoxModel1(String[] items) {
        super(items);
    }
    
    public static DockerConfigComboBoxModel1 build(Set<String> profiles) {
        Set<String> alphaConfigs = new TreeSet<>(getComparator());
        alphaConfigs.addAll(profiles);
        
        String[] items = new String[alphaConfigs.size()];
        alphaConfigs.toArray(items);
        return new DockerConfigComboBoxModel1(items);
    }

    private static Comparator<String> getComparator() {
        return new Comparator<String>() {
            Collator coll = Collator.getInstance();

            @Override
            public int compare(String s1, String s2) {
                return coll.compare(s1, s2);
            }
        };
    }

}
