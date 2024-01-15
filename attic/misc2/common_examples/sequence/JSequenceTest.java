/*
=====================================================================

  JSequenceTest.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/
package sequence;

import common.ui.sequence.*;
import java.awt.*;
import javax.swing.*;

public class JSequenceTest extends JPanel
{
  protected JSequence sequence;
  protected JComponent image;
  
  public JSequenceTest()
  {
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setLayout(new BorderLayout(10, 10));
    SequenceList list = new SequenceList();
    list.addSequenceItem(
      new TestSequenceItem("Connecting to server"));
    list.addSequenceItem(
      new TestSequenceItem("Transmitting user profile"));
    list.addSequenceItem(
      new TestSequenceItem("Receiving product list"));
    list.addSequenceItem(
      new TestSequenceItem("Downloading product update"));
    list.addSequenceItem(
      new TestSequenceItem("Unpackaging product"));
    list.addSequenceItem(
      new TestSequenceItem("Installing software upgrade"));
    list.addSequenceItem(
      new TestSequenceItem("Configuring software upgrade"));
    list.addSequenceItem(
      new TestSequenceItem("Removing temporary files"));
    
    sequence = new JSequence(list);
    
    add(BorderLayout.WEST, image = getImage());
    JScrollPane scroll = sequence.getScrollPane();
    scroll.setBorder(BorderFactory.
      createLoweredBevelBorder());
    add(BorderLayout.CENTER, scroll);
    setPreferredSize(new Dimension(450,
      image.getPreferredSize().height + 10));

  }
  
  public void execute()
  {
    sequence.execute();
  }
  
  public JComponent getImage()
  {
    ImageIcon icon =        
      new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/sequence/images/cylinders.gif")));
    JLabel image = new JLabel(icon);
    image.setBorder(BorderFactory.
      createLoweredBevelBorder());
    return image;
  }
  
  public static void main(String[] args)
  {
    //PLAF.setNativeLookAndFeel(true);
    JSequenceTest test = new JSequenceTest();
    JFrame frame = new JFrame("JSequence Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(test);
    frame.pack();
    frame.show();
    
    test.execute();
  }
}

