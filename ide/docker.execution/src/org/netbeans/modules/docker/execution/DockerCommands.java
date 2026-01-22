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
import org.netbeans.modules.docker.execution.project.DockerExecConfiguration;

public final class DockerCommands {

    public static final String DOCKER_BASE_COMMAND = "docker"; // NOI18N
    public static final String DOCKER_EXEC = "exec"; // NOI18N

    public static final String DOCKER_COMMAND_OPTION = "c"; // NOI18N
    public static final String DOCKER_INTERACTIVE_OPTION = "i"; // NOI18N
    public static final String DOCKER_TTY_OPTION = "t"; // NOI18N
    public static final String DOCKER_WORKDIR_OPTION = "w"; // NOI18N
    public static final String DOCKER_USER_OPTION = "u"; // NOI18N

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
