package common.ui.radar;

import javax.swing.*;
import javax.swing.event.*;

public interface RadarModel
{
  public int getCount();
  public void addRange(String label, BoundedRangeModel range);
  public void removeRange(String label, BoundedRangeModel range);
  public BoundedRangeModel getRange(int index);
  public String getLabel(int index);
  public int indexOf(String label);
  public int indexOf(BoundedRangeModel model);
  public double getAmplitude(int index);
  public void setAmplitude(int index, double amplitude);
  public void addRadarListener(RadarListener listener);
  public void removeRadarListener(RadarListener listener);
}

