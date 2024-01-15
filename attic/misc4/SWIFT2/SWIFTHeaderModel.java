import com.sun.java.swing.*;
import com.sun.java.swing.table.*;
import java.util.*;
import java.text.*;
import COM.odi.util.*;

public class SWIFTHeaderModel extends AbstractTableModel {
        
    private Object dataArray[][];
    private int number_of_rows;
    private int number_of_columns;
    
    private String[] names;
    private int[] alignments;
    private int[] width;
    private Class[] classes;    

    private ISWIFTListServer isls;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat dateFormatH = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    public SWIFTHeaderModel(ISWIFTListServer isls) {               
       
        number_of_rows = 0;        
        number_of_columns = 11;
                    
        this.isls = isls;
        
        dataArray = new Object[isls.getNumberOfHeaders()][number_of_columns];                

        names = new String[number_of_columns];
        alignments = new int[number_of_columns];
        width = new int[number_of_columns];
        classes = new Class[number_of_columns];
        
        names[ 0] = "TOSN";
        names[ 1] = "Send Date";
        names[ 2] = "Address Sender";
        names[ 3] = "Session Number";
        names[ 4] = "Sequence Number";
        names[ 5] = "Address Receiver";
        names[ 6] = "Proc Center";
        names[ 7] = "Message Type";
        names[ 8] = "Duplicate";
        names[ 9] = "Priority";
        names[10] = "Receive Date";
        
        alignments[ 0] = SwingConstants.RIGHT;
        alignments[ 1] = SwingConstants.CENTER;
        alignments[ 2] = SwingConstants.RIGHT;
        alignments[ 3] = SwingConstants.RIGHT;        
        alignments[ 4] = SwingConstants.RIGHT;
        alignments[ 5] = SwingConstants.RIGHT;
        alignments[ 6] = SwingConstants.RIGHT;
        alignments[ 7] = SwingConstants.RIGHT;
        alignments[ 8] = SwingConstants.RIGHT;
        alignments[ 9] = SwingConstants.LEFT;
        alignments[10] = SwingConstants.LEFT;
        
        width[ 0] = 50;
        width[ 1] = 80;
        width[ 2] = 90;
        width[ 3] = 50;
        width[ 4] = 60;
        width[ 5] = 100;
        width[ 6] = 40;
        width[ 7] = 30;
        width[ 8] = 30;
        width[ 9] = 30;
        width[10] = 125;

        classes[ 0] = String.class;
        classes[ 1] = String.class;
        classes[ 2] = String.class;
        classes[ 3] = String.class;
        classes[ 4] = String.class;
        classes[ 5] = String.class;        
        classes[ 6] = String.class;
        classes[ 7] = String.class;
        classes[ 8] = String.class;
        classes[ 9] = String.class;
        classes[10] = String.class;
    }
        
    public int getColumnCount() { return number_of_columns; } 
    public int getRowCount() { return number_of_rows; }
    
    public Object getValueAt(int row, int col) {
        return (dataArray[row][col]);
    }
        
    public String getColumnName(int column) {return names[column];}
    public int getAlignment(int column) {return alignments[column];}
    public int getWidth(int column) {return width[column];}
    
    public Class getColumnClass(int col) {
        return (classes[col]);
    }

    public boolean isCellEditable(int row, int col) {return (false);} 

    public String getTOSN(int row) {
        if ((row >= 0) && (row < number_of_rows)) {
            return (String)dataArray[row][0];
        }
        return null;
    }

    void fillDataArray(Iterator e) {
        SWIFTHeader sh;        
        int i = 0;        
        while(e.hasNext()) {
            sh = (SWIFTHeader)e.next();  

            dataArray[i][ 0] = sh.getTOSN();
            dataArray[i][ 1] = dateFormat.format(sh.getSend_Date().getTime());
            dataArray[i][ 2] = sh.getAddress_Sender(); 
            dataArray[i][ 3] = sh.getSession_Number(); 
            dataArray[i][ 4] = sh.getSequence_Number(); 
            dataArray[i][ 5] = sh.getAddress_Receiver(); 
            dataArray[i][ 6] = sh.getProc_Center(); 
            dataArray[i][ 7] = sh.getMessage_Type();
            dataArray[i][ 8] = sh.getDuplicate(); 
            dataArray[i][ 9] = sh.getPriority(); 
            dataArray[i][10] = dateFormatH.format(sh.getReceive_Date().getTime());
            i++;
        }
        number_of_rows = i;
    }
    
    public void showHeaders(Calendar fromDate, Calendar toDate) {
        Collection collection = isls.getSWIFTHeaders(fromDate, toDate);
        fillDataArray(collection.iterator());
        fireTableChanged(null);             
    }
    
}
