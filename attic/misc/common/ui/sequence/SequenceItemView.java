package common.ui.sequence;
/*
=====================================================================

  SequenceItemView.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class SequenceItemView extends JPanel
  implements SequenceListener
{
  protected JLabel label;
  protected JProgressBar bar;
  
  protected static final ImageIcon
    BLANK_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SequenceItemView.class.getResource("images/blank.gif")));
  protected static final ImageIcon
    READY_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SequenceItemView.class.getResource("images/ready.gif")));
  protected static final ImageIcon
    UPDATE_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SequenceItemView.class.getResource("images/update.gif")));
  protected static final ImageIcon
    DONE_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SequenceItemView.class.getResource("images/done.gif")));

  

  public SequenceItemView()
  {
    setOpaque(false);
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    setLayout(new BorderLayout());
    label = new JLabel("", BLANK_ICON, JLabel.LEFT);
    label.setOpaque(false);
    add(BorderLayout.NORTH, label);
    
    bar = new JProgressBar();
    bar.setOpaque(false);
    bar.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 5));
    add(BorderLayout.CENTER, bar);
    setVisible(false);
  }

  public void scrollToVisible()
  {
    Container ancestor = SwingUtilities.
      getAncestorOfClass(JViewport.class, this);
    if (ancestor != null)
    {
      JViewport viewport = (JViewport)ancestor;
      Rectangle rect = viewport.getViewRect();
      Rectangle bounds = getBounds();
      if (!rect.contains(bounds))
      {
        viewport.scrollRectToVisible(bounds);
      }
    }
  }
  
  public void progressReady(SequenceEvent event)
  {
    Object source = event.getSource();
    if (source instanceof SequenceItem)
    {
      SequenceItem item = (SequenceItem)source;
      label.setText(item.getText());
    }
    label.setIcon(READY_ICON);
    bar.setMinimum(0);
    bar.setMaximum(event.getValue());
    setVisible(true);
    scrollToVisible();
  }

  public void progressUpdate(SequenceEvent event)
  {
    label.setIcon(UPDATE_ICON);
    bar.setValue(event.getValue());
    scrollToVisible();
  }

  public void progressDone(SequenceEvent event)
  {
    bar.setValue(bar.getMaximum());
    label.setIcon(DONE_ICON);
    sleep(100);
  }

  public static void sleep(int time)
  {
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException e) {}
  }
}

