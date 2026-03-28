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

import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.DockerExecModel;
import static org.netbeans.modules.docker.execution.ProjectUtils.NB_PHP_PROJECT_TYPE;
import static org.netbeans.modules.docker.execution.ProjectUtils.NB_WEB_PROJECT_TYPE;
import static org.netbeans.modules.docker.execution.project.DockerProjectModulePreferences.DEFAULT_CONFIG_NAME;
import org.netbeans.spi.project.LookupProvider;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.openide.filesystems.FileObject;

public class DockerProjectSettings {

    private final Project project;
    public static final String DOCKER_CONFIG_FOLDER = "nbproject/docker_configs"; // NOI18N

    public DockerProjectSettings(Project project) {
        this.project = project;
    }

    public static synchronized DockerProjectSettings projectLookup(Project project) {
        DockerProjectSettings dockerSettings = project.getLookup().lookup(DockerProjectSettings.class);
        assert dockerSettings != null : "DockerSettings should be found in project " + project.getClass().getName() + " (lookup: " + project.getLookup() + ")";
        return dockerSettings;
    }

    //SHOULD THIS BE DONE ON INIT?
    public Set<String> getProfiles() {
        Set<String> result = new HashSet<>();
        FileObject projectDir = project.getProjectDirectory();
        FileObject dockerConfigFolder = projectDir.getFileObject(DOCKER_CONFIG_FOLDER);

        result.add(DEFAULT_CONFIG_NAME);

        if (dockerConfigFolder != null && dockerConfigFolder.isFolder()) {
            for (FileObject configFile : dockerConfigFolder.getChildren()) {
                if (configFile.hasExt("properties")) { // NOI18N
                    result.add(configFile.getName());
                }
            }
        }

        return result;
    }

    @ProjectServiceProvider(service = DockerProjectSettings.class, projectTypes = {
        @LookupProvider.Registration.ProjectType(id = NB_PHP_PROJECT_TYPE), // NOI18N
        @LookupProvider.Registration.ProjectType(id = NB_WEB_PROJECT_TYPE), // NOI18N
    }) // NOI18N
    public static DockerProjectSettings create(Project project) {
        DockerProjectSettings settings = new DockerProjectSettings(project);

        return settings;
    }

    public synchronized DockerExecModel getDockerExecModel() {
        return new DockerExecModel(project, getProfiles());
    }

    public boolean useDockerForNpm() {
        return getDockerExecModel().getNpmEnabled();
    }
}
