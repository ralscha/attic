package common.ui.meter;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;

public class DefaultRadialDivisionRenderer extends JPanel
  implements RadialDivisionRenderer
{
  protected int minimumValue, maximumValue;
  protected int normalValue, dangerValue;
  protected Point centerPoint;
  protected int insideRadius, outsideRadius;
  protected Hashtable labels;
  protected boolean insideLabels;
  protected int startAngle;
  protected int extentAngle;

  protected Color tickColor = Color.white;
  protected Color textColor = Color.white;
  protected Color normalValueColor = Color.green;
  protected Color dangerValueColor = Color.yellow;
  protected Color criticalColor = Color.red;
  
  public DefaultRadialDivisionRenderer()
  {
    setOpaque(false);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }
  
  public JComponent getRadialDivisionRendererComponent(
    int minimumValue, int maximumValue,
    int normalValue, int dangerValue,
    Point centerPoint,
    int insideRadius, int outsideRadius,
    Hashtable labels, boolean insideLabels,
    int startAngle, int extentAngle)
  {
    this.minimumValue = minimumValue;
    this.maximumValue = maximumValue;
    this.normalValue = normalValue;
    this.dangerValue = dangerValue;
    this.centerPoint = centerPoint;
    this.insideRadius = insideRadius;
    this.outsideRadius = outsideRadius;
    this.labels = labels;
    this.insideLabels = insideLabels;
    this.startAngle = startAngle;
    this.extentAngle = extentAngle;
    return this;
  }
  
  public void paintComponent(Graphics gc)
  {
    Graphics2D g = (Graphics2D)gc;
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);

    g.setColor(normalValueColor);
    g.fill(getArc(centerPoint, 
      getAngleForValue(minimumValue),
      getAngleForValue(normalValue), 
      insideRadius, outsideRadius));
    g.setColor(dangerValueColor);
    g.fill(getArc(centerPoint,
      getAngleForValue(normalValue),
      getAngleForValue(dangerValue),
      insideRadius, outsideRadius));
    g.setColor(criticalColor);
    g.fill(getArc(centerPoint, 
      getAngleForValue(dangerValue),
      getAngleForValue(maximumValue),
      insideRadius, outsideRadius));
    
    g.setColor(tickColor);
    for (int i = 0; i < extentAngle; i += 5)
      drawTick(g, centerPoint, startAngle + i,
      insideRadius, outsideRadius);
    for (int i = 0; i < extentAngle; i += 10)
      drawTick(g, centerPoint, startAngle + i,
      insideRadius - 2, outsideRadius + 2);
    
    g.setColor(textColor);
    Enumeration keys = labels.keys();
    while (keys.hasMoreElements())
    {
      Integer key = (Integer)keys.nextElement();
      String label = (String)labels.get(key);
      int angle = getAngleForValue(key.intValue());
      int rad =
        (insideLabels ? insideRadius : outsideRadius) +
        (insideLabels ? -1 : 1) * getLabelOffset(g);
      drawLabel(g, centerPoint, angle, rad, label);
    }
  }

  protected int getLabelOffset(Graphics2D g)
  {
    Enumeration keys = labels.keys();
    FontMetrics metrics = g.getFontMetrics();
    int w = 0;
    int h = metrics.getAscent();
    while (keys.hasMoreElements())
    {
      Integer key = (Integer)keys.nextElement();
      String label = (String)labels.get(key);
      w = Math.max(w, metrics.stringWidth(label) / 2);
    }
    Point point = new Point(0, 0);
    return (int)point.distance(w, h);
  }

  protected Point toRadialPoint(
    Point axis, int angle, int radius)
  {
    double radian = Math.toRadians(90 - angle);
    int x = (int)(axis.x + Math.cos(radian) * radius);
    int y = (int)(axis.y - Math.sin(radian) * radius);
    return new Point(x, y);
  }
  
  protected void drawLabel(Graphics2D g, Point axis,
    int angle, int radius, String label)
  {
    FontMetrics metrics = g.getFontMetrics();
    int w = metrics.stringWidth(label) / 2;
    int h = metrics.getAscent() / 2;
    
    if (angle == startAngle)
    {
      if (angle >= 0 && angle < 90) w -= w + 2;
      if (angle >= 90 && angle < 180) h += h + 2;
      if (angle >= 180 && angle < 270) w += w + 2;
      if (angle >= 270 && angle < 360) h -= h + 2;
    }
    if (angle == startAngle + extentAngle)
    {
      angle %= 360;
      if (angle == 0) angle = 360;
      if (angle > 0 && angle <= 90) h -= h + 2;
      if (angle > 90 && angle <= 180) w -= w + 2;
      if (angle > 180 && angle <= 270) h += h + 2;
      if (angle > 270 && angle <= 360) w += w + 2;
    }
    Point point = toRadialPoint(axis, angle, radius);
    g.drawString(label, point.x - w, point.y + h - 1);
  }
  
  protected void drawTick(Graphics2D g, Point centerPoint,
    int angle, int insideRadius, int outsideRadius)
  {
    Point a = toRadialPoint(centerPoint, angle, insideRadius);
    Point b = toRadialPoint(centerPoint, angle, outsideRadius);
    g.draw(new Line2D.Double(a, b));
  }
  
  protected Shape getArc(
    Point centerPoint, int start, int end, 
    int insideRadius, int outsideRadius)
  {
    int extent = end - start;
    Arc2D insideLabels = new Arc2D.Double(
      centerPoint.x - insideRadius,
      centerPoint.y - insideRadius,
      insideRadius * 2,
      insideRadius * 2,
      90 - start,
      0 - extent,
      Arc2D.PIE);
    Arc2D outside = new Arc2D.Double(
      centerPoint.x - outsideRadius,
      centerPoint.y - outsideRadius,
      outsideRadius * 2,
      outsideRadius * 2,
      90 - start,
      0 - extent,
      Arc2D.PIE);
    Area area = new Area(outside);
    area.subtract(new Area(insideLabels));
    return area;
  }
  
  protected int getAngleForValue(int value)
  {
    double range = maximumValue - minimumValue;
    return (int)(startAngle +
      (extentAngle / range) *
      (value - minimumValue));
  }
}

