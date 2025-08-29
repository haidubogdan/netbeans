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
package org.netbeans.modules.javascript.nodejs.util;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.DockerCommands;
import org.netbeans.modules.docker.execution.project.DockerExecConfiguration;
import org.netbeans.modules.docker.execution.project.DockerProjectSettings;

public class DockerContainerUtils {

    public static boolean useDockerExecContainer(@NullAllowed Project project) {
        if (project == null) {
            return false;
        }
        DockerProjectSettings dockerSettings = project.getLookup().lookup(DockerProjectSettings.class);

        if (dockerSettings == null) {
            return false;
        }

        return dockerSettings.useDockerForJSCommands();
    }

    public static List<String> loadExecutableParams(Project project) {
        DockerProjectSettings dockerSettings = project.getLookup().lookup(DockerProjectSettings.class);
        assert dockerSettings != null;
        String currentJsProfile = dockerSettings.getJSDockerContainerProfile();
        assert currentJsProfile != null;
        DockerExecConfiguration config = dockerSettings.loadExecConfig(currentJsProfile);
        assert config != null;

        List<String> executableParams = new ArrayList<>();
        executableParams.add(DockerCommands.DOCKER_EXEC);
        executableParams.add(DockerCommands.DOCKER_INTERACTIVE_OPTION);
        String dockerWorkdir = config.getDockerWorkDir();

        if (dockerWorkdir != null && dockerWorkdir.length() > 0) {
            executableParams.add(DockerCommands.DOCKER_WORKDIR_OPTION);
            executableParams.add(dockerWorkdir);
        }

        String dockerContainer = config.getContainerName();
        assert dockerContainer != null;

        executableParams.add(dockerContainer);
        executableParams.add(config.getBashType());
        executableParams.add(DockerCommands.DOCKER_COMMAND_OPTION);

        return executableParams;
    }

    public static String getDockerExecutablePath() {
        return "/usr/bin/docker"; // NOI18N
    }
}
