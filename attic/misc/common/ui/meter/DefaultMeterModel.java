package common.ui.meter;

import java.util.*;
import javax.swing.event.*;

public class DefaultMeterModel
  implements MeterModel
{
  protected Vector listeners = new Vector();
  protected double startAngle, extentAngle, valueAngle;
  protected double normalExtent, dangerExtent;
  protected double minimum, maximum, value;
  
  
  public DefaultMeterModel(
    double startAngle, double extentAngle,
    double normalExtent, double dangerExtent,
    double valueAngle)
  {
    this.startAngle = startAngle;
    this.extentAngle = extentAngle;
    this.normalExtent = normalExtent;
    this.dangerExtent = dangerExtent;
    setValueAngle(valueAngle);
  }
  
  public void setStartAngle(double startAngle)
  {
    this.startAngle = startAngle;
    fireChangeEvent();
  }
  
  public double getStartAngle()
  {
    return startAngle;
  }
  
  public void setExtentAngle(double extentAngle)
  {
    this.extentAngle = extentAngle;
    fireChangeEvent();
  }
  
  public void setValueAngle(double valueAngle)
  {
    this.valueAngle = valueAngle;
    fireChangeEvent();
  }
  
  public double getValueAngle()
  {
    return valueAngle;
  }
  
  public double getExtentAngle()
  {
    return extentAngle;
  }
  
  public void setNormalExtent(double normalExtent)
  {
    this.normalExtent = normalExtent;
    fireChangeEvent();
  }
  
  public double getNormalExtent()
  {
    return normalExtent;
  }
  
  public void setDangerExtent(double dangerExtent)
  {
    this.dangerExtent = dangerExtent;
    fireChangeEvent();
  }
  
  public double getDangerExtent()
  {
    return dangerExtent;
  }
  
  public void setMinimum(double minimum)
  {
    this.minimum = minimum;
    fireChangeEvent();
  }
  
  public double getMinimum()
  {
    return minimum;
  }
  
  public void setMaximum(double maximum)
  {
    this.maximum = maximum;
    fireChangeEvent();
  }
  
  public double getMaximum()
  {
    return maximum;
  }
  
  public void addChangeListener(ChangeListener listener)
  {
    listeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener)
  {
    listeners.remove(listener);
  }
  
  protected void fireChangeEvent()
  {
    ChangeEvent event = new ChangeEvent(this);
    Vector list = (Vector)listeners.clone();
    for (int i = 0; i < list.size(); i++)
    {
      ChangeListener target = (ChangeListener)list.get(i);
      target.stateChanged(event);
    }
  }
}

