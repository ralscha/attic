/*
 * InvokeDialogTest.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.invokedialog;

import common.swing.InvokeUtils;

import java.awt.event.*;
import javax.swing.*;

/**
 * Tests the InvokeUtils.invokeXXXDialog method. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class InvokeDialogTest extends JApplet implements Runnable {

// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel;
// End of variables declaration//GEN-END:variables

    /** Worker thread. */
    private Thread thread;

    /** Initializes the Form */
    public InvokeDialogTest() {
        initComponents ();
    }

    /**
     * Called from within the constructor to initialize the form.
     * The content of this method is generated by NetBeans.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        getContentPane ().setLayout (new java.awt.BorderLayout ());

        jLabel = new javax.swing.JLabel ();
        jLabel.setPreferredSize (new java.awt.Dimension(200, 100));
        jLabel.setText ("Running...");
        jLabel.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);
        getContentPane ().add (jLabel, "Center");

    }//GEN-END:initComponents

    /** Start worker thread. */
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    /** Stop worker thread. */
    public synchronized void stop() {
        if (thread != null) {
            /* Try-catch is workaround for JDK1.2.1 applet security bug.
               JDK1.2.1 overzealously throws a security exception if an
               applet interrupts a thread that is no longer alive. */
            try { thread.interrupt(); } catch (Exception ex) { }
            thread = null;
        }
    }

    /**
     * Simulates a timeout every three seconds and invokes a confirm
     * dialog to see if the user wants to keep going.
     */
    public void run () {
        try {
            while (true) {
                Thread.sleep(3000);
                int n = InvokeUtils.invokeConfirmDialog(
                        jLabel,
                        "Operation timed out. Try again?",
                        "Timeout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (n != JOptionPane.YES_OPTION) {
                    break;
                }
            }
        }
        catch (InterruptedException ex) { }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jLabel.setText("Stopped");
            }
        });
    }


    /**
     * Launches this applet in a frame.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Invoke Dialog Test");
        JApplet applet = new InvokeDialogTest();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
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