import com.sun.java.swing.*;
import com.sun.java.swing.table.*;
import java.util.*;
import java.text.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;

public class CLTableModel extends LiabilityTableModel {
    
    public CLTableModel() {   
        super();
        number_of_rows = 0;        
        number_of_columns = 13;
        
        dataArray = new Object[DbManager.getContCollectionSize()][number_of_columns];                

        names = new String[number_of_columns];
        alignments = new int[number_of_columns];
        width = new int[number_of_columns];
        classes = new Class[number_of_columns];
        
        names[ 0] = "Sequence Number";
        names[ 1] = "Type";
        names[ 2] = "Dossier Number";
        names[ 3] = "Currency";
        names[ 4] = "Amount";
        names[ 5] = "Account Number";
        names[ 6] = "Expiry Date";
        names[ 7] = "Exchange Rate";
        names[ 8] = "Processing Center";
        names[ 9] = "Customer Reference";
        names[10] = "GTF Type";
        names[11] = "Dossier Item";
        names[12] = "BU Code";
        
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
        alignments[11] = SwingConstants.LEFT;
        alignments[12] = SwingConstants.LEFT;
        
        width[ 0] = 20;
        width[ 1] = 10;
        width[ 2] = 40;
        width[ 3] = 30;
        width[ 4] = 100;
        width[ 5] = 135;
        width[ 6] = 80;
        width[ 7] = 80;
        width[ 8] = 32;
        width[ 9] = 160;
        width[10] = 130;
        width[11] = 130;
        width[12] = 40;

        classes[ 0] = Integer.class;
        classes[ 1] = String.class;
        classes[ 2] = Integer.class;
        classes[ 3] = String.class;
        classes[ 4] = String.class;
        classes[ 5] = String.class;        
        classes[ 6] = String.class;
        classes[ 7] = String.class;
        classes[ 8] = String.class;
        classes[ 9] = String.class;
        classes[10] = String.class;
        classes[11] = String.class;
        classes[12] = String.class;
    }
    

    Collection getCollection() {
        return DbManager.getContCollection();
    }
    
    void fillDataArray(Iterator e) {
        ContingentLiability cl;        
        int i = 0;
        
        while(e.hasNext()) {
            cl = (ContingentLiability)e.next();  
            dataArray[i][ 0] = new Integer(cl.getSequence_number()); 
            dataArray[i][ 1] = cl.getType(); 
            dataArray[i][ 2] = new Integer(cl.getGtf_number()); 
            dataArray[i][ 3] = cl.getCurrency(); 
            dataArray[i][ 4] = form.format(cl.getAmount()); 
            dataArray[i][ 5] = accountFormat(cl.getAcct_mgmt_unit()+cl.getAccount_number()); 
            dataArray[i][ 6] = dateFormat(cl.getExpiry_date().toString()); 
            dataArray[i][ 7] = form2.format(cl.getExchange_rate());
            dataArray[i][ 8] = cl.getGtf_proc_center(); 
            dataArray[i][ 9] = cl.getCustomer_ref(); 
            dataArray[i][10] = cl.getGtf_type(); 
            dataArray[i][11] = cl.getDossier_item();
            dataArray[i][12] = cl.getBu_code();             
            i++;
        }
        number_of_rows = i;
    }
}
