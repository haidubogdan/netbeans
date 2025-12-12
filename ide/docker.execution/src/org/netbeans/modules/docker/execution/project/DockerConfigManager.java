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

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeListener;
import org.netbeans.api.project.Project;
import static org.netbeans.modules.docker.execution.project.DockerProjectPreferences.DEFAULT_CONFIG_NAME;
import org.netbeans.spi.project.support.ant.EditableProperties;

/**
 *
 * @author bhaidu
 */
public class DockerConfigManager {

    public static final String DOCKER_CONTAINER_NAME = "docker_container_name"; // NOI18N
    public static final String DOCKER_BASH_PATH = "docker_bash_path"; // NOI18N

    public static final String DOCKER_USE_TTY = "docker_use_tty"; // NOI18N
    public static final String DOCKER_USE_INTERACTIVE = "docker_use_interactive"; // NOI18N

    public static final String DOCKER_USER = "docker_user"; // NOI18N
    public static final String DOCKER_WORKDIR = "docker_workdir"; // NOI18N

    public static final boolean DEFAULT_DOCKER_TTY = true;
    public static final boolean DEFAULT_DOCKER_INTERACTIVE = true;

    public static final String DEFAULT_CONFIG_FOLDER = "nbproject";
    public static final String DOCKER_CONFIG_FOLDER = "nbproject/docker_configs";

    private static final String[] CFG_PROPS = new String[]{
        DOCKER_CONTAINER_NAME,
        DOCKER_BASH_PATH,
        DOCKER_WORKDIR,
        DOCKER_USE_TTY,
        DOCKER_USER,
        DOCKER_USE_INTERACTIVE,};

    public static Map<String, String> readConfigProfile(String profile, Project project) {
        Map<String, String> config = new HashMap<>();

        String path = DEFAULT_CONFIG_FOLDER + "/project.properties";
        if (!profile.equals(DEFAULT_CONFIG_NAME)) {
            
        }

        EditableProperties ep = ProjectHelper.getProperties(project, path);

        for (String propertyName : CFG_PROPS) {
            String value = ep.getProperty(propertyName);
            int x= 1;
        }
        
        return config;
    }
}
