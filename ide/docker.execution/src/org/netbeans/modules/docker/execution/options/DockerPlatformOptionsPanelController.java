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
package org.netbeans.modules.docker.execution.options;

import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

@OptionsPanelController.TopLevelRegistration(
        id = "Docker",
        categoryName = "#OptionsCategory_Name_Docker",
        iconBase = "org/netbeans/modules/docker/execution/resources/docker_logo.png", 
        keywords = "#OptionsCategory_Keywords_Docker", 
        keywordsCategory = "Docker",
        position = 10001
)
@NbBundle.Messages(value = {"OptionsCategory_Name_Docker=Docker", "OptionsCategory_Keywords_Docker=docker container"})
public class DockerPlatformOptionsPanelController extends OptionsPanelController {

    private DockerPlatformPanel component;
    //private Collection<? extends FontsColorsController> delegates;
    
    @Override
    public void update() {
    }

    @Override
    public void applyChanges() {
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return false;
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getDockerPlatformPanel();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("netbeans.optionsDialog.dockerPlatformPanel");
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
    }

    private synchronized DockerPlatformPanel getDockerPlatformPanel() {
        if (component == null && SwingUtilities.isEventDispatchThread()) {
            //assert !delegates.isEmpty() : "Font and Colors Panel is empty."; //NOI18N
            component = new DockerPlatformPanel();
        }
        return component;
    }
    
}
