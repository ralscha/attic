package common.ui.scroller;

import java.awt.*;

public class TabLayout extends CardLayout
{
  public void show(Container parent, int index)
  {
    synchronized (parent.getTreeLock())
    {
      if (parent.getLayout() != this)
      {
        throw new IllegalArgumentException("wrong parent for TabLayout");
      }
      int ncomponents = parent.getComponentCount();
      for (int i = 0 ; i < ncomponents ; i++)
      {
        Component comp = parent.getComponent(i);
        if (comp.isVisible())
        {
          comp.setVisible(false);
          comp = parent.getComponent(index);
          comp.setVisible(true);
          parent.validate();
          return;
        }
      }
    }
  }
}

