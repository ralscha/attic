package common.ui.stock;
/*
=====================================================================

  JStock.java

  Created by Claude Duguay
  Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class JStock extends JPanel
{
  protected StockModel model;
  protected StockChart price, volume;

  public JStock(StockModel model)
  {
    this.model = model;
    setLayout(new BorderLayout(4, 4));
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    add(BorderLayout.CENTER, 
      price = new StockPriceChart(model));
    add(BorderLayout.SOUTH,
      volume = new StockVolumeChart(model));
  }

  public void setModel(StockModel model)
  {
    this.model = model;
    price.setModel(model);
    volume.setModel(model);
  }

  public StockModel getModel()
  {
    return model;
  }
}

