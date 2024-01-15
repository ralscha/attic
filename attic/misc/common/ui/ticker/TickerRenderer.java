package common.ui.ticker;
/*
=====================================================================

  TickerRenderer.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public interface TickerRenderer
{
  public static final int STATE_NORMAL = 1;
  public static final int STATE_MOUSE = 2;
  public static final int STATE_CLICK = 3;

  public JComponent getTickerRendererComponent(
    JTicker ticker, Object value, int state);
}

