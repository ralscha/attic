package meter;

/*
=====================================================================

  MeterDemoThread.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import common.ui.meter.*;

public class MeterDemoThread extends Thread
{
  protected JMeter meter;
  
  public MeterDemoThread(JMeter meter)
  {
    this.meter = meter;
  }
  
  public void run()
  {
    try
    {
      Thread.sleep(1000);
      while (true)
      {
        Thread.sleep(500);
        BoundedRangeModel model = meter.getModel();
        int range = model.getMaximum() - model.getMinimum();
        int value = model.getMinimum() +
          (int)(Math.random() * range);
        model.setValue(value);
      }
    }
    catch (InterruptedException e) {}
  }
}

