import javax.swing.*;
import javax.swing.table.*;

import java.text.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;
import COM.odi.util.query.*;

public abstract class LiabilityTableModel extends AbstractTableModel {
        
    protected  DecimalFormat form, form2;
    protected Object dataArray[][];
    protected int number_of_rows;
    protected int number_of_columns;
    
    protected String[] names;
    protected int[] alignments;
    protected int[] width;
    protected Class[] classes;    

    public LiabilityTableModel() {
        form = new DecimalFormat("#,##0.00");
        form2 = new DecimalFormat("#######0.000000");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);        
        form2.setDecimalFormatSymbols(dfs);
    } 
        
    void addLiability(Liability l) {
    	Transaction tr = DbManager.startUpdateTransaction();
    	DbManager.addLiability(l);    	
    	updateData();
    	DbManager.commitUpdateTransaction(tr);
    	fireTableDataChanged();
    }
        
    public int getColumnCount() { return number_of_columns; } 
    public int getRowCount() { return number_of_rows; }
    
    public Object getValueAt(int row, int col) {
        return (dataArray[row][col]);
    }
    
    protected String accountFormat(String in) {
        if (in.length() == 16) {
            return(in.substring(0,4)+'-'+in.substring(4,11)+'-'+in.substring(11,13)+'-'+in.substring(13));
        } else 
            return(in);
    }
    
    protected String dateFormat(String in) {
        if (in.length() == 8) {
            return(in.substring(0,2)+'.'+in.substring(2,4)+'.'+in.substring(4));
        } else
            return(in);
    }
    
    
    public String getColumnName(int column) {return names[column];}
    public int getAlignment(int column) {return alignments[column];}
    public int getWidth(int column) {return width[column];}
    
    public Class getColumnClass(int col) {
        return (classes[col]);
    }

    public boolean isCellEditable(int row, int col) {return (false);} 
    
    protected void updateData() {
    	dataArray = new Object[getCollectionSize()][number_of_columns];    
    	fillDataArray(getCollection().iterator()); 
    }
    
    abstract int getCollectionSize();
    abstract Collection getCollection();                  
    abstract void fillDataArray(Iterator e);    
    
}