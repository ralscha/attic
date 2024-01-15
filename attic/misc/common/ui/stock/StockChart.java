package common.ui.stock;
/*
=====================================================================

  StockChart.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import java.util.*;
import javax.swing.*;

public abstract class StockChart extends JPanel
  implements SwingConstants
{
  protected static final String[] months =
  {
    "J", "F", "M", "A", "M", "J",
    "J", "A", "S", "O", "N", "D"
  };
  
  protected Insets inside;
  protected StockModel model;
  protected double hi, lo;
  protected double min, max;
  protected int range, divs, unit;

  public StockChart(StockModel model)
  {
    this.model = model;
    calculateMinMax();
    //System.out.println("hi=" + hi + ", lo=" + lo);
    range = calculateRange();
    unit = calculateUnit();
    divs = (int)(range / unit) + 2;
    min = (int)Math.floor(lo / unit) * unit;
    max = divs * unit + min;
    System.out.println(
      "range=" + range +
      ", unit=" + unit +
      ", divs="+ divs +
      ", lo=" + lo );
  }
  
  public void setModel(StockModel model)
  {
    this.model = model;
  }
  
  public StockModel getModel()
  {
    return model;
  }
  
  protected int calculateRange()
  {
    return (int)(Math.ceil(hi) - Math.floor(lo));
  }
  
  protected int calculateUnit()
  {
    double range = calculateRange();
    if (range > 10000000) return 10000000;
    if (range > 1000000) return 1000000;
    if (range > 100000) return 100000;
    if (range > 10000) return 10000;
    if (range > 1000) return 1000;
    if (range > 100) return 100;
    if (range > 10) return 10;
    return 1;
  }

  protected int normalize(double value, double height)
  {
    double range = max - min;
    double factor = ((value - min) / range);
    return (int)(height - factor * height);
  }

  protected void drawText(
    Graphics g, int x, int y, int w, int h,
    String text, int justify)
  {
    FontMetrics metrics = g.getFontMetrics();
    int width = metrics.stringWidth(text);
    if (justify == LEFT) x += 3;
    if (justify == CENTER) x += (w - width) / 2;
    if (justify == RIGHT) x += w - width - 3;
    y += (h / 2) - (metrics.getHeight() / 2) + metrics.getAscent();
    g.drawString(text, x, y);
  }
  
  public void drawHorzGridLines(
    Graphics g, int x, int y, int w, int h)
  {
    int count = model.getRowCount();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(model.getDate(count - 1));
    int month = calendar.get(Calendar.MONTH);
    for(int i = count - 1; i >= 0; i--)
    {
      calendar.setTime(model.getDate(count - i - 1));
      int next = calendar.get(Calendar.MONTH);
      if (month != next)
      {
        month = next;
        g.drawLine(x + i * 3, y, x + i * 3, y + h);
      }
    }
    g.drawLine(x, y, x, y + h);
    g.drawLine(x + w, y, x + w, y + h);
  }

  public void drawHorzAxisLabels(
    Graphics g, int x, int y, int w, int h)
  {
    int count = model.getRowCount();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(model.getDate(count - 1));
    int month = calendar.get(Calendar.MONTH);
    for(int i = count - 1; i >= 0; i--)
    {
      calendar.setTime(model.getDate(count - i - 1));
      int next = calendar.get(Calendar.MONTH);
      if (month != next)
      {
        month = next;
        String label = months[month];
        if (month == 0)
        {
          label = "" + calendar.get(Calendar.YEAR);
          label = label.substring(label.length() - 2);
        }
        drawText(g, x + i * 3 - 15,
          y, 30, h, label, CENTER);
      }
    }
  }

  public void drawVertGridLines(
    Graphics g, int x, int y, int w, int h)
  {
    int incr = (int)(h / divs);
    for(int i = 0; i < h; i += incr)
    {
      g.drawLine(x, y + h - i, x + w, y + h - i);
    }
    g.drawLine(x, y, x + w, y);
    g.drawLine(x, y + h, x + w, y + h);
  }
  
  protected void drawVertAxisLabels(
    Graphics g, int x, int y, int w, int h, int justify)
  {
    int incr = (int)(h / divs);
    int count = 0;
    for(int i = 0; i < h; i += incr)
    {
      drawText(g, x, y + h - i - 10, w, 20,
        formatLabel((int)(count * unit + min)),
        justify);
      count++;
    }
  }
  
  protected abstract String formatLabel(int value);
  protected abstract void calculateMinMax();
  protected abstract void drawChartValues(
    Graphics g, int x, int y, int w, int h);

}

