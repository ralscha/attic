/*
 * FormaDocument.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.swingutils;

import javax.swing.*;
import javax.swing.text.*;
import java.text.*;
import java.awt.Toolkit;

/**
 * A Document constrained by a Format, adapted from the Java Tutorial.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class FormatDocument extends PlainDocument {

    private final Format format;

    public FormatDocument(Format f) {
        format = f;
    }

    public Format getFormat() {
        return format;
    }

    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException {

        String currentText = getText(0, getLength());
        String beforeOffset = currentText.substring(0, offs);
        String afterOffset = currentText.substring(offs, currentText.length());
        String proposedResult = beforeOffset + str + afterOffset;

        /* Allow empty strings; otherwise make sure the entire contents
           can be parsed. */

        if (proposedResult.length() > 0) {
            ParsePosition pos = new ParsePosition(0);
            if (format.parseObject(proposedResult, pos) == null ||
                pos.getIndex() < proposedResult.length()) {
                //System.err.println("insertString cannot parse: " + proposedResult);
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }

        super.insertString(offs, str, a);
    }

    public void remove(int offs, int len) throws BadLocationException {
        String currentText = getText(0, getLength());
        String beforeOffset = currentText.substring(0, offs);
        String afterOffset = currentText.substring(len + offs, currentText.length());
        String proposedResult = beforeOffset + afterOffset;

        /* Allow empty strings; otherwise make sure the entire contents
           can be parsed. */

        if (proposedResult.length() > 0) {
            ParsePosition pos = new ParsePosition(0);
            if (format.parseObject(proposedResult, pos) == null ||
                pos.getIndex() < proposedResult.length()) {
                //System.err.println("remove: could not parse: " + proposedResult);
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }

        super.remove(offs, len);
    }
}
