
package daytime;


import java.awt.*;
import javax.swing.*;
import common.ui.daytime.*;

public class JDayTimeTest
{
  protected static JComponent createTextArea(String text)
  {
    JTextArea area = new JTextArea(text);
    area.setWrapStyleWord(true);
    area.setLineWrap(true);
    return area;
  }

  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    
    JDayTime dayTime = new JDayTime(2, 20);
    dayTime.addEntry(13, 1, createTextArea("This is appointment 1"));
    dayTime.addEntry(15, 1.5, createTextArea("This is appointment 2"));
    dayTime.addEntry(17.5, 1.5, createTextArea("This is appointment 3"));

    JFrame frame = new JFrame("JDayTime Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(BorderLayout.CENTER, 
      new JScrollPane(dayTime));
    frame.setSize(300, 400);
    frame.setVisible(true);
  }
}

