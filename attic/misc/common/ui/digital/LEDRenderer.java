package common.ui.digital;

import java.awt.*;
import javax.swing.*;

public class LEDRenderer extends JPanel
  implements DigitalRenderer
{
  protected static final Color DARK =
    new Color(32, 32, 32);
  
  public static final int SQUARE = 1;
  public static final int ROUND = 2;
  
  protected int type;
  
  public LEDRenderer()
  {
    this(SQUARE);
  }
  
  public LEDRenderer(int type)
  {
    this.type = type;
  }
  
  public JComponent getDigitalRendererComponent(
    JComponent parent, int color)
  {
    setForeground(new Color(color));
    return this;
  }
  
  public void paintComponent(Graphics gc)
  {
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    Graphics2D g = (Graphics2D)gc;
    
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    
    Color color = getForeground();
    if (color.equals(Color.black))
    {
      color = DARK;
    }
    Color shade = color.darker();
    Color light = color.brighter();
    
    if (type == ROUND)
    {
      g.setPaint(new GradientPaint(
        1, 1, light, w - 2, h - 2, shade));
      g.fillOval(0, 0, w, h);
      if (color != DARK)
      {
        g.setColor(Color.white);
        g.drawLine(w / 4 + 1, h / 4, w / 4 + 1, h / 4);
        g.drawLine(w / 4, h / 4 + 1, w / 4, h / 4 + 1);
      }
    }
    else
    {
      g.setPaint(new GradientPaint(
        0, 0, light, w, h, shade));
      g.fillRect(0, 0, w, h);
    }
  }
}

