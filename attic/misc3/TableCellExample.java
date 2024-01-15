import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.JFrame;
import javax.swing.JTable;

public class TableCellExample 
{
    public static void main( String[] args ) 
    {
        JTable table = new JTable( new ExampleTableModel() ); 
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();
        renderer.addRow(1);
        renderer.addRow(3);

            table.setDefaultRenderer( String.class, renderer );
   

        JFrame frame = new JFrame();
        frame.addWindowListener( 
            new WindowAdapter() 
            {
                public void windowClosing(WindowEvent e) 
                {
                    System.exit(0);
                }
            }
        );

        

        frame.getContentPane().add( table );
        frame.pack();
        frame.setVisible( true );

    }
}