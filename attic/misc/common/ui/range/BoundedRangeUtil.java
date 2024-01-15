package common.ui.range;

import java.awt.*;
import javax.swing.*;

public class BoundedRangeUtil
{
  public static int scaleValueToWidth(
    BoundedRangeModel model,
    int value, int width)
  {
    double range = getRange(model);
    double unit = (double)width / range;
    return (int)(value * unit);
  }
  
  public static int getExtentAsPosition(BoundedRangeModel model)
  {
    int extent = model.getExtent();
    int value = model.getValue();
    return value + extent;
  }
  
  public static void setExtentFromPosition(
    BoundedRangeModel model, int pos)
  {
    int value = model.getValue();
    int extent = pos - value;
    model.setExtent(extent);
  }
  
  public static int getScaledValuePos(
    BoundedRangeModel model, int width)
  {
    int pos = model.getValue();
    return scaleValueToWidth(model, pos, width);
  }
  
  public static int getScaledExtentPos(
    BoundedRangeModel model, int width)
  {
    int pos = getExtentAsPosition(model);
    return scaleValueToWidth(model, pos, width);
  }

  public static int getScaledExtentLen(
    BoundedRangeModel model, int width)
  {
    int pos = model.getExtent();
    return scaleValueToWidth(model, pos, width);
  }
  
  public static int getRange(BoundedRangeModel model)
  {
    int min = model.getMinimum();
    int max = model.getMaximum();
    return max - min;
  }

  public static void setValue(
    BoundedRangeModel model, int value)
  {
    int min = model.getMinimum();
    int max = model.getMaximum();
    if (value < min) value = min;
    if (value > max) value = max;
    model.setValue(value);
  }

  public static void setExtent(
    BoundedRangeModel model, int extent)
  {
    int val = model.getValue();
    int min = model.getMinimum();
    int max = model.getMaximum();
    if (extent < 1) extent = 1;
    if (extent > max - val)
    {
      extent = max - val;
    }
    model.setExtent(extent);
  }
}

