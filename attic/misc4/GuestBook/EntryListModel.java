import com.oti.ulc.util.*;
import com.oti.ulc.application.*;
import java.util.*;
import java.text.*;

public class EntryListModel extends ULCTableModel implements EntryListener {

    private SimpleDateFormat dateFormatH = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private String[] names;
    private int[] width;
    private int columnCount;
    
    public EntryListModel() {
        super();
        
        columnCount = 3;
        names = new String[columnCount];
        names[ 0] = "Date";
        names[ 1] = "Name";
        names[ 2] = "Message";
       
        width = new int[columnCount];
        width[ 0] = 100;
        width[ 1] = 250;
        width[ 2] = 250;
        
        
        GuestBook.getInstance().addEntryListener(this);
    }
    
    public int getColumnCount() { return columnCount; }
    
    public String getColumnName(int column) {return names[column];}

    public int getWidth(int column) {return width[column];}                


    public Object getValueAt(String colId, int row) {        
        
        Entry entry = (Entry)GuestBook.getInstance().getEntries().elementAt(row);
        if (entry != null) {
            if (names[0].equals(colId))
                return dateFormatH.format(entry.getDate());
            else if (names[1].equals(colId)) 
                return entry.getName();
            else if (names[2].equals(colId))             
                return entry.getMessage();
        }
        return null;
    }
                    
                    
    public void removeListenerConnection() {
        GuestBook.getInstance().removeEntryListener(this);
    }
    
    public int getRowCount() {
        return GuestBook.getInstance().getEntries().size();             
    }
    
    public void newEntryArrived(EntryEvent e) {
        Vector entries = GuestBook.getInstance().getEntries();
        notify(ULCTableModel.ROWS_ADDED, null, entries.size()-1, entries.size()-1);
    }
    
}
