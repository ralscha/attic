/*
=====================================================================

  TestSequenceItem.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/
package sequence;
import java.awt.event.*;
import javax.swing.*;
import common.ui.sequence.*;

public class TestSequenceItem
  extends AbstractSequenceItem
  implements ActionListener
{
  protected static final int MAX_VALUE = 100;
  protected int increment = 5;
  protected int count = 0;
  protected Timer timer;
  
  public TestSequenceItem(String text)
  {
    super(text);
  }

  public void execute()
  {
    timer = new Timer(100, this);
    timer.start();
  }
  
  public void actionPerformed(ActionEvent event)
  {
    if (count == 0)
    {
      fireProgressReady(MAX_VALUE);
      count += increment;
    }
    else if (count > MAX_VALUE)
    {
      timer.stop();
      timer = null;
      fireProgressDone(MAX_VALUE);
    }
    else
    {
      fireProgressUpdate(count);
      count += increment;
    }
  }
}

