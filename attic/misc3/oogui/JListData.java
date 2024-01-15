package oogui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
//swing classes
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.border.*;

public class JListData extends AbstractListModel {
    private Vector data;

    public JListData() {
        data = new Vector();
    }
//-----------------------------------------
    public int getSize() {
        return data.size();
    }
//-----------------------------------------
    public Object getElementAt(int index) {
        return data.elementAt(index);
    }
//-----------------------------------------
    public void addElement(String s) {
        data.addElement(s);
        fireIntervalAdded(this, data.size()-1, data.size());
    }
//-----------------------------------------
    public void removeElement(String s) {
        data.removeElement(s);
        fireIntervalRemoved(this, 0, data.size());
    }
//-----------------------------------------
    public void clear() {
        data = new Vector();
        fireContentsChanged(this, 0, data.size());
    }
}


