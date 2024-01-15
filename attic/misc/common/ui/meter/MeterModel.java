package common.ui.meter;

import javax.swing.event.*;

public interface MeterModel
{
  public void setStartAngle(double startAngle);
  public double getStartAngle();
  
  public void setExtentAngle(double extentAngle);
  public double getExtentAngle();
  
  public void setValueAngle(double value);
  public double getValueAngle();

  public void setNormalExtent(double normalExtent);
  public double getNormalExtent();
  
  public void setDangerExtent(double dangerExtent);
  public double getDangerExtent();
  
  public void setMinimum(double minimum);
  public double getMinimum();
  
  public void setMaximum(double maximum);
  public double getMaximum();
  
  public void addChangeListener(ChangeListener listener);
  public void removeChangeListener(ChangeListener listener);
}

