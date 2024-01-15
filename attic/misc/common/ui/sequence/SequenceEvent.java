package common.ui.sequence;
/*
=====================================================================

  SequenceEvent.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import javax.swing.*;
import javax.swing.event.*;

public class SequenceEvent
  extends ChangeEvent
{
  public static final int STATE_READY = 1;
  public static final int STATE_UPDATE = 2;
  public static final int STATE_DONE = 3;

  protected int state, value;

  public SequenceEvent(Object source, int state, int value)
  {
    super(source);
    this.state = state;
    this.value = value;
  }
  
  public int getState()
  {
    return state;
  }
  
  public int getValue()
  {
    return value;
  }
}

