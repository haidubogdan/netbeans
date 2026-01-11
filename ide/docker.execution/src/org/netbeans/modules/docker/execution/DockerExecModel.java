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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.project.DockerConfigManager;
import org.netbeans.modules.docker.execution.project.DockerExecConfiguration;
import org.netbeans.modules.docker.execution.project.DockerSettings;

/**
 *
 * @author bhaidu
 */
public class DockerExecModel {
    
    private final Project project;
    private final DockerSettings dockerSettings;
    
    public DockerExecModel(Project  project) {
        this.project = project;
        this.dockerSettings = DockerSettings.projectLookup(project);
    }
    
    public Set<String> getProfiles() {
        return dockerSettings.getProfiles();
    }
    
    public String getCurrentProfile() {
        return dockerSettings.getCurrentProfile();
    }
    
    public DockerExecConfiguration getConfiguration(String profile) {
        return DockerConfigManager.readConfigProfile(profile, project);
    }
}
