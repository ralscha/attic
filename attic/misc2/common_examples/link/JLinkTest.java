package link;
import java.awt.*;
import javax.swing.*;
import common.ui.link.*;

public class JLinkTest extends JPanel
{
  public JLinkTest() {
    setPreferredSize(new Dimension(350, 350));
    setLayout(new GridLayout());
    add(new JLink());
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("JLink Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JLinkTest());
    frame.pack();
    frame.show();
  }
}

