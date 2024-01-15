package common.ui.daytime;

import java.awt.*;
import javax.swing.*;

public class DayTimeLayers extends JLayeredPane
{
  public final static Integer LIST_LAYER = new Integer(0);
  public final static Integer ITEM_LAYER = new Integer(100);
  
  protected JList list;
  
  public DayTimeLayers(JList list)
  {
    this.list = list;
    list.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
    list.setBackground(getBackground());
    add(list, LIST_LAYER);
  }
  
  public Dimension getPreferredSize()
  {
    return list.getPreferredSize();
  }

  public Dimension getMinimumSize()
  {
    return list.getMinimumSize();
  }
  
  public void reshape(int x, int y, int w, int h)
  {
    super.reshape(x, y, w, h);
    list.setBounds(0, 0, w, h);
  }
}

