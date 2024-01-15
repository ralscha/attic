package common.ui.surface;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class ImageUtil
{
  public static Image loadImage(Image filename)
  {
    ImageIcon icon = new ImageIcon(filename);
    return icon.getImage();
  }


  public static BufferedImage toBufferedImage(Image image)
  {
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    BufferedImage buffer = new BufferedImage(
      width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics g = buffer.getGraphics();
    g.drawImage(image, 0, 0, null);
    return buffer;
  }
  
  public static void tileImage(
    Graphics g, Rectangle rect, Image image)
  {
    int w = image.getWidth(null);
    int h = image.getHeight(null);
    g.setClip(rect.x, rect.y, rect.width, rect.height);
    for (int x = rect.x; x < rect.x + rect.width; x += w)
    {
      for (int y = rect.y; y < rect.y + rect.height; y += h)
      {
        g.drawImage(image, x, y, null);
      }
    }
  }
}

