/*
 * RemoteTableModel.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.rmi.*;

/**
 *  The RemoteTableModel interface specifies the methods for interrogating
 *  a remote tabular data source.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 * @see javax.swing.table.TableModel
 */
public interface RemoteTableModel extends Remote {

    /**
     * Returns the number of records managed by the data source object.
     *
     * @return the number or rows in the model
     * @throws RemoteException
     * @throws InterruptedException
     */
    int getRowCount() throws RemoteException, InterruptedException;

    /**
     * Returns the number of columns managed by the data source object.
     *
     * @return the number or columns in the model
     * @throws RemoteException
     * @throws InterruptedException
     */
    int getColumnCount() throws RemoteException, InterruptedException;

    /**
     * Returns the name of the column at <i>columnIndex</i>.
     *
     * @param columnIndex the index of column
     * @return the name of the column
     * @throws RemoteException
     * @throws InterruptedException
     */
    String getColumnName(int columnIndex)
        throws RemoteException, InterruptedException;

    /**
     * Returns the lowest common denominator Class in the column.
     *
     * @return the common ancestor class of the object values in the model.
     * @throws RemoteException
     * @throws InterruptedException
     */
    Class getColumnClass(int columnIndex)
        throws RemoteException, InterruptedException;

    /**
     * Returns true if the cell at <I>rowIndex</I> and <I>columnIndex</I>
     * is editable.  Otherwise, setValueAt() on the cell will not change
     * the value of that cell.
     *
     * @param rowIndex the row whose value is to be looked up
     * @param columnIndex the column whose value is to be looked up
     * @return true if the cell is editable.
     * @throws RemoteException
     * @throws InterruptedException
     */
    boolean isCellEditable(int rowIndex, int columnIndex)
        throws RemoteException, InterruptedException;

    /**
     * Returns an attribute value for the cell at <I>columnIndex</I>
     * and <I>rowIndex</I>.
     *
     * @param rowIndex the row whose value is to be looked up
     * @param columnIndex the column whose value is to be looked up
     * @return the value Object at the specified cell
     * @throws RemoteException
     * @throws InterruptedException
     */
    Object getValueAt(int rowIndex, int columnIndex)
        throws RemoteException, InterruptedException;

    /**
     * Returns the attribute values for all the cells in the table.
     *
     * @throws RemoteException
     * @throws InterruptedException
     */
    Object getValues() throws RemoteException, InterruptedException;

    /**
     * Returns the attribute values for the cells from
     * (firstRow, column) to (lastRow, column). When <I>column</I>
     * is ALL_COLUMNS, all cells in the specified range of rows
     * are returned.
     *
     * @throws RemoteException
     * @throws InterruptedException
     */
    Object getValues(int firstRow, int lastRow, int column)
        throws RemoteException, InterruptedException;

    /**
     * Sets an attribute value for the record in the cell at
     * <I>columnIndex</I> and <I>rowIndex</I>.  <I>aValue</I> is
     * the new value.
     *
     * @param aValue the new value
     * @param rowIndex the row whose value is to be changed
     * @param columnIndex the column whose value is to be changed
     * @throws RemoteException
     * @throws InterruptedException
     */
    void setValueAt(Object aValue, int rowIndex, int columnIndex)
        throws RemoteException, InterruptedException;

    /**
     * Sets the attribute values for the cells from
     * (firstRow, column) to (lastRow, column). When <I>column</I>
     * is ALL_COLUMNS, all cells in the specified range of rows
     * are set.
     *
     * @throws RemoteException
     * @throws InterruptedException
     */
    void setValues(Object data, int firstRow, int lastRow, int column)
        throws RemoteException, InterruptedException;

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
    void addRemoteTableModelListener(RemoteTableModelListener l)
        throws RemoteException, InterruptedException;

    /**
     * Remove a listener from the list that's notified each time a
     * change to the data model occurs.
     *
     * @param l the RemoteTableModelListener
     * @throws RemoteException
     */
    void removeRemoteTableModelListener(RemoteTableModelListener l)
        throws RemoteException;

}
