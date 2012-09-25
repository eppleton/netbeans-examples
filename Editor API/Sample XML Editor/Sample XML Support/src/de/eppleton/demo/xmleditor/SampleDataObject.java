/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.demo.xmleditor;

import java.io.IOException;
import org.netbeans.api.xml.cookies.CheckXMLCookie;
import org.netbeans.api.xml.cookies.ValidateXMLCookie;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.NbBundle.Messages;
import org.xml.sax.InputSource;

@Messages({
    "LBL_Sample_LOADER=Files of Sample"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Sample_LOADER",
mimeType = "text/x-sample+xml",
extension = {".smpl"})
@DataObject.Registration(
    mimeType = "text/x-sample+xml",
iconBase = "SET/PATH/TO/ICON/HERE",
displayName = "#LBL_Sample_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-sample+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class SampleDataObject extends MultiDataObject {

    public SampleDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        InputSource inputSource = DataObjectAdapters.inputSource(this);

        CheckXMLCookie checkCookie = new CheckXMLSupport(inputSource);

        getCookieSet().add(checkCookie);
        ValidateXMLCookie validateXMLCookie = new ValidateXMLSupport(inputSource);
        getCookieSet().add(validateXMLCookie);
        registerEditor("text/x-sample+xml", false);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }
}
