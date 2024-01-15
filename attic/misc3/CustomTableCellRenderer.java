
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer 
{

    Set intSet = new HashSet();

    public void addRow(int row) {
      intSet.add(new Integer(row));
    }

    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);

        if (contains(row, intSet)) {
          setBackground( Color.red );
        } else {
          setBackground( Color.white); 
        }

        return this;

    }

    private boolean contains(int row, Set s) {
      Iterator it = s.iterator();
      while(it.hasNext()) {
        return (row == ((Integer)it.next()).intValue()) ;
      }
      return false;
    }

}