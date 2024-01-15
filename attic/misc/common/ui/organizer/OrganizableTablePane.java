package common.ui.organizer;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class OrganizableTablePane extends JPanel
  implements OrganizableList, OrganizerConstants
{
  protected JTable table;
  protected DefaultTableModel tableModel;
  protected OrganizerToolbar toolbar;
  protected ListSelectionModel selectionModel;

  public OrganizableTablePane(String title, JTable table, 
    OrganizerInput input, int orderButtons)
  {
    if (!(table.getModel() instanceof DefaultTableModel))
      throw new IllegalArgumentException("DefaultTableModel required");

    this.table = table;
    selectionModel = table.getSelectionModel();
    selectionModel.setSelectionMode(
      ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    tableModel = (DefaultTableModel)table.getModel();
    setLayout(new BorderLayout(4, 4));
    
    if (title != null)
      add(BorderLayout.NORTH, new JLabel(title));
  
    add(BorderLayout.CENTER, new JScrollPane(table));
    
    if (orderButtons != TOOLBAR_NONE)
    {
      add(BorderLayout.SOUTH, toolbar =
        new OrganizerToolbar(this, input, orderButtons));
      table.getSelectionModel().addListSelectionListener(toolbar);
    }
  }
  
  protected Vector getColumnNames()
  {
    int count = tableModel.getColumnCount();
    Vector vector = new Vector(count);
    for (int i = 0; i < count; i++)
      vector.addElement(tableModel.getColumnName(i));
    return vector;
  }
  
  protected Vector getRowVector(int row)
  {
    int count = tableModel.getColumnCount();
    Vector list = new Vector(count);
    for (int i = 0; i < count; i++)
      list.addElement(tableModel.getValueAt(row, i));
    return list;
  }
  
  protected Vector getElements()
  {
    return tableModel.getDataVector();
  }
  
  public int getElementCount()
  {
    return tableModel.getRowCount();
  }
  
  public ListSelectionModel getSelectionModel()
  {
    return selectionModel;
  }
  
  public void insertSelectionList(Object[] values)
  {
    int pos = selectionModel.getMinSelectionIndex();
    if (pos < 0) pos = getElementCount();
    for (int i = 0; i < values.length; i++)
    {
      tableModel.insertRow(pos + i, (Vector)values[i]);
    }
    selectionModel.setSelectionInterval(
      pos, pos + values.length - 1);
  }
  
  public Object[] getSelectionList()
  {
    Vector rows = tableModel.getDataVector();
    Vector vector = new Vector();
    for (int i = 0; i < rows.size(); i++)
    {
      if (selectionModel.isSelectedIndex(i))
        vector.addElement(getRowVector(i));
    }
    int size = vector.size();
    Object[] list = new Object[size];
    for (int i = 0; i < size; i++)
      list[i] = vector.elementAt(i);
    return list;
  }

  public void moveSelectedUp()
  {
    Vector vector = getElements();
    for (int i = 0; i < vector.size(); i++)
    {
      if (selectionModel.isSelectedIndex(i))
      {			
        Object one = vector.elementAt(i - 1);
        Object two = vector.elementAt(i);
        vector.setElementAt(two, i - 1);
        vector.setElementAt(one, i);
      }
    }
    int anchor = selectionModel.getAnchorSelectionIndex() - 1;
    int lead = selectionModel.getLeadSelectionIndex() - 1;
    selectionModel.setSelectionInterval(anchor, lead);
  }

  public void moveSelectedDown()
  {
    Vector vector = getElements();
    for (int i = vector.size() - 1; i >= 0; i--)
    {
      if (selectionModel.isSelectedIndex(i))
      {			
        Object one = vector.elementAt(i + 1);
        Object two = vector.elementAt(i);
        vector.setElementAt(two, i + 1);
        vector.setElementAt(one, i);
      }
    }
    int anchor = selectionModel.getAnchorSelectionIndex() + 1;
    int lead = selectionModel.getLeadSelectionIndex() + 1;
    selectionModel.setSelectionInterval(anchor, lead);
  }

  public void removeSelected()
  {
    int min = selectionModel.getMinSelectionIndex() - 1;
    int size = getElementCount() - 1;
    for (int i = size; i >= 0; i--)
    {
      if (selectionModel.isSelectedIndex(i))
      {			
        tableModel.removeRow(i);
      }
    }
    size = getElementCount() - 1;
    if (min < 0) min = 0;
    if (min > size) min = size;
    selectionModel.setSelectionInterval(min, min);
  }
  
  public JComponent getComponent()
  {
    return this;
  }
}

