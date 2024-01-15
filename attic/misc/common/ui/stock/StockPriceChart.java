package common.ui.stock;
/*
===================================================================

    StockPriceChart.java
    
    Creaded by Claude Duguay
    Copyright (c) 2001
    
===================================================================
*/

import java.awt.*;
import java.text.*;
import javax.swing.*;

public class StockPriceChart extends StockChart
{
  protected static final DecimalFormat form =
    new DecimalFormat("$###.##");

  public StockPriceChart(StockModel model)
  {
    super(model);
    setBackground(Color.white);
    int count = model.getRowCount();
    inside = new Insets(20, 30, 20, 30);
    setPreferredSize(new Dimension(
      count * 3 + (inside.left + inside.right), 280));
  }
  
  protected void calculateMinMax()
  {
    hi = Double.MIN_VALUE;
    lo = Double.MAX_VALUE;
    int count = model.getRowCount();
    for(int i = 0; i < count; i++)
    {
      double high = model.getHigh(i);
      double low = model.getLow(i);
      if (high > hi) hi = high;
      if (low < lo) lo = low;
    }
  }
  
  protected String formatLabel(int value)
  {
    return "" + value;
  }
  
  protected void drawTopLabel(
    Graphics g, int x, int y, int w, int h)
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Price (");
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
    g.fillRect(0, 0, w, h);
    
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
    int count = model.getRowCount();
    for(int i = count - 1; i >= 0; i--)
    {
      int xx = x + i * 3;
      int y1 = y + normalize(model.getHigh(i), h);
      int y2 = y + normalize(model.getLow(i), h);
      int y3 = y + normalize(model.getOpen(i), h);
      int y4 = y + normalize(model.getClose(i), h);
      g.setColor(Color.blue);
      g.drawLine(xx + 1, y1, xx + 1, y2);
      
      g.setColor(Color.blue);
      g.drawLine(xx, y3, xx, y4);
      g.drawLine(xx + 1, y3, xx + 1, y4);
      g.drawLine(xx + 2, y3, xx + 2, y4);
    }
  }
}

