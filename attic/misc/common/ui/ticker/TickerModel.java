package common.ui.ticker;
/*
=====================================================================

  TickerModel.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public interface TickerModel extends ListModel
{
  public Object get(int index);
  public int indexOf(Object item);
  public void add(Object item);
  public void remove(Object item);
}

