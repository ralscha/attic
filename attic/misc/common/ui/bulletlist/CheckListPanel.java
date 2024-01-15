package common.ui.bulletlist;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class CheckListPanel extends JBulletList
  implements ListSelectionListener
{
  public CheckListPanel(String[] labels)
  {
    this(labels, null, CONTENT_BELOW);
  }
  
  public CheckListPanel(String[] labels,
    JComponent[] components)
  {
    this(labels, components, CONTENT_BELOW);
  }
  
  public CheckListPanel(String[] labels,
    JComponent[] components, int position)
  {
    selectionModel.setSelectionMode(
      ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    JCheckBox checkBox = new JCheckBox("");
    for (int i = 0; i < labels.length; i++)
    {
      add(new CheckListEntry(i, selectionModel, labels[i],
        components == null ? null : components[i],
        position, checkBox.getMinimumSize().width));
    }
    selectionModel.addListSelectionListener(this);
  }

  public void valueChanged(ListSelectionEvent event)
  {
    if (event.getValueIsAdjusting()) return;
    int min = selectionModel.getMinSelectionIndex();
    int max = selectionModel.getMaxSelectionIndex();
    Vector vector = new Vector();
    for (int i = min; i <= max; i++)
    {
      if (selectionModel.isSelectedIndex(i))
        vector.addElement(new Integer(i));
    }
    int count = vector.size();
    selectionList = new int[count];
    for (int i = 0; i < count; i++)
    {
      selectionList[i] =
        ((Integer)vector.elementAt(i)).intValue();
    }
    fireActionEvent("CheckList");
  }
}

