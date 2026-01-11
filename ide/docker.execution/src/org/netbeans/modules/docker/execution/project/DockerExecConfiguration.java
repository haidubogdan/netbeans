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

import java.util.Map;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_BASH_PATH;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_CONTAINER_NAME;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_USER;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_USE_INTERACTIVE;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_USE_TTY;
import static org.netbeans.modules.docker.execution.project.DockerConfigManager.DOCKER_WORKDIR;

/**
 *
 * @author bhaidu
 */
public class DockerExecConfiguration {
    private final String containerName;
    private final String bashType;

    private final boolean interactive;
    private final boolean asTerminal;

    private final String user;
    private final String containerWorkDir;
    
    public DockerExecConfiguration(String containerName, String bashType, 
            boolean interactive, boolean asTerminal, String user, String containerWorkDir) {
        this.containerName = containerName;
        this.bashType = bashType;
        this.interactive = interactive;
        this.asTerminal = asTerminal;
        this.user = user;
        this.containerWorkDir = containerWorkDir;
    }

    public DockerExecConfiguration(Map<String, String> properties) {
        this.containerName = properties.get(DOCKER_CONTAINER_NAME);
        this.bashType = properties.get(DOCKER_BASH_PATH);
        this.interactive = Boolean.parseBoolean(properties.get(DOCKER_USE_INTERACTIVE));
        this.asTerminal = Boolean.parseBoolean(properties.get(DOCKER_USE_TTY));
        this.user = properties.get(DOCKER_USER);
        this.containerWorkDir = properties.get(DOCKER_WORKDIR);
    }

    public String getContainerName() {
        return containerName;
    }

    public String getBashType() {
        return bashType;
    }

    public boolean getAsTerminal() {
        return asTerminal;
    }

    public boolean getInteractive() {
        return interactive;
    }
    
    public String getDockerWorkDir() {
        return containerWorkDir;
    }
    
    public String getDockerUser() {
        return user;
    }
}
