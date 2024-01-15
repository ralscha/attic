package common.ui.sequence;
/*
=====================================================================

  SequenceList.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.util.*;

public class SequenceList
{
  protected List list = new ArrayList();

  public SequenceList() {}

  public void addSequenceItem(SequenceItem item)
  {
    list.add(item);
  }

  public int getSequenceItemCount()
  {
    return list.size();
  }
  
  public SequenceItem getSequenceItem(int index)
  {
    return (SequenceItem)list.get(index);
  }
}

