package gtf.mbooker;

import javax.swing.*;
import javax.swing.table.*;

import java.text.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public abstract class LiabilityTableModel extends AbstractTableModel {
        
    protected DecimalFormat form, form2;
    protected SimpleDateFormat tsformat;
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
        
        tsformat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
    } 
        
    void addLiability(Liability liab) {

		if (liab != null) {           

			Connection conn = gtf.crapa.CrapaDbManager.getConnection();
			try {		
				if (liab instanceof ContingentLiability) {
					M_CONTLIABTable mct = new M_CONTLIABTable(conn);
					mct.insert((ContingentLiability)liab);
				} else if (liab instanceof FirmLiability) {
					M_FIRMLIABTable mft = new M_FIRMLIABTable(conn);
					mft.insert((FirmLiability)liab);
				}
		     	updateData();
		    	fireTableDataChanged();

			} catch (SQLException sqle) {
				System.err.println(sqle);
			}
		}
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
    	fillDataArray(getCollectionIterator()); 
    }
    
    abstract int getCollectionSize();
    abstract Iterator getCollectionIterator();                  
    abstract void fillDataArray(Iterator e);    
    
}