package validate;

import java.awt.*;
import javax.swing.*;
import common.ui.validate.*;

public class JValidatingFieldTest extends JPanel
{
  public JValidatingFieldTest()
  {
    JValidatingField field1 = new JValidatingField("Integer");
    field1.addValidator(new BlankFieldValidator());
    field1.addValidator(new IntegerFieldValidator());
    
    JValidatingField field2 = new JValidatingField("Range");
    field2.addValidator(new BlankFieldValidator());
    field2.addValidator(new IntegerFieldValidator());
    field2.addValidator(new RangeFieldValidator(0, 10));
    
    JValidatingField field3 = new JValidatingField("Decimal");
    field3.addValidator(new BlankFieldValidator());
    field3.addValidator(new DecimalFieldValidator());
    
    setLayout(new BorderLayout(4, 4));
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
    JPanel prompts = new JPanel(new GridLayout(3, 1, 4, 4));
    prompts.add(new JLabel("Integer:", JLabel.RIGHT));
    prompts.add(new JLabel("Range:", JLabel.RIGHT));
    prompts.add(new JLabel("Decimal:", JLabel.RIGHT));
    add(BorderLayout.WEST, prompts);
    
    JPanel fields = new JPanel(new GridLayout(3, 1, 4, 4));
    add(BorderLayout.CENTER, fields);
    fields.add(field1);
    fields.add(field2);
    fields.add(field3);
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JValidatingField Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JValidatingFieldTest());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.show();
  }
}

