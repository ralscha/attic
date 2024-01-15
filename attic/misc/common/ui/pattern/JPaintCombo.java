package common.ui.pattern;

import java.awt.*;
import javax.swing.*;

public class JPaintCombo extends JComboBox
{
  public JPaintCombo(Paint[] list)
  {
    super(list);
    setRenderer(new PaintCellRenderer());
  }
  
  public Paint getSelectedPaint()
  {
    return (Paint)getSelectedItem();
  }
}

