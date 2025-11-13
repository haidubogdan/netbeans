package org.netbeans.modules.languages.env;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.lookup.ServiceProvider;

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
    ),}
)

@ServiceProvider(service = MIMEResolver.class)
public class EnvFileResolver extends MIMEResolver {
    public static final String ENV_EXT = "env"; //NOI18N
    public static final String MIME_TYPE = "text/x-env"; //NOI18N

    public EnvFileResolver() {
        super(MIME_TYPE);
    }

    @CheckForNull
    @Override
    public String findMIMEType(@NonNull final FileObject fo) {
        final String ext = fo.getExt();
        
        if (ext.equals(ENV_EXT)) {
            return MIME_TYPE;
        }
        
        final String nameWithExt = fo.getNameExt().toLowerCase();
        
        if (nameWithExt.equals("." + ENV_EXT)) { //NOI18N
            return MIME_TYPE;
        }
        
        String[] nameParts = nameWithExt.split("\\."); //NOI18N
        if (nameParts.length < 2) {
            return null;
        }
        if (nameParts[nameParts.length - 2].equals(ENV_EXT)) {
            return MIME_TYPE;
        }
        return null;
    }
}
