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

import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.project.ConfigManager;
import org.netbeans.modules.docker.execution.project.DockerProjectPreferences;
import static org.netbeans.modules.docker.execution.project.DockerServiceProjectProperties.*;

/**
 *
 * @author bhaidu
 */
public class DockerExecutableConfig {

    private final String containerName;
    private final String bashType;

    private boolean interactive = true;
    private boolean asTerminal = true;

    private final String user;
    private String containerWorkDir;

    public static DockerExecutableConfig forProject(Project project) {
        DockerProjectPreferences dockerPreferences = new DockerProjectPreferences(project);
        DockerExecutableConfig dockerConfig = new DockerExecutableConfig(dockerPreferences);

        return dockerConfig;
    }

    public DockerExecutableConfig(DockerProjectPreferences dockerPreferences) {
        this.containerName = dockerPreferences.getDockerContainerName();
        assert containerName != null && !containerName.isEmpty();
        this.bashType = dockerPreferences.getDockerExecBashPath();
        this.asTerminal = dockerPreferences.getDockerPseudoTerminal();
        this.interactive = dockerPreferences.getDockerInteractive();
        this.user = dockerPreferences.getDockerUser();
        this.containerWorkDir = dockerPreferences.getDockerWorkdir();
    }

    public DockerExecutableConfig(ConfigManager.Configuration configuration) {
        this.containerName = configuration.getValue(DOCKER_CONTAINER_NAME);
        assert containerName != null && !containerName.isEmpty();
        this.bashType = configuration.getValue(DOCKER_BASH_PATH);
        this.asTerminal = Boolean.parseBoolean(configuration.getValue(DOCKER_USE_TTY));
        this.interactive = Boolean.parseBoolean(configuration.getValue(DOCKER_USE_INTERACTIVE));
        this.user = configuration.getValue(DOCKER_USER);
        this.containerWorkDir = configuration.getValue(DOCKER_WORKDIR);
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
}
