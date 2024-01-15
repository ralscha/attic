/*
 * RemoteTableModelEvent.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetable;

import java.util.EventObject;
import javax.swing.table.TableModel;
import javax.swing.event.TableModelEvent;

/**
 * RemoteTableModelEvent is used to notify listeners that a remote table
 * model has changed. The event describes changes to a RemoteTableModel
 * and all references to rows and columns are in the coordinate system
 * of the model. The event contains the values for the updated cells. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class RemoteTableModelEvent extends EventObject {

    /** Identifies the addition of new rows or columns. */
    public static final int INSERT =  TableModelEvent.INSERT;
    /** Identifies a change to existing data. */
    public static final int UPDATE =  TableModelEvent.UPDATE;
    /** Identifies the removal of rows or columns. */
    public static final int DELETE = TableModelEvent.DELETE;

    /** Identifies the header row. */
    public static final int HEADER_ROW = TableModelEvent.HEADER_ROW;
    /** Specifies all columns in a row or rows. */
    public static final int ALL_COLUMNS = TableModelEvent.ALL_COLUMNS;

//
//  Instance Variables
//

//NOTE: Change to 'final' if using Java 2 compilers.
    private /*final*/ int type;
    private /*final*/ int firstRow;
    private /*final*/ int lastRow;
    private /*final*/ int column;
    private /*final*/ Object data;

//
// Constructors
//

    /**
     *  The cells from (firstRow, column) to (lastRow, column) have been
     *  changed. The <I>column</I> refers to the column index of the cell
     *  in the model's coordinate system. When <I>column</I> is ALL_COLUMNS,
     *  all cells in the specified range of rows are considered changed.
     *  <p>
     *  The <I>type</I> should be one of: INSERT, UPDATE and DELETE.
     */
    public RemoteTableModelEvent(RemoteTableModel source, int firstRow,
                        int lastRow, int column, int type, Object data) {
        super(source);
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.column = column;
        this.type = type;
        this.data = data;
    }

    /**
     *  The cells described by <I>event</I> have changed. 
     */
    public RemoteTableModelEvent(RemoteTableModel source, TableModelEvent event) {
        super(source);
        this.firstRow = event.getFirstRow();
        this.lastRow = event.getLastRow();
        this.column = event.getColumn();
        this.type = event.getType();
        this.data = getValues(event);
    }

    /**
     * Transfers the data from this event to <I>model</I>.
     * 
     * @throws IllegalStateException if this event is a column
     *                  structure change or delete operation
     */
    public void setValues(TableModel model) {
        if (firstRow != HEADER_ROW && type != DELETE) {
            setValues(model, data, firstRow, lastRow, getColumn());
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Sets new values in <I>model</I>. The cells from (firstRow, column)
     * to (lastRow, column) are assigned new values from <I>data</I>. 
     */
    public static void setValues(TableModel model, Object data,
                                 int firstRow, int lastRow, int column) {
        /* Unpack the data. */
        if (firstRow == lastRow) {
            if (column != ALL_COLUMNS) {
                // single value
                model.setValueAt(data, firstRow, column);
            } else {
                // single row
                Object[] value = (Object[]) data;
                for (int i = 0; i < value.length; i++) {
                    model.setValueAt(value[i], firstRow, i);
                }
            }
        } else {
            // multiple rows (or all rows)
            Object[] value = (Object[]) data;
            if (lastRow != Integer.MAX_VALUE &&
                lastRow != firstRow + value.length - 1) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < value.length; i++) {
                setValues(model, value[i], firstRow+i, firstRow+i, column);
            }
        }
    }

    /**
     * Gets the data for the change described by <I>event</I>.
     */
    protected static Object getValues(TableModelEvent event) {
        TableModel model = (TableModel) event.getSource();
        int firstRow = event.getFirstRow();
        int lastRow = event.getLastRow();
        int column = event.getColumn();
        int type = event.getType();
        if (firstRow != HEADER_ROW && type != DELETE) {
            return getValues(model, firstRow, lastRow, column);
        } else {
            return null;
        }
    }

    /**
     * Gets a block of values from <I>model</I>. Values are fetched
     * from cells (firstRow, column) to (lastRow, column). 
     */
    public static Object getValues(TableModel model, int firstRow,
                                   int lastRow, int column) {
        /* Pack the data. */
        if (firstRow == lastRow) {
            if (column != ALL_COLUMNS) {
                // single cell
                return model.getValueAt(firstRow, column);
            } else {
                // single row
                Object[] value = new Object[model.getColumnCount()];
                for (int i = 0; i < value.length; i++) {
                    value[i] = model.getValueAt(firstRow, i);
                }
                return value;
            }
        } else {
            // multiple rows
            if (lastRow == Integer.MAX_VALUE) {
                lastRow = model.getRowCount() - 1;
            }
            Object[] value = new Object[lastRow - firstRow + 1];
            for (int i = 0; i < value.length; i++) {
                value[i] = getValues(model, firstRow+i, firstRow+i, column);
            }
            return value;
        }
    }

//
// Querying Methods
//

    /**
     *  Returns the first row that changed.  HEADER_ROW means the meta data,
     *  ie. names, types and order of the columns.
     */
    public int getFirstRow() { return firstRow; };

    /** Returns the last row that changed. */
    public int getLastRow() { return lastRow; };

    /**
     *  Returns the column for the event.  If the return
     *  value is ALL_COLUMNS; it means every column in the specified
     *  rows changed.
     */
    public int getColumn() { return column; };

    /** Returns the type of event - one of: INSERT, UPDATE and DELETE. */
    public int getType() { return type; }

    /** Returns the data for the event. */
    public Object getData() { return data; }
}

