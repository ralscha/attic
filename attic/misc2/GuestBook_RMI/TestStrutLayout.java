import java.awt.*;
import com.sun.java.swing.*;
import com.sun.java.swing.border.*;
import matthew.awt.*;

public class TestStrutLayout extends JFrame
{
  protected static String tableData [][] = {{"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            {"William", "31"}, {"Larry", "37"}, {"Scott", "41"},
                                            };
  protected static String tableColumns [] = {"Name", "Age"};

  public TestStrutLayout ()
  {
    super ("TestStrutLayout");

    JPanel panel = new JPanel ();
    panel.setBorder (new EmptyBorder (5, 5, 5, 5));

    getContentPane ().add (panel);

    // create components
    JLabel nameLabel = new JLabel ("Name:");
    JTextField nameField = new JTextField (30);
    JLabel ageLabel = new JLabel ("Age:");
    JTextField ageField = new JTextField (10);
    JPanel tablePanel = new JPanel ();
    JTable table = new JTable (tableData, tableColumns);
    JScrollPane tablePane = JTable.createScrollPaneForTable (table);
    tablePane.setSize (200, 100);
    JButton editButton = new JButton ("Edit");
    JButton removeButton = new JButton ("Remove");

    // create StrutLayout and install in panel
    StrutLayout strutLayout = new StrutLayout ();
    panel.setLayout (strutLayout);

    // add root component
    panel.add (nameLabel);

    // add other components connected by struts
    panel.add (nameField, new StrutLayout.StrutConstraint
               (nameLabel, StrutLayout.MID_RIGHT, StrutLayout.MID_LEFT,
                StrutLayout.EAST));

    panel.add (ageField, new StrutLayout.StrutConstraint
               (nameField, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                StrutLayout.SOUTH));

    panel.add (ageLabel, new StrutLayout.StrutConstraint
               (ageField, StrutLayout.MID_LEFT, StrutLayout.MID_RIGHT,
                StrutLayout.WEST));

    panel.add (tablePane, new StrutLayout.StrutConstraint
               (ageField, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                StrutLayout.SOUTH));

    panel.add (editButton, new StrutLayout.StrutConstraint
               (tablePane, StrutLayout.TOP_RIGHT, StrutLayout.TOP_LEFT,
                StrutLayout.EAST));

    panel.add (removeButton, new StrutLayout.StrutConstraint
               (editButton, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                StrutLayout.SOUTH));

    // set springs on tablePane and nameField
    strutLayout.setSprings (tablePane, StrutLayout.SPRING_BOTH);
    strutLayout.setSprings (nameField, StrutLayout.SPRING_HORIZ);

    // ensure editButton and removeButton are always the same size
    StrutLayout.SizeGroup buttonGroup = strutLayout.createSizeGroup ();
    buttonGroup.add (editButton, StrutLayout.SIZE_WIDTH);
    buttonGroup.add (removeButton, StrutLayout.SIZE_WIDTH);

    pack ();
  }

  public static void main (String [] args)
  {
    new TestStrutLayout ().setVisible (true);
  }
}
