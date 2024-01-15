package ticker;
/*
=====================================================================

  JTickerTest.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import common.ui.ticker.*;

public class JTickerTest extends JPanel
{
  public JTickerTest()
  {
    JTicker ticker = new JTicker();
    ticker.setInterval(1000);
    
    ticker.addItem(new TickerAction(
      "This is the first message"));
    ticker.addItem(new TickerAction(
      "This is the second message"));
    ticker.addItem(new TickerAction(
      "This is the third message"));
    ticker.addItem(new TickerAction(
      "This is the fourth message"));
    ticker.addItem(new TickerAction(
      "This is the fifth message"));

    setLayout(new BorderLayout());
    add(BorderLayout.SOUTH, ticker);
    setPreferredSize(new Dimension(500, 300));
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JTicker Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JTickerTest());
    frame.pack();
    frame.show();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}

