package common.ui.slide;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class BoundedRangeEditor extends JTextField
  implements BoundedRangeView, ChangeListener
{
  protected BoundedRangeModel model;
  
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
    setText("" + model.getValue());
    repaint();
  }
  
  public JComponent getComponent()
  {
    return this;
  }
}
