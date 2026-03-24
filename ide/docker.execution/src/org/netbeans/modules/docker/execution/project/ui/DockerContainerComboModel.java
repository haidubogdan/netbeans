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

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.modules.docker.execution.containers.DockerContainerConfig;

/**
 *
 * @author bhaidu
 */
public class DockerContainerComboModel extends DefaultComboBoxModel<String> {

    private static final long serialVersionUID = -546879865427974L;

    private final List<String> data = new ArrayList<>();

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index);
    }

    public void setElements(List<String> configs) {
        int size = data.size();
        data.clear();
        if (size > 0) {
            fireIntervalRemoved(this, 0, size - 1);
        }
        if (configs.size() > 0) {
            data.addAll(configs);
            //data.sort(ConfigManager.getConfigurationComparator());
            fireIntervalAdded(this, 0, data.size() - 1);
        }
    }
}
