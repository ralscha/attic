package common.ui.ticker;
/*
=====================================================================

  TickerAction.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TickerAction extends AbstractAction
{
  public static final Icon defaultIcon =
    new ImageIcon("page.gif");
  
  public TickerAction(String text)
  {
    this(text, defaultIcon);
  }
  
  public TickerAction(String text, Icon icon)
  {
    super(text, icon);
  }
  
  public void actionPerformed(ActionEvent event)
  {
    System.out.println(getValue(NAME));
  }
}

