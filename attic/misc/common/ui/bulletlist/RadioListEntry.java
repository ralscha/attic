package common.ui.bulletlist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RadioListEntry extends BasicListEntry
  implements ItemListener
{
  protected int index;
  protected ListSelectionModel model;
  protected JRadioButton radioButton;
  protected JComponent component;

  public RadioListEntry(int index, ListSelectionModel model,
    String label, JComponent component,
    int position, int width, ButtonGroup group)
  {
    this(index, model,
      position == JBulletList.CONTENT_RIGHT ?
        northPanel(new JLabel(label)) :
        (JComponent)new JTextLabel(label),
      component, position, width, group);
  }
  
  public RadioListEntry(int index, ListSelectionModel model,
    JComponent label, JComponent component,
    int position, int width, ButtonGroup group)
  {
    this.index = index;
    this.model = model;
    if (component != null)
      component.setEnabled(false);
    radioButton = new JRadioButton("", model.isSelectedIndex(index));
    initListEntry(radioButton, label, component, position, width);
    radioButton.addItemListener(this);
    group.add(radioButton);
    setComponentEnabled(false);
  }
  
  public void itemStateChanged(ItemEvent event)
  {
    boolean state = event.getStateChange() == ItemEvent.SELECTED;
    setComponentEnabled(state);
    if (state)
    {
      model.setSelectionInterval(index, index);
    }
  }
}

