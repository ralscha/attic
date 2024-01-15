import com.sun.java.swing.*;
import com.sun.java.swing.table.*;
import java.util.*;
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

    private Query queryNumberANDBranch;
    private Query queryNumber;
    private Query queryBranch;

    public LiabilityTableModel() {
        form = new DecimalFormat("#,##0.00");
        form2 = new DecimalFormat("#######0.000000");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);        
        form2.setDecimalFormatSymbols(dfs);
        
        FreeVariables freeV = new FreeVariables();
        freeV.put("gtfNumber", int.class);
        freeV.put("branch", String.class);
        queryNumberANDBranch = new Query(Liability.class, "getGtf_number() == gtfNumber && getGtf_proc_center() == branch", freeV);
                
        freeV = new FreeVariables();
        freeV.put("gtfNumber", int.class);
        queryNumber          = new Query(Liability.class, "getGtf_number() == gtfNumber", freeV);
        
        freeV = new FreeVariables();
        freeV.put("branch", String.class);
        queryBranch          = new Query(Liability.class, "getGtf_proc_center() == branch", freeV);        
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
            return(in.substring(6,8)+'.'+in.substring(4,6)+'.'+in.substring(0,4));
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
    
    public void searchDossier(String dossierNumber, String branch, boolean showOnlyErrors) {

	    Transaction tr = Transaction.begin(ObjectStore.READONLY);           
        Collection liabCollection = getCollection();
        FreeVariableBindings freeVB = new FreeVariableBindings();
        Collection queryResult;
        
 		if (dossierNumber != null && dossierNumber.length() > 0) {

            if (branch != null && branch.length() > 0) {
                
                if (branch.equalsIgnoreCase("all")) {
                    freeVB.put("gtfNumber", new Integer(dossierNumber));
                    queryResult = queryNumber.select(liabCollection, freeVB);
                } else {
                    freeVB.put("gtfNumber", new Integer(dossierNumber));
                    freeVB.put("branch", branch);
                    queryResult = queryNumberANDBranch.select(liabCollection, freeVB);
                }
                
                if (showOnlyErrors)
                    fillDataArray(searchErrors(queryResult.iterator()));
                else
                    fillDataArray(queryResult.iterator());                                    
            }
		} else {
            if (branch != null && branch.length() > 0) {
                
                if (!branch.equalsIgnoreCase("all")) {
                    freeVB.put("branch", branch);                
                    queryResult = queryBranch.select(liabCollection, freeVB);
                
                    if (showOnlyErrors)
                        fillDataArray(searchErrors(queryResult.iterator()));                                
                    else
                        fillDataArray(queryResult.iterator());                            
                } else {
                    if (showOnlyErrors)
                        fillDataArray(searchErrors(liabCollection.iterator()));                                
                    else                         
                        fillDataArray(liabCollection.iterator());                            
                    
                }
            }
		}
  		tr.commit(ObjectStore.RETAIN_HOLLOW);        
        fireTableChanged(null);             
    }
    
    Iterator searchErrors(Iterator e)
    {
        OSVector errorRows = new OSVector();
        Liability holder;
        
        while(e.hasNext()) {
            holder = (Liability)e.next();
            if (holder.getDuplicateFlag() || holder.hasEmptyAccountNumbers())
                errorRows.addElement(holder);    
        }        
        return(errorRows.iterator());
    }

    public String getTransferString() {
        StringBuffer sb = new StringBuffer();
        for (int row = 0; row < number_of_rows; row++) {
            for (int column = 0; column < number_of_columns; column++) {
                sb.append(getValueAt(row,column));
                if (!(column == number_of_columns-1)) 
                    sb.append("\t");
            }
            sb.append("\n");    
        }
        
        return(sb.toString());     
    }

           
    abstract Collection getCollection();                  
    abstract void fillDataArray(Iterator e);    
    
}
