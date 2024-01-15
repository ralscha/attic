package common.ui.sequence;
/*
=====================================================================

  AbstractSequenceItem.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.util.*;

public abstract class AbstractSequenceItem
  implements SequenceItem
{
  protected String text;
  protected ArrayList listeners = new ArrayList();	
  
  public AbstractSequenceItem(String text)
  {
    this.text = text;
  }
  
  public String getText()
  {
    return text;
  }
  
  public void addSequenceListener(SequenceListener listener)
  {
    listeners.add(listener);
  }

  public void removeSequenceListener(SequenceListener listener)
  {
    listeners.remove(listener);
  }
  
  public void fireProgressReady(int max)
  {
    ArrayList list = (ArrayList)listeners.clone();
    SequenceEvent event = new SequenceEvent(
      this, SequenceEvent.STATE_READY, max);
    for (int i = 0; i < list.size(); i++)
    {
      SequenceListener listener =
        (SequenceListener)list.get(i);
      listener.progressReady(event);
    }
  }

  public void fireProgressUpdate(int val)
  {
    ArrayList list = (ArrayList)listeners.clone();
    SequenceEvent event = new SequenceEvent(
      this, SequenceEvent.STATE_UPDATE, val);
    for (int i = 0; i < list.size(); i++)
    {
      SequenceListener listener =
        (SequenceListener)list.get(i);
      listener.progressUpdate(event);
    }
  }
  
  public void fireProgressDone(int max)
  {
    ArrayList list = (ArrayList)listeners.clone();
    SequenceEvent event = new SequenceEvent(
      this, SequenceEvent.STATE_DONE, max);
    for (int i = 0; i < list.size(); i++)
    {
      SequenceListener listener =
        (SequenceListener)list.get(i);
      listener.progressDone(event);
    }
  }
}

