package common.ui.radar;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class RadarEdit extends RadarView
  implements MouseListener, MouseMotionListener
{
  protected Point2D selected;
  protected int selectionIndex;
  protected Robot robot;

  public RadarEdit(JRadar radar, int count)
  {
    super(radar, count);
    addMouseListener(this);
    addMouseMotionListener(this);
    try
    {
      robot = new Robot();
    }
    catch (AWTException e) {}
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (selected != null)
    {
      int x = (int)selected.getX();
      int y = (int)selected.getY();
      g.setColor(Color.white);
      g.fillRect(x - 3, y - 3, 6, 6);
      g.setColor(Color.black);
      g.drawRect(x - 3, y - 3, 6, 6);
    }
  }
  
  protected void repositionMouse(Point2D selected)
  {
    if (robot == null) return;
    Point point = new Point(
      (int)selected.getX(), (int)selected.getY());
    SwingUtilities.convertPointToScreen(point, this);
    robot.mouseMove(point.x, point.y);
  }

  public void mousePressed(MouseEvent event)
  {
    int radius = getRadius();
    Point point = event.getPoint();
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    Point2D center = new Point2D.Float(w / 2, h / 2);
    double rad = point.distance(center);
    radar.model.setAmplitude(
      selectionIndex, Math.min(rad / radius, 1));
    createAmplitudeShape();
    selected = points[selectionIndex];
    repositionMouse(selected);
  }
  
  public void mouseClicked(MouseEvent event) {}
  public void mouseReleased(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event)
  {
    selected = null;
    getParent().repaint();
  }
  
  public void mouseMoved(MouseEvent event)
  {
    if (points == null) return;
    Point point = event.getPoint();
    int count = points.length;
    double step = 360 / count;
    double angle = Math.atan2(
      event.getX() - (getSize().width / 2),
      event.getY() - (getSize().height / 2));
    angle = 360 - (Math.toDegrees(angle) + 180);
    selectionIndex = (int)Math.round(angle / step);
    if (selectionIndex < 0 ||
        selectionIndex >= count)
          selectionIndex = 0;
    selected = points[selectionIndex];
    getParent().repaint();
  }
  
  public void mouseDragged(MouseEvent event)
  {
    int radius = getRadius();
    Point point = event.getPoint();
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    Point2D center = new Point2D.Float(w / 2, h / 2);
    double rad = point.distance(center);
    radar.model.setAmplitude(
      selectionIndex, Math.min(rad / radius, 1));
    createAmplitudeShape();
    selected = points[selectionIndex];
    getParent().repaint();
  }
}

