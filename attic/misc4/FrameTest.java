import java.awt.*;
import java.awt.event.*;
import COM.taligent.widget.*;
import COM.taligent.widget.event.*;
import COM.taligent.util.*;

public class FrameTest extends Frame implements ListboxListener
{

    MultiColumnListbox listbox;
    Object rows[][] = {
        { "1", "Andy",      "Clark"        },
        { "2", "Carl",    "Danowski"     },
        { "3", "Rich",      "gutierrez"      },
        { "4", "eugene",    "Shumulinsky"  },
        { "5", "Charlie", "Cleveland"    },
        { "6", "Tad",     "kollar"         }
        };

    public FrameTest()
    {
        super("MCL");
        setLayout(new BorderLayout());

        listbox = new MultiColumnListbox();
        listbox.addListboxListener(this);
        listbox.addColumn("#");
        listbox.addColumn("First\nName");
        listbox.addColumn("Last\nName");
        listbox.setCaptionBarHeight(listbox.getCaptionBarHeight() * 2);




        // Add the data to the listbox
        listbox.addRows(rows);


        // Change settings for status column
         ListboxColumn column = listbox.getColumnInfo(0);
         column.setWidth(30);
         column.setResizable(false);

         // Change settings for first name column
         column = listbox.getColumnInfo(1);
         column.setSorter(new SelectionSorter());

         // Change settings for last name column
         column = listbox.getColumnInfo(2);
         column.setWidth(100);
         column.setSorter(new SelectionSorter());


        add("Center", listbox);

        addWindowListener(new Exit());
    }

    public void rowSelected(ListboxEvent evt)
    {
        if (evt.getClickCount() == 2)
        {
            int row = evt.getRow();
            System.out.println("Row = "+row);
            System.out.println("Data = "+listbox.getRow(row)[0]);
        }
    }
    public void rowDeselected(ListboxEvent evt) { }

    public static void main(String args[])
    {

        FrameTest win = new FrameTest();

        win.setSize(300, 200);
        win.setVisible(true);
    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }
}