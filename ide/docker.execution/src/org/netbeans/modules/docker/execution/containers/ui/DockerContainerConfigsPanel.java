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
package org.netbeans.modules.docker.execution.containers.ui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;
import org.netbeans.modules.docker.execution.containers.DockerContainerConfig;
import org.netbeans.modules.docker.execution.containers.DockerContainers;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author bhaidu
 */
public class DockerContainerConfigsPanel extends javax.swing.JPanel {

    private final DockerContainers dockerContainers;
    private DialogDescriptor descriptor = null;
    private final DockerContainerListModel dockerContainerListModel = new DockerContainerListModel();

    public DockerContainerConfigsPanel(DockerContainers dockerContainers) {
        this.dockerContainers = dockerContainers;
        initComponents();

        dockerContainersConfigList.setModel(dockerContainerListModel);

        registerListeners();
    }

    private void registerListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConfig();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeConfig();
            }
        });
    }

    private void removeConfig() {
        DockerContainerConfig selectedConfig = dockerContainersConfigList.getSelectedValue();

        if (selectedConfig != null) {
            dockerContainers.removeConfig(selectedConfig);
            dockerContainerListModel.removeElement(selectedConfig);
        }
    }

    public boolean open() {
        descriptor = new DialogDescriptor(
                this,
                "Docker containers",
                true,
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.OK_OPTION,
                null);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);

        try {
            dialog.setVisible(true);
        } finally {
            dialog.dispose();
        }

        return descriptor.getValue() == NotifyDescriptor.OK_OPTION;
    }

    private void addConfig() {
        NewDockerContainerConfigPanel panel = new NewDockerContainerConfigPanel();

        if (panel.open()) {
            String dockerContainerName = panel.getDockerContainerName();
            assert dockerContainerName != null;
            DockerContainerConfig config = new DockerContainerConfig(dockerContainerName);
            dockerContainers.addNewConfig(config);
            dockerContainerListModel.addElement(config);
        }
    }

    public void setConfigurations(List<DockerContainerConfig> configs) {
        dockerContainerListModel.setElements(configs);
    }

    public static final class DockerContainerListModel extends AbstractListModel<DockerContainerConfig> {

        private static final long serialVersionUID = -546879865427974L;

        private final List<DockerContainerConfig> data = new ArrayList<>();

        @Override
        public int getSize() {
            return data.size();
        }

        @Override
        public DockerContainerConfig getElementAt(int index) {
            return data.get(index);
        }

        public boolean addElement(DockerContainerConfig configuration) {
            assert configuration != null;
            return data.add(configuration);
        }

        public boolean removeElement(DockerContainerConfig configuration) {
            int idx = data.indexOf(configuration);
            if (idx == -1) {
                return false;
            }
            boolean result = data.remove(configuration);
            assert result;
            fireIntervalRemoved(this, idx, idx);
            return true;
        }

        private void setElements(List<DockerContainerConfig> configs) {
            int size = data.size();
            data.clear();
            if (size > 0) {
                fireIntervalRemoved(this, 0, size - 1);
            }
            if (configs.size() > 0) {
                data.addAll(configs);
                fireIntervalAdded(this, 0, data.size() - 1);
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        dockerContainersConfigList = new javax.swing.JList<>();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        testContainer = new javax.swing.JButton();

        jScrollPane1.setViewportView(dockerContainersConfigList);

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(DockerContainerConfigsPanel.class, "DockerContainerConfigsPanel.addButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(DockerContainerConfigsPanel.class, "DockerContainerConfigsPanel.removeButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(testContainer, org.openide.util.NbBundle.getMessage(DockerContainerConfigsPanel.class, "DockerContainerConfigsPanel.testContainer.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(testContainer)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(testContainer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JList<DockerContainerConfig> dockerContainersConfigList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton testContainer;
    // End of variables declaration//GEN-END:variables
}
