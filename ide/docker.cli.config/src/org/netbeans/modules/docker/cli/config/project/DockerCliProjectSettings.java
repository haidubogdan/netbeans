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
package org.netbeans.modules.docker.cli.config.project;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.cli.config.DockerExecParamsConfig;
import static org.netbeans.modules.docker.cli.config.DockerExecCommandParams.SH_COMMAND;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DEFAULT_CONFIG_NAME;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DOCKER_BASH_PATH;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DOCKER_CLI_CONFIG_NAME;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DOCKER_CONTAINER_NAME;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DOCKER_USER;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.DOCKER_WORKDIR;
import static org.netbeans.modules.docker.cli.config.project.DockerCliConfigPreferences.PREF_JS_NODE;

public class DockerCliProjectSettings {

    static final Logger LOGGER = Logger.getLogger(DockerCliProjectSettings.class.getName());

    private final DockerCliConfigPreferences dockerCliConfigPreferences;
    private final Set<String> profiles;

    public DockerCliProjectSettings(Project project) {
        this.dockerCliConfigPreferences = new DockerCliConfigPreferences(project);
        this.profiles = loadProfiles();
    }

    private Set<String> loadProfiles() {
        Preferences dockerCommandConfigs = getDockerCliConfigPreferences();

        Set<String> loadedProfiles = new HashSet<>();

        try {
            loadedProfiles.add(DEFAULT_CONFIG_NAME);//always have the default option
            loadedProfiles.addAll(Arrays.asList(dockerCommandConfigs.childrenNames()));
        } catch (BackingStoreException bse) {
            LOGGER.log(Level.INFO, "Error while loading docker command preferences configs", bse);  // NOI18N
        }

        return loadedProfiles;
    }

    public void setCurrentProfile(String profile) {
        getDockerCommandPreferences().setDockerCommandConfigName(profile);
    }

    public String getCurrentProfile() {
        return getDockerCommandPreferences().getDockerCliConfigName();
    }

    public Set<String> getProfiles() {
        return Collections.unmodifiableSet(profiles);
    }

    public boolean profileExists(String configName) {
        return getProfiles().contains(configName);
    }

    private Preferences getDockerCliConfigPreferences() {
        return getDockerCommandPreferences().getDockerCommandConfigs();
    }

    private DockerCliConfigPreferences getDockerCommandPreferences() {
        return dockerCliConfigPreferences;
    }

    public Preferences getDockerCommandNodePreferences(String node) {
        return dockerCliConfigPreferences.getDockerNodePreferences(node);
    }

    public String getDefaultNodeTypeConfigName(String node) {
        Preferences jsPreferences = getDockerCommandNodePreferences(PREF_JS_NODE);
        if (jsPreferences == null) {
            return null;
        }
        
        return jsPreferences.get(DOCKER_CLI_CONFIG_NAME, null);
    }

    
    public DockerExecParamsConfig loadExecConfig(String profile) {
        return getProfileConfiguration(profile);
    }

    public boolean useDockerForJSCommands() {
        return getDockerCommandPreferences().getUseDockerForJSCommands();
    }

    public void setJSDockerConfig(String configName) {
        getDockerCommandPreferences().setJSDockerConfig(configName);
    }

    public void setUseDockerForJSCommands(boolean status) {
        getDockerCommandPreferences().setUseDockerForJSCommands(status);
    }

    public DockerExecParamsConfig getProfileConfiguration(String profile) {
        Preferences dockerCommandConfigs = getDockerCliConfigPreferences();
        Preferences profileConfig = dockerCommandConfigs.node(profile);

        String containeName = profileConfig.get(DOCKER_CONTAINER_NAME, null);

        if (containeName == null) {
            LOGGER.log(Level.INFO, "Config {0} container name is not set", profile);  // NOI18N
            return null;
        }

        return new DockerExecParamsConfig(
                containeName,
                profileConfig.get(DOCKER_BASH_PATH, SH_COMMAND), // NOI18N
                profileConfig.get(DOCKER_USER, null),
                profileConfig.get(DOCKER_WORKDIR, null)
        );
    }

    public void saveConfig(DockerExecParamsConfig config, String profile) {
        Preferences configPref = getDockerCommandPreferences().getDockerCommandConfigs().node(profile);
        configPref.put(DOCKER_CONTAINER_NAME, config.getContainerName());
        configPref.put(DOCKER_BASH_PATH, config.getBashType());
        configPref.put(DOCKER_USER, config.getDockerUser());
        configPref.put(DOCKER_WORKDIR, config.getDockerWorkDir());
    }

    public void removeProfileConfig(String profile) {
        if (profile.equals(DEFAULT_CONFIG_NAME)) {
            return;
        }
        try {
            dockerCliConfigPreferences.getDockerCommandConfigs().node(profile).removeNode();
        } catch (BackingStoreException bse) {
            LOGGER.log(Level.INFO, "Error while removing unused docker command profile: " + profile, bse);  // NOI18N
        }
    }
}
