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
package org.netbeans.modules.php.api.executable;

import java.util.Collections;
import java.util.List;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.cli.config.api.DockerExecuteCliConfigProvider;
import static org.netbeans.modules.docker.cli.config.api.DockerExecuteCliConfigProvider.PHP_DOCKER_PATH;

public class DockerCliConfig {

    private static final DockerCliConfig INSTANCE = new DockerCliConfig();

    private final DockerExecuteCliConfigProvider provider;
    
    private DockerCliConfig() {
        provider = getProvider();
    }
    
    public static DockerCliConfig getInstance() {
        return INSTANCE;
    }
    
    public boolean useCliConfig(@NullAllowed Project project) {
        if (project == null) {
            return false;
        }
        
        if (provider == null) {
            return false;
        }

        return provider.isEnabled(project);
    }

    public List<String> generateExecutableParams(Project project) {
        if (provider == null) {
            return Collections.emptyList();
        }
        
        return provider.generateExecutableParams(project);
    }

    private DockerExecuteCliConfigProvider getProvider() {
        return DockerExecuteCliConfigProvider.getProvider(PHP_DOCKER_PATH);
    }

    public static String getDockerExecutablePath() {
        return DockerExecuteCliConfigProvider.getDockerExecutablePath();
    }
}
