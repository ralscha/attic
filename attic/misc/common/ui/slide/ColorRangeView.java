package common.ui.slide;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorRangeView extends JLabel
  implements BoundedRangeView, ChangeListener
{
  protected BoundedRangeModel model;
  
  public ColorRangeView()
  {
    setOpaque(true);
    setBorder(UIManager.getBorder("TextField.border"));
  }
  
  public void setModel(BoundedRangeModel model)
  {
    if (this.model != null)
    {
      this.model.removeChangeListener(this);
    }
    this.model = model;
    model.addChangeListener(this);
    update();
  }
  
  public BoundedRangeModel getModel()
  {
    return model;
  }
  
  public void stateChanged(ChangeEvent event)
  {
    update();
  }
  
  protected void update()
  {
    int v = model.getValue();
    Color color = new Color(v, v, v);
    setBackground(color);
    repaint();
  }
  
  public JComponent getComponent()
  {
    return this;
  }
}

