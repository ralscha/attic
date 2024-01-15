package common.ui.stock;
/*
=====================================================================

  StockModel.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public interface StockModel extends TableModel
{
  public static final int DATE = 0;
  public static final int OPEN = 1;
  public static final int HIGH = 2;
  public static final int LOW = 3;
  public static final int CLOSE = 4;
  public static final int VOLUME = 5;

  public Date getDate(int row);
  public double getOpen(int row);
  public double getHigh(int row);
  public double getLow(int row);
  public double getClose(int row);
  public long getVolume(int row);
}

