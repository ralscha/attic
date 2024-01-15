/*
 * DefaultRemoteTableModelListener.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.table.DefaultTableModel;
/*if[JDK1.2.2]
import javax.swing.SwingUtilities;
  else[JDK1.2.2]*/
import java.awt.*;
import java.awt.event.InvocationEvent;
/*end[JDK1.2.2]*/

/**
 * Receives callbacks from the remote table model and transfers the
 * changes to the local model (on the event-dispatch thread). Because
 * the remote model may report that rows have been inserted or deleted,
 * the local model is required to be a DefaultTableModel, which supports
 * insertion and deletion.
 * <p>
 * Note: Prior to JDK1.2.2, RMI callbacks cannot call invokeLater.
 * This code includes a temporary workaround. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class DefaultRemoteTableModelListener extends UnicastRemoteObject
    implements RemoteTableModelListener {

    /** Local model. */
    private final DefaultTableModel model;

/*if[JDK1.2.2]
  else[JDK1.2.2]*/
    private final EventQueue eventQueue;
/*end[JDK1.2.2]*/

    /** Constructs a remote listener to update <code>model</code>. */
    public DefaultRemoteTableModelListener(DefaultTableModel model)
        throws RemoteException {
        this.model = model;
/*if[JDK1.2.2]
  else[JDK1.2.2]*/
        /* HACK ALERT: Workaround RMI callback invokeLater bug.
           Stash event queue now, assuming we are running on the
           event thread now... */
        this.eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
/*end[JDK1.2.2]*/
    }

    /**
     * Updates the local model on the event-dispatch thread.
     * 
     * @throws IllegalStateException if column structure changed
     */
    public void remoteTableChanged(final RemoteTableModelEvent event) {
        Runnable doUpdate = new Runnable() {
            public void run() {
                int type = event.getType();
                int firstRow = event.getFirstRow();
                int lastRow = event.getLastRow();

                if (firstRow == event.HEADER_ROW) {
                    new IllegalStateException("header change");
                }

                switch (type) {
                    case event.UPDATE:
                        if (lastRow != Integer.MAX_VALUE) {
                            /* Partial update. */
                            event.setValues(model);
                            break;
                        }

                        /* Full update - remove old rows and
                           and fall through to insert new rows. */
                        model.setNumRows(0);

                    case event.INSERT:
                        Object[] data = (Object[]) event.getData();
                        if (firstRow == lastRow) {
                            model.insertRow(firstRow, data);
                        } else {
                            for (int i = 0; i < data.length; i++) {
                                model.insertRow(firstRow+i, (Object[]) data[i]);
                            }
                        }
                        break;

                    case event.DELETE:
                        for (int row = lastRow; row >= firstRow; --row) {
                            model.removeRow(row);
                        }
                        break;
                }
            }
        };
/*if[JDK1.2.2]
        SwingUtilities.invokeLater(doUpdate);
  else[JDK1.2.2]*/
        eventQueue.postEvent(new InvocationEvent(
            Toolkit.getDefaultToolkit(), doUpdate));
/*end[JDK1.2.2]*/
    }
}

