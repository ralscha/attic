import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.table.*;
import java.rmi.*;
import java.rmi.server.*;

public class EntryTableModel extends AbstractTableModel implements EntryListener {

    protected Object dataArray[][];
    protected int number_of_rows;
    protected int number_of_columns;
    
    protected String[] names;
    protected int[] alignments;
    protected int[] width;
    protected Class[] classes;    
    
    private IGuestBook guestBook;

    private void initRemoteObject(String serverName) {
        try {
            UnicastRemoteObject.exportObject(this);
            guestBook = (IGuestBook)Naming.lookup(serverName);
            guestBook.addEntryListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finalize() {
        removeListenerConnection();        
    }
    
    public void removeListenerConnection() {
        try {
            guestBook.removeEntryListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
        
    public EntryTableModel(String serverName) {
        super();
                
        initRemoteObject(serverName);
        
        
        number_of_rows = 0;        
        number_of_columns = 3;
        
        //TO DO 
        dataArray = new Object[200][number_of_columns];                

        names = new String[number_of_columns];
        alignments = new int[number_of_columns];
        width = new int[number_of_columns];
        classes = new Class[number_of_columns];
        
        names[ 0] = "Time";
        names[ 1] = "Name";
        names[ 2] = "Message";
       
        alignments[ 0] = SwingConstants.RIGHT;
        alignments[ 1] = SwingConstants.RIGHT;
        alignments[ 2] = SwingConstants.RIGHT;
       
        width[ 0] = 70;
        width[ 1] = 160;
        width[ 2] = 350;
        
        classes[ 0] = String.class;
        classes[ 1] = String.class;
        classes[ 2] = String.class;   

        try {
            addEntries(guestBook.getEntries());
        } catch(RemoteException re) {
            re.printStackTrace();
        }

    }
    
    public int getColumnCount() { return number_of_columns; } 
    public int getRowCount() { return number_of_rows; }
    public String getColumnName(int column) {return names[column];}
    public int getAlignment(int column) {return alignments[column];}
    public int getWidth(int column) {return width[column];}
    public Class getColumnClass(int col) {
        return (classes[col]);
    }
    public boolean isCellEditable(int row, int col) {return (false);} 
        
    
    public Object getValueAt(int row, int col) {
        return (dataArray[row][col]);
    }    
    
    public void add(String name, String message) {
        
        final Entry newEntry = new Entry(name, message);
        
        synchronized(this) {
            dataArray[number_of_rows][ 0] = newEntry.getTime();
            dataArray[number_of_rows][ 1] = name;
            dataArray[number_of_rows][ 2] = message;        
            number_of_rows++;
        }
        
        fireTableRowsInserted(number_of_rows-1,number_of_rows);
    
        new Thread(new Runnable() {
                    public void run() {
                        try {
                            guestBook.addEntry(EntryTableModel.this, newEntry);
                        } catch (RemoteException re) {
                            re.printStackTrace();
                        }
                    }}).start();
    }
    
    private synchronized void addEntries(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Entry e = (Entry)v.elementAt(i);
            dataArray[number_of_rows][ 0] = e.getTime();
            dataArray[number_of_rows][ 1] = e.getName();
            dataArray[number_of_rows][ 2] = e.getMessage();
            number_of_rows++;
        }        
    }
    
    public void newEntryArrived(Entry e) throws java.rmi.RemoteException {
        
        synchronized(this) {
            dataArray[number_of_rows][ 0] = e.getTime();
            dataArray[number_of_rows][ 1] = e.getName();
            dataArray[number_of_rows][ 2] = e.getMessage();
        
            number_of_rows++;        
        }
        fireTableRowsInserted(number_of_rows-1,number_of_rows);    
    }
    
}
