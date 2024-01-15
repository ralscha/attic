package digital;

import java.awt.*;
import javax.swing.*;
import common.ui.digital.*;
public class JDigitalTest
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JDigital Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[0]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[1]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[2]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[3]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[4]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[5]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[6]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[7]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[8]));
    frame.getContentPane().add(new JDigital(PatternImage.DIGIT[9]));
    frame.pack();
    frame.show();
  }
}

