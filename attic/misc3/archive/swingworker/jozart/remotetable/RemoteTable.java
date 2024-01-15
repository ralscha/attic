/*
 * RemoteTable.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.Vector;

import common.swing.SwingWorker;
import EDU.oswego.cs.dl.util.concurrent.*;

/**
 * Extends JTable to work with remote table models.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTable extends javax.swing.JTable {

    /** The task executor. */
    private transient Executor executor;

    /** The last scheduled connection task. */
    private transient FutureResult connectTask;

    /** The current connection. */
    private transient Connection connection;

    /** The foreground color of pending cells. */
    protected Color pendingForeground = null;

    /** The background color of pending cells. */
    protected Color pendingBackground = Color.yellow;

    /**
     * Constructs a default RemoteTable which is initialized with a
     * default data model, a default column model, and a default
     * selection model.
     */
    public RemoteTable() {
        this(null, null, null);
    }

    /**
     * Constructs a RemoteTable which is initialized with <i>dm</i>
     * as the data model, a default column model, and a default
     * selection model.
     */
    public RemoteTable(DefaultTableModel dm) {
        this(dm, null, null);
    }

    /**
     * Constructs a RemoteTable which is initialized with <i>dm</i>
     * as the data model, <i>cm</i> as the column model, and a default
     * selection model.
     */
    public RemoteTable(DefaultTableModel dm, TableColumnModel cm) {
        this(dm, cm, null);
    }

    /**
     * Constructs a RemoteTable which is initialized with <i>dm</i>
     * as the data model, <i>cm</i> as the column model, and <i>sm</i>
     * as the selection model.  If any of the parameters are <b>null</b>
     * this method will initialize the table with the corresponding
     * default model. The <i>autoCreateColumnsFromModel</i> flag is set
     * to false if <i>cm</i> is non-null, otherwise it is set to true
     * and the column model is populated with suitable TableColumns
     * for the columns in <i>dm</i>.
     */
    public RemoteTable(DefaultTableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        executor = new QueuedExecutor();
    }

    /**
     * Constructs a RemoteTable with <i>numRows</i> and <i>numColumns</i>
     * of empty cells using the DefaultTableModel.  The columns will have
     * names of the form "A", "B", "C", etc.
     */
    public RemoteTable(int numRows, int numColumns) {
        this(new DefaultTableModel(numRows, numColumns));
    }

    /**
     * Constructs a RemoteTable to display the values in the Vector of
     * Vectors, <i>rowData</i>, with column names, <i>columnNames</i>.
     */
    public RemoteTable(Vector rowData, Vector columnNames) {
        this(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Constructs a RemoteTable to display the values in the two
     * dimensional array, <i>rowData</i>, with column names,
     * <i>columnNames</i>.
     */
    public RemoteTable(Object[][] rowData, Object[] columnNames) {
        this(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Sets the data model for this table to <I>newModel</I> and registers
     * for listener notifications from the new data model.
     * <p>
     * Note: The data model must be a DefaultTableModel. 
     *
     * @param newModel the new data source for this table
     * @exception IllegalArgumentException if <I>newModel</I> is null or is not a DefaultTableModel
     * @beaninfo
     * description: The DefaultTableModel that is the source of the data for this view.
     */
    public void setModel(TableModel newModel) {
        if (newModel instanceof DefaultTableModel) {
            disconnect();
            super.setModel(newModel);
        } else {
            throw new IllegalArgumentException("DefaultTableModel required");
        }
    }

    /**
     * Returns the cell value at <I>row</I> and <I>column</I>.
     */
    public Object getValueAt(int row, int column) {
        Object value = super.getValueAt(row, column);
        if (value instanceof PendingValue) {
            // unwrap pending value
            value = ((PendingValue) value).getValue();
        }
        return value;
    }

    /**
     * Sets the value for the cell at <I>row</I> and <I>column</I>.
     * <I>aValue</I> is the new value.
     */
    public void setValueAt(Object aValue, int row, int column) {
        // suppress do-nothing updates
        Object oldValue = super.getValueAt(row, column);
        if (aValue != oldValue &&
            (aValue == null || !aValue.equals(oldValue))) {
            // set pending value and start task to update remote model
            super.setValueAt(new PendingValue(aValue), row, column);
            setRemoteValueAt(aValue, row, column);
        }
    }

    /**
     * Sets the remote value for the cell at <I>row</I> and <I>column</I>.
     * <I>aValue</I> is the new value.
     */
    public FutureResult setRemoteValueAt(Object aValue, int row, int column) {
        if (connection != null) {
            // start a task to update remote model
            return update(connection, aValue, row,
                          convertColumnIndexToModel(column));
        } else {
            return null;
        }
    }

    /**
     * Returns false if not connected; otherwise returns true if
     * the cell at <I>row</I> and <I>column</I> is editable. 
     */
    public boolean isCellEditable(int row, int column) {
        return connection != null &&
            super.isCellEditable(row, column);
    }

    /**
     * Prepares the specified renderer with an appropriate value
     * from the dataModel, and an appropriate selection value from
     * the selection models. If the cell contains a pending value,
     * the pending colors are applied to the renderer component.
     * <p>
     * Notes: The renderer should be a Component instance, such as
     * DefaultTableCellRenderer. Unselected cell colors are always
     * reset to their default values. These constraints can be removed
     * if special renderers are installed that handle the pending values
     * directly. 
     */
    public Component prepareRenderer(TableCellRenderer renderer,
                                     int row, int column) {

        if (!(renderer instanceof Component)) {
            return super.prepareRenderer(renderer, row, column);
        }

        Object value = super.getValueAt(row, column);
        boolean isSelected = isCellSelected(row, column);
        boolean rowIsAnchor =
            (selectionModel.getAnchorSelectionIndex() == row);
        boolean colIsAnchor =
            (columnModel.getSelectionModel().getAnchorSelectionIndex() == column);
        boolean hasFocus = (rowIsAnchor && colIsAnchor) && hasFocus();

        boolean isPending = (value instanceof PendingValue);
        if (!isPending) {
            /* Remove any lingering pending colors. This resets
               the unselected foreground and background colors
               to their default values. */
            Component comp = (Component) renderer;
            comp.setBackground(null);
            comp.setForeground(null);
        } else {
            value = ((PendingValue) value).getValue();
        }

        Component comp = renderer.getTableCellRendererComponent(
            this, value, isSelected, hasFocus, row, column);

        if (isPending) {
            /* Apply pending colors. */
            if (pendingBackground != null) {
                comp.setBackground(pendingBackground);
            }
            if (pendingForeground != null) {
                comp.setForeground(pendingForeground);
            }
        }

        return comp;
    }

    /**
     * Returns the foreground color for pending cells,
     * or <I>null</I> if disabled.
     *
     * @return the Color used for the foreground of pending cells
     */
    public Color getPendingForeground() {
        return pendingForeground;
    }

    /**
     * Set the foreground color for pending cells, or <I>null</I>
     * to disable. 
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param pendingForeground  the Color to use in the foreground
     *                             for pending cells
     * @beaninfo
     *       bound: true
     * description: A foreground color for pending cells.
     */
    public void setPendingForeground(Color pendingForeground) {
        Color oldValue = this.pendingForeground;
        this.pendingForeground = pendingForeground;
        firePropertyChange("pendingForeground", oldValue, pendingForeground);
    }

    /**
     * Returns the background color for pending cells,
     * or <I>null</I> if disabled.
     *
     * @return the Color used for the background of pending cells
     */
    public Color getPendingBackground() {
        return pendingBackground;
    }

    /**
     * Set the background color for pending cells, or <I>null</I>
     * to disable. 
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param pendingBackground  the Color to use for the background
     *                             of pending cells
     * @beaninfo
     *       bound: true
     * description: A background color for pending cells.
     */
    public void setPendingBackground(Color pendingBackground) {
        Color oldValue = this.pendingBackground;
        this.pendingBackground = pendingBackground;
        firePropertyChange("pendingBackground", oldValue, pendingBackground);
    }

    /**
     * Returns the current connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the current connection.
     */
    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Starts a task to connect to the remote model. 
     */
    public FutureResult connect(RemoteTableModel rm) throws RemoteException {
        disconnect();
        RemoteTableModelListener lis =
            createRemoteListener((DefaultTableModel) getModel());
        connectTask = connect(rm, lis);
        return connectTask;
    }

    /**
     * Creates and starts a task to attach the remote listener
     * to the remote model. 
     */
    protected FutureResult connect(final RemoteTableModel rm,
                                   final RemoteTableModelListener lis) {

        SwingWorker worker = new SwingWorker() {
            protected Object construct() throws Exception {
                rm.addRemoteTableModelListener(lis);
                return new Connection(rm, lis);
            }
            protected void finished() {
                try {
                    setConnection((Connection) get());
                }
                catch (Exception ex) { }
                endTask(RemoteTableTaskEvent.CONNECT, this);
            }
        };

        return startTask(RemoteTableTaskEvent.CONNECT, worker);
    }

    /**
     * Starts a task to disconnect from the remote model. 
     */
    public FutureResult disconnect() {

        if (connectTask == null) {
            // no pending connection
            return null;
        }

        if (connectTask.getException() != null) {
            // pending connection failed
            connectTask = null;
            return null;
        }

        /* Schedule disconnect and clear pending connection. */
        FutureResult task = disconnect(connectTask);
        if (task != null) {
            connectTask = null;
        }
        return task;
    }

    /**
     * Creates and starts a task to remove the remote listener
     * from the remote model. 
     */
    protected FutureResult disconnect(final FutureResult connTask) {

        SwingWorker worker = new SwingWorker() {
            protected Object construct() throws Exception {
                try {
                    Connection conn = (Connection) connTask.get();
                    RemoteTableModel rm = conn.getModel();
                    RemoteTableModelListener lis = conn.getListener();
                    rm.removeRemoteTableModelListener(lis);
                    return conn; // success
                }
                catch (InvocationTargetException ex) {
                    return null; // success: connection task failed
                }
            }
            protected void finished() {
                try {
                    get(); // see if the disconnect succeeded
                    setConnection(null);
                    // Note: Clearing the connection disables editing,
                    //   so we don't bother to remove the row data.
                    //((DefaultTableModel) getModel()).setNumRows(0);
                }
                catch (Exception ex) { }
                endTask(RemoteTableTaskEvent.DISCONNECT, this);
            }
        };

        return startTask(RemoteTableTaskEvent.DISCONNECT, worker);
    }

    /**
     * Creates and starts a task to update the remote model. 
     */
    protected FutureResult update(
            final Connection conn,
            final Object aValue,
            final int row,
            final int column) {

        SwingWorker worker = new SwingWorker() {
            protected Object construct() throws Exception {
                RemoteTableModel rm = conn.getModel();
                rm.setValueAt(aValue, row, column);
                return null; // success
            }
            protected void finished() {
                endTask(RemoteTableTaskEvent.UPDATE, this);
            }
        };

        return startTask(RemoteTableTaskEvent.UPDATE, worker);
    }

    /**
     * Schedules a task and notifies the task listeners.
     */
    protected SwingWorker startTask(int type, SwingWorker worker) {
        try {
            executor.execute(worker);
            fireTaskStarted(type, worker);
            return worker;
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Notifies the task listeners that a task ended. The listener
     * can examine the task's result to see if it succeeded or failed. 
     */
    protected void endTask(int type, SwingWorker worker) {
        fireTaskEnded(type, worker);
    }

    /**
     * Add a listener to the list that's notified each time a
     * task is started or stopped.
     *
     * @param l the RemoteTableTaskListener
     */
    public void addRemoteTableTaskListener(RemoteTableTaskListener l) {
        listenerList.add(RemoteTableTaskListener.class, l);
    }

    /**
     * Remove a listener from the list that's notified each time a
     * task is started or stopped.
     *
     * @param l the RemoteTableTaskListener
     */
    public void removeRemoteTableTaskListener(RemoteTableTaskListener l) {
        listenerList.remove(RemoteTableTaskListener.class, l);
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     */
    protected void fireTaskStarted(int type, FutureResult result) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==RemoteTableTaskListener.class) {
                // Lazily create the event:
                RemoteTableTaskEvent event =
                    new RemoteTableTaskEvent(this, type, result);
                RemoteTableTaskListener lis =
                    (RemoteTableTaskListener) listeners[i+1];
                lis.taskStarted(event);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     */
    protected void fireTaskEnded(int type, FutureResult result) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==RemoteTableTaskListener.class) {
                // Lazily create the event:
                RemoteTableTaskEvent event =
                    new RemoteTableTaskEvent(this, type, result);
                RemoteTableTaskListener lis =
                    (RemoteTableTaskListener) listeners[i+1];
                lis.taskEnded(event);
            }
        }
    }


    /**
     * Creates a remote listener to update <I>model</I>.
     */
    protected RemoteTableModelListener createRemoteListener(DefaultTableModel model)
        throws RemoteException {
        return new DefaultRemoteTableModelListener(model);
    }


    /**
     * Tags a pending value in the local table model. 
     */
    public static class PendingValue implements Serializable {
        /** Pending value. */
        private final Object value;
        public PendingValue(Object value) {
            this.value = value;
        }
        public Object getValue() {
            return value;
        }
        public String toString() {
            return "[Pending "+value+"]";
        }
    }


    /**
     * Holds the connection state.
     */
    public static class Connection {
        private final RemoteTableModel model;
        private final RemoteTableModelListener listener;
        public Connection(RemoteTableModel rm, RemoteTableModelListener lis) {
            this.model = rm;
            this.listener = lis;
        }
        public RemoteTableModel getModel() {
            return model;
        }
        public RemoteTableModelListener getListener() {
            return listener;
        }
    }

}