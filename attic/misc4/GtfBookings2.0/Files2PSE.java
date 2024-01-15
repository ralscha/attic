
import java.util.*;
import java.io.*;
import COM.odi.util.*;

public class Files2PSE
{
    static final String DB_NAME = "Liability.odb";
    
    public Files2PSE(String args[]) {
           
        if (args[0].equalsIgnoreCase("new"))
            DbManager.initialize(DB_NAME, true);
        else
            DbManager.initialize(DB_NAME, false);
        
        DbManager.startUpdateTransaction();
        DbManager.removeIndex();
        readFiles(args[1]);        
        DbManager.createIndex();
        DbManager.commitUpdateTransaction();
        DbManager.shutdown();
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

            String file = null;
            Enumeration iterator = contFiles.elements();
            while (iterator.hasMoreElements()) {
                file = (String)iterator.nextElement();
                System.out.println("reading ... "+path+"\\"+file);                
                readContFile(path+"\\"+file);                
            }
            
            file = null;
            iterator = firmFiles.elements();
            while (iterator.hasMoreElements()) {
                file = (String)iterator.nextElement();
                System.out.println("reading ... "+path+"\\"+file);                
                readFirmFile(path+"\\"+file);                
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
                cl.setType(((String)elem.elementAt(2)).trim());
                cl.setGtf_number(Integer.parseInt(((String)elem.elementAt(3)).trim()));
                cl.setCurrency(((String)elem.elementAt(4)).trim());
                cl.setAmount(Double.valueOf((String)elem.elementAt(5)).doubleValue());
                cl.setAcct_mgmt_unit(((String)elem.elementAt(6)).trim());
                cl.setAccount_number(((String)elem.elementAt(7)).trim());
                cl.setExpiry_date(((String)elem.elementAt(8)).trim());
                                                   
                cl.setExchange_rate(Double.valueOf((String)elem.elementAt(9)).doubleValue());
                cl.setGtf_proc_center(((String)elem.elementAt(10)).trim());
                cl.setCustomer_ref(((String)elem.elementAt(11)).trim());
                cl.setGtf_type(((String)elem.elementAt(12)).trim());
                cl.setDossier_item(((String)elem.elementAt(13)).trim());
                cl.setBu_code(((String)elem.elementAt(14)).trim());
                addLiability(cl);
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
                fl.setCurrency(((String)elem.elementAt(3)).trim());
                fl.setAmount(Double.valueOf((String)elem.elementAt(4)).doubleValue());
                fl.setDeb_acct_mgmt_unit(((String)elem.elementAt(5)).trim());
                fl.setDeb_acct_number(((String)elem.elementAt(6)).trim());
                fl.setCre_acct_mgmt_unit(((String)elem.elementAt(7)).trim());
                fl.setCre_acct_number(((String)elem.elementAt(8)).trim());
                
                fl.setValue_date(((String)elem.elementAt(9)).trim());
                fl.setExchange_rate(Double.valueOf((String)elem.elementAt(10)).doubleValue());
                fl.setGtf_proc_center(((String)elem.elementAt(11)).trim());
                fl.setCustomer_ref(((String)elem.elementAt(12)).trim());
                fl.setGtf_type(((String)elem.elementAt(13)).trim());
                fl.setDossier_item(((String)elem.elementAt(14)).trim());
                fl.setBu_code(((String)elem.elementAt(15)).trim());
                
                addLiability(fl);
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

    Liability search(Liability liab) {
        Collection collection = null;
        Liability holder = null;
        
        if (liab instanceof ContingentLiability)
            collection = DbManager.getContCollection();
        else if (liab instanceof FirmLiability)
            collection = DbManager.getFirmCollection();
            
        if (collection == null) return(null);    
        
        Iterator iter = collection.iterator();
        while(iter.hasNext()) {
            holder = (Liability)iter.next();    
            if (holder.equalsTo(liab)) return(holder);
        }
        return(null);
    }


    void addLiability(Liability liab) {
        Liability holder;
        
        if (liab != null) {
            if ((holder = search(liab)) != null) {                
                liab.setDuplicateFlag();
                holder.setDuplicateFlag();
            }
            DbManager.addLiability(liab);
        }
     
    }

    
    
    public static void main(String args[])
    {
        if (args.length == 2)
            new Files2PSE(args);        
        else
            System.out.println("java PSETest <new|old> <path>");
    }
    
    
}
