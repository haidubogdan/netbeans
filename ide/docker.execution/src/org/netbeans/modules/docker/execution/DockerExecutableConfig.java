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
package org.netbeans.modules.docker.execution;

import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.project.DockerProjectModulePreferences;

public class DockerExecutableConfig {

    private final String containerName;
    private final String bashType;

    private boolean interactive = true;
    private boolean asTerminal = true;

    private final String user;
    private String containerWorkDir;

    private final boolean isValid;
    
    public static enum Type { NPM_NODE, GENERIC };

    public static DockerExecutableConfig forProject(Project project, Preferences preferences) {
        DockerProjectModulePreferences dockerPreferences = new DockerProjectModulePreferences(project, preferences);
        DockerExecutableConfig dockerConfig = new DockerExecutableConfig(dockerPreferences);
        
        return dockerConfig;
    }

    public DockerExecutableConfig(DockerProjectModulePreferences dockerPreferences) {
        this.containerName = dockerPreferences.getDockerContainerName();
        this.isValid = containerName != null && !containerName.isEmpty();
        this.bashType = dockerPreferences.getDockerExecBashPath();
        this.asTerminal = dockerPreferences.getDockerPseudoTerminal();
        this.interactive = dockerPreferences.getDockerInteractive();
        this.user = dockerPreferences.getDockerUser();
        this.containerWorkDir = dockerPreferences.getDockerWorkdir();
    }

    public String getDockerContainerName() {
        return containerName;
    }

    public String getBashType() {
        return bashType;
    }

    public void setDockerWorkDir(String workDir) {
        this.containerWorkDir = workDir;
    }


    public boolean getDockerPseudoTerminal() {
        return asTerminal;
    }

    public boolean getDockerInteractive() {
        return interactive;
    }
    
    public String getDockerWorkDir() {
        return containerWorkDir;
    }
    
    public String getDockerUser() {
        return user;
    }
    
    public boolean isValid() {
        return isValid;
    }
}
