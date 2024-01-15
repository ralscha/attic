package common.ui.link;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class DefaultPageRenderer
  extends JPanel
  implements PageRenderer
{
  public static Dimension SIZE = new Dimension(24, 32);
  public static Color FOREGROUND = Color.black;
  public static Color BACKGROUND = Color.yellow;

  protected Page page;
  protected int x, y;
  
  public DefaultPageRenderer()
  {
    setPreferredSize(SIZE);
  }
  
  public JComponent getPageRendererComponent(
    JLink link, Page page, int x, int y)
  {
    this.page = page;
    this.x = x;
    this.y = y;
    return this;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    
    int r = 6;
    int s = 2;
    int w = getPreferredSize().width - 1;
    int h = getPreferredSize().height - 1;
    g.translate(x - w / 2, y - h / 2);
    
    Polygon poly = new Polygon();
    poly.addPoint(0, 0);
    poly.addPoint(w - r - s, 0);
    poly.addPoint(w - s, r);
    poly.addPoint(w - s, h - s);
    poly.addPoint(0, h - s);
    
    GradientPaint gradient = new GradientPaint(
      0, 0, Color.white, w, h, Color.lightGray);
    
    // Shadow
    g.setPaint(Color.gray);
    g.translate(s, s);
    g.fillPolygon(poly);
    // Gradient
    g.setPaint(gradient);
    g.translate(-s, -s);
    g.fillPolygon(poly);
    // Outline
    g.setPaint(Color.black);
    g.drawPolygon(poly);
    g.drawLine(w - r - s, 0, w - r - s, r);
    g.drawLine(w - r - s, r, w - s, r);
    
    // Name
    FontRenderContext context = g.getFontRenderContext();
    TextLayout layout = new TextLayout(
      page.getName(), getFont(), context);
    Rectangle2D bounds = layout.getBounds();
    bounds = new Rectangle2D.Double(
      bounds.getX() - 2,
      bounds.getY() - 2,
      bounds.getWidth() + 4,
      bounds.getHeight() + 4);
    
    int ww = (int)bounds.getWidth();
    int hh = (int)bounds.getHeight();
    int xx = (w - ww) / 2;
    int yy = h + hh;
    g.translate(xx, yy);
    g.setColor(BACKGROUND);
    g.fill(bounds);
    g.setColor(FOREGROUND);
    g.draw(bounds);
    layout.draw(g, 0, 0);
  }
}

