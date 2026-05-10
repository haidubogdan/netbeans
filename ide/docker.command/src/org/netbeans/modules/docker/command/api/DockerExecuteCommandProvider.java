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
package org.netbeans.modules.docker.command.api;

import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.command.configurations.DockerExecConfiguration;
import org.openide.util.lookup.Lookups;

public abstract class DockerExecuteCommandProvider {

    public static final String JS_DOCKER_PATH = "docker_exec/commands/javascript"; // NOI18N

    public abstract DockerExecConfiguration findProjectConfiguration(Project project);

    public static DockerExecConfiguration findDockerExecuteCommandConfiguration(Project project, String path) {
        DockerExecuteCommandProvider execCommandProvider = Lookups.forPath(path).lookup(DockerExecuteCommandProvider.class);
        return execCommandProvider.findProjectConfiguration(project);
    }
}
