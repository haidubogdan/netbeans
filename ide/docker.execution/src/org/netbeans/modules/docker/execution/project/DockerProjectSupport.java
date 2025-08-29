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

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.netbeans.spi.project.ui.ProjectOpenedHook;

/**
 *
 * @author bhaidu
 */
public class DockerProjectSupport extends ProjectOpenedHook {

    private final Project project;
    private final DockerProjectPreferences preferences;

    private DockerProjectSupport(Project project) {
        assert project != null;
        this.project = project;
        preferences = new DockerProjectPreferences(project);
    }

    @ProjectServiceProvider(service = ProjectOpenedHook.class, projectType = "org-netbeans-modules-web-project")
    public static DockerProjectSupport forWebProject(Project project) {
        return create(project);
    }

    private static DockerProjectSupport create(Project project) {
        return new DockerProjectSupport(project);
    }
    
    public static DockerProjectSupport forProject(Project project) {
        DockerProjectSupport support = project.getLookup().lookup(DockerProjectSupport.class);
        assert support != null : "DockerProjectSupport should be found in project " + project.getClass().getName() + " (lookup: " + project.getLookup() + ")";
        return support;
    }
    
    public DockerProjectPreferences getPreferences() {
        return preferences;
    }

    @Override
    protected void projectOpened() {
    }

    @Override
    protected void projectClosed() {
    }

}
