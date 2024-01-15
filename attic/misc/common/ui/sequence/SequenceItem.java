package common.ui.sequence;
/*
=====================================================================

  SequenceItem.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public interface SequenceItem
{
  public void addSequenceListener(
    SequenceListener listener);
  public void removeSequenceListener(
    SequenceListener listener);
  
  public String getText();
  public void execute();
}

