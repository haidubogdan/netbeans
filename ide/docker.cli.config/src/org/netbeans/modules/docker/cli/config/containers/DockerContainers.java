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
package org.netbeans.modules.docker.cli.config.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.docker.cli.config.containers.ui.DockerContainerConfigsPanel;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;

public final class DockerContainers {

    private static final DockerContainers INSTANCE = new DockerContainers();
    private final ChangeSupport changeSupport;
    private final DockerContainersModel dockerContainerModel = new DockerContainersModel();

    public static DockerContainers get() {
        return INSTANCE;
    }

    private DockerContainers() {
        changeSupport = new ChangeSupport(this);
    }

    private DockerContainerConfigsPanel createPanel() {
        DockerContainerConfigsPanel panel = new DockerContainerConfigsPanel(this);
        panel.setConfigurations(dockerContainerModel.getConfigs());
        return panel;
    }

    public boolean openManager() {
        DockerContainerConfigsPanel panel = createPanel();

        final boolean changed = panel.open();

        if (changed) {
            saveDockerContainers();
        } else {
            dockerContainerModel.reloadConfigs();
        }

        if (changed) {
            changeSupport.fireChange();
        }

        return changed;
    }

    public List<String> getDockerContainers() {
        List<String> containers = new ArrayList<>();
        Preferences dockerContainers = DockerContainerPreferences.getDockerContainerPreferences();
        try {
            containers.addAll(Arrays.asList(dockerContainers.childrenNames()));
        } catch (BackingStoreException bse) {

        }
        return containers;
    }

    public void addNewConfig(DockerContainerConfig config) {
        dockerContainerModel.addConfig(config);
    }

    public void removeConfig(DockerContainerConfig config) {
        dockerContainerModel.removeConfig(config);
    }

    public void saveDockerContainers() {
        List<DockerContainerConfig> configs = dockerContainerModel.getConfigs();
        Preferences dockerContainers = DockerContainerPreferences.getDockerContainerPreferences();

        Set<String> existingConfigNames = new HashSet<>();

        for (DockerContainerConfig config : configs) {
            try {
                String containerName = config.getContainerName();
                existingConfigNames.add(containerName);
                if (!dockerContainers.nodeExists(config.getContainerName())) {
                    Preferences node = dockerContainers.node(containerName);
                    node.put("container_name", config.getContainerName());
                }
            } catch (BackingStoreException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        try {
            if (existingConfigNames.size() != dockerContainers.childrenNames().length) {
                //sync deletion
                for (String prefContainerName : dockerContainers.childrenNames()) {
                    if (!existingConfigNames.contains(prefContainerName)) {
                        dockerContainers.node(prefContainerName).removeNode();
                    }
                }
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    public static class DockerContainerConfig {

        private final String containerName;

        public DockerContainerConfig(String containerName) {
            this.containerName = containerName;
        }

        public String getContainerName() {
            return containerName;
        }

        @Override
        public String toString() {
            return containerName;
        }
    }

}
