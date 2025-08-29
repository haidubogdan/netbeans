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

import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;

/**
 *
 * @author bhaidu
 */
public class DockerProjectPreferences {

    //TODO save multiple configs
    private static final String DOCKER_CONFIG_NAME = "docker_config_name"; // NOI18N
    private static final String DOCKER_CONTAINER_NAME = "docker_container_name"; // NOI18N
    private static final String DOCKER_BASH_PATH = "docker_bash_path"; // NOI18N
    private static final String DOCKER_WORKDIR = "docker_workdir"; // NOI18N
    private static final String DOCKER_USE_TTY = "docker_use_tty"; // NOI18N
    private static final String DOCKER_USE_INTERACTIVE = "docker_use_interactive"; // NOI18N

    private static final boolean DEFAULT_DOCKER_TTY = true;
    private static final boolean DEFAULT_DOCKER_INTERACTIVE = true;

    private final Project project;

    // @GuardedBy("this")
    private Preferences privatePreferences;

    public DockerProjectPreferences(Project project) {
        assert project != null;
        this.project = project;
    }

    public void setDockerContainerName(String name) {
        getPrivatePreferences().put(DOCKER_CONFIG_NAME, name);
    }

    public String getDockerContainerName() {
        return getPrivatePreferences().get(DOCKER_CONFIG_NAME, null);
    }

    public String getDockerExecBashPath() {
        return getPrivatePreferences().get(DOCKER_BASH_PATH, null);
    }

    private synchronized Preferences getPrivatePreferences() {
        if (privatePreferences == null) {
            privatePreferences = ProjectUtils.getPreferences(project, DockerProjectPreferences.class, true);
        }
        return privatePreferences;
    }

}
