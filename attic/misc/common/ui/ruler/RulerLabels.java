package common.ui.ruler;

import java.awt.*;
import java.text.*;
import javax.swing.*;

public class RulerLabels extends JPanel
{
  public RulerLabels(RulerModel model)
  {
    setOpaque(false);
    setLayout(new RulerLabelsLayout(model));
    double offset = model.getOffset();
    double increment = model.getIncrement();
    double length = model.getLengthInUnits();
    
    int precision = model.getPrecision();
    NumberFormat formater = NumberFormat.getNumberInstance();
    formater.setMinimumFractionDigits(precision);
    formater.setMaximumFractionDigits(precision);
    
    for (double i = 0; i <= length; i++)
    {
      add(makeLabel(formater.format(offset + i * increment)));
    }
  }
  
  protected JLabel makeLabel(String text)
  {
    JLabel label = new JLabel(text);
    label.setFont(label.getFont().deriveFont(Font.BOLD));
    return label;
  }
}

