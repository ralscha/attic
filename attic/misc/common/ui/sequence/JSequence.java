package common.ui.sequence;

/*
=====================================================================

  JSequence.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class JSequence extends JPanel
  implements SequenceListener
{
  protected SequenceList list;
  protected int current = 0;

  public JSequence(SequenceList list)
  {
    setBackground(Color.white);
    setOpaque(false);
    this.list = list;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    int count = list.getSequenceItemCount();
    for (int i = 0; i < count; i++)
    {
      SequenceItemView view = new SequenceItemView();
      SequenceItem item = list.getSequenceItem(i);
      item.addSequenceListener(this);
      item.addSequenceListener(view);
      add(view);
    }
  }
  
  public void execute()
  {
    int count = list.getSequenceItemCount();
    if (count > 0)
    {
      current = 0;
      list.getSequenceItem(0).execute();
    }
  }

  public void progressReady(SequenceEvent event) {}
  public void progressUpdate(SequenceEvent event) {}
  public void progressDone(SequenceEvent event)
  {
    int count = list.getSequenceItemCount();
    current++;
    if (current < count)
    {
      list.getSequenceItem(current).execute();
    }
  }
  
  public JScrollPane getScrollPane()
  {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(getBackground());
    panel.add(BorderLayout.NORTH, this);
    return new JScrollPane(panel);
  }
}

