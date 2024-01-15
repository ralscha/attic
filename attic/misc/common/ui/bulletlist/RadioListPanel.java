package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class RadioListPanel extends JBulletList
  implements ListSelectionListener
{
  public RadioListPanel(String[] labels)
  {
    this(labels, null, CONTENT_BELOW);
  }
  
  public RadioListPanel(String[] labels,
    JComponent[] components)
  {
    this(labels, components, CONTENT_BELOW);
  }
  
  public RadioListPanel(String[] labels,
    JComponent[] components, int position)
  {
    selectionModel.setSelectionMode(
      ListSelectionModel.SINGLE_SELECTION);

    ButtonGroup group = new ButtonGroup();
    JRadioButton radioButton = new JRadioButton("");
    for (int i = 0; i < labels.length; i++)
    {
      add(new RadioListEntry(i, selectionModel, labels[i], 
        components == null ? null : components[i],
        position, radioButton.getMinimumSize().width, group));
    }
    selectionModel.addListSelectionListener(this);
  }
  
  public void valueChanged(ListSelectionEvent event)
  {
    selectionList = new int[] {selectionModel.getMinSelectionIndex()};
    fireActionEvent("CheckList");
  }
}

