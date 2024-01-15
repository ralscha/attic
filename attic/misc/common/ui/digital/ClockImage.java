package common.ui.digital;

import java.awt.*;
import java.util.*;
import java.text.*;

public class ClockImage
  extends PatternImage
{
  protected static final Format CLOCK_FORMAT =
    new SimpleDateFormat("hh:mm:ss");
  
  public ClockImage()
  {
    super(39, 7, TYPE_INT_RGB);
  }

  public void setTime(long time)
  {
    String text = CLOCK_FORMAT.format(new Date(time));
    int w = getWidth();
    int h = getHeight();
    Graphics g = getGraphics();
    g.setColor(Color.black);
    g.fillRect(0, 0, w, h);
    
    g.setColor(Color.green);
    int x = 0;
    for (int i = 0; i < text.length(); i++)
    {
      char chr = text.charAt(i);
      if (chr == ':')
      {
        g.drawLine(x, 2, x, 2);
        g.drawLine(x, 4, x, 4);
        x += 2;
      }
      else
      {
        int digit = chr - '0';
        g.drawImage(DIGIT[digit], x, 0, null);
        x += 6;
      }
    }
    fireChangeEvent();
  }
}

