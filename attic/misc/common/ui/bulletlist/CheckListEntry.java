package common.ui.bulletlist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckListEntry extends BasicListEntry
  implements ItemListener
{
  protected int index;
  protected ListSelectionModel model;
  protected JCheckBox checkBox;

  public CheckListEntry(int index, ListSelectionModel model,
    String label, JComponent component,
    int position, int width)
  {
    this(index, model,
      position == JBulletList.CONTENT_RIGHT ?
        northPanel(new JLabel(label)) :
        (JComponent)new JTextLabel(label),
      component, position, width);
  }
  
  public CheckListEntry(int index, ListSelectionModel model,
    JComponent label, JComponent component,
    int position, int width)
  {
    this.index = index;
    this.model = model;
    checkBox = new JCheckBox("", model.isSelectedIndex(index));
    initListEntry(checkBox, label, component, position, width);
    checkBox.addItemListener(this);
    setComponentEnabled(false);
  }

  public void itemStateChanged(ItemEvent event)
  {
    boolean state = event.getStateChange() == ItemEvent.SELECTED;
    setComponentEnabled(state);
    if (state)
    {
      model.addSelectionInterval(index, index);
    }
    else
    {
      model.removeSelectionInterval(index, index);
    }
  }
}

