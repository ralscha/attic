/*
 * RemoteTableViewer.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetabledemo;

import java.awt.event.*;
import java.rmi.RMISecurityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Views a remote table model. A viewer is just an editor with the
 * editing capability disabled. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableViewer extends RemoteTableEditor {

    /** Creates a default model with cell editing disabled. */
    protected DefaultTableModel createLocalModel() {
        return new DefaultModelTemplate() {
            public boolean isCellEditable (int rowIndex, int columnIndex) {
                return false;
            }
        };
    }


    /**
     * Launches this applet in a frame.
     */
    public static void main(String[] args) {

        /* Create and install a security manager. */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        JFrame frame = new JFrame("Remote Table Viewer");
        final RemoteTableViewer applet = new RemoteTableViewer();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                applet.shutdown(1000);
                System.exit(0);
            }
        });
        frame.getContentPane().add(applet, "Center");
        frame.pack();
        frame.show();
        applet.init();
        applet.start();
    }

}
