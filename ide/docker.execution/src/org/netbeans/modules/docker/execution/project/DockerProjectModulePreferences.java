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

import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.openide.util.Exceptions;

/**
 *
 * @author bhaidu
 */
public class DockerProjectModulePreferences {

    private static final String DOCKER_CONFIG_NAME = "docker.exec.configname"; // NOI18N
    public static final String DOCKER_CONTAINER_NAME = "docker.exec.containername"; // NOI18N
    public static final String DOCKER_BASH_PATH = "docker.exec.bashpath"; // NOI18N
    public static final String DOCKER_WORKDIR = "docker.exec.workdir"; // NOI18N
    public static final String DOCKER_USER = "docker.exec.user"; // NOI18N

    public static final String DOCKER_ENABLED = "docker.exec.enabled"; // NOI18N
    public static final String DOCKER_NODE_NPM_NAME = "docker.exec.nodenpm"; // NOI18N

    public static final String DEFAULT_CONFIG_NAME = "<default>";

    public static final String PREF_JS_NODE = "javascript"; // NOI18N

    // @GuardedBy("this")
    private final Preferences modulePreferences;
    private final Preferences dockerPreferences;
    
    public DockerProjectModulePreferences(Project project, Preferences modulePreferences) {
        assert project != null;
        this.dockerPreferences = ProjectUtils.getPreferences(project, DockerProjectModulePreferences.class, true);
        this.modulePreferences = modulePreferences;
    }
    
    public String getDockerConfigName() {
        return getModulePreferences().get(DOCKER_CONFIG_NAME, DEFAULT_CONFIG_NAME);
    }

    public String getDockerContainerName() {
        return getModulePreferences().get(DOCKER_CONTAINER_NAME, null);
    }

    public String getDockerExecBashPath() {
        return getModulePreferences().get(DOCKER_BASH_PATH, null);
    }

    public String getDockerUser() {
        return getModulePreferences().get(DOCKER_USER, null);
    }

    public String getDockerWorkdir() {
        return getModulePreferences().get(DOCKER_WORKDIR, null);
    }

    public void setDockerNpmEnabled(boolean npmEnabled) {
        getDockerNpmPreferences().putBoolean(DOCKER_ENABLED, npmEnabled);
    }

    public void setDockerNpmConfigName(String configName) {
        getDockerNpmPreferences().put(DOCKER_CONFIG_NAME, configName);
    }

    public String getDockerNpmConfigName() {
        if (getDockerNpmPreferences() == null) {
            return null;
        }
        return getDockerNpmPreferences().get(DOCKER_CONFIG_NAME, null);
    }

    public boolean getDockerJavascriptEnabled() {

        return getModulePreferences().node(PREF_JS_NODE).getBoolean(DOCKER_ENABLED, false);
    }

    private Preferences getDockerNpmPreferences() {
        try {
            if (getModulePreferences().nodeExists(PREF_JS_NODE)) {
                return getModulePreferences().node(PREF_JS_NODE);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }

    private Preferences getModulePreferences() {
        return modulePreferences;
    }

    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        modulePreferences.addPreferenceChangeListener(listener);
    }

    public void removePreferenceChangeListener(PreferenceChangeListener listener) {
        modulePreferences.removePreferenceChangeListener(listener);
    }

}
