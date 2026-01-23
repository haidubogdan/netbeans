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

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import static org.netbeans.modules.docker.execution.DockerCommands.DOCKER_BASE_COMMAND;
import static org.netbeans.modules.docker.execution.DockerCommands.DOCKER_EXEC;
import static org.netbeans.modules.docker.execution.DockerCommands.DOCKER_INTERACTIVE_OPTION;
import static org.netbeans.modules.docker.execution.DockerCommands.DOCKER_USER_OPTION;
import static org.netbeans.modules.docker.execution.DockerCommands.DOCKER_WORKDIR_OPTION;
import org.netbeans.modules.docker.execution.project.DockerExecConfiguration;
import static org.netbeans.modules.docker.execution.project.DockerProjectPreferences.DEFAULT_CONFIG_NAME;
import org.netbeans.modules.web.common.ui.api.ExternalExecutable;

/**
 *
 * @author bhaidu
 */
public class DockerExecutableBuilder {

    private final DockerExecModel dockerExecModel;
    
    public DockerExecutableBuilder(Project project) {
        this.dockerExecModel = new DockerExecModel(project);
    }
    //todo add profile
    public ExternalExecutable buildExternalExec(String command) {
        String profile = DEFAULT_CONFIG_NAME;
        DockerExecConfiguration config = dockerExecModel.getConfiguration(profile);
        ExternalExecutable exec = new ExternalExecutable("/usr/bin/docker", buildExecCommandParams(config));
        List<String> params = new ArrayList<>();
        //splitter
        params.add(command);
        exec.additionalParameters(params);
        
        return exec;
    }
    
    public static List<String> buildExecCommandParams(DockerExecConfiguration config) {
        List<String> params = new ArrayList<>();

        params.add(DOCKER_EXEC);
        
        if (config.getDockerWorkDir() != null && !config.getDockerWorkDir().trim().isEmpty()) {
            params.add("-" + DOCKER_WORKDIR_OPTION); // NOI18N
            params.add(config.getDockerWorkDir());
        }

        if (config.getInteractive()) {
            params.add("-" + DOCKER_INTERACTIVE_OPTION); // NOI18N
        }

        //as terminal ??
        if (config.getDockerUser() != null) {
            params.add("-" + DOCKER_USER_OPTION); // NOI18N
            params.add(config.getDockerUser());
        }

        params.add(config.getContainerName());

        if (config.getBashType() != null) {
            params.add(config.getBashType());
        }

        return params;
    }
}
