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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.docker.execution.project.ConfigManager.Configuration;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Mutex;
import org.openide.util.MutexException;

/**
 *
 * @author bhaidu
 */
public class DockerServiceProjectProperties implements ConfigManager.ConfigProvider {

    private final Project project;
    private ConfigManager configManager;
    private final DockerProjectPreferences dockerPreferences;

    public static final String DOCKER_CONTAINER_NAME = "docker_container_name"; // NOI18N
    public static final String DOCKER_BASH_PATH = "docker_bash_path"; // NOI18N

    public static final String DOCKER_USE_TTY = "docker_use_tty"; // NOI18N
    public static final String DOCKER_USE_INTERACTIVE = "docker_use_interactive"; // NOI18N

    public static final String DOCKER_USER = "docker_user"; // NOI18N
    public static final String DOCKER_WORKDIR = "docker_workdir"; // NOI18N

    public static final boolean DEFAULT_DOCKER_TTY = true;
    public static final boolean DEFAULT_DOCKER_INTERACTIVE = true;

    public static final String DOCKER_CONFIG_FOLDER = "nbproject/docker_configs";

    private static final String[] CFG_PROPS = new String[]{
        DOCKER_CONTAINER_NAME,
        DOCKER_BASH_PATH,
        DOCKER_WORKDIR,
        DOCKER_USE_TTY,
        DOCKER_USER,
        DOCKER_USE_INTERACTIVE,};

    private final Map<String, Map<String, String>> configs;

    private DockerServiceProjectProperties(Project project) {
        this.project = project;
        this.dockerPreferences = new DockerProjectPreferences(project);
        configs = readDockerConfigs();
    }

    public static DockerServiceProjectProperties fromProject(Project project) {
        DockerServiceProjectProperties instance = new DockerServiceProjectProperties(project);
        instance.initConfigManager();
        return instance;
    }

    public void initConfigManager() {
        configManager = new ConfigManager(this);
        if (configManager.configurationFor(dockerPreferences.getDockerConfigName()) != null) {
            configManager.markAsCurrentConfiguration(dockerPreferences.getDockerConfigName());
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public String[] getConfigProperties() {
        return CFG_PROPS;
    }

    @Override
    public Map<String, Map<String, String>> getConfigs() {
        return configs;
    }

    public void saveProperties() {
        AntProjectHelper helper = getHelper();
        storeDockerExecConfigs(helper);
        Configuration currentConfig = configManager.currentConfiguration();
        String currentConfigName = currentConfig.getName();

        if (currentConfigName != null) {
            dockerPreferences.setDockerConfigName(currentConfigName);
        }
        dockerPreferences.setDockerContainerName(currentConfig.getValue(DOCKER_CONTAINER_NAME));
        dockerPreferences.setDockerExecBashPath(currentConfig.getValue(DOCKER_BASH_PATH));
        boolean ttySelected = Boolean.parseBoolean(currentConfig.getValue(DOCKER_USE_TTY));
        dockerPreferences.setDockerPseudoTerminal(ttySelected);
        boolean interactive = Boolean.parseBoolean(currentConfig.getValue(DOCKER_USE_INTERACTIVE));
        dockerPreferences.setDockerInteractive(interactive);
        dockerPreferences.setDockerUser(currentConfig.getValue(DOCKER_USER));
        dockerPreferences.setDockerWorkdir(currentConfig.getValue(DOCKER_WORKDIR));

    }

    private void storeDockerExecConfigs(AntProjectHelper helper) {
        for (String name : configManager.configurationNames()) {
            if (name == null) {
                // default config
                continue;
            }

            String sharedPath = DOCKER_CONFIG_FOLDER + "/" + name + ".properties"; // NOI18N

            if (!configManager.exists(name)) {
                // deleted config
                helper.putProperties(sharedPath, null);
                continue;
            }

            Configuration configuration = configManager.configurationFor(name);
            EditableProperties ep = helper.getProperties(sharedPath);

            for (String propertyName : configuration.getPropertyNames()) {
                String value = configuration.getValue(propertyName);

                if (value == null) {
                    continue;
                }
                ep.setProperty(propertyName, value);
            }

            helper.putProperties(sharedPath, ep);

        }
    }

    private Map<String, Map<String, String>> readDockerConfigs() {
        Map<String, Map<String, String>> cfgs = ConfigManagerUtils.createEmptyConfigs();
        Map<String, String> def = new TreeMap<>();
        AntProjectHelper helper = getHelper();

        cfgs.put(null, new HashMap<>());

        def.put(DOCKER_CONTAINER_NAME, dockerPreferences.getDockerContainerName());
        def.put(DOCKER_BASH_PATH, dockerPreferences.getDockerExecBashPath());
        def.put(DOCKER_USE_TTY, dockerPreferences.getDockerPseudoTerminal() ? "true" : "false");
        def.put(DOCKER_USE_INTERACTIVE, dockerPreferences.getDockerInteractive() ? "true" : "false");
        def.put(DOCKER_USER, dockerPreferences.getDockerUser());
        def.put(DOCKER_WORKDIR, dockerPreferences.getDockerWorkdir());

        cfgs.put(null, def);

        FileObject configsFolder = project.getProjectDirectory().getFileObject(DOCKER_CONFIG_FOLDER); // NOI18N
        if (configsFolder != null) {
            FileObject projectDir = project.getProjectDirectory();

            for (FileObject kid : configsFolder.getChildren()) {
                if (!kid.hasExt("properties")) { // NOI18N
                    continue;
                }

                String path = FileUtil.getRelativePath(projectDir, kid);
                cfgs.put(kid.getName(), new TreeMap<>(helper.getProperties(path)));
            }
        }

        return cfgs;
    }

    private AntProjectHelper getHelper() {
        AntProjectHelper helper = project.getLookup().lookup(AntProjectHelper.class);
        assert helper != null;
        return helper;
    }

    public void save() {
        try {
            // store properties
            ProjectManager.mutex().writeAccess(new Mutex.ExceptionAction<Void>() {
                @Override
                public Void run() throws IOException {
                    saveProperties();
                    return null;
                }
            });
        } catch (MutexException e) {
            Exceptions.printStackTrace((IOException) e.getException());
        }
    }
}
