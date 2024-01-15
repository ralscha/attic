import com.sun.java.swing.*;
import com.sun.java.swing.table.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import java.io.*;


public class FLTableModel extends AbstractTableModel {

    private com.twolink.dbgen.support.Driver driver;
    private com.twolink.dbgen.support.DbAccess dba;
    private String drv, url;
    
    final static int STRING = 0;
    final static int PLAIN_TEXT = 1;
    

    final static String[] names = {"Sequence Number", "Dossier Number",
                                   "Currency", "Amount", "Debit Account Number",
                                   "Credit Account Number", 
                                   "Value Date", "Exchange Rate", "Processing Center",
                                   "Customer Reference", "GTF Type", "Dossier Item",
                                   "BU Code"};
    final static int[] alignments = {SwingConstants.RIGHT, SwingConstants.RIGHT,
                                   SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.RIGHT,
                                   SwingConstants.RIGHT,
                                   SwingConstants.RIGHT, SwingConstants.RIGHT, SwingConstants.RIGHT,
                                   SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.LEFT,
                                   SwingConstants.LEFT };
    final static int[] width = {20, 40, 30, 100, 135, 135, 80, 80, 32, 160, 130, 130, 40};

    private Vector rows;
    private Vector errorRows;
    private Vector allRows;
    
    private Class strclass = new String().getClass();
    private DecimalFormat form, form2;

    public FLTableModel(String drv, String url) {
        this.drv = drv;
        this.url = url;

        errorRows = new Vector();
        allRows = new Vector();
        rows = allRows;

        form = new DecimalFormat("#,##0.00");
        form2 = new DecimalFormat("0.0000000");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);
        form2.setDecimalFormatSymbols(dfs);
    }

    public void executeQuery(String dossierNumber,
                             String branch,
                             boolean showOnlyErrors) throws SQLException, ClassNotFoundException {
        dba = new com.twolink.dbgen.support.DbAccess(drv, url, "", "");
    	driver = new com.twolink.dbgen.support.Driver(dba);

        boolean dossier = false;

    	String whereClause = new String();
		if (dossierNumber != null && dossierNumber.length() > 0) {
		    whereClause = "gtf_number = "+dossierNumber;
		    dossier = true;
		}
		if (branch != null && branch.length() > 0) {
		    if (dossier)
    			whereClause += " AND gtf_proc_center = '"+branch+"'";
    		else
    		    whereClause = "gtf_proc_center = '"+branch+"'";
    	}

    	FirmLiability holder = new FirmLiability();
        ResultSet rs = driver.query(holder, whereClause.toString(), "gtf_number ASC, sequence_number ASC");
        FirmLiability obj;
        allRows = new Vector();
        while ((obj = (FirmLiability)driver.getNextRecord(holder, rs)) != null)
    	    allRows.addElement(obj);

        searchErrors();
       	if (showOnlyErrors) rows = errorRows;
    	else                rows = allRows;
                    
        fireTableChanged(null);
    }

    void searchErrors()
    {
        FirmLiability holder, holder2;
        Vector branchRows = new Vector();
        int memgtf; 
        
        int i, j;
        boolean found;
        errorRows = new Vector();
        
        for (i = 0; i < allRows.size(); i++) {
            holder = (FirmLiability)allRows.elementAt(i);
            if (holder.getDeb_acct_number().equals("") || holder.getDeb_acct_mgmt_unit().equals("") || 
                holder.getCre_acct_number().equals("") || holder.getCre_acct_mgmt_unit().equals("") )  
                errorRows.addElement(allRows.elementAt(i));                           
        }
        
        Enumeration e = allRows.elements();
        holder = null;
        if (e.hasMoreElements()) {
            holder = (FirmLiability)e.nextElement();            

            while(e.hasMoreElements()) {       
                branchRows.removeAllElements();            
                memgtf = holder.getGtf_number();
                branchRows.addElement(holder);
                while(e.hasMoreElements() && 
                     (holder = (FirmLiability)e.nextElement()).getGtf_number() == memgtf)
                    branchRows.addElement(holder);            
                        
                removeOkRows(branchRows);
                for (int n = 0; n < branchRows.size(); n++)
                    errorRows.addElement(branchRows.elementAt(n));
            }
        }        
    }
    
    void removeOkRows(Vector v)
    {
        FirmLiability holder, holder2;
        boolean found;
        Vector removers = new Vector();
        int j,i;
        
        for(i = 0; i < v.size(); i++) {
            found = false;
            holder = (FirmLiability)v.elementAt(i);            
            j = 0;
            while(j < v.size() && !found) {
                if (i != j) {
                    holder2 = (FirmLiability)v.elementAt(j);                                
                    if (holder.equals(holder2)) found = true;            
                }
                j++;
            }
            if (!found) removers.addElement(holder);
        }
        
        for (i = 0; i < removers.size(); i++)
            v.removeElement(removers.elementAt(i));
    }

    public int getColumnCount() { return names.length; }
    public int getRowCount() { return rows.size(); }

    public Object getValueAt(int row, int col) {
        FirmLiability fl = (FirmLiability)rows.elementAt(row);
        switch(col) {
            case  0 : return (new Integer(fl.getSequence_number()));
            case  1 : return (new Integer(fl.getGtf_number()));
            case  2 : return (fl.getCurrency());
            case  3 : return (form.format(fl.getAmount()));
            case  4 : return accountFormat(fl.getDeb_acct_mgmt_unit()+fl.getDeb_acct_number());
            case  5 : return accountFormat(fl.getCre_acct_mgmt_unit()+fl.getCre_acct_number());
            case  6 : return (fl.getValue_date().toString());
            case  7 : return (form2.format(fl.getExchange_rate()));
            case  8 : return (fl.getGtf_proc_center());
            case  9 : return (fl.getCustomer_ref());
            case 10 : return (fl.getGtf_type());
            case 11 : return (fl.getDossier_item());
            case 12 : return (fl.getBu_code());
            default : return(null);
        }        
    }

    String accountFormat(String in) {
        if (in.length() == 16) {
            return(in.substring(0,4)+'-'+in.substring(4,11)+'-'+in.substring(11,13)+'-'+in.substring(13));
        } else 
            return(in);
    }

    public String getColumnName(int column) {return names[column];}
    public int getAlignment(int column) {return alignments[column];}
    public int getWidth(int column) {return width[column];}

    public Class getColumnClass(int col) {
        return (strclass);
        /*return getValueAt(0,col).getClass();*/
    }

    public boolean isCellEditable(int row, int col) {return (false);}

    /*
    public void setValueAt(Object aValue, int row, int column) {
        data[row][column] = aValue;
    }*/
        
    public String getTransferString(boolean showOnlyErrors) {
        StringBuffer sb = new StringBuffer();
        Vector v = new Vector();
        if (showOnlyErrors) v = errorRows;
        else                v = allRows;
        
        for (int i = 0; i < v.size(); i++)
            sb.append(((FirmLiability)v.elementAt(i)).toExternalString()).append("\n");
        return(sb.toString());     
    }
   
}
