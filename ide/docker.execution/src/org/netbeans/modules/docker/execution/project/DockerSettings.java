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

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import static org.netbeans.modules.docker.execution.ProjectUtils.NB_PHP_PROJECT_TYPE;
import static org.netbeans.modules.docker.execution.ProjectUtils.NB_WEB_PROJECT_TYPE;
import static org.netbeans.modules.docker.execution.project.DockerProjectPreferences.DEFAULT_CONFIG_NAME;
import static org.netbeans.modules.docker.execution.project.DockerServiceProjectProperties.DOCKER_CONFIG_FOLDER;
import org.netbeans.spi.project.LookupProvider;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author bhaidu
 */
public class DockerSettings {

    private final Project project;
    public static final String DOCKER_CONFIG_FOLDER = "nbproject/docker_configs"; // NOI18N
    
    private DockerProjectPreferences dockerProjectPreferences;
    
    public DockerSettings(Project project) {
        this.project = project;
        this.dockerProjectPreferences = new DockerProjectPreferences(project, null);
    }
    
    public static synchronized DockerSettings projectLookup(Project project) {
        DockerSettings dockerSettings = project.getLookup().lookup(DockerSettings.class);
        assert dockerSettings != null : "DockerSettings should be found in project " + project.getClass().getName() + " (lookup: " + project.getLookup() + ")";
        return dockerSettings;
    }
    
    public Set<String> getProfiles() {
        Set<String> result = new HashSet<>();
        FileObject projectDir = project.getProjectDirectory();
        FileObject dockerConfigFolder = projectDir.getFileObject(DOCKER_CONFIG_FOLDER);
        
        result.add(DEFAULT_CONFIG_NAME);

        if (dockerConfigFolder != null && dockerConfigFolder.isFolder()) {
            
        }
        
        return result;
    }
    
    public String getCurrentProfile() {
        return dockerProjectPreferences.getDockerConfigName();
    }

    @ProjectServiceProvider(service = DockerSettings.class,projectTypes = {
        @LookupProvider.Registration.ProjectType(id = NB_PHP_PROJECT_TYPE), // NOI18N
        @LookupProvider.Registration.ProjectType(id = NB_WEB_PROJECT_TYPE), // NOI18N
    }) // NOI18N
    public static DockerSettings create(Project project) {
        DockerSettings settings = new DockerSettings(project);
 
        return settings;
    }

}
