package common.ui.split;

import java.awt.*;
import javax.swing.*;
import common.ui.layout.*;

public class SplitViewLayout
  extends AbstractLayout
{
  public static final String NW = "NW";
  public static final String NE = "NE";
  public static final String SW = "SW";
  public static final String SE = "SE";
  public static final String VERT = "VERT";
  public static final String HORZ = "HORZ";
  
  protected Rectangle apex = new Rectangle(0, 0, 4, 4);
  protected JComponent nw, ne, sw, se;
  protected Component vert, horz;
  
  public SplitViewLayout() {}
  
  public void setApex(Rectangle apex)
  {
    this.apex = apex;
  }
  
  public void addLayoutComponent(Component c, Object constraints)
  {
    JComponent comp = (JComponent)c;
    synchronized (comp.getTreeLock())
    {
      if (constraints.equals(NW)) nw = comp;
      else if (constraints.equals(NE)) ne = comp;
      else if (constraints.equals(SW)) sw = comp;
      else if (constraints.equals(SE)) se = comp;
      else if (constraints.equals(VERT)) vert = comp;
      else if (constraints.equals(HORZ)) horz = comp;
      else throw new IllegalArgumentException(
        "Unrecognized constrain value: " +
          constraints.toString());
    }
  }
  
  public Dimension minimumLayoutSize(Container c)
  {
    Dimension nwSize = nw != null ?
      nw.getMinimumSize() : new Dimension(0, 0);
    Dimension neSize = ne != null ?
      ne.getMinimumSize() : new Dimension(0, 0);
    Dimension swSize = sw != null ?
      sw.getMinimumSize() : new Dimension(0, 0);
    Dimension seSize = se != null ?
      se.getMinimumSize() : new Dimension(0, 0);
    Dimension vertSize = vert != null ?
      vert.getMinimumSize() : new Dimension(0, 0);
    Dimension horzSize = horz!= null ?
      horz.getMinimumSize() : new Dimension(0, 0);
    int w = horzSize.width +
      Math.max(nwSize.width, swSize.width) +
      Math.max(neSize.width, seSize.width);
    int h = vertSize.height +
      Math.max(nwSize.height, neSize.height) +
      Math.max(swSize.height, seSize.height);
    return new Dimension(w, h);
  }
  
  public Dimension preferredLayoutSize(Container c)
  {
    Dimension nwSize = nw != null ?
      nw.getPreferredSize() : new Dimension(0, 0);
    Dimension neSize = ne != null ?
      ne.getPreferredSize() : new Dimension(0, 0);
    Dimension swSize = sw != null ?
      sw.getPreferredSize() : new Dimension(0, 0);
    Dimension seSize = se != null ?
      se.getPreferredSize() : new Dimension(0, 0);
    Dimension vertSize = vert != null ?
      vert.getPreferredSize() : new Dimension(0, 0);
    Dimension horzSize = horz!= null ?
      horz.getPreferredSize() : new Dimension(0, 0);
    int w = horzSize.width +
      Math.max(nwSize.width, swSize.width) +
      Math.max(neSize.width, seSize.width);
    int h = vertSize.height +
      Math.max(nwSize.height, neSize.height) +
      Math.max(swSize.height, seSize.height);
    return new Dimension(w, h);
  }
  
  public void layoutContainer(Container c)
  {
    int w = c.getSize().width;
    int h = c.getSize().height;
    int r = apex.x + apex.width;
    int b = apex.y + apex.height;
    if (nw != null)
    {
      nw.setBounds(0, 0, apex.x, apex.y);
      if (nw.isValidateRoot())
      {
        nw.doLayout();
        nw.revalidate();
      }
    }
    if (ne != null)
    {
      ne.setBounds(r, 0, w - r, apex.y);
      if (ne.isValidateRoot())
      {
        ne.doLayout();
        ne.revalidate();
      }
    }
    if (sw != null)
    {
      sw.setBounds(0, b, apex.x, h - b);
      if (sw.isValidateRoot())
      {
        sw.doLayout();
        sw.revalidate();
      }
    }
    if (se != null)
    {
      se.setBounds(r, b, w - r, h - b);
      if (se.isValidateRoot())
      {
        se.doLayout();
        se.revalidate();
      }
    }
  }
}
