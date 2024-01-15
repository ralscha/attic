package common.ui.meter;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class JMeter extends JPanel
  implements ChangeListener
{
  protected CellRendererPane rendererPane =
    new CellRendererPane();
  protected RadialDivisionRenderer renderer =
    new DefaultRadialDivisionRenderer();
  protected BoundedRangeModel model;
  protected Hashtable labels;
  
  protected QuadrantManager quadrant;
  protected int startAngle;
  protected int extentAngle;
  protected int radius;
  protected int normal = 50;
  protected int danger = 75;
  
  protected Color backgroundColor = Color.black;
  protected Color needleColor = Color.white;

  public JMeter(int start, int extent, int radius)
  {
    startAngle = start % 360;
    extentAngle = extent % 360;
    if (extentAngle == 0) extentAngle = 360;
    quadrant = new QuadrantManager(startAngle, extentAngle);
    this.radius = radius;
    setOpaque(false);
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    setModel(new DefaultBoundedRangeModel());
    labels = createStandardLabels();
  }
  
  protected Hashtable createStandardLabels()
  {
    Hashtable table = new Hashtable();
    for (int i = 0; i <= 100; i += 25)
      table.put(new Integer(i), "" + i);
    return table;
  }
  
  public BoundedRangeModel getModel()
  {
    return model;
  }
  
  public void setModel(BoundedRangeModel model)
  {
    if (model != null)
      model.removeChangeListener(this);
    this.model = model;
    model.addChangeListener(this);
  }
  
  public void paintComponent(Graphics gc)
  {
    Insets insets = getInsets();
    int width = getSize().width;
    int height = getSize().height;
    int w = width - (insets.left + insets.right);
    int h = height - (insets.top + insets.bottom);

    Point center = quadrant.getCenter(getSize(), insets);
    
    Graphics2D g = (Graphics2D)gc;
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    
    int rad = radius;
    // Draw background
    Shape arc = getArc(center, rad);
    g.setColor(backgroundColor);
    g.fill(arc);

    // Oustide border
    Shape outsideBorder = getArc(center, rad);
    g.setPaint(new GradientPaint(
        center.x - radius, center.y - radius, Color.white,
        center.x + radius, center.y + radius, Color.black));
    g.fill(outsideBorder);
    
    rad -= 2;
    // Inside border
    Shape insideBorder = getArc(center, rad);
    g.setPaint(new GradientPaint(
        center.x - radius, center.y - radius, Color.black,
        center.x + radius, center.y + radius, Color.white));
    g.fill(insideBorder);

    rad -= 2;
    // Inside fill
    Shape centerShape = getArc(center, rad);
    g.setColor(backgroundColor);
    g.fill(centerShape);

    Component rendererComponent =
      renderer.getRadialDivisionRendererComponent(
        model.getMinimum(), model.getMaximum(),
        normal, danger,
        center, radius - 22, radius - 8,
        labels, true, startAngle, extentAngle);
    rendererPane.paintComponent(gc, rendererComponent,
      this, 0, 0, getSize().width, getSize().height);
    
    rad -= 5;
    // Draw needle
    drawNeedle(g, center,
      getAngleForValue(model.getValue()), rad, 5);
    
    int s = 10;
    g.setPaint(new GradientPaint(
      center.x - s, center.y - s, Color.white,
      center.x + s, center.y + s, Color.black));
    g.fillOval(center.x - s, center.y - s, s * 2, s * 2);
  }
  
  public void setValue(int value)
  {
    model.setValue(value);
  }

  public void stateChanged(ChangeEvent event)
  {
    repaint();
  }
  
  protected Shape getArc(Point center, int radius)
  {
    return getArc(center, startAngle, extentAngle, radius);
  }
  
  protected Shape getArc(Point center,
    double start, double extent, int radius)
  {
    return new Arc2D.Double(
      center.x - radius,
      center.y - radius,
      radius * 2, radius * 2,
      90 - start, 0 - extent,
      Arc2D.PIE);
  }
  
  protected int getAngleForValue(int value)
  {
    double range = model.getMaximum() - model.getMinimum();
    return (int)(startAngle + (extentAngle / range) *
      (value - model.getMinimum()));
  }
  
  protected void drawNeedle(Graphics2D g, Point axis,
    double angle, int radius, int thick)
  {
    Polygon polygon = new Polygon();
    polygon.addPoint(0, -thick);
    polygon.addPoint(-thick, 0);
    polygon.addPoint(0, radius);
    polygon.addPoint(thick, 0);
    
    AffineTransform transform = new AffineTransform();
    transform.translate(axis.x, axis.y);
    transform.rotate(Math.toRadians(angle - 180));
    
    Shape shape = transform.createTransformedShape(polygon);
    g.setColor(needleColor);
    g.fill(shape);
  }

  public Dimension getMinimumSize()
  {
    return getPreferredSize();
  }
  
  public Dimension getPreferredSize()
  {
    Insets insets = getInsets();
    Dimension size = quadrant.getPreferredSize(radius);
    size.width += insets.left + insets.right;
    size.height += insets.top + insets.bottom;
    return size;
  }
}

