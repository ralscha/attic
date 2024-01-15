package common.ui.slide;

import java.awt.*;
import javax.swing.*;

public class DefaultSlide extends JSlider
  implements BoundedRangeView
{
  public DefaultSlide() {}
  
  public DefaultSlide(BoundedRangeModel model)
  {
    super(model);
  }
    
  public DefaultSlide(int orientation)
  {
    super(orientation);
  }
  
  public DefaultSlide(int min, int max)
  {
    super(min, max);
  }  
    
  public DefaultSlide(int min, int max, int value)
  {
    super(min, max, value);
  }
  
  public DefaultSlide(int orientation, int min, int max, int value)
  {
    super(orientation, min, max, value);
  }

  public JComponent getComponent()
  {
    return this;
  }
}

