package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public class NumberListPanel extends JBulletList
{
  public NumberListPanel(String[] labels)
  {
    this(labels, null, CONTENT_BELOW, "(", ")");
  }
  
  public NumberListPanel(String[] labels,
    JComponent[] components)
  {
    this(labels, components, CONTENT_BELOW, "(", ")");
  }
  
  public NumberListPanel(String[] labels,
    JComponent[] components, int position)
  {
    this(labels, components, position, "(", ")");
  }
  
  public NumberListPanel(String[] labels,
    JComponent[] components, int position,
    String prefix, String suffix)
  {
    int width = calculateWidth(labels, prefix, suffix);
    for (int i = 0; i < labels.length; i++)
    {
      add(new NumberListEntry(i + 1, labels[i], 
        components == null ? null : components[i],
        prefix, suffix, position, width));
    }
  }
  
  public int calculateWidth(String[] labels, String prefix, String suffix)
  {
    int width = 0;
    for (int i = 0; i < labels.length; i++)
    {
      JLabel label = new JLabel(prefix + i + suffix + "  ");
      int w = label.getPreferredSize().width;
      width = Math.max(w, width);
    }
    return width;
  }
}

