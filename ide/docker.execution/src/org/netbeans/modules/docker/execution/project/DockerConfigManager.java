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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.DockerExecModel;
import static org.netbeans.modules.docker.execution.project.DockerProjectPreferences.DEFAULT_CONFIG_NAME;
import static org.netbeans.modules.docker.execution.project.DockerServiceProjectProperties.DOCKER_CONFIG_FOLDER;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

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
        DOCKER_USE_INTERACTIVE,
        DOCKER_USE_TTY,
        DOCKER_USER,
        DOCKER_WORKDIR,};

    public static DockerExecConfiguration readConfigProfile(String profile, Project project) {
        String path = DEFAULT_CONFIG_FOLDER + "/project.properties";

        if (!profile.equals(DEFAULT_CONFIG_NAME)) {
            path = DOCKER_CONFIG_FOLDER + "/" + profile + ".properties";
        }

        EditableProperties ep = ProjectHelper.getProperties(project, path);

        Map<String, String> configMapping = new HashMap<>();

        for (String propertyName : CFG_PROPS) {
            configMapping.put(propertyName, ep.getProperty(propertyName));
        }

        return new DockerExecConfiguration(configMapping);
    }

    public static void saveConfigProfile(DockerExecModel model,
            DockerExecConfiguration config, String profile, Project project) {

        String path = DEFAULT_CONFIG_FOLDER + "/project.properties";
        
        if (!profile.equals(DEFAULT_CONFIG_NAME)) {
            FileObject projectDir = project.getProjectDirectory();
            FileObject dockerConfigFolder = projectDir.getFileObject(DOCKER_CONFIG_FOLDER);
            if (dockerConfigFolder == null || !dockerConfigFolder.isFolder()) {
                File projectDirFolder = FileUtil.toFile(projectDir);
                File dir = new File(projectDirFolder, DOCKER_CONFIG_FOLDER);

                if (!dir.exists()) {
                    dir.mkdir();
                }
            }

            path = DOCKER_CONFIG_FOLDER + "/" + profile + ".properties";
        }

        //TODO
        //check if file exists
        //assert ep is not null
        EditableProperties ep = ProjectHelper.getProperties(project, path);
        ep.put(DOCKER_CONTAINER_NAME, config.getContainerName());
        ep.put(DOCKER_BASH_PATH, config.getBashType());
        ep.put(DOCKER_USE_INTERACTIVE, Boolean.toString(config.getInteractive()));
        ep.put(DOCKER_USE_TTY, Boolean.toString(config.getAsTerminal()));
        ep.put(DOCKER_USER, config.getDockerUser());
        ep.put(DOCKER_WORKDIR, config.getDockerWorkDir());
     
        try {
            ProjectHelper.storeEditableProperties(project, path, ep);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
