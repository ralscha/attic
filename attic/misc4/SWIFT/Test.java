import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;


public class Test
{

    public Test()
    {
        PreparedStatement insert,delete;
        java.sql.Date     date;
        java.sql.Time     time;
        int year,month,day;

        BufferedReader dis;
        String line;
        String url = "jdbc:odbc:SWIFTInput";

        String insertStr = "INSERT INTO State(Proc_Center,Order_Number,Gtf_Number,State_Num,"+
                           "State_Tran,Message_Type,State_Date,State_Time) "+
                           "VALUES(?,?,?,?,?,?,?,?)";

        String selectAllStr =
                        "SELECT Proc_Center,Gtf_Number,State_Num,State_Tran,"+
                        "Message_Type,State_Date,State_Time "+
                        "from State ORDER BY Gtf_Number";

        String deleteStr = "DELETE FROM State WHERE State_Date < ?";

        try
        {

        	Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
       	    Connection con = DriverManager.getConnection(url, "", "");
            checkForWarning(con.getWarnings ());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectAllStr);
            while (rs.next())
            {
                System.out.println(rs.getInt(2));
            }

        }
        catch (SQLException ex)
        {
            while (ex != null)
            {
              System.out.println ("SQLState: " + ex.getSQLState ());
              System.out.println ("Message:  " + ex.getMessage ());
              System.out.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              System.out.println ("");
            }
            System.exit(1);
        }
        catch (NumberFormatException nfe)
        {
             System.err.println("date error: "+nfe);
        }
        catch (ClassNotFoundException cne)
        {
            System.err.println("Class sun.jdbc.odbc.JdbcOdbcDriver not found "+cne);
        }

    }

    public boolean checkForWarning(SQLWarning warn)
    {
        boolean rc = false;

        if (warn != null)
        {
            System.out.println ("\n *** Warning ***\n");
            rc = true;
            while (warn != null)
            {
                System.out.println ("SQLState: " + warn.getSQLState ());
                System.out.println ("Message:  " + warn.getMessage ());
                System.out.println ("Vendor:   " + warn.getErrorCode ());
                System.out.println ("");
                warn = warn.getNextWarning ();
            }
        }
        return rc;
    }


    public static void main(String args[])
    {
        new Test();
    }

}
