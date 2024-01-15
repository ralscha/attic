package common.ui.split;

import java.awt.*;
import javax.swing.*;

public interface SplitViewFactory
{
  public JComponent createComponentView(Object obj);
  public Object getModel(JComponent comp);
  public void setModel(JComponent comp, Object model);
}

