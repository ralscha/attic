package common.ui.digital;

import java.awt.*;
import java.awt.image.*;

public class PatternImage
  extends DigitalImage
{
  public static final int ON = Color.green.getRGB();
  public static final int OFF = Color.black.getRGB();

  public static final String[] PATTERN =
  {
    "01110100011000110001100011000101110", // 0
    "00100111000010000100001000010011111", // 1
    "01110100010000100010001000100011111", // 2
    "01110100010000101110000011000101110", // 3
    "00010001100101011111000100001000010", // 4
    "11111100001000011110000011000101110", // 5
    "01110100011000011110100011000101110", // 6
    "11111000010001000100001000010000100", // 7
    "01110100011000101110100011000101110", // 8
    "01110100011000101111000010000100001"  // 9
  };
  
  public static final PatternImage[] DIGIT =
  {
    new PatternImage(0),
    new PatternImage(1),
    new PatternImage(2),
    new PatternImage(3),
    new PatternImage(4),
    new PatternImage(5),
    new PatternImage(6),
    new PatternImage(7),
    new PatternImage(8),
    new PatternImage(9)
  };
  
  public PatternImage(Image image)
  {
    super(image);
  }
  
  public PatternImage(int w, int h, int type)
  {
    super(w, h, type);
  }
  
  public PatternImage(int digit)
  {
    this(5, 7, PATTERN[digit]);
  }
  
  public PatternImage(
    int w, int h, String pattern)
  {
    this(w, h, pattern, ON, OFF);
  }
  
  public PatternImage(
    int w, int h, String pattern,
    int foreground, int background)
  {
    super(w, h, TYPE_INT_RGB);
    
    for (int y = 0; y < h; y++)
    {
      for (int x = 0; x < w; x++)
      {
        char bit =  pattern.charAt(y * w + x);
        setRGB(x, y, bit == '1' ? foreground : background);
      }
    }
  }
}

