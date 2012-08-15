/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.nbexamples.misc.clipboard;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ClipboardEvent;
import org.openide.util.datatransfer.ClipboardListener;
import org.openide.util.datatransfer.ExClipboard;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

public class Installer extends ModuleInstall implements ClipboardListener{

    @Override
    public void restored() {
        Lookup.getDefault().lookup(ExClipboard.class).addClipboardListener(this);
    }

    @Override
    public void clipboardChanged(ClipboardEvent ev) {
        InputOutput io = IOProvider.getDefault().getIO ("ClipBoard History", true);

        ExClipboard clipboard = ev.getClipboard();   
        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            try {
                io.getOut().println("ClipBoard Content Changed:");
                io.getOut().println(""+clipboard.getData(DataFlavor.stringFlavor)+"");
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        io.getOut().close();
    }
}
