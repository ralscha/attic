package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public class BulletListEntry extends BasicListEntry
{
  public BulletListEntry(ImageIcon icon,
    String label, JComponent component,
    int position, int width)
  {
    this(icon,
      position == JBulletList.CONTENT_RIGHT ?
        northPanel(new JLabel(label)) :
        (JComponent)new JTextLabel(label),
      component, position, width);
  }
  
  public BulletListEntry(ImageIcon icon,
    JComponent label, JComponent component,
    int position, int width)
  {
    initListEntry(new JLabel(" ", icon, JLabel.RIGHT),
      label, component, position, width);
  }
}

