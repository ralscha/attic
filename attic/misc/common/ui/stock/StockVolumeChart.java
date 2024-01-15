package common.ui.stock;
/*
===================================================================

    StockVolumeChart.java
    
    Creaded by Claude Duguay
    Copyright (c) 2001
    
===================================================================
*/

import java.awt.*;
import java.text.*;
import javax.swing.*;

public class StockVolumeChart extends StockChart
{
  protected static final DecimalFormat form =
    new DecimalFormat("#,###,###,###");

  public StockVolumeChart(StockModel model)
  {
    super(model);
    setBackground(Color.white);
    int count = model.getRowCount();
    inside = new Insets(20, 30, 20, 30);
    setPreferredSize(new Dimension(
      count * 3 + (inside.left + inside.right), 120));
  }

  protected void calculateMinMax()
  {
    hi = Long.MIN_VALUE;
    lo = Long.MAX_VALUE;
    int count = model.getRowCount();
    for(int i = count - 1; i >= 0; i--)
    {
      long vol = model.getVolume(i);
      if (vol > hi) hi = vol;
      if (vol < lo) lo = vol;
    }
  }

  protected String formatLabel(int value)
  {
    return "" + (value / 1000000) + "M";
  }
  
  protected void drawTopLabel(
    Graphics g, int x, int y, int w, int h)
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Volume (");
    buffer.append("low = ");
    buffer.append(form.format(lo));
    buffer.append(", high = ");
    buffer.append(form.format(hi));
    buffer.append(")");
    String text = buffer.toString();
    drawText(g, x, y, w, h, text, LEFT);
  }
  
  public void paintComponent(Graphics g)
  {
    int x = 0;
    int y = 0;
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(getBackground());
    g.fillRect(x, y, w, h);
    
    x += inside.left;
    y += inside.top;
    w -= (inside.left + inside.right);
    h -= (inside.top + inside.bottom);
    
    g.setColor(Color.gray);
    drawHorzGridLines(g, x, y, w, h);
    drawVertGridLines(g, x, y, w, h);
    
    drawChartValues(g, x, y, w, h);
    
    g.setColor(getForeground());
    drawTopLabel(g, x, 0, x + w, y);
    drawHorzAxisLabels(g, x, y + h, w, inside.bottom);
    
    drawVertAxisLabels(g, 0, y, x, h, RIGHT);
    drawVertAxisLabels(g, x + w, y, inside.right, h, LEFT);
  }

  protected void drawChartValues(
    Graphics g, int x, int y, int w, int h)
  {
    g.setColor(Color.blue);
    int count = model.getRowCount();
    for(int i = count - 1; i >= 0; i--)
    {
      int xx = x + i * 3;
      int yy = y + normalize(model.getVolume(i), h);
      g.drawLine(xx, y + h, xx, yy);
      g.drawLine(xx + 1, y + h, xx + 1, yy);
    }
  }
}

