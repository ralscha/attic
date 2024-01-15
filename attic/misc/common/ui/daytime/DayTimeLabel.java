package common.ui.daytime;

import java.awt.*;
import javax.swing.*;

public class DayTimeLabel extends JPanel
{
  protected int divisions;
  protected JLabel baseLabel;
  protected Font hourFont, minuteFont;
  
  public DayTimeLabel(String hour, int divisions, boolean isPM)
  {
    if (60 % divisions != 0) throw new IllegalArgumentException(
      "Division value must be equaly divisible into sixty.");
    this.divisions = divisions;
    setLayout(new BorderLayout());
    setFont(getFont());
    setOpaque(true);

    JLabel hourLabel = new JLabel(hour);
    hourLabel.setHorizontalAlignment(JLabel.RIGHT);
    hourLabel.setVerticalAlignment(JLabel.TOP);
    hourLabel.setFont(hourFont);
    hourLabel.setOpaque(false);
    add(BorderLayout.CENTER, hourLabel);
    
    JPanel minutePanel = new JPanel(new GridLayout(divisions, 1, 1, 1));
    minutePanel.setOpaque(false);
    minutePanel.add(baseLabel = createLabel(
      isPM ? " pm " : " am ", minuteFont));
    
    int unit = 60 / divisions;
    for (int i = unit; i < 60; i += unit)
    {
      minutePanel.add(createLabel(" " + i +  " ", minuteFont));
    }
    add(BorderLayout.EAST, minutePanel);
  }

  public void setFont(Font font)
  {
    super.setFont(font);
    hourFont = font.deriveFont(22f);
    minuteFont = font.deriveFont(10f);
  }
  
  protected JLabel createLabel(String text, Font font)
  {
    JLabel label = new JLabel(text);
    label.setFont(font);
    return label;
  }

  public void paintComponent(Graphics g)
  {
    int w = getSize().width - 1;
    int h = getSize().height;
    g.drawLine(0, 0, w, 0);
    g.drawLine(0, h, w, h);
    int p = baseLabel.getSize().width - 3;
    int unit = h / divisions;
    for (int i = 0; i < h; i += unit)
      g.drawLine(w - p, i, w, i);
  }
}

