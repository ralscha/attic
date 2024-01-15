
import COM.odi.*;
import com.objectspace.djgl.*;
import java.util.*;
import java.io.*;

public class PSETest
{
    static final String DB_NAME = "Liability.odb";
    static final String CONT_LIABILITY_ROOT = "ContLiability";
    static final String FIRM_LIABILITY_ROOT = "FirmLiability";
    
    OrderedMap firmMap, contMap;
    
    void go(String path) {
        boolean dbNew = false;
        Database db;
        Session session = Session.create(null, null);        
        session.join();

        try {
            db = Database.open(DB_NAME, ObjectStore.UPDATE);          
        } catch(DatabaseNotFoundException e) { 
            db = Database.create(DB_NAME, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);                    
            dbNew = true;
        }
        
        Transaction tr = Transaction.begin(ObjectStore.UPDATE);
        
        if (dbNew) {
            firmMap = new OrderedMap(true);
            contMap = new OrderedMap(true);
            db.createRoot(FIRM_LIABILITY_ROOT, firmMap);
            db.createRoot(CONT_LIABILITY_ROOT, contMap);        
        } else {
            firmMap = (OrderedMap)db.getRoot(FIRM_LIABILITY_ROOT);
            contMap = (OrderedMap)db.getRoot(CONT_LIABILITY_ROOT);
        }
        
        readFiles(path);
        tr.commit();
        session.terminate();
    }
    
    void readFiles(String path) {
        Vector contFiles = new Vector();
        Vector firmFiles = new Vector();
        
        try {
            File f;
            f = new File(path);            
            String lst[] = f.list();

            for (int i = 0; i < lst.length; i++) {
                if (lst[i].endsWith(".del"))
                    if (lst[i].startsWith("cont_"))
                        contFiles.addElement(lst[i]);
                    else if (lst[i].startsWith("firm_"))
                        firmFiles.addElement(lst[i]);
            }

            for (int i = 0; i < contFiles.size(); i++) {
                System.out.println("reading ... "+path+"\\"+(String)contFiles.elementAt(i));                
                readContFile(path+"\\"+(String)contFiles.elementAt(i));
            }
            
            for (int i = 0; i < firmFiles.size(); i++) {
                System.out.println("reading ... "+path+"\\"+(String)firmFiles.elementAt(i));                
                readFirmFile(path+"\\"+(String)firmFiles.elementAt(i));
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
             System.err.println(e);
        }
    }
    
    void readContFile(String fileName) throws IOException, NumberFormatException {        
        BufferedReader dis = new BufferedReader(new FileReader(fileName));
        StreamTokenizer st;
        String line = null;                        
        int type;
        Vector elem = new Vector();
        
        ContingentLiability cl;
                
        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {
                elem.removeAllElements();
                st = createStreamTokenizer(line);
                
                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }
                               
                cl = new ContingentLiability();                    
                cl.setSequence_number(Integer.parseInt((String)elem.elementAt(1)));
                cl.setType((String)elem.elementAt(2));
                cl.setGtf_number(Integer.parseInt(((String)elem.elementAt(3)).trim()));
                cl.setCurrency((String)elem.elementAt(4));
                cl.setAmount(Double.valueOf((String)elem.elementAt(5)).doubleValue());
                cl.setAcct_mgmt_unit((String)elem.elementAt(6));
                cl.setAccount_number((String)elem.elementAt(7));
                cl.setExpiry_date((String)elem.elementAt(8));
                                                   
                cl.setExchange_rate(Double.valueOf((String)elem.elementAt(9)).doubleValue());
                cl.setGtf_proc_center((String)elem.elementAt(10));
                cl.setCustomer_ref((String)elem.elementAt(11));
                cl.setGtf_type((String)elem.elementAt(12));
                cl.setDossier_item((String)elem.elementAt(13));
                cl.setBu_code((String)elem.elementAt(14));
                
                contMap.add(cl.getKey(), cl);
            }
        }
        dis.close();
    }

    void readFirmFile(String fileName) throws IOException, NumberFormatException {        
        BufferedReader dis = new BufferedReader(new FileReader(fileName));
        String line = null;                        
        StreamTokenizer st;        
        int type;
        Vector elem = new Vector();        
        FirmLiability fl;
                        
        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {
                
                elem.removeAllElements();
                st = createStreamTokenizer(line);
                
                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }
                         
                fl = new FirmLiability();                
                fl.setSequence_number(Integer.parseInt((String)elem.elementAt(1))); 
                fl.setGtf_number(Integer.parseInt(((String)elem.elementAt(2)).trim()));
                fl.setCurrency((String)elem.elementAt(3));
                fl.setAmount(Double.valueOf((String)elem.elementAt(4)).doubleValue());
                fl.setDeb_acct_mgmt_unit((String)elem.elementAt(5));
                fl.setDeb_acct_number((String)elem.elementAt(6));
                fl.setCre_acct_mgmt_unit((String)elem.elementAt(7));
                fl.setCre_acct_number((String)elem.elementAt(8));
                
                fl.setValue_date((String)elem.elementAt(9));
                fl.setExchange_rate(Double.valueOf((String)elem.elementAt(10)).doubleValue());
                fl.setGtf_proc_center((String)elem.elementAt(11));
                fl.setCustomer_ref((String)elem.elementAt(12));
                fl.setGtf_type((String)elem.elementAt(13));
                fl.setDossier_item((String)elem.elementAt(14));
                fl.setBu_code((String)elem.elementAt(15));
                
                firmMap.add(fl.getKey(), fl);
            }
        }
        dis.close();
    }
    
    StreamTokenizer createStreamTokenizer(String str) {
        StreamTokenizer st = new StreamTokenizer(new StringReader(str));            
        st.resetSyntax();
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.wordChars(128 + 32, 255);
        st.wordChars('0', '9');
        st.wordChars('+', '+');
        st.wordChars('-', '-');
        st.wordChars('.', '.');
        st.whitespaceChars(0, ' ');
        st.commentChar('/');
        st.quoteChar('"');
        st.quoteChar('\'');
        return(st);
    }

    
    public static void main(String args[])
    {
        if (args.length == 1)
            new PSETest().go(args[0]);        
        else
            System.out.println("java PSETest <path>");
    }
    
    
}
