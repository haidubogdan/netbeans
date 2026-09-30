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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.modules.php.api.PhpOptions;
import static org.netbeans.modules.php.api.executable.PhpExecutable.parseCommand;
import org.netbeans.modules.php.api.util.StringUtils;
import org.netbeans.modules.php.api.util.UiUtils;
import org.openide.util.Lookup;
import org.openide.util.Pair;
import org.openide.util.Parameters;

public final class PhpDockerExecutable extends PhpExecutable {

    private List<String> dockerParams;

    /**
     * Parse command which can be just binary or binary with parameters. As a
     * parameter separator, "-" or "/" is used.
     *
     * @param command command to parse, can be {@code null}.
     */
    public PhpDockerExecutable(String command, List<String> dockerParams) {
        super(command);
        this.dockerParams = dockerParams;
    }

    @CheckForNull
    protected Future<Integer> runInternal(ExecutionDescriptor executionDescriptor, ExecutionDescriptor.InputProcessorFactory2 outProcessorFactory, boolean debug) {
        Parameters.notNull("executionDescriptor", executionDescriptor); // NOI18N

        org.netbeans.api.extexecution.base.ProcessBuilder processBuilder = getProcessBuilder(debug);
        if (processBuilder == null) {
            return null;
        }
        executionDescriptor = getExecutionDescriptor(executionDescriptor, outProcessorFactory);
        return ExecutionService.newService(processBuilder, executionDescriptor, getDisplayName()).run();
    }

    @CheckForNull
    protected org.netbeans.api.extexecution.base.ProcessBuilder getProcessBuilder(boolean debug) {
        Pair<org.netbeans.api.extexecution.base.ProcessBuilder, List<String>> processBuilderInfo = createProcessBuilder();
        if (processBuilderInfo == null) {
            return null;
        }
        org.netbeans.api.extexecution.base.ProcessBuilder processBuilder = processBuilderInfo.first();
        List<String> arguments = processBuilderInfo.second();
        for (String param : parameters) {
            fullCommand.add(param);
            arguments.add(param);
        }
        for (String param : additionalParameters) {
            fullCommand.add(param);
            arguments.add(param);
        }
        processBuilder.setArguments(arguments);

        for (Map.Entry<String, String> variable : environmentVariables.entrySet()) {
            processBuilder.getEnvironment().setVariable(variable.getKey(), variable.getValue());
        }
        processBuilder.setRedirectErrorStream(redirectErrorStream);

        return processBuilder;
    }

    protected Pair<org.netbeans.api.extexecution.base.ProcessBuilder, List<String>> createProcessBuilder() {
        String dockerPath = DockerCliConfig.getDockerExecutablePath();
        List<String> arguments = new ArrayList<>();
        if (dockerPath != null) {
            StringBuilder sb = new StringBuilder();

            fullCommand.add(dockerPath);
            fullCommand.addAll(dockerParams);
            arguments.addAll(dockerParams);

            sb.append(executable);
            sb.append(" ");
            sb.append(StringUtils.implode(parameters, " ")); // NOI18N

            fullCommand.add(sb.toString());
            arguments.add(sb.toString());

            org.netbeans.api.extexecution.base.ProcessBuilder processBuilder = org.netbeans.api.extexecution.base.ProcessBuilder.getLocal();
            processBuilder.setExecutable(dockerPath);

            return Pair.of(processBuilder, arguments);
        }

        return null;
    }
}
