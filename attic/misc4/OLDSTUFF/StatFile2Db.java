import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;


public class StatFile2Db {

    private String makeInsertString(String line, String branch) throws NumberFormatException, NoSuchElementException  {
        StringBuffer sb = new StringBuffer();        
        sb.append("INSERT INTO StatCSBS(transaction,transactionDate,transactionTime,response,responseTime,putByte,getByte,branch) VALUES(");
               
        sb.append("'").append(line.substring(0,23)).append("'").append(",");

        /* year, month, date, hour, minute, second, nano */
        String date = new java.sql.Date(Integer.parseInt(line.substring(30,32)),
                                 Integer.parseInt(line.substring(27,29))-1,
                                 Integer.parseInt(line.substring(24,26))).toString();
        String time = new java.sql.Time(Integer.parseInt(line.substring(33,35)),
                                 Integer.parseInt(line.substring(36,38)),
                                 Integer.parseInt(line.substring(39,41))).toString();
                                 
        sb.append("'").append(date).append("'").append(",");
        sb.append("'").append(time).append("'").append(",");
        sb.append("'").append(line.substring(45,87)).append("'").append(",");                 
                        
        StringTokenizer st = new StringTokenizer(line.substring(88));
        
        sb.append(Double.valueOf(st.nextToken()).doubleValue()).append(",");
        st.nextToken(); /* String: secs */
        st.nextToken(); /* String: Put  */                    
        sb.append(Integer.parseInt(st.nextToken())).append(",");
        st.nextToken(); /* String: Get */
        sb.append(Integer.parseInt(st.nextToken())).append(",");                                                
        sb.append("'").append(branch).append("'");                 
        sb.append(")");        
        return sb.toString();
    }


    public StatFile2Db(String args[]) {
        Statement stmt;
        String branch = args[1];
        
        BufferedReader dis;        
        String line = null;
        StringTokenizer st;
                       
        String url = "jdbc:odbc:StatCSBS";

        try {
        	Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
       	    Connection con = DriverManager.getConnection(url, "", "");
            checkForWarning(con.getWarnings ());
            stmt = con.createStatement();                
            dis = new BufferedReader(new FileReader(args[0]));

            while ((line = dis.readLine()) != null) {
                if (line.length() > 60) {                    
                    try {
                        stmt.executeUpdate(makeInsertString(line, branch));
                    } catch (Exception nfe) {System.out.println(line);}                                                                
                }
           }
           dis.close();
           con.close();

        } catch (SQLException ex) {
            System.err.println(line);            
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


    public boolean checkForWarning(SQLWarning warn)
    {
        boolean rc = false;

        if (warn != null) {
            System.out.println ("\n *** Warning ***\n");
            rc = true;
            while (warn != null) {
                System.out.println ("SQLState: " + warn.getSQLState ());
                System.out.println ("Message:  " + warn.getMessage ());
                System.out.println ("Vendor:   " + warn.getErrorCode ());
                System.out.println ("");
                warn = warn.getNextWarning ();
            }
        }
        return rc;
    }


    public static void main(String args[]) {
        if (args.length == 2)
            new StatFile2Db(args);
        else
            System.out.println("java StatFile2Db <statFile> <branch>");
    }

}
