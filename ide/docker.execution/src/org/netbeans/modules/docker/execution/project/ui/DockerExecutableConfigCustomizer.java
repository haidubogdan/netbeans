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
package org.netbeans.modules.docker.execution.project.ui;

import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import java.awt.event.ActionListener;
import org.netbeans.modules.docker.execution.project.DockerServiceProjectProperties;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author bhaidu
 */
public class DockerExecutableConfigCustomizer implements ProjectCustomizer.CompositeCategoryProvider {

    public static final String DOCKER_EXECUTABLE_CUSTOMIZER = "docker_exec_customizer"; // NOI18N
    private DockerExecutableConfigPanel component;

    @Override
    public ProjectCustomizer.Category createCategory(Lookup context) {
        return ProjectCustomizer.Category.create(DOCKER_EXECUTABLE_CUSTOMIZER,
                NbBundle.getMessage(DockerExecutableConfigCustomizer.class,
                        "LBL_DockerExecutableCustomizer"), null);
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category category, Lookup context) {
        if (component == null) {
            Project project = context.lookup(Project.class);
            assert project != null;
            DockerServiceProjectProperties dockerPropeties = DockerServiceProjectProperties.fromProject(project);
            component = new DockerExecutableConfigPanel(dockerPropeties);
            category.setOkButtonListener(new Listener(component));
        }

        return component;
    }

    private class Listener implements ActionListener {

        private final DockerExecutableConfigPanel panel;

        public Listener(DockerExecutableConfigPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.saveSettings();
        }

    }

    @ProjectCustomizer.CompositeCategoryProvider.Registration(
            projectType = "org.netbeans.modules.web.clientproject",
            position = 367)
    public static DockerExecutableConfigCustomizer forHtml5Project() {
        return new DockerExecutableConfigCustomizer();
    }

    @ProjectCustomizer.CompositeCategoryProvider.Registration(
            projectType = "org-netbeans-modules-php-project",
            position = 402)
    public static ProjectCustomizer.CompositeCategoryProvider forPhpProject() {
        return new DockerExecutableConfigCustomizer();
    }
}
