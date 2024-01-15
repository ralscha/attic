package common.ui.slide;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * JSlide lets you slide the mouse mointer across a JSlider
 * control when it gets poped up from an Arrow button in a
 * mixed field environment. The mixed field may be a spinner
 * controlled view.
**/

public class JSlide extends JPanel
  implements MouseListener, MouseMotionListener
{
  protected BoundedRangeView field;
  protected BoundedRangeView slider;
  protected JButton button =
    new BasicArrowButton(BasicArrowButton.SOUTH);
  protected JPopupMenu popup = new JPopupMenu();
  protected Point anchor;
  protected int offset;

  public JSlide()
  {
    this(new BoundedRangeEditor(), new DefaultSlide());
  }
  
  public JSlide(BoundedRangeView slider)
  {
    this(new BoundedRangeEditor(), slider);
  }
  
  public JSlide(BoundedRangeView field, BoundedRangeView slider)
  {
    this.field = field;
    this.slider = slider;
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, field.getComponent());
    add(BorderLayout.EAST, button);
    button.setPreferredSize(new Dimension(16, 16));
    button.addMouseListener(this);
    popup.add(slider.getComponent());
    field.setModel(slider.getModel());
  }
  
  protected void popup()
  {
    BoundedRangeModel model = slider.getModel();
    double min = model.getMinimum();
    double max = model.getMaximum();
    double val = model.getValue();
    double pos = (val - min) / (max - min);
    double width = slider.getComponent().getPreferredSize().width;
    offset = (int)(pos * width) - (button.getSize().width / 2);
    popup.show(button, -offset, button.getSize().height);
  }
  
  protected void adjust(int x)
  {
    BoundedRangeModel model = slider.getModel();
    double min = model.getMinimum();
    double max = model.getMaximum();
    double val = model.getValue();
    double width = slider.getComponent().getPreferredSize().width;
    double pos = min + ((double)x / width) * (max - min);
    model.setValue((int)pos);
  }
  
  public void mousePressed(MouseEvent event)
  {
    int x = button.getSize().width / 2;
    int y = button.getSize().height / 2;
    anchor = new Point(x, y);
    button.addMouseMotionListener(this);
    popup();
  }
  
  public void mouseReleased(MouseEvent event)
  {
    button.removeMouseMotionListener(this);
    MenuSelectionManager.defaultManager().clearSelectedPath();
  }

  public void mouseDragged(MouseEvent event)
  {
    int x = offset + event.getPoint().x - anchor.x;
    adjust(x);
  }
  
  public void mouseClicked(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseMoved(MouseEvent event) {}
}

