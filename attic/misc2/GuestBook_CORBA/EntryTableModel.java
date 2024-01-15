import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.table.*;

public class EntryTableModel extends AbstractTableModel {

    protected Object dataArray[][];
    protected int number_of_rows;
    protected int number_of_columns;
    
    protected String[] names;
    protected int[] alignments;
    protected int[] width;
    protected Class[] classes;    
    
    private IGuestBook guestBook;
    private ControlThread listenerThread;
    private EntryListenerAdapter adapter;
    

    class ControlThread extends Thread {
        
        EntryListenerAdapter adapter;
        IGuestBook guestBook;
        boolean stop;
        
        ControlThread(IGuestBook gb, EntryListenerAdapter adapter) {
            this.adapter = adapter;
            this.guestBook = gb;
            stop = false;
        }
        
        public void stopThread() {
            System.out.println("STOP ControlThread");
            stop = true;
        }
        
        public void run() {
            try {                
                
                guestBook.addEntryListener(adapter);            
                
                while((Thread.currentThread() == this) && (!stop)) {
                    try {
                        sleep(5000);
                    } catch(InterruptedException ex) { }
                }
                
                guestBook.removeEntryListener(adapter);
            
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }


    public void stopListening() {
        listenerThread.stopThread();
    }
        
    public EntryTableModel(IGuestBook guestBook, org.omg.CORBA.ORB orb) {
        super();
        
        this.guestBook = guestBook;        
                
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

        addEntries(guestBook.getEntries());
        
        adapter = new EntryListenerAdapter(this);
        orb.connect(adapter);
        
        listenerThread = new ControlThread(guestBook, adapter);
        listenerThread.start();
        listenerThread.setPriority(Thread.NORM_PRIORITY + 1);

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
    
        /*
        synchronized(this) {
            dataArray[number_of_rows][ 0] = newEntry.getTime();
            dataArray[number_of_rows][ 1] = name;
            dataArray[number_of_rows][ 2] = message;        
            number_of_rows++;
        }
        
        fireTableRowsInserted(number_of_rows-1,number_of_rows);
        */
    
        new Thread(new Runnable() {
                    public void run() {
                        guestBook.addEntry(adapter, newEntry);
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
    
    public void newEntryArrived(Entry e) {
        
        synchronized(this) {
            dataArray[number_of_rows][ 0] = e.getTime();
            dataArray[number_of_rows][ 1] = e.getName();
            dataArray[number_of_rows][ 2] = e.getMessage();
        
            number_of_rows++;        
        }
        fireTableRowsInserted(number_of_rows-1,number_of_rows);    
    }
    
}
