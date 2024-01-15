package common.ui.daytime;

import java.awt.*;
import javax.swing.*;

public class JDayTime extends JPanel
{
  protected int divisions;
  protected DayTimeLayers layers;
  protected DayTimeGroup group;
  protected int cellHeight;
  
  public JDayTime(int divisions, int cellHeight)
  {
    this.divisions = divisions;
    this.cellHeight = cellHeight;
    setLayout(new BorderLayout());
    add(BorderLayout.WEST, new DayTimeRuler(divisions, cellHeight));
    
    String[] emptyList = new String[24 * divisions];
    for (int i = 0; i < emptyList.length; i++)
    {
      emptyList[i] = " ";
    }
    JList list = new JList(emptyList);
    list.setCellRenderer(new DayTimeRenderer(cellHeight));
    
    layers = new DayTimeLayers(list);
    group = new DayTimeGroup();
    
    add(BorderLayout.CENTER, layers);
  }
  
  public int getDivisions()
  {
    return divisions;
  }
  
  public DayTimeEntry addEntry(double pos, double len, JComponent component)
  {
    DayTimeEntry entry = new DayTimeEntry(this, pos, len, component);
    layers.add(entry, DayTimeLayers.ITEM_LAYER);
    layers.addComponentListener(entry);
    group.add(entry);
    return entry;
  }
}

