package common.ui.surface;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

public class SurfaceBorder
  implements Border
{
  protected Surface surface;
  protected Insets insets;
  
  public SurfaceBorder(Surface surface, Insets insets)
  {
    this.surface = surface;
    this.insets = insets;
  }
  
  public boolean isBorderOpaque()
  {
    return false;
  }
  
  public Insets getBorderInsets(Component parent)
  {
    return insets;
  }
  
  public void paintBorder(
    Component parent, Graphics g,
    int x, int y, int w, int h)
  {
    // We are assuming that x and y are zero;
    ImageUtil.tileImage(g, 
      SurfaceUtil.getNorthWestArea(w, h, insets),
      surface.getNorthWestImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getNorthEastArea(w, h, insets),
      surface.getNorthEastImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getSouthWestArea(w, h, insets),
      surface.getSouthWestImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getSouthEastArea(w, h, insets),
      surface.getSouthEastImage());
    
    ImageUtil.tileImage(g, 
      SurfaceUtil.getNorthArea(w, h, insets),
      surface.getNorthImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getSouthArea(w, h, insets),
      surface.getSouthImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getEastArea(w, h, insets),
      surface.getEastImage());
    ImageUtil.tileImage(g, 
      SurfaceUtil.getWestArea(w, h, insets),
      surface.getWestImage());
    g.setClip(x, y, w, h);
  }
}

