package common.ui.imagemap;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class JImageMap extends JPanel
  implements MouseListener, MouseMotionListener
{
  protected Image image, light, press;
  protected ImageMap model;
  protected Shape selectable, pressed;

  public JImageMap(
    Image image, ImageMap model)
  {
    this(image, null, null, model);
  }
  
  public JImageMap(
    Image image, Image light, ImageMap model)
  {
    this(image, light, null, model);
  }
  
  public JImageMap(
    Image image, Image light, Image press,
    ImageMap model)
  {
    this.image = image;
    this.light = light;
    this.press = press;
    this.model = model;
    addMouseListener(this);
    addMouseMotionListener(this);
    ToolTipManager.sharedInstance().registerComponent(this);
  }

  public Dimension getPreferredSize()
  {
    Insets insets = getInsets();
    int w = insets.left + insets.right;
    int h = insets.top + insets.bottom;
    return new Dimension(
      image.getWidth(null) + w,
      image.getHeight(null) + h);
  }
  
  public Dimension getMinimumSize()
  {
    return getPreferredSize();
  }
  
  public void paintComponent(Graphics gc)
  {
    Insets insets = getInsets();
    int x = insets.left;
    int y = insets.top;
    int w = getSize().width;
    int h = getSize().height;
    Graphics2D g = (Graphics2D)gc;
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING ,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(getBackground());
    g.fillRect(0, 0, w, h);
    g.drawImage(image, x, y, this);
    if (pressed != null)
    {
      if (press != null)
      {
        g.setClip(pressed);
        g.drawImage(press, x, y, this);
      }
    }
    if (selectable != null)
    {
      if (light != null)
      {
        g.setClip(selectable);
        g.drawImage(light, x, y, this);
      }
      else
      {
        g.setColor(getForeground());
        g.draw(selectable);
      }
    }
  }
  
  public Point getToolTipLocation(MouseEvent event)
  {
    Shape shape = model.getShape(event.getX(), event.getY());
    if (shape == null) return null;
    return new Point(
      event.getPoint().x,
      event.getPoint().y + 20);
  }
  
  public String getToolTipText(MouseEvent event)
  {
    Action action = model.getAction(event.getX(), event.getY());
    return (action != null) ? action.toString() : null;
  }
  
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
  public void mouseDragged(MouseEvent event) {}
  
  public void mousePressed(MouseEvent event)
  {
    Action action = model.getAction(event.getX(), event.getY());
    if (action == null) return;
    ActionEvent actionEvent = new ActionEvent(
      this, ActionEvent.ACTION_PERFORMED, action.toString());
    action.actionPerformed(actionEvent);
    pressed = model.getShape(event.getX(), event.getY());
    selectable = null;
    repaint();
  }
  
  public void mouseReleased(MouseEvent event)
  {
    selectable = model.getShape(event.getX(), event.getY());
    pressed = null;
    repaint();
  }
  
  public void mouseMoved(MouseEvent event)
  {
    selectable = model.getShape(event.getX(), event.getY());
    pressed = null;
    repaint();
  }
}

