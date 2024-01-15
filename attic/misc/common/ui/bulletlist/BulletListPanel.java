package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public class BulletListPanel extends JBulletList
{
  public BulletListPanel(String[] labels)
  {
    this(new ImageIcon(JBulletList.class.getResource("icons/BulletIcon.gif")), labels, null, CONTENT_BELOW);
  }
  
  public BulletListPanel(ImageIcon icon, String[] labels)
  {
    this(icon, labels, null, CONTENT_BELOW);
  }
  
  public BulletListPanel(String[] labels, JComponent[] components)
  {
    this(new ImageIcon(JBulletList.class.getResource("icons/BulletIcon.gif")),
      labels, components, CONTENT_BELOW);
  }
  
  public BulletListPanel(ImageIcon icon,
    String[] labels, JComponent[] components,
    int position)
  {
    JLabel label = new JLabel(" ", icon, JLabel.RIGHT);
    for (int i = 0; i < labels.length; i++)
    {
      add(new BulletListEntry(icon, labels[i], 
        components == null ? null : components[i],
        position, label.getMinimumSize().width));
    }
  }
}

