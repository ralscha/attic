package scroller;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import common.ui.scroller.*;

public class JScrollerTest extends JPanel
  implements SwingConstants
{
  protected JTabBar tab;

  public JScrollerTest()
  {
    setLayout(new GridLayout());
    setPreferredSize(new Dimension(500, 300));
    
    JTabPane tabs = new JTabPane();
    tabs.addTab("Heads", new JLabel(
      new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/heads.jpg")))));
    tabs.addTab("Leonardo", new JLabel(
      new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/leonardo.jpg")))));
    tabs.addTab("Nebula", new JLabel(
      new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/nebula.jpg")))));
    tabs.addTab("Tree", new JLabel(
      new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/tree.jpg")))));
    
    JScrollBar vert = new JScrollerBar(
      JScrollerBar.VERTICAL, null, null,
      JScrollerBar.EXTENDED);
    
    JScrollBar horz = new JScrollerBar(
      JScrollerBar.HORIZONTAL,
      tabs.getTabBar(), null,
      JScrollerBar.EXTENDED);
    
    add(new JScrollerPane(tabs,
      JScrollerPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollerPane.HORIZONTAL_SCROLLBAR_ALWAYS,
      vert, horz));
  }
  
  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    
    JFrame frame = new JFrame("JScrollerPane Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(new JScrollerTest());
    frame.pack();
    frame.show();
  }
}

