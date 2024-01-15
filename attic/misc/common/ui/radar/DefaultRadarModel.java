package common.ui.radar;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class DefaultRadarModel
  implements RadarModel, ChangeListener
{
  protected ArrayList models = new ArrayList(); 
  protected ArrayList labels = new ArrayList(); 
  protected ArrayList listeners = new ArrayList();
  
  public int getCount()
  {
    return models.size();
  }
  
  public void addRange(String label, BoundedRangeModel range)
  {
    labels.add(label);
    models.add(range);
    range.addChangeListener(this);
  }
  
  public void removeRange(String label, BoundedRangeModel range)
  {
    range.removeChangeListener(this);
    models.remove(range);
    labels.remove(label);
  }
  
  public BoundedRangeModel getRange(int index)
  {
    return (BoundedRangeModel)models.get(index);
  }
  
  public String getLabel(int index)
  {
    return (String)labels.get(index);
  }
  
  public int indexOf(String label)
  {
    return models.indexOf(label);
  }
  
  public int indexOf(BoundedRangeModel model)
  {
    return models.indexOf(model);
  }
  
  public double getAmplitude(int index)
  {
    BoundedRangeModel model = getRange(index);
    double min = model.getMinimum();
    double max = model.getMaximum();
    double val = model.getValue();
    double amplitude = (val - min) / (max - min);
    return amplitude;
  }

  public void setAmplitude(int index, double amplitude)
  {
    BoundedRangeModel model = getRange(index);
    double min = model.getMinimum();
    double max = model.getMaximum();
    int val = (int)(min + ((max - min) * amplitude));
    model.setValue(val);
  }
  
  public void addRadarListener(RadarListener listener)
  {
    listeners.add(listener);
  }
  
  public void removeRadarListener(RadarListener listener)
  {
    listeners.remove(listener);
  }
  
  public void stateChanged(ChangeEvent event)
  {
    BoundedRangeModel range =
      (BoundedRangeModel)event.getSource();
    String name = getLabel(indexOf(range));
    fireRadarEvent(name, range);
  }
  
  protected void fireRadarEvent(String name, BoundedRangeModel range)
  {
    RadarEvent event = new RadarEvent(this, name, range);
    ArrayList list = (ArrayList)listeners.clone();
    for (int i = 0; i < list.size(); i++)
    {
      RadarListener listener = (RadarListener)list.get(i);
      listener.radarChanged(event);
    }
  }
}

