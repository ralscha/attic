package common.ui.radar;

import java.util.*;
import javax.swing.*;

public class RadarEvent extends EventObject
{
  protected String name;
  protected BoundedRangeModel model;

  public RadarEvent(Object source, String name, BoundedRangeModel model)
  {
    super(source);
    this.name = name;
    this.model = model;
  }
  
  public String getName()
  {
    return name;
  }
  
  public BoundedRangeModel getModel()
  {
    return model;
  }
}

