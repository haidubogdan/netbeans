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
package org.netbeans.modules.docker.command.project.ui;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.docker.command.DockerCommandModel;
import org.netbeans.modules.docker.command.DockerExecutablePreference;
import org.netbeans.modules.docker.command.containers.DockerContainers;
import org.netbeans.modules.docker.command.configurations.DockerExecConfiguration;
import static org.netbeans.modules.docker.command.project.DockerCommandPreferences.DEFAULT_CONFIG_NAME;
import org.netbeans.modules.docker.command.project.DockerProjectSettings;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.util.NbBundle;

public class DockerExecutableConfigPanel extends javax.swing.JPanel {
    private static final Logger LOGGER = Logger.getLogger(DockerExecutableConfigPanel.class.getName());
    private final ProjectCustomizer.Category category;
    private final Project project;
    private final DockerCommandModel dockerCommandModel;
    private final DockerConfigComboBoxModel comboModel;
    private final DockerConfigComboBoxModel javascriptComboModel;
    private final DockerContainerComboModel dockerContainerListModel = new DockerContainerComboModel();

    public DockerExecutableConfigPanel(ProjectCustomizer.Category category, Project project) {
        assert category != null;
        assert project != null;

        this.category = category;
        this.project = project;

        this.dockerCommandModel = new DockerProjectSettings(project).getDockerCommandModel();
        initComponents();

        Set<String> configs = dockerCommandModel.getProfiles();
        comboModel = DockerConfigComboBoxModel.build(configs);
        javascriptComboModel = DockerConfigComboBoxModel.build(configs);
        ConfigOptionCombo.setModel(comboModel);
        jsCommandsDockerConfigCombo.setModel(javascriptComboModel);
        dockerContainersComboBox.setModel(dockerContainerListModel);

        init();
    }

    private void init() {
        dockerExecPath.setText(DockerExecutablePreference.getDockerExecutablePath());
        dockerExecPath.setEditable(false);
        String currentProfile = dockerCommandModel.getCurrentProfile();
        comboModel.setSelectedItem(currentProfile);
        dockerContainerListModel.setElements(DockerContainers.get().getDockerContainers());
        
        loadDockerExecSettings();

        dockerContainersComboBox.addActionListener((ActionEvent e) -> {
            validateData();
        });
        
        DocumentListener defaultDocumentListener = new DefaultDocumentListener();
        dockerVolumeDir.getDocument().addDocumentListener(defaultDocumentListener);
        
        category.setStoreListener((ActionEvent e) -> {
            store();
        });
        
        validateData();
    }

    private void loadDockerExecSettings() {
        String currentProfile = (String) comboModel.getSelectedItem();
        DockerExecConfiguration config = dockerCommandModel.getProfileConfiguration(currentProfile);

        if (config != null) {
            dockerContainersComboBox.setSelectedItem(config.getContainerName());
            dockerBashType.setText(config.getBashType());
            dockerUser.setText(config.getDockerUser());
            dockerVolumeDir.setText(config.getDockerWorkDir());
        } else {
            dockerVolumeDir.setText("/" + project.getProjectDirectory().getName());  // NOI18N
        }

        jsCommandsDockerEnabled.setSelected(dockerCommandModel.getUseDockerForJSCommands());
        jsCommandsDockerConfigCombo.setSelectedItem(dockerCommandModel.getJSDockerContainerProfile());
    }

    private DockerExecConfiguration buildConfig() {
        return new DockerExecConfiguration(
                dockerContainersComboBox.getSelectedItem().toString(),
                dockerBashType.getText(),
                dockerUser.getText(),
                dockerVolumeDir.getText()
        );
    }

    private void store() {
        String selectedConfig = (String) ConfigOptionCombo.getSelectedItem();
        
        if (dockerContainersComboBox.getSelectedItem() != null) {
            DockerExecConfiguration config = buildConfig();
            dockerCommandModel.saveConfig(config, selectedConfig);
        }
        dockerCommandModel.setCurrentProfile(selectedConfig);
        dockerCommandModel.setUseDockerForJSCommands(jsCommandsDockerEnabled.isSelected());
        Object selectedJsDockerConfig = jsCommandsDockerConfigCombo.getSelectedItem();

        if (selectedJsDockerConfig != null) {
            dockerCommandModel.setJSDockerConfig((String) selectedJsDockerConfig);
        }
    }

    private void validateData() {
        assert EventQueue.isDispatchThread();
        
        category.setErrorMessage(null);
        category.setValid(true);

        //just warnings
        if (dockerVolumeDir.getText() != null && !dockerVolumeDir.getText().isEmpty()) {
            if(dockerVolumeDir.getText().contains("\\")) {// NOI18N
                category.setErrorMessage(NbBundle.getMessage(DockerExecutableConfigPanel.class, "HINT_workdir_should_be_in_unix_style")); // NOI18N
            } else if(!dockerVolumeDir.getText().startsWith("/")) { // NOI18N
                category.setErrorMessage(NbBundle.getMessage(DockerExecutableConfigPanel.class, "HINT_workdir_should_be_absolute")); // NOI18N
            }
        }
        
        if (dockerContainersComboBox.getSelectedItem() == null) {
            category.setErrorMessage(NbBundle.getMessage(DockerExecutableConfigPanel.class, "ERROR_set_container_name")); // NOI18N
            category.setValid(false);
        }
    }

    private final class DefaultDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateData();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validateData();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validateData();
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
        dockerContainersComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        dockerExecPath = new javax.swing.JTextField();
        editDockerExecutablePathButton = new javax.swing.JButton();
        dockerContainerExecDocLink = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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

        org.openide.awt.Mnemonics.setLocalizedText(openContainerConfigPanelBtn, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.openContainerConfigPanelBtn.text")); // NOI18N
        openContainerConfigPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openContainerConfigPanelBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jLabel3.text")); // NOI18N

        dockerExecPath.setText(org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.dockerExecPath.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editDockerExecutablePathButton, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.editDockerExecutablePathButton.text")); // NOI18N
        editDockerExecutablePathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDockerExecutablePathButtonActionPerformed(evt);
            }
        });

        dockerContainerExecDocLink.setFont(new java.awt.Font("Cantarell", 2, 15)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(dockerContainerExecDocLink, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.dockerContainerExecDocLink.text")); // NOI18N
        dockerContainerExecDocLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dockerContainerExecDocLinkMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dockerContainerExecDocLinkMousePressed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(DockerExecutableConfigPanel.class, "DockerExecutableConfigPanel.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LBL_ConfigName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ConfigOptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configDel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dockerExecPath, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editDockerExecutablePathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jsCommandsDockerEnabled)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jsCommandsDockerConfigCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LBL_BashType)
                            .addComponent(LBL_ContainerName)
                            .addComponent(LBL_User)
                            .addComponent(LBL_DockerWorkdir))
                        .addGap(172, 172, 172)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dockerContainersComboBox, 0, 206, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openContainerConfigPanelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dockerBashType)
                            .addComponent(dockerVolumeDir)
                            .addComponent(dockerUser))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dockerContainerExecDocLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dockerExecPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editDockerExecutablePathButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfigOptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(configNew)
                    .addComponent(configDel)
                    .addComponent(LBL_ConfigName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dockerContainerExecDocLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LBL_ContainerName)
                    .addComponent(openContainerConfigPanelBtn)
                    .addComponent(dockerContainersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void configNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configNewActionPerformed
        NotifyDescriptor.InputLine d = new NotifyDescriptor.InputLine(
                NbBundle.getMessage(DockerExecutableConfigPanel.class, "LBL_ConfigurationName"), // NOI18N
                NbBundle.getMessage(DockerExecutableConfigPanel.class, "LBL_CreateNewConfiguration")); // NOI18N

        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.OK_OPTION) {
            String name = d.getInputText();
            if (name.trim().length() == 0) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(DockerExecutableConfigPanel.class, "MSG_ConfigurationNameBlank"), // NOI18N
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }
            String configName = name.replaceAll("[^a-zA-Z0-9_.-]", "_"); // NOI18N

            if (dockerCommandModel.profileExists(configName)) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(DockerExecutableConfigPanel.class, "WARNING_ConfigurationExists", configName), // NOI18N
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }

            if (dockerContainersComboBox.getSelectedItem() == null) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(DockerExecutableConfigPanel.class, "WARNING_missing_container_name", configName), // NOI18N
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }
            
            if (dockerContainersComboBox.getSelectedItem() != null) {
                DockerExecConfiguration config = buildConfig();
                dockerCommandModel.saveConfig(config, configName);

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

    private void configDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configDelActionPerformed
        String selectedConfig = (String) comboModel.getSelectedItem();
        String slectedJsConfig = (String) javascriptComboModel.getSelectedItem();
        dockerCommandModel.removeProfileConfig(selectedConfig);
        
        //remove config from JcomboBox
        comboModel.removeElement(selectedConfig);
        if (slectedJsConfig.equals(slectedJsConfig)) {
            javascriptComboModel.removeElement(selectedConfig);
        }

        //reset
        dockerCommandModel.setCurrentProfile(DEFAULT_CONFIG_NAME);
        javascriptComboModel.setSelectedItem(DEFAULT_CONFIG_NAME);
    }//GEN-LAST:event_configDelActionPerformed

    private void openContainerConfigPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openContainerConfigPanelBtnActionPerformed
        DockerContainers containers = DockerContainers.get();
        boolean ok = containers.openManager();
        
        if (ok) {
            dockerContainerListModel.setElements(DockerContainers.get().getDockerContainers());
        }
    }//GEN-LAST:event_openContainerConfigPanelBtnActionPerformed

    private void dockerContainerExecDocLinkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dockerContainerExecDocLinkMousePressed
        try {
            HtmlBrowser.URLDisplayer.getDefault().showURL(new URL("https://docs.docker.com/reference/cli/docker/container/exec/")); // NOI18N
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
    }//GEN-LAST:event_dockerContainerExecDocLinkMousePressed

    private void dockerContainerExecDocLinkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dockerContainerExecDocLinkMouseEntered
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_dockerContainerExecDocLinkMouseEntered

    private void editDockerExecutablePathButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDockerExecutablePathButtonActionPerformed
        EditDockerExecutablePathPanel editPanel = new EditDockerExecutablePathPanel();
        if (editPanel.open()) {
            String executablePath = editPanel.getDockerExecutablePath();
            DockerExecutablePreference.setDockerExecutablePath(executablePath);
            dockerExecPath.setText(executablePath);
        }
    }//GEN-LAST:event_editDockerExecutablePathButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ConfigOptionCombo;
    private javax.swing.JLabel LBL_BashType;
    private javax.swing.JLabel LBL_ConfigName;
    private javax.swing.JLabel LBL_ContainerName;
    private javax.swing.JLabel LBL_DockerWorkdir;
    private javax.swing.JLabel LBL_User;
    private javax.swing.JButton configDel;
    private javax.swing.JButton configNew;
    private javax.swing.JTextField dockerBashType;
    private javax.swing.JLabel dockerContainerExecDocLink;
    private javax.swing.JComboBox<String> dockerContainersComboBox;
    private javax.swing.JTextField dockerExecPath;
    private javax.swing.JTextField dockerUser;
    private javax.swing.JTextField dockerVolumeDir;
    private javax.swing.JButton editDockerExecutablePathButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox<String> jsCommandsDockerConfigCombo;
    private javax.swing.JCheckBox jsCommandsDockerEnabled;
    private javax.swing.JButton openContainerConfigPanelBtn;
    // End of variables declaration//GEN-END:variables
}
