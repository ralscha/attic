package common.ui.ticker;

/*
=====================================================================

  DefaultTickerModel.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class DefaultTickerModel extends DefaultListModel
  implements TickerModel
{
  public void add(Object item)
  {
    addElement(item);
  }

  public void remove(Object item)
  {
    removeElement(item);
  }
}

