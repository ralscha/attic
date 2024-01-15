package common.ui.organizer;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class OrganizableListPane extends JPanel
  implements OrganizableList, OrganizerConstants
{
  protected JList list;
  protected OrganizerToolbar toolbar;
  protected ListSelectionModel selectionModel;
  
  public OrganizableListPane(String title, JList list,
    OrganizerInput input, int orderButtons)
  {
    this.list = list;
    selectionModel = list.getSelectionModel();
    selectionModel.setSelectionMode(
      ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    setLayout(new BorderLayout(4, 4));
    
    if (title != null)
      add(BorderLayout.NORTH, new JLabel(title));
    
    add(BorderLayout.CENTER, new JScrollPane(list));
    
    if (orderButtons != TOOLBAR_NONE)
    {
      add(BorderLayout.SOUTH, toolbar =
        new OrganizerToolbar(this, input, orderButtons));
      selectionModel.addListSelectionListener(toolbar);
    }
  }
  
  protected Vector getElements()
  {
    Vector vector = new Vector();
    ListModel model = list.getModel();
    for (int i = 0; i < model.getSize(); i++)
    {
      vector.add(model.getElementAt(i));
    }
    return vector;
  }
  
  public int getElementCount()
  {
    return list.getModel().getSize();
  }
  
  public ListSelectionModel getSelectionModel()
  {
    return selectionModel;
  }

  public void insertSelectionList(Object[] values)
  {
    int pos = selectionModel.getMinSelectionIndex();
    if (pos < 0) pos = getElementCount();
    
    Vector vector = getElements();
    for (int i = 0; i < values.length; i++)
    {
      vector.add(pos + i, values[i]);
    }
    list.setListData(vector);
    selectionModel.setSelectionInterval(
      pos, pos + values.length - 1);
  }
  
  public Object[] getSelectionList()
  {
    return list.getSelectedValues();
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
    list.setListData(vector);
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
        Object one = vector.elementAt(i);
        Object two = vector.elementAt(i + 1);
        vector.setElementAt(two, i);
        vector.setElementAt(one, i + 1);
      }
    }
    list.setListData(vector);
    int anchor = selectionModel.getAnchorSelectionIndex() + 1;
    int lead = selectionModel.getLeadSelectionIndex() + 1;
    selectionModel.setSelectionInterval(anchor, lead);
  }
  
  public void removeSelected()
  {
    int min = selectionModel.getMinSelectionIndex();
    Vector vector = getElements();
    int size = getElementCount() - 1;
    for (int i = size; i >= 0; i--)
    {
      if (selectionModel.isSelectedIndex(i))
      {			
        vector.removeElementAt(i);
      }
    }
    list.setListData(vector);
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

