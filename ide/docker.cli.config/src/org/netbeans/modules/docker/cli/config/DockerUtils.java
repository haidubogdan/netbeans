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
package org.netbeans.modules.docker.cli.config;

import org.openide.util.NbPreferences;
import org.openide.util.Utilities;

public class DockerUtils {

    public static final String DOCKER_EXEC_FILE_PATH = "docker_path"; // NOI18N

    public static final String DOCKER_BASE_UNIX_COMMAND = "/usr/bin/docker"; // NOI18N
    public static final String DOCKER_BASE_MACOS_COMMAND = "/usr/local/bin/docker"; // NOI18N
    public static final String DOCKER_BASE_WIN_COMMAND = "C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe"; // NOI18N

    private DockerUtils() {

    }

    public static String getDockerExecutablePath() {
        String basePath = Utilities.isWindows() ? DOCKER_BASE_WIN_COMMAND : ( Utilities.isMac()? DOCKER_BASE_MACOS_COMMAND : DOCKER_BASE_UNIX_COMMAND);
        return NbPreferences.forModule(DockerExecCommandParams.class).get(DOCKER_EXEC_FILE_PATH, basePath);
    }

    public static void setDockerExecutablePath(String path) {
        NbPreferences.forModule(DockerExecCommandParams.class).put(DOCKER_EXEC_FILE_PATH, path);
    }
}
