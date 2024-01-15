package range;

import java.awt.*;
import javax.swing.*;
import common.ui.range.*;

public class JRangeTest extends JPanel
{
  protected static final RangeRenderer ganttGroupRenderer =
    new GanttRangeRenderer(GanttRangeRenderer.GROUP);
  protected static final RangeRenderer ganttTaskRenderer =
    new GanttRangeRenderer(GanttRangeRenderer.TASK);
  protected static final RangeEditor ganttGroupEditor =
    new GanttRangeEditor(GanttRangeEditor.GROUP);
  protected static final RangeEditor ganttTaskEditor =
    new GanttRangeEditor(GanttRangeEditor.TASK);
  
  public JRangeTest(boolean gantt)
  {
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    setLayout(new BorderLayout(4, 4));
    
    int count = 12;
    JPanel panel = new JPanel(new GridLayout(count, 1, 2, 2));
    for (int i = 0; i < count; i++)
    {
      int val = (int)(Math.random() * 40) + 5;
      int len = (int)(Math.random() * 40) + 5;
      JRange range = new JRange(val, len, 0, 100);
      if (gantt)
      {
        if (i % 3 == 0)
        {
          range.setEditor(ganttGroupEditor);
          range.setRenderer(ganttGroupRenderer);
        }
        else
        {
          range.setEditor(ganttTaskEditor);
          range.setRenderer(ganttTaskRenderer);
        }
      }
      else
      {
        range.setBorder(BorderFactory.createLoweredBevelBorder());
      }
      panel.add(range);
    }
    add(BorderLayout.NORTH, panel);
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JRange Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JRangeTest(true));
    frame.pack();
    frame.show();
  }
}

