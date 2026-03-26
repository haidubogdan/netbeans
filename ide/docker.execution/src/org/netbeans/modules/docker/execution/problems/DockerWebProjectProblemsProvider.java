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
package org.netbeans.modules.docker.execution.problems;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.netbeans.spi.project.ui.*;
import org.netbeans.spi.project.ui.support.ProjectProblemsProviderSupport;

@ProjectServiceProvider(service = ProjectProblemsProvider.class, projectType = "org-netbeans-modules-web-clientproject") // NOI18N
public class DockerWebProjectProblemsProvider implements ProjectProblemsProvider {

    private final ProjectProblemsProviderSupport problemsProviderSupport = new ProjectProblemsProviderSupport(this);

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        int x = 1;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        int x = 2;
    }

    @Override
    public Collection<? extends ProjectProblem> getProblems() {
        return problemsProviderSupport.getProblems(new ProjectProblemsProviderSupport.ProblemsCollector() {
            @Override
            public Collection<? extends ProjectProblem> collectProblems() {
//                if (!getNodeJsSupport().getPreferences().isEnabled()) {
//                    return Collections.emptyList();
//                }
                Collection<ProjectProblemsProvider.ProjectProblem> currentProblems = new ArrayList<>();
                checkPreferences(currentProblems);
                return currentProblems;
            }
        });
    }

    void checkPreferences(Collection<ProjectProblem> currentProblems) {

    }
}
