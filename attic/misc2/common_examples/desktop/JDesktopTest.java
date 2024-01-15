package desktop;

/*
=====================================================================

    JDesktopTest.java
    
    Created by Claude Duguay
    Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.ui.desktop.*;
import common.swing.*;

public class JDesktopTest extends JPanel
  implements ActionListener
{
  protected JDesktop desktop;
  protected static JMenuItem cascade, tileVert, tileHorz;
  
  public JDesktopTest()
  {
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, desktop = new JDesktop());
    
    JInternalFrame calc = createRollupFrame("Calculator");
    calc.getContentPane().add(new ExampleCalculator()); 
    calc.setBounds(460, 10, 122, 170);
    desktop.add(calc, JDesktopPane.PALETTE_LAYER);
        
    JInternalFrame tool = createRollupFrame("Toolbox");
    tool.getContentPane().add(new ExampleToolbox()); 
    tool.setBounds(460, 190, 122, 170);
    desktop.add(tool, JDesktopPane.PALETTE_LAYER);
        
    JInternalFrame normal = createConstrainedFrame("Constrained");
    normal.setBounds(0, 280, 200, 100);
    normal.setBackground(Color.lightGray);
    desktop.add(normal);
    
    for (int i = 1; i <= 10; i++)
    {
      desktop.add(createNormalFrame(i * 10, i * 10, i));
    }
    
    calc.setVisible(true);
    tool.setVisible(true);
    normal.setVisible(true);
  }
    
  public JInternalFrame createRollupFrame(String title)
  {
    return new JDesktopFrame(title,
      false, true, false, true, false, true);
  }
    
  public JInternalFrame createConstrainedFrame(String title)
  {
    return new JDesktopFrame(title,
      true, true, true, true, true);
  }
    
  public JInternalFrame createNormalFrame(int x, int y, int i)
  {
    JInternalFrame frame = new JDesktopFrame("" + i);
    frame.setBackground(Color.lightGray);
    frame.setBounds(x, y, 300, 150);
    frame.setVisible(true);
    return frame;
  }
    
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == cascade)
    {
      desktop.cascade();
    }
    if (source == tileVert)
    {
      desktop.tileVertical();
    }
    if (source == tileHorz)
    {
      desktop.tileHorizontal();
    }
  }
  
  protected static JMenuBar createMenuBar(JDesktopTest desktop)
  {
    JMenuBar bar = new JMenuBar();
    bar.setBorder(null);
    JMenu menu = new JMenu("Window");
    menu.add(cascade = new JMenuItem("Cascade"));
    menu.add(tileVert = new JMenuItem("Tile vertically"));
    menu.add(tileHorz = new JMenuItem("Tile horizontally"));
    cascade.addActionListener(desktop);
    tileVert.addActionListener(desktop);
    tileHorz.addActionListener(desktop);
    bar.add(menu);
    return bar;
  }
  
  public static void main(String[] args)
  {
    PlafPanel.setNativeLookAndFeel(true);
    JDesktopTest desktop = new JDesktopTest();
    JFrame frame = new JFrame("JDesktop Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setBounds(50, 25, 600, 430);
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(desktop);
    frame.setJMenuBar(createMenuBar(desktop));
    frame.show();
  }
}

