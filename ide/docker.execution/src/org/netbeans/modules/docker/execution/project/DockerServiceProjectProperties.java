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
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.docker.execution.DockerExecutableConfig;
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

    private final PreferenceChangeListener preferencesListener = new PreferencesListener();

    private static final String[] CFG_PROPS = new String[]{
        DOCKER_CONTAINER_NAME,
        DOCKER_BASH_PATH,
        DOCKER_WORKDIR,
        DOCKER_USE_TTY,
        DOCKER_USER,
        DOCKER_USE_INTERACTIVE,};

    private final Map<String, Map<String, String>> configs;

    private boolean dockerNpmEnabled = false;

    private DockerServiceProjectProperties(Project project, Preferences preferences) {
        this.project = project;
        this.dockerPreferences = new DockerProjectPreferences(project, preferences);
        configs = readDockerConfigs();
        this.dockerNpmEnabled = dockerPreferences.getDockerNpmEnabled();
        addListeners();
    }

    public static DockerServiceProjectProperties fromProject(Project project, Preferences preferences) {
        DockerServiceProjectProperties instance = new DockerServiceProjectProperties(project, preferences);
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

    private void addListeners() {
        dockerPreferences.addPreferenceChangeListener(preferencesListener);
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
        storeDockerExecConfigs();
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
        dockerPreferences.setDockerNpmEnabled(dockerNpmEnabled);
    }

    private void storeDockerExecConfigs() {

        for (String name : configManager.configurationNames()) {
            if (name == null) {
                // default config
                continue;
            }

            String sharedPath = DOCKER_CONFIG_FOLDER + "/" + name + ".properties"; // NOI18N
            EditableProperties ep = ProjectHelper.getProperties(project, sharedPath);
            if (!configManager.exists(name)) {
                try {
                    // deleted config
                    ProjectHelper.storeEditableProperties(project, sharedPath, ep);
                    continue;
                } catch (IOException ex) {
                    continue;
                }
            }

            Configuration configuration = configManager.configurationFor(name);

            for (String propertyName : configuration.getPropertyNames()) {
                String value = configuration.getValue(propertyName);

                if (value == null) {
                    continue;
                }
                ep.setProperty(propertyName, value);
            }
            try {
                // deleted config
                ProjectHelper.storeEditableProperties(project, sharedPath, ep);
            } catch (IOException ex) {
            }

        }
    }

    private Map<String, Map<String, String>> readDockerConfigs() {
        Map<String, Map<String, String>> cfgs = ConfigManagerUtils.createEmptyConfigs();
        Map<String, String> def = new TreeMap<>();

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
                EditableProperties ep = ProjectHelper.getProperties(project, path);
                cfgs.put(kid.getName(), new TreeMap<>(ep));
            }
        }

        return cfgs;
    }

    public void setDockerNpmEnabled(boolean useDocker) {
        this.dockerNpmEnabled = useDocker;
    }

    public boolean isDockerNpmEnabled() {
        return dockerNpmEnabled;
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

    private final class PreferencesListener implements PreferenceChangeListener {

        @Override
        public void preferenceChange(PreferenceChangeEvent evt) {
            int x = 1;
        }

    }
}
