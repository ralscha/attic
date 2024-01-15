/*
 * RemoteTableModelAdapter.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.event.*;
import javax.swing.table.TableModel;

import EDU.oswego.cs.dl.util.concurrent.*;

/**
 * Adapts a TableModel to the RemoteTableModel interface.
 * A ReadWriteLock controls access to the adapted table model. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableModelAdapter extends UnicastRemoteObject
    implements RemoteTableModel, TableModelListener {

    /** Adaptee. */
    private final TableModel model;

    /** RemoteTableModelListener(s). */
    private final EventListenerList listenerList;

    /** Read-write lock. */
    private final ReadWriteLock rwl;

    /**
     * Adapts <I>model</I>.
     *
     * @throws RemoteException
     */
    public RemoteTableModelAdapter(TableModel model) throws RemoteException {
        this.model = model;
        listenerList = new EventListenerList();
        rwl = new ReentrantWriterPreferenceReadWriteLock();
    }

    /**
     * Returns the number of records managed by the data source object.
     *
     * @return the number or rows in the model
     * @throws InterruptedException
     */
    public int getRowCount() throws InterruptedException {
        readLock().acquire();
        try {
            return model.getRowCount();
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns the number of columns managed by the data source object.
     *
     * @return the number or columns in the model
     * @throws InterruptedException
     */
    public int getColumnCount() throws InterruptedException {
        readLock().acquire();
        try {
            return model.getColumnCount();
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns the name of the column at <i>columnIndex</i>.
     *
     * @param columnIndex the index of column
     * @return the name of the column
     * @throws InterruptedException
     */
    public String getColumnName(int columnIndex)
        throws InterruptedException {
        readLock().acquire();
        try {
            return model.getColumnName(columnIndex);
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns the lowest common denominator Class in the column.
     *
     * @return the common ancestor class of the object values in the model.
     * @throws InterruptedException
     */
    public Class getColumnClass(int columnIndex)
        throws InterruptedException {
        readLock().acquire();
        try {
            return model.getColumnClass(columnIndex);
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns true if the cell at <I>rowIndex</I> and <I>columnIndex</I>
     * is editable.  Otherwise, setValueAt() on the cell will not change
     * the value of that cell.
     *
     * @param rowIndex the row whose value is to be looked up
     * @param columnIndex the column whose value is to be looked up
     * @return true if the cell is editable.
     * @throws InterruptedException
     */
    public boolean isCellEditable(int rowIndex, int columnIndex)
        throws InterruptedException {
        readLock().acquire();
        try {
            return model.isCellEditable(rowIndex, columnIndex);
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns an attribute value for the cell at <I>columnIndex</I>
     * and <I>rowIndex</I>.
     *
     * @param rowIndex the row whose value is to be looked up
     * @param columnIndex the column whose value is to be looked up
     * @return the value Object at the specified cell
     * @throws InterruptedException
     */
    public Object getValueAt(int rowIndex, int columnIndex)
        throws InterruptedException {
        readLock().acquire();
        try {
            return model.getValueAt(rowIndex, columnIndex);
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns the attribute values for all the cells in the table.
     *
     * @throws InterruptedException
     */
    public Object getValues() throws InterruptedException {
        readLock().acquire();
        try {
            /* RemoteTableModelEvent fetches the values for us. */
            TableModelEvent event = new TableModelEvent(model);
            return new RemoteTableModelEvent(this, event).getData();
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Returns the attribute values for the cells from
     * (firstRow, column) to (lastRow, column). When <I>column</I>
     * is ALL_COLUMNS, all cells in the specified range of rows
     * are returned.
     *
     * @throws InterruptedException
     */
    public Object getValues(int firstRow, int lastRow, int column)
        throws InterruptedException {
        readLock().acquire();
        try {
            /* RemoteTableModelEvent fetches the values for us. */
            TableModelEvent event =
                new TableModelEvent(model, firstRow, lastRow, column);
            return new RemoteTableModelEvent(this, event).getData();
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Sets an attribute value for the record in the cell at
     * <I>columnIndex</I> and <I>rowIndex</I>. <I>aValue</I> is
     * the new value.
     *
     * @param aValue the new value
     * @param rowIndex the row whose value is to be changed
     * @param columnIndex the column whose value is to be changed
     * @throws InterruptedException
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        throws InterruptedException {
        writeLock().acquire();
        try {
            model.setValueAt(aValue, rowIndex, columnIndex);
        }
        finally {
            writeLock().release();
        }
    }

    /**
     * Sets the attribute values for the cells from
     * (firstRow, column) to (lastRow, column). When <I>column</I>
     * is ALL_COLUMNS, all cells in the specified range of rows
     * are set.
     *
     * @throws InterruptedException
     */
    public void setValues(Object data, int firstRow, int lastRow,
                          int column) throws InterruptedException {
        writeLock().acquire();
        try {
            /* RemoteTableModelEvent sets the values for us. */
            RemoteTableModelEvent.setValues(
                model, data, firstRow, lastRow, column);
        }
        finally {
            writeLock().release();
        }
    }

    /**
     * Add a listener to the list that's notified each time a change
     * to the data model occurs. To jump-start a new client, a full
     * update event is sent to the listener just before adding the 
     * listener to the list. 
     *
     * @param l the RemoteTableModelListener
     * @throws RemoteException
     * @throws InterruptedException
     */
    public void addRemoteTableModelListener(RemoteTableModelListener l)
        throws RemoteException, InterruptedException {

        readLock().acquire();
        try {

            /* Send the listener a full update and, if successful,
               add the listener to the listener list. */

            l.remoteTableChanged(
                new RemoteTableModelEvent(this, new TableModelEvent(model)));

            synchronized (listenerList) {
                if (listenerList.getListenerCount() == 0) {
                    /* Start listening to adapted model. */
                    model.addTableModelListener(this);
                }
                listenerList.add(RemoteTableModelListener.class, l);
            }
        }
        finally {
            readLock().release();
        }
    }

    /**
     * Remove a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the RemoteTableModelListener
     */
    public void removeRemoteTableModelListener(RemoteTableModelListener l) {
        synchronized (listenerList) {
            listenerList.remove(RemoteTableModelListener.class, l);
            if (listenerList.getListenerCount() == 0) {
                /* Stop listening to adapted model. */
                model.removeTableModelListener(this);
            }
        }
    }

//
//  Local methods
//

    /**
     * TableModelListener implementation. Listens for changes in the 
     * adapted model. 
     * <p>
     * This method is public as an implementation side effect.
     * Do not call or override.
     */
    public void tableChanged(TableModelEvent event) {
        fireRemoteTableChanged(new RemoteTableModelEvent(this, event));
    }

    /**
     * Forward the given notification event to all RemoteTableModelListeners
     * that registered themselves as listeners for this remote table model.
     */
    protected void fireRemoteTableChanged(RemoteTableModelEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==RemoteTableModelListener.class) {
                RemoteTableModelListener lis =
                    (RemoteTableModelListener) listeners[i+1];
                try {
                    lis.remoteTableChanged(e);
                }
                catch (RemoteException ex) {
                    // Warning: Unresponsive listeners will be removed!
                    System.err.println("Callback failed: "+ex);
                    System.err.println("Removing "+lis);
                    removeRemoteTableModelListener(lis);
                }
            }
        }
    }

    /**
     * Returns the read lock. The read lock should be acquired before
     * attempting to read from the adapted model directly (outside
     * this adapter). 
     */
    public Sync readLock() {
        return rwl.readLock();
    }

    /**
     * Returns the write lock. The write lock should be acquired
     * before attempting to write to the adapted model directly
     * (outside this adapter). 
     */
    public Sync writeLock() {
        return rwl.writeLock();
    }

}
