package common.ui.key;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class KeyMapTableModel extends DefaultTableModel
{
  public KeyMapTableModel()
  {
    addColumn("Action");
    addColumn("Key Assignment");
  }
  
  public void setModelInputMap(InputMap inputMap)
  {
    KeyStroke[] keys = inputMap.allKeys();
    SortedMap sortedMap = new TreeMap();
    for (int i = 0; i < keys.length; i++)
    {
      sortedMap.put(inputMap.get(keys[i]).toString(), keys[i]);
    }
    int count = getRowCount();
    for (int i = count - 1; i >= 0; i--)
    {
      removeRow(i);
    }
    Iterator iterator = sortedMap.entrySet().iterator();
    while (iterator.hasNext())
    {
      Map.Entry entry = (Map.Entry)iterator.next();
      Object[] col = {entry.getKey(), entry.getValue()};
      addRow(col);
    }
  }
  
  public InputMap getModelInputMap()
  {
    InputMap inputMap = new InputMap();
    for (int i = 0; i < getRowCount(); i++)
    {
      Object stroke = getValueAt(i, 1);
      if (stroke instanceof KeyStroke)
      {
        inputMap.put((KeyStroke)stroke,
          (String)getValueAt(i, 0));
      }
    }
    return inputMap;
  }
}

