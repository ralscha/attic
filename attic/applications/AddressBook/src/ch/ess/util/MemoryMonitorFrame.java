package ch.ess.util;

import javax.swing.*;


public class MemoryMonitorFrame extends JFrame {
  public MemoryMonitorFrame() {  
    super("Memory Monitor");
    //setResizable(false);
    getContentPane().add("Center", new MemoryMonitor());
    pack();
    //setSize(new Dimension(200, 200));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
  }
}

// End of MemoryMonitorFrame.java
