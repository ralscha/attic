package common.ui.link;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class DefaultConnectionRenderer
  extends JPanel
  implements ConnectionRenderer
{
  protected JLink link;
  protected int x1, y1, x2, y2;

  public JComponent getConnnectionRendererComponent(
    JLink link, int x1, int y1, int x2, int y2)
  {
    this.link = link;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    return this;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    Shape shape = new Line2D.Double(x1, y1, x2, y2);
    g.setColor(link.getForeground());
    g.draw(shape);
  }
}

