/*
 * DefaultModelTemplate.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.remotetabledemo;

import javax.swing.table.DefaultTableModel;

/**
 * DefaultModelTemplate defines the column structure and
 * other meta data shared by the demo server and its clients. 
 * This template was extracted from the Java Tutorial. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class DefaultModelTemplate extends DefaultTableModel {

    /** Column names. */
    private static final String[] names = {
        "First Name", "Last Name", "Sport", "# of Years", "Vegetarian"
    };

    /** Column classes. */
    private static final Class[] classes = new Class[] {
        String.class, String.class, String.class, Integer.class, Boolean.class
    };

    /** Creates an empty model. */
    public DefaultModelTemplate() {
        super(names, 0);
    }

    /** Creates a model with the given data. */
    public DefaultModelTemplate(Object[][] data) {
        super(data, names);
    }

    public Class getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    public boolean isCellEditable (int rowIndex, int columnIndex) {
        // first two columns aren't editable
        return (columnIndex > 1);
    }

}

