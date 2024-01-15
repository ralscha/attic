package common.ui.bulletlist;

import java.awt.*;
import javax.swing.*;

public class NumberListEntry extends BasicListEntry
{
  public NumberListEntry(int number,
    String label, JComponent component,
    String prefix, String suffix,
    int position, int width)
  {
    this(number,
	position == JBulletList.CONTENT_RIGHT ?
        northPanel(new JLabel(label)) :
        (JComponent)new JTextLabel(label),
      component, prefix, suffix, position, width);
  }
  
  public NumberListEntry(int number,
    JComponent label, JComponent component,
    String prefix, String suffix,
    int position, int width)
  {
    initListEntry(new JLabel(prefix + number + suffix + "  "),
      label, component, position, width);
  }
}

