package key;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import common.ui.key.*;

public class JKeyMapTest extends JPanel
{
  public JKeyMapTest()
  {
    JComponent component = new JTree();
    JKeyMap keyMap = new JKeyMap();
    keyMap.setModelInputMap(component.getInputMap());
    add(keyMap);
  }

  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
  
    JFrame frame = new JFrame("JKeyMap Test");
    frame.getContentPane().add(new JKeyMapTest());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.show();
  }
}

