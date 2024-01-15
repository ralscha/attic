/*
 * RemoteTableModelListener.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.util.EventListener;
import java.rmi.*;

/**
 * RemoteTableModelListener defines the interface for an object that
 * listens to changes in a RemoteTableModel.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public interface RemoteTableModelListener extends EventListener, Remote {

    /**
     * This stateful notification tells listeners the location and
     * new contents of the cells, rows, or columns that changed.
     */
    void remoteTableChanged(RemoteTableModelEvent e)
        throws RemoteException;
}

