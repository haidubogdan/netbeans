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
import java.awt.event.ActionListener;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.execution.DockerExecModel;
import org.netbeans.modules.docker.execution.containers.DockerContainers;
import org.netbeans.modules.docker.execution.project.DockerConfigManager;
import org.netbeans.modules.docker.execution.project.DockerExecConfiguration;
import org.netbeans.modules.docker.execution.project.DockerProjectSettings;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

public class DockerExecutableConfigPanel extends javax.swing.JPanel {

    private final ProjectCustomizer.Category category;
    private final Project project;
    private final DockerExecModel dockerExecModel;
    private final DockerConfigComboBoxModel comboModel;
    private final DockerConfigComboBoxModel javascriptComboModel;
    private final DockerContainerComboModel dockerContainerListModel = new DockerContainerComboModel();

    public DockerExecutableConfigPanel(ProjectCustomizer.Category category, Project project) {
        assert category != null;
        assert project != null;

        this.category = category;
        this.project = project;

        this.dockerExecModel = new DockerProjectSettings(project).getDockerExecModel();
        initComponents();

        Set<String> profiles = dockerExecModel.getProfiles();
        comboModel = DockerConfigComboBoxModel.build(profiles);
        javascriptComboModel = DockerConfigComboBoxModel.build(profiles);
        ConfigOptionCombo.setModel(comboModel);
        jsCommandsDockerConfigCombo.setModel(javascriptComboModel);
        ContainerConfigOption.setModel(dockerContainerListModel);

        init();
    }
//
//    @Override
//    public void addNotify() {
//        super.addNotify();
//        ConfigOptionCombo.setSelectedItem(manager.currentConfiguration().getName());
//    }

    private void init() {
        //validate configProfile
        String currentProfile = dockerExecModel.getCurrentProfile();
        comboModel.setSelectedItem(currentProfile);
        dockerContainerListModel.setElements(DockerContainers.get().getDockerContainers());
        loadDockerExecSettings();

        category.setStoreListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                store();
            }
        });
    }

    private void loadDockerExecSettings() {
        String currentProfile = (String) comboModel.getSelectedItem();
        DockerExecConfiguration config = dockerExecModel.getConfiguration(currentProfile);
        ContainerConfigOption.setSelectedItem(config.getContainerName());
        dockerBashType.setText(config.getBashType());
        dockerUser.setText(config.getDockerUser());
        dockerVolumeDir.setText(config.getDockerWorkDir());

        jsCommandsDockerEnabled.setSelected(dockerExecModel.getUseDockerForJSCommands());
        jsCommandsDockerConfigCombo.setSelectedItem(dockerExecModel.getJSDockerContainerProfile());
    }

    private DockerExecConfiguration buildConfig() {
        return new DockerExecConfiguration(
                ContainerConfigOption.getSelectedItem().toString(),
                dockerBashType.getText(),
                dockerUser.getText(),
                dockerVolumeDir.getText()
        );
    }

    private void store() {
        String selectedProfile = (String) ConfigOptionCombo.getSelectedItem();
        DockerExecConfiguration config = buildConfig();
        DockerConfigManager.saveConfigProfile(config, selectedProfile, project);
        dockerExecModel.setCurrentProfile(selectedProfile);
        dockerExecModel.setUseDockerForJSCommands(jsCommandsDockerEnabled.isSelected());
        Object selectedJsDockerConfig = jsCommandsDockerConfigCombo.getSelectedItem();

        if (selectedJsDockerConfig != null) {
            dockerExecModel.setJSDockerConfig((String) selectedJsDockerConfig);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LBL_ContainerName = new javax.swing.JLabel();
        LBL_BashType = new javax.swing.JLabel();
        dockerBashType = new javax.swing.JTextField();
        LBL_User = new javax.swing.JLabel();
        dockerUser = new javax.swing.JTextField();
        LBL_DockerWorkdir = new javax.swing.JLabel();
        dockerVolumeDir = new javax.swing.JTextField();
        LBL_ConfigName = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        ConfigOptionCombo = new javax.swing.JComboBox<>();
        configNew = new javax.swing.JButton();
        configDel = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jsCommandsDockerEnabled = new javax.swing.JCheckBox();
        jsCommandsDockerConfigCombo = new javax.swing.JComboBox<>();
        openContainerConfigPanelBtn = new javax.swing.JButton();
        ContainerConfigOption = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(LBL_ContainerName, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.LBL_ContainerName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(LBL_BashType, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.LBL_BashType.text")); // NOI18N

        dockerBashType.setText(org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.dockerBashType.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(LBL_User, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.LBL_User.text")); // NOI18N

        dockerUser.setText(org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.dockerUser.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(LBL_DockerWorkdir, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.LBL_DockerWorkdir.text")); // NOI18N

        dockerVolumeDir.setText(org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.dockerVolumeDir.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(LBL_ConfigName, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.LBL_ConfigName.text")); // NOI18N

        ConfigOptionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configComboActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(configNew, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.configNew.text")); // NOI18N
        configNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configNewActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(configDel, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.configDel.text")); // NOI18N
        configDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configDelActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jsCommandsDockerEnabled, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jsCommandsDockerEnabled.text")); // NOI18N

        jsCommandsDockerConfigCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsCommandsDockerConfigComboconfigComboActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(openContainerConfigPanelBtn, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.openContainerConfigPanelBtn.text")); // NOI18N
        openContainerConfigPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openContainerConfigPanelBtnActionPerformed(evt);
            }
        });

        ContainerConfigOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContainerConfigOptionconfigComboActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LBL_ConfigName)
                                .addGap(43, 43, 43)
                                .addComponent(ConfigOptionCombo, 0, 328, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(configNew)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(configDel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LBL_BashType)
                                    .addComponent(LBL_ContainerName))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ContainerConfigOption, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(openContainerConfigPanelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dockerBashType)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LBL_User)
                                    .addComponent(LBL_DockerWorkdir))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dockerVolumeDir)
                                    .addComponent(dockerUser)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jsCommandsDockerEnabled)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jsCommandsDockerConfigCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator2))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfigOptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(configNew)
                    .addComponent(configDel)
                    .addComponent(LBL_ConfigName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LBL_ContainerName)
                    .addComponent(openContainerConfigPanelBtn)
                    .addComponent(ContainerConfigOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBL_BashType)
                    .addComponent(dockerBashType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dockerUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LBL_User))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBL_DockerWorkdir)
                    .addComponent(dockerVolumeDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jsCommandsDockerEnabled)
                    .addComponent(jsCommandsDockerConfigCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void configNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configNewActionPerformed
        NotifyDescriptor.InputLine d = new NotifyDescriptor.InputLine(
                NbBundle.getMessage(DockerExecutableConfigPanel.class, "LBL_ConfigurationName"),
                NbBundle.getMessage(DockerExecutableConfigPanel.class, "LBL_CreateNewConfiguration"));

        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.OK_OPTION) {
            String name = d.getInputText();
            if (name.trim().length() == 0) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(DockerExecutableConfigPanel.class, "MSG_ConfigurationNameBlank"),
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }
            String configName = name.replaceAll("[^a-zA-Z0-9_.-]", "_"); // NOI18N

            if (dockerExecModel.profileExists(configName)) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(DockerExecutableConfigPanel.class, "MSG_ConfigurationExists", configName),
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }

            if (ContainerConfigOption.getSelectedItem() != null) {
                DockerExecConfiguration config = buildConfig();
                DockerConfigManager.saveConfigProfile(config, configName, project);

                comboModel.addElement(configName);
                comboModel.setSelectedItem(configName);
                javascriptComboModel.addElement(configName);
            }
            loadDockerExecSettings();
        }
    }//GEN-LAST:event_configNewActionPerformed

    private void configComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configComboActionPerformed
        loadDockerExecSettings();
    }//GEN-LAST:event_configComboActionPerformed

    private void jsCommandsDockerConfigComboconfigComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsCommandsDockerConfigComboconfigComboActionPerformed

    }//GEN-LAST:event_jsCommandsDockerConfigComboconfigComboActionPerformed

    private void configDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configDelActionPerformed
        String currentProfile = (String) comboModel.getSelectedItem();
        dockerExecModel.remove(currentProfile);
        comboModel.removeElement(currentProfile);
        javascriptComboModel.removeElement(currentProfile);
    }//GEN-LAST:event_configDelActionPerformed

    private void ContainerConfigOptionconfigComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContainerConfigOptionconfigComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ContainerConfigOptionconfigComboActionPerformed

    private void openContainerConfigPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openContainerConfigPanelBtnActionPerformed
        DockerContainers containers = DockerContainers.get();
        containers.openManager();
    }//GEN-LAST:event_openContainerConfigPanelBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ConfigOptionCombo;
    private javax.swing.JComboBox<String> ContainerConfigOption;
    private javax.swing.JLabel LBL_BashType;
    private javax.swing.JLabel LBL_ConfigName;
    private javax.swing.JLabel LBL_ContainerName;
    private javax.swing.JLabel LBL_DockerWorkdir;
    private javax.swing.JLabel LBL_User;
    private javax.swing.JButton configDel;
    private javax.swing.JButton configNew;
    private javax.swing.JTextField dockerBashType;
    private javax.swing.JTextField dockerUser;
    private javax.swing.JTextField dockerVolumeDir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> jsCommandsDockerConfigCombo;
    private javax.swing.JCheckBox jsCommandsDockerEnabled;
    private javax.swing.JButton openContainerConfigPanelBtn;
    // End of variables declaration//GEN-END:variables
}
