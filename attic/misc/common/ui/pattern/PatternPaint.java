package common.ui.pattern;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class PatternPaint
  extends TexturePaint
{
  protected int width, height;
  protected int[] array;
  protected Color foreground, background;

  public PatternPaint(PatternPaint pattern)
  {
    this(pattern.width, pattern.height, pattern.array,
      pattern.foreground, pattern.background);
  }
  
  public PatternPaint(PatternPaint pattern,
    Color foreground, Color background)
  {
    this(pattern.width, pattern.height, pattern.array,
      foreground, background);
  }
  
  public PatternPaint(int width, int height, int[] array)
  {
    this(width, height, array, Color.black, Color.white);
  }

  public PatternPaint(int width, int height, int[] array, 
    Color foreground, Color background)
  {
    super(createTexture(width, height, array,
      foreground, background),
      new Rectangle(0, 0, width, height));
    this.width = width;
    this.height = height;
    this.array = array;
    this.foreground = foreground;
    this.background = background;
  }

  private static BufferedImage createTexture(int width, int height,
    int[] array, Color foreground, Color background)
  {
    BufferedImage image = new BufferedImage(
      width, height, BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        image.setRGB(x, y, array[x + y * width] > 0 ? 
          foreground.getRGB() : background.getRGB());
      }
    }
    return image;
  }
  
  public boolean equals(Object obj)
  {
    if (obj instanceof PatternPaint)
    {
      PatternPaint paint = (PatternPaint)obj;
      return paint.array.equals(array) &&
        paint.width == width &&
        paint.height == height;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("PatternPaint[");
    buffer.append("[width=" + width);
    buffer.append(",height=" + height);
    buffer.append(",array={");
    for (int i = 0; i < array.length; i++)
    {
      if (i > 0) buffer.append(",");
      buffer.append("" + array[i]);
    }
    buffer.append("},foreground=" + foreground);
    buffer.append(",background=" + background + "]");
    return buffer.toString();
  }
}

