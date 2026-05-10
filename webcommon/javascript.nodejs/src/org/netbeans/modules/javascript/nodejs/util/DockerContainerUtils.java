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

import java.util.List;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.command.DockerExecutable;
import org.netbeans.modules.docker.command.DockerExecuteCommand;
import org.netbeans.modules.docker.command.api.DockerExecuteCommandProvider;
import static org.netbeans.modules.docker.command.api.DockerExecuteCommandProvider.JS_DOCKER_PATH;
import org.netbeans.modules.docker.command.configurations.DockerExecConfiguration;
import org.netbeans.modules.docker.command.project.DockerProjectSettings;

public class DockerContainerUtils {

    public static boolean useDockerExecContainer(@NullAllowed Project project) {
        if (project == null) {
            return false;
        }

        DockerProjectSettings dockerSettings = new DockerProjectSettings(project);

        return dockerSettings.useDockerForJSCommands();
    }

    public static List<String> generateExecutableParams(Project project) {
        DockerExecConfiguration config = DockerExecuteCommandProvider.findDockerExecuteCommandConfiguration(project, JS_DOCKER_PATH);
        return DockerExecuteCommand.generateExecutableParams(config);
    }

    public static String getDockerExecutablePath() {
        return DockerExecutable.getDockerExecutablePath();
    }
}
