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
package org.netbeans.modules.docker.command;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.command.api.DockerExecuteCommandProvider;
import org.netbeans.modules.docker.command.configurations.DockerExecConfiguration;
import org.openide.util.lookup.Lookups;

public class DockerExecuteCommand {

    public static final String DOCKER_EXEC = "exec"; // NOI18N

    public static final String DOCKER_COMMAND_OPTION = "-c"; // NOI18N
    public static final String DOCKER_INTERACTIVE_OPTION = "-i"; // NOI18N
    public static final String DOCKER_TTY_OPTION = "-t"; // NOI18N
    public static final String DOCKER_WORKDIR_OPTION = "-w"; // NOI18N
    public static final String DOCKER_USER_OPTION = "-u"; // NOI18N
    
    public static final String SH_COMMAND = "sh"; // NOI18N

    public static enum AppType {
        JAVASCRIPT,
        PHP
    };
    public static final String JS_DOCKER_PATH = "docker_exec/commands/javascript"; // NOI18N

    //TODO move to DockerProject settings
    public static DockerExecConfiguration findJSDockerExecuteCommandConfiguration(Project project) {
        DockerExecuteCommandProvider execCommandProvider = Lookups.forPath(JS_DOCKER_PATH).lookup(DockerExecuteCommandProvider.class);
        return execCommandProvider.findProjectConfiguration(project);
    }

    public static List<String> generateExecutableParams(Project project, AppType type) {
        List<String> executableParams = new ArrayList<>();

        DockerExecConfiguration config = switch (type) {
            case JAVASCRIPT ->
                findJSDockerExecuteCommandConfiguration(project);
            default ->
                null;
        };

        if (config == null) {
            return executableParams;
        }

        String dockerContainer = config.getContainerName();

        if (dockerContainer == null) {
            return executableParams;
        }

        executableParams.add(DOCKER_EXEC);
        executableParams.add(DOCKER_INTERACTIVE_OPTION);
        String dockerWorkdir = config.getDockerWorkDir();

        if (dockerWorkdir != null && dockerWorkdir.length() > 0) {
            executableParams.add(DOCKER_WORKDIR_OPTION);
            executableParams.add(dockerWorkdir);
        }

        executableParams.add(dockerContainer);
        executableParams.add(config.getBashType());
        executableParams.add(DOCKER_COMMAND_OPTION);

        return executableParams;
    }
}
