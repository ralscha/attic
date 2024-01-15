package common.ui.sequence;
/*
=====================================================================

  SequenceListener.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public interface SequenceListener
{
  public void progressReady(SequenceEvent event);
  public void progressUpdate(SequenceEvent event);
  public void progressDone(SequenceEvent event);
}

