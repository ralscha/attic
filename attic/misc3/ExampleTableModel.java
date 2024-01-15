
import javax.swing.table.AbstractTableModel;

public class ExampleTableModel extends AbstractTableModel
{
    private final String[] columnNames = { "Month", "Income" , "A"};

    final Object[][] data = {
        {"January",   "50" , "A"},
        {"February",  "50" , "A"},
        {"March",    "50"  , "A"},
        {"April",     "50" , "A"},
        {"May",       "50"  , "A"},
        {"June",     "50"  , "A"},
        {"July",      "50" , "B"},
        {"August",   "50" , "B"},
        {"September", "50", "1"},
        {"October",   "50" , "1"},
        {"November",  "50"  , "1"},
        {"December",  "50"  , "A"} 
    };

    public Class getColumnClass( int column ) 
    {
        return String.class;
    }

    public int getColumnCount() 
    {
        return columnNames.length;
    }

    public String getColumnName( int column ) 
    {
        return columnNames[column];
    }

    public int getRowCount() 
    {
        return data.length;
    }

    public Object getValueAt( int row, int column ) 
    {
        return data[row][column];
    }
}


