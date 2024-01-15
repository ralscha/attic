/*
 * RemoteTableServer.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetabledemo;

import java.rmi.*;
//import java.rmi.registry.*;
import javax.swing.table.*;

import jozart.remotetable.*;

/**
 * Server for a remote table model. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableServer {

    /**
     * Creates the table model to export. Override to export a
     * table model of your choice.
     * <p>
     * Note: The remote model provides the row data, but the local
     * and remote models must agree on the column structure. 
     */
    protected static TableModel createLocalModel() {
        /* DefaultModelTemplate defines the shared information.
           The row data is from the Java Tutorial. */
        return new DefaultModelTemplate (
            new Object[][] {
                {"Mary", "Campione", "Snowboarding", new Integer(5), Boolean.FALSE},
                {"Alison", "Huml", "Rowing", new Integer(3), Boolean.TRUE},
                {"Kathy", "Walrath", "Chasing toddlers", new Integer(2), Boolean.FALSE},
                {"Mark", "Andrews", "Speed reading", new Integer(20), Boolean.TRUE},
                {"Angela", "Lih", "Teaching high school", new Integer(4), Boolean.FALSE}
            }
        ) {
            /** Sleeps <code>millis</code> msecs, postponing interrupts. */
            private void delay(int millis) {
                try {
                    Thread.sleep(millis);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            /** Reads slow. */
            public Object getValueAt(int rowIndex, int columnIndex) {
                delay(120);
                return super.getValueAt(rowIndex, columnIndex);
            }

            /** Writes even slower. */
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                delay(1600);
                super.setValueAt(aValue, rowIndex, columnIndex);
            }
        };
    }

    public static void main(String[] args) {

        /* Create and install a security manager. */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            //Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            TableModel model = createLocalModel();
            RemoteTableModelAdapter adapter = new RemoteTableModelAdapter(model);
            Naming.rebind("/RemoteTableServer", adapter);
            System.out.println("RemoteTableServer bound in registry. Ready.");
        }
        catch (Exception ex) {
            System.out.println("RemoteTableServer err: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
