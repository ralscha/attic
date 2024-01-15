package common.ui.slide;

import java.awt.*;
import javax.swing.*;

public interface BoundedRangeView
{
  public BoundedRangeModel getModel();
  public void setModel(BoundedRangeModel model);
  public JComponent getComponent();
}

