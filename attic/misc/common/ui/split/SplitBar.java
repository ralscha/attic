package common.ui.split;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class SplitBar extends JPanel
{
  protected TexturePaint paint;
  protected boolean transparency;
  
  public SplitBar()
  {
    transparency = true;
    BufferedImage image = new BufferedImage(
      2, 2, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    g.setColor(Color.black);
    g.drawLine(0, 0, 0, 0);
    g.drawLine(1, 1, 1, 1);
    paint = new TexturePaint(image,
      new Rectangle(0, 0, 2, 2));
    setPreferredSize(new Dimension(4, 4));
  }
  
  public void setTransparency(boolean transparency)
  {
    this.transparency = transparency;
  }
  
  public void paintComponent(Graphics gc)
  {
    if (transparency) return;
    int w = getSize().width;
    int h = getSize().height;
    Graphics2D g = (Graphics2D)gc;
    g.setPaint(paint);
    g.fillRect(0, 0, w, h);
  }
}

