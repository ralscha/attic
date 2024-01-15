package organizer;

import common.ui.organizer.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class JOrganizerTest extends JPanel
  implements OrganizerConstants
{
  public static void main(String[] args)
  {
    JOrganizer organizer;
    int orderButtons =
      TOOLBAR_INSERT + TOOLBAR_DELETE + 
      TOOLBAR_UPDOWN;
    if (false)
    {
      String[] list = {"One", "Two", "Three", "Four", "Five"};
      JList westList = new JList(list);
      JList eastList = new JList();
      organizer = new JOrganizer(
        "Source", westList, "Target", eastList,orderButtons);
    }
    else
    {
      String[] headers = {"One", "Two"};
      String[][] data = {{"A", "1"}, {"B", "2"}, {"C", "3"}, {"D", "4"}};
      String[][] none = {};
      JTable westTable = new JTable(new DefaultTableModel(data, headers));
      JTable eastTable = new JTable(new DefaultTableModel(none, headers));
      organizer = new JOrganizer(
        "Source", westTable, "Target", eastTable,orderButtons);
    }
    
    JFrame frame = new JFrame("JOrganizer Test");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(BorderLayout.CENTER, organizer);
	 frame.setDefaultCloseOperation(3);
    frame.pack();
    frame.setVisible(true);
  }
}

