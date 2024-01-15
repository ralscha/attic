package common.ui.surface;

import java.awt.*;
import javax.swing.*;

public class JSurface extends JPanel
{
  protected Surface surface;
  
  public JSurface(Image imageFile, Insets insets)
  {
    setOpaque(true);
    Image image = ImageUtil.loadImage(imageFile);
    surface = new SurfaceImage(image, insets);
    setBorder(new SurfaceBorder(surface, insets));
  }
  
  public void paintComponent(Graphics g)
  {
    int w = getSize().width;
    int h = getSize().height;
    Insets insets = getInsets();
    ImageUtil.tileImage(g, 
      SurfaceUtil.getFillArea(w, h, insets),
      surface.getFillImage());
    g.setClip(0, 0, w, h);
  }
}

