import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class File2Db {

    private final static String drv = "sun.jdbc.odbc.JdbcOdbcDriver";    
    private final static String url = "jdbc:odbc:GtfBookings";
    private com.twolink.dbgen.support.Driver driver;
    private com.twolink.dbgen.support.DbAccess dba;

    public File2Db(String args[]) {
        
        Vector contFiles = new Vector();
        Vector firmFiles = new Vector();
        try {
            dba = new com.twolink.dbgen.support.DbAccess(drv, url, "", "");
		    driver = new com.twolink.dbgen.support.Driver(dba);
		    //dba.setDebug(true); // Set to TRUE for verbose SQL inspection		  		  

            File f;
            f = new File(args[0]);            
            String lst[] = f.list();

            for (int i = 0; i < lst.length; i++) {
                if (lst[i].endsWith(".del"))
                    if (lst[i].startsWith("cont_"))
                        contFiles.addElement(lst[i]);
                    else if (lst[i].startsWith("firm_"))
                        firmFiles.addElement(lst[i]);
            }

            for (int i = 0; i < contFiles.size(); i++) {
                System.out.println("reading ... "+args[0]+"\\"+(String)contFiles.elementAt(i));                
                readContFile(args[0]+"\\"+(String)contFiles.elementAt(i));
            }
            
            for (int i = 0; i < firmFiles.size(); i++) {
                System.out.println("reading ... "+args[0]+"\\"+(String)firmFiles.elementAt(i));                
                readFirmFile(args[0]+"\\"+(String)firmFiles.elementAt(i));
            }
                                    
        } catch (SQLException ex) {
            while (ex != null) {
              System.out.println ("SQLState: " + ex.getSQLState ());
              System.out.println ("Message:  " + ex.getMessage ());
              System.out.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              System.out.println ("");
            }
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
             System.err.println(e);
        } catch (ClassNotFoundException cne) {
            System.err.println("Class sun.jdbc.odbc.JdbcOdbcDriver not found "+cne);
        }
    }

    void readContFile(String fileName) throws IOException, NumberFormatException, SQLException {        
        BufferedReader dis = new BufferedReader(new FileReader(fileName));
        StreamTokenizer st;
        String line = null;                        
        String dateStr = null; 
        int type;
        Vector elem = new Vector();
        
        ContingentLiability cl = new ContingentLiability();
                
        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {
                elem.removeAllElements();
                st = createStreamTokenizer(line);
                
                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }
                                
                cl.setSequence_number(Integer.parseInt((String)elem.elementAt(1)));
                cl.setType((String)elem.elementAt(2));
                cl.setGtf_number(Integer.parseInt(((String)elem.elementAt(3)).trim()));
                cl.setCurrency((String)elem.elementAt(4));
                cl.setAmount(Double.valueOf((String)elem.elementAt(5)).doubleValue());
                cl.setAcct_mgmt_unit((String)elem.elementAt(6));
                cl.setAccount_number((String)elem.elementAt(7));
                
                dateStr = (String)elem.elementAt(8);
                cl.setExpiry_date(new java.sql.Date(Integer.parseInt(dateStr.substring(0,4)) - 1900,
                                                   Integer.parseInt(dateStr.substring(4,6))-1,
                                                   Integer.parseInt(dateStr.substring(6,8))));
                                                   
                cl.setExchange_rate(Double.valueOf((String)elem.elementAt(9)).doubleValue());
                cl.setGtf_proc_center((String)elem.elementAt(10));
                cl.setCustomer_ref((String)elem.elementAt(11));
                cl.setGtf_type((String)elem.elementAt(12));
                cl.setDossier_item((String)elem.elementAt(13));
                cl.setBu_code((String)elem.elementAt(14));
                
                driver.insert(cl);      
            }
        }
        dis.close();
    }

    void readFirmFile(String fileName) throws IOException, NumberFormatException, SQLException {        
        BufferedReader dis = new BufferedReader(new FileReader(fileName));
        String line = null;                        
        String dateStr = null; 
        StreamTokenizer st;        
        int type;
        Vector elem = new Vector();        
        FirmLiability fl = new FirmLiability();
                        
        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {
                
                elem.removeAllElements();
                st = createStreamTokenizer(line);
                
                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }
                                
                fl.setSequence_number(Integer.parseInt((String)elem.elementAt(1))); 
                fl.setGtf_number(Integer.parseInt(((String)elem.elementAt(2)).trim()));
                fl.setCurrency((String)elem.elementAt(3));
                fl.setAmount(Double.valueOf((String)elem.elementAt(4)).doubleValue());
                fl.setDeb_acct_mgmt_unit((String)elem.elementAt(5));
                fl.setDeb_acct_number((String)elem.elementAt(6));
                fl.setCre_acct_mgmt_unit((String)elem.elementAt(7));
                fl.setCre_acct_number((String)elem.elementAt(8));
                
                dateStr = (String)elem.elementAt(9);
                fl.setValue_date(new java.sql.Date(Integer.parseInt(dateStr.substring(0,4)) - 1900,
                                                   Integer.parseInt(dateStr.substring(4,6))-1,
                                                   Integer.parseInt(dateStr.substring(6,8))));
                fl.setExchange_rate(Double.valueOf((String)elem.elementAt(10)).doubleValue());
                fl.setGtf_proc_center((String)elem.elementAt(11));
                fl.setCustomer_ref((String)elem.elementAt(12));
                fl.setGtf_type((String)elem.elementAt(13));
                fl.setDossier_item((String)elem.elementAt(14));
                fl.setBu_code((String)elem.elementAt(15));
                
                driver.insert(fl);      
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
    
    

    public static void main(String args[]) {
        if (args.length == 1)
            new File2Db(args);
        else
            System.out.println("java File2Db <path>");
    }

}
