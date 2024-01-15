package desktop;

/*
=====================================================================
  
  ExampleToolbox.java

  Created by Claude Duguay
  Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class ExampleToolbox extends JPanel
{
  public ExampleToolbox()
  {
    setLayout(new GridLayout(5, 4, 2, 2));
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    add(makeButton("JButton"));
    add(makeButton("JRadioButton"));
    add(makeButton("JCheckBox"));
    add(makeButton("JComboBox"));
    
    add(makeButton("JWindow"));
    add(makeButton("JDialog"));
    add(makeButton("JFrame"));
    add(makeButton("JInternalFrame"));

    add(makeButton("JComponent"));
    add(makeButton("JDesktopPane"));
    add(makeButton("JProgressBar"));
    add(makeButton("JTabbedPane"));

    add(makeButton("JLabel"));
    add(makeButton("JList"));
    add(makeButton("JTree"));
    add(makeButton("JTable"));

    add(makeButton("Border"));
    add(makeButton("JApplet"));
    add(makeButton("JPanel"));
    add(makeButton("JLayeredPane"));
  }
  
  protected JButton makeButton(String item)
  {
    ImageIcon icon = new ImageIcon(
      "desktop/images/" + item + "Color16.gif");
    JButton button = new JButton(icon);
    button.setMargin(new Insets(0, 0, 0, 0));
    return button;
  }

}
