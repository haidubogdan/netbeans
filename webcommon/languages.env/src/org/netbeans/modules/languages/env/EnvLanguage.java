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
package org.netbeans.modules.languages.env;

import org.netbeans.api.lexer.Language;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import static org.netbeans.modules.languages.env.EnvLanguage.MIME_TYPE;
import org.netbeans.modules.languages.env.lexer.EnvLexer;
import org.netbeans.modules.languages.env.lexer.EnvTokenId;
import org.netbeans.modules.languages.env.lexer.EnvTokenId.EnvLanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.*;
import org.openide.windows.TopComponent;

@MIMEResolver.ExtensionRegistration(
        extension = {"env", "Env"},
        displayName = "Env",
        mimeType = MIME_TYPE,
        position = 196
)

@ActionReferences({
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.PasteAction"),
            position = 500,
            separatorAfter = 600
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 700
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 800,
            separatorAfter = 900
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 1000,
            separatorAfter = 1100
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1200,
            separatorAfter = 1300
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1400
    ),
    @ActionReference(
            path = "Loaders/text/x-env/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1500
    ),
    @ActionReference(
            path = "Editors/text/x-env/Popup",
            id = @ActionID(category = "Refactoring", id = "org.netbeans.modules.refactoring.api.ui.WhereUsedAction"),
            position = 1600
    ),})

@LanguageRegistration(mimeType = "text/x-env", useMultiview = true)
public class EnvLanguage extends DefaultLanguageConfig {

    public static final String MIME_TYPE = "text/x-env"; //NOI18N

    @NbBundle.Messages("Source=&Source Env")
    @MultiViewElement.Registration(displayName = "#Source",
            iconBase = "org/netbeans/modules/languages/env/resources/env_file_16.png",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "env.source",
            mimeType = MIME_TYPE,
            position = 2)
    public static MultiViewEditorElement createMultiViewEditorElement(Lookup context) {
        return new MultiViewEditorElement(context);
    }

    public EnvLanguage() {
        super();
    }

    @Override
    public Language<EnvTokenId> getLexerLanguage() {
        return language;
    }

    @Override
    public String getDisplayName() {
        return "Env"; //NOI18N
    }

    @Override
    public String getPreferredExtension() {
        return "env"; // NOI18N
    }

    @Override
    public boolean isIdentifierChar(char c) {
        return super.isIdentifierChar(c) || c == '_'; //NOI18N
    }

    @Override
    public String getLineCommentPrefix() {
        return "#"; // NOI18N
    }

    private static final Language<EnvTokenId> language
            = new EnvLanguageHierarchy() {

                @Override
                protected String mimeType() {
                    return EnvLanguage.MIME_TYPE;
                }

                @Override
                protected Lexer<EnvTokenId> createLexer(LexerRestartInfo<EnvTokenId> info) {
                    return new EnvLexer(info);
                }

            }.language();
}
