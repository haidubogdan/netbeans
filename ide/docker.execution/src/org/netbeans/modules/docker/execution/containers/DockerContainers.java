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
package org.netbeans.modules.docker.execution.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.docker.execution.containers.ui.DockerContainerConfigsPanel;
import org.openide.util.ChangeSupport;

public final class DockerContainers {

    private static final DockerContainers INSTANCE = new DockerContainers();
    private final ChangeSupport changeSupport;

    public static DockerContainers get() {
        return INSTANCE;
    }
    
    private DockerContainers() {
        changeSupport = new ChangeSupport(this);
    }

    private DockerContainerConfigsPanel createPanel() {
        DockerContainerConfigsPanel panel = new DockerContainerConfigsPanel();
        //panel.setConfigurations(getConfigurations());
        return panel;
    }

    public boolean openManager() {
        DockerContainerConfigsPanel panel = createPanel();
        // original remote configurations
        final List<String> remoteConfigurations = getRemoteContainers();

        final boolean changed = panel.open();

        if (changed) {
            //saveRemoteConnections(remoteConfigurations);
        }
        // reset & reread config provider & manager (configs are kept in memory)
//        configProvider.resetConfigs();
//        configManager.reset();

        if (changed) {
            changeSupport.fireChange();
        }

        return changed;
    }
    
    public List<String> getRemoteContainers() {
        List<String> containers = new ArrayList<>();
        return containers;
    }

    public void saveDockerContainers() {
        Preferences preferences = DockerContainerPreferences.getDockerContainerPreferences();
        
        Preferences node = preferences.node("test");
    }
    
    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }
}
