package cellborder;

import java.awt.*;
import javax.swing.*;
import common.ui.cellborder.*;
import common.swing.*;

public class JCellBorderTest extends JPanel
  implements SwingConstants
{
  public JCellBorderTest()
  {
    CellBorderPanel borderPanel = new CellBorderPanel();
    
    setLayout(new GridLayout());
    setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createEmptyBorder(10, 10, 10, 10),
      BorderFactory.createEtchedBorder()));
    add(new JCellBorder());
  }
  
  public static void main(String[] args)
  {
    PlafPanel.setNativeLookAndFeel(true);
    
    JFrame frame = new JFrame("JCellBorder Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JCellBorderTest());
    frame.pack();
    frame.show();
  }
}

