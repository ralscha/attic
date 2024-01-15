package common.ui.daytime;

import java.awt.*;
import javax.swing.*;

public class DayTimeRuler extends JPanel
{
  protected int divisions, cellHeight;

  public DayTimeRuler(int divisions, int cellHeight)
  {
    this.divisions = divisions;
    this.cellHeight = cellHeight;
    setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
    setLayout(new GridLayout(24, 1));
    for (int i = 0; i < 24; i++)
    {
      add(new DayTimeLabel(
        i % 12 == 0 ? "12" : "" + (i % 12),
        divisions, i >= 12));
    }
  }

  public Dimension getPreferredSize()
  {
    Insets insets = getInsets();
    return new Dimension(48, 24 * divisions *
      cellHeight + (insets.top + insets.bottom));
  }
  
  public void paintComponent(Graphics g)
  {
    Insets insets = getInsets();
    int w = getSize().width;
    int h = getSize().height -
      (insets.top + insets.bottom);
    g.setColor(Color.black);
    int bottom = h + insets.top - 1;
    g.drawLine(0, bottom, w, bottom);
  }
}

