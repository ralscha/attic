package common.ui.slide;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class GradientSlide extends JPanel
  implements BoundedRangeView, ChangeListener,
    MouseListener, MouseMotionListener
{
  protected Paint gradient;
  protected BoundedRangeModel model;
  
  public GradientSlide(Color source, Color target)
  {
    gradient = new GradientPaint(0, 0, source, 255, 0, target);
    setModel(new DefaultBoundedRangeModel(0, 1, 0, 255));
    setBorder(UIManager.getBorder("Slider.border"));
    addMouseListener(this);
  }
  
  public void setModel(BoundedRangeModel model)
  {
    if (this.model != null)
    {
      this.model.removeChangeListener(this);
    }
    this.model = model;
    model.addChangeListener(this);
  }
  
  public BoundedRangeModel getModel()
  {
    return model;
  }
  
  public void setBorder(Border border)
  {
    super.setBorder(border);
    Insets insets = getInsets();
    int w = insets.left + insets.right + 256;
    int h = insets.top + insets.bottom + 14;
    setPreferredSize(new Dimension(w, h));
  }

  public void paintComponent(Graphics gc)
  {
    Insets insets = getInsets();
    int x = insets.left;
    int y = insets.top;
    int w = getSize().width;
    int h = getSize().height;
    w -= insets.left + insets.right;
    h -= insets.top + insets.bottom;
    Graphics2D g = (Graphics2D)gc;
    g.setPaint(gradient);
    g.fillRect(x, y, w, h);
    int p = x + model.getValue();
    g.setColor(Color.black);
    g.drawLine(p, y, p, h);
    g.setColor(Color.white);
    g.drawLine(p - 1, y, p - 1, h);
    g.drawLine(p + 1, y, p + 1, h);
  }
  
  public void stateChanged(ChangeEvent event)
  {
    repaint();
  }
    
  public void mouseClicked(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseMoved(MouseEvent event) {}
  
  public void mousePressed(MouseEvent event)
  {
    addMouseMotionListener(this);
    model.setValue(event.getX());
  }
  
  public void mouseReleased(MouseEvent event)
  {
    removeMouseMotionListener(this);
  }
  
  public void mouseDragged(MouseEvent event)
  {
    model.setValue(event.getX());
  }
  
  public JComponent getComponent()
  {
    return this;
  }
}

