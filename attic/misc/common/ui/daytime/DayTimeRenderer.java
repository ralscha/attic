package common.ui.daytime;

import java.awt.*;
import javax.swing.*;

public class DayTimeRenderer extends JLabel
  implements ListCellRenderer
{
  public Color selectedColor = new Color(0, 0, 128);
  public Color normalColor = Color.white;
  protected boolean isFirst, isLast;
  protected int edge = 4;

  protected boolean isSelected;
  
  public DayTimeRenderer(int cellHeight)
  {
    setOpaque(true);
    setPreferredSize(new Dimension(cellHeight, cellHeight));
    setMinimumSize(new Dimension(cellHeight, cellHeight));
  }
  
  public Component getListCellRendererComponent(
    JList list, Object value, int index,
    boolean isSelected, boolean hasFocus)
  {
    isFirst = index == 0;
    isLast = index == list.getModel().getSize() - 1;
    this.isSelected = isSelected;
    setText((String)value);
    return this;
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int w = getSize().width;
    int h = getSize().height;
    g.setColor(isSelected ? selectedColor : normalColor);
    g.fillRect(0, 0, w, h);
    g.setColor(Color.gray);
    g.drawLine(edge, 0, w, 0);
    g.setColor(normalColor);
    g.fillRect(0, 0, edge, h);
    g.setColor(Color.black);
    g.drawLine(0, 0, 0, h);
    g.drawLine(edge, 0, edge, h);
    if (isFirst) g.drawLine(0, 0, w, 0);
    if (isLast) g.drawLine(0, h - 1, w, h - 1);
  }
}

