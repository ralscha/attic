/*
 * IntegerCellEditor.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.swingutils;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.text.NumberFormat;

/**
 * Editor for Integer-valued cells. Uses a JTextField component.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class IntegerCellEditor extends DefaultCellEditor {

    private final JTextField integerField;

    public IntegerCellEditor() {
        super(new JTextField());

        /* Set textfield alignment and border. */
        integerField = (JTextField) getComponent();
        integerField.setHorizontalAlignment(JTextField.RIGHT);
        integerField.setBorder(new LineBorder(Color.black));

        /* Create an integer-only format, create a document constrained
           by the format, and install the document in the textfield. 
           BUG: More digits may be entered than Integer will parse. */

        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setParseIntegerOnly(true);
        //formatter.setMaximumIntegerDigits(10);

        integerField.setDocument(new FormatDocument(formatter));
    }

    public Object getCellEditorValue() {
        try {
            return new Integer(integerField.getText());
        }
        catch (Exception ex) {
            return null;
        }
    }
}

