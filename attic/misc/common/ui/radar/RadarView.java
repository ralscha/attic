package common.ui.radar;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

public class RadarView extends JPanel
{
  protected static final double PI2 = Math.PI * 2;

  protected int count;
  protected JRadar radar;
  protected Point2D points[];
  protected Dimension lastSize;
  protected Shape outline;
  protected BufferedImage background;
  protected BufferedImage foreground;
  protected Point2D moveto;

  public RadarView(JRadar radar, int count)
  {
    this.radar = radar;
    this.count = count;
    setPreferredSize(new Dimension(200, 200));
  }
  
  protected void drawRuler(Graphics2D g, Point2D center,
    double angle, double radius, int ticks)
  {
    g.setColor(Color.green);
    g.setTransform(AffineTransform.getRotateInstance(
      angle, center.getX(), center.getY()));
    Line2D line = new Line2D.Double(
      center.getX(), center.getY(),
      center.getX(), center.getY() - radius);
    g.draw(line);
    double step = radius / ticks;
    for (double i = step; i < radius; i += step)
    {
      line = new Line2D.Double(
        center.getX() - 2, center.getY() - i,
        center.getX() + 2, center.getY() - i);
      g.draw(line);
    }
  }
  
  protected Shape createOutlineShape(
    Point2D center, float radius, int count)
  {
    double step = PI2 / (double)count;
    GeneralPath path = new GeneralPath();
    for (double angle = 0; angle < PI2; angle += step)
    {
      double x = center.getX() + Math.sin(angle) * radius;
      double y = center.getY() - Math.cos(angle) * radius;
      if (angle == 0)
      {
        path.moveTo((float)x, (float)y);
      }
      else
      {
        path.lineTo((float)x, (float)y);
      }
    }
    path.closePath();
    return path;
  }
  
  protected void createAmplitudeShape()
  {
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    Point2D center = new Point2D.Float(w / 2, h / 2);
    createAmplitudeShape(center, getRadius(), count);
  }
  
  protected Shape createAmplitudeShape(
    Point2D center, float radius, int count)
  {
    points = new Point2D[count];
    double max = Math.PI * 2;
    double step = max / (double)count;
    GeneralPath path = new GeneralPath();
    for (int i = 0; i < count; i++)
    {
      double angle = i * step;
      double rad = radius * radar.model.getAmplitude(i);
      double x = center.getX() + Math.sin(angle) * rad;
      double y = center.getY() - Math.cos(angle) * rad;
      points[i] = new Point2D.Double(x, y);
      if (angle == 0)
      {
        path.moveTo((float)x, (float)y);
      }
      else
      {
        path.lineTo((float)x, (float)y);
      }
    }
    path.closePath();
    return path;
  }

  protected int getRadius()
  {
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    return Math.min(w / 2, h / 2) - 10;
  }	
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    Point2D center = new Point2D.Float(w / 2, h / 2);
    int radius = getRadius();
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING ,
      RenderingHints.VALUE_ANTIALIAS_ON);
    if (lastSize == null || !lastSize.equals(getSize()))
    {
      lastSize = getSize();
      outline = createOutlineShape(center, radius, count);
      
      background = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      Graphics2D bg = (Graphics2D)background.getGraphics();
      bg.setColor(getBackground());
      bg.fillRect(0, 0, w, h);
      bg.setColor(Color.black);
      bg.fill(outline);
      
      foreground = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics2D fg = (Graphics2D)foreground.getGraphics();
      double step = PI2 / count;
      for (double angle = 0; angle < PI2; angle += step)
      {
        drawRuler(fg, center, angle, radius, 10);
      }
      fg.setTransform(AffineTransform.getRotateInstance(0));
      drawBevel(fg, outline, new BasicStroke(2), getBackground());
    }
    
    g.drawImage(background, 0, 0, this);
    Shape shape = createAmplitudeShape(center, radius, count);
    g.setPaint(Color.blue);
    g.fill(shape);
    g.drawImage(foreground, 0, 0, this);
  }

  public void drawBevel(Graphics2D g,
    Shape shape, Stroke stroke, Color color)
  {
    Color light = color.brighter();
    Color shade = color.darker();
    
    // We flatten the shape to drop curves.
    PathIterator enum = new FlatteningPathIterator(
      shape.getPathIterator(null), 1);
    g.setStroke(stroke);
    
    while (!enum.isDone())
    {
      Point2D point1 = getPoint(enum);
      enum.next();
      if (enum.isDone()) break;
      Point2D point2 = getPoint(enum);
      int x = (int)(point1.getX() + point2.getX()) / 2;
      int y = (int)(point1.getY() + point2.getY()) / 2;
      g.setColor(shape.contains(x, y) ? light : shade);
      g.draw(new Line2D.Double(point1, point2));
    }
  }

  protected Point2D getPoint(PathIterator enum)
  {
    double[] array = new double[6];
    int type = enum.currentSegment(array);
    if (type == PathIterator.SEG_CLOSE)
      return moveto;
    Point2D point = new Point2D.Double(array[0], array[1]);
    if (type == PathIterator.SEG_MOVETO)
      moveto = point;
    return point;
  }
}

