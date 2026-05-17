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

import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.openide.util.Exceptions;

public class DockerCliConfigPreferences {

    public static final String DOCKER_CLI_CONFIG_NAME = "docker.exec.command.configname"; // NOI18N
    public static final String DOCKER_CONTAINER_NAME = "docker.exec.command.containername"; // NOI18N
    public static final String DOCKER_BASH_PATH = "docker.exec.command.bashpath"; // NOI18N
    public static final String DOCKER_WORKDIR = "docker.exec.command.workdir"; // NOI18N
    public static final String DOCKER_USER = "docker.exec.command.user"; // NOI18N

    public static final String DOCKER_ENABLED = "docker.exec.command.enabled"; // NOI18N

    public static final String DEFAULT_CONFIG_NAME = "<default>";

    public static final String DOCKER_COMMAND_CONFIG_NODE = "docker_command_configs";
    public static final String PREF_JS_NODE = "javascript"; // NOI18N

    private final Preferences dockerPreferences;

    public DockerCliConfigPreferences(Project project) {
        assert project != null;
        this.dockerPreferences = ProjectUtils.getPreferences(project, DockerCliConfigPreferences.class, true);
    }

    public void setDockerCommandConfigName(String profile) {
        getPreferences().put(DOCKER_CLI_CONFIG_NAME, profile);
    }

    public String getDockerCliConfigName() {
        return getPreferences().get(DOCKER_CLI_CONFIG_NAME, DEFAULT_CONFIG_NAME);
    }

    public String getDockerContainerName() {
        return getPreferences().get(DOCKER_CONTAINER_NAME, null);
    }

    public String getDockerExecBashPath() {
        return getPreferences().get(DOCKER_BASH_PATH, null);
    }

    public String getDockerUser() {
        return getPreferences().get(DOCKER_USER, null);
    }

    public String getDockerWorkdir() {
        return getPreferences().get(DOCKER_WORKDIR, null);
    }

    public void setUseDockerForJSCommands(boolean status) {
        if (getJSDockerPreferences() == null) {
            //create empty JsNode
            getPreferences().node(PREF_JS_NODE);
        }
        getJSDockerPreferences().putBoolean(DOCKER_ENABLED, status);
    }

    public void setJSDockerConfig(String configName) {
        if (getJSDockerPreferences() == null) {
            //create empty JsNode
            getPreferences().node(PREF_JS_NODE);
        }
        getJSDockerPreferences().put(DOCKER_CLI_CONFIG_NAME, configName);
    }

    public String getJSDockerConfigName() {
        if (getJSDockerPreferences() == null) {
            return null;
        }
        return getJSDockerPreferences().get(DOCKER_CLI_CONFIG_NAME, null);
    }

    public boolean getUseDockerForJSCommands() {

        if (getJSDockerPreferences() == null) {
            return false;
        }
        return getJSDockerPreferences().getBoolean(DOCKER_ENABLED, false);
    }

    public Preferences getDockerNodePreferences(String node) {
        try {
            if (getPreferences().nodeExists(node)) {
                return getPreferences().node(node);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }
    
    private Preferences getJSDockerPreferences() {
        try {
            if (getPreferences().nodeExists(PREF_JS_NODE)) {
                return getPreferences().node(PREF_JS_NODE);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }

    public Preferences getDockerCommandConfigs() {
        return getPreferences().node(DOCKER_COMMAND_CONFIG_NODE);
    }
    
    private Preferences getPreferences() {
        return dockerPreferences;
    }

    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        dockerPreferences.addPreferenceChangeListener(listener);
    }

    public void removePreferenceChangeListener(PreferenceChangeListener listener) {
        dockerPreferences.removePreferenceChangeListener(listener);
    }

}
