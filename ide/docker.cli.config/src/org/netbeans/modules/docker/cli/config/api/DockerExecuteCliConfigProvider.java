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
package org.netbeans.modules.docker.cli.config.api;

import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.cli.config.DockerExecParamsConfig;
import org.netbeans.modules.docker.cli.config.DockerExecCommandParams;
import org.netbeans.modules.docker.cli.config.DockerUtils;
import org.openide.util.lookup.Lookups;

public abstract class DockerExecuteCliConfigProvider {

    public static final String JS_DOCKER_PATH = "docker_exec/commands/javascript"; // NOI18N

    public static DockerExecuteCliConfigProvider getProvider(String path) {
        return Lookups.forPath(path).lookup(DockerExecuteCliConfigProvider.class);
    }

    public DockerExecParamsConfig findDockerExecuteCommandConfiguration(Project project) {
        return findProjectConfiguration(project);
    }

    public List<String> generateExecutableParams(Project project) {
        DockerExecParamsConfig dockerExecConfig = findDockerExecuteCommandConfiguration(project);
        return DockerExecCommandParams.generateExecutableParams(dockerExecConfig);
    }

    public static String getDockerExecutablePath() {
        return DockerUtils.getDockerExecutablePath();
    }
    
    public abstract boolean isEnabled(Project project);
    public abstract DockerExecParamsConfig findProjectConfiguration(Project project);
}
