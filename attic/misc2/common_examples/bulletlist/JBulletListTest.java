package bulletlist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.ui.bulletlist.*;

public class JBulletListTest implements ActionListener
{
  public JBulletListTest() {}

  public void actionPerformed(ActionEvent event)
  {
    JBulletList pane = (JBulletList)event.getSource();
    System.out.println(pane);
  }

  public static JComponent[] createTestComponents(int count)
  {
    JComponent[] fields = new JComponent[count];
    for (int i = 0; i < count; i++)
    {
      JPanel panel = new JPanel(new GridLayout(2, 2, 4, 4));
      panel.add(new JTextField("Example Text"));
      panel.add(new JComboBox(new String[] {"Example Combo"}));
      panel.add(new JLabel("Example label"));
      fields[i] = panel;
    }
    return fields;
  }

  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    JBulletListTest listener = new JBulletListTest();
    
    String[] labels =
    {
      "This is a long bullet item that demonstrates how JTextLabel wraps " +
      "lines, as required.",
      "This multi-line\nlabel uses forced\nnew line characters.",
      "This is a single line label."
    };

    JTabbedPane tabs = new JTabbedPane();
    tabs.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

    JBulletList bulletList = JBulletList.createBulletList(
      labels, createTestComponents(3));
    bulletList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    tabs.addTab(" Bullet List ", bulletList);
    
    JBulletList numberList = JBulletList.createNumberList(
      labels, createTestComponents(3));
    numberList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    tabs.addTab(" Number List ", numberList);
    
    JBulletList radioList = JBulletList.createRadioList(
      labels, createTestComponents(3));
    radioList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    radioList.addActionListener(listener);
    tabs.addTab(" Radio List ", radioList);
    
    JBulletList checkList = JBulletList.createCheckList(
      labels, createTestComponents(3));
    checkList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    checkList.addActionListener(listener);
    
    //(checkList.getListEntries())[0].setBulletComponent(new JLabel());
    //(checkList.getListEntries())[0].setComponentEnabled(true);
    
    tabs.addTab(" Check List ", checkList);
    
    JFrame frame = new JFrame("JBulletList Test");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(BorderLayout.CENTER, tabs);
	 frame.setDefaultCloseOperation(3);
    frame.setSize(350, 350);
    frame.setVisible(true);
  }
}

