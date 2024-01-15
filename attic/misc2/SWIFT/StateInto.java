import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;


public class StateInto
{

    public StateInto()
    {
        PreparedStatement insert,delete, update;
        java.sql.Date     date;
        java.sql.Time     time;
        int year,month,day;

        BufferedReader dis;
        String line;
        String url = "jdbc:odbc:SWIFTInput";

        String insertStr = "INSERT INTO State(Proc_Center,Order_Number,Gtf_Number,State_Num,"+
                           "State_Tran,Message_Type,State_Date,State_Time) "+
                           "VALUES(?,?,?,?,?,?,?,?)";

        String updateStr = "Update State SET State_Num=?, State_Tran=?, State_Date=?, State_Time=? "+
                           "WHERE Order_Number = ?";

        String deleteStr = "DELETE FROM State WHERE State_Date < ?";

        try
        {

        	Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
       	    Connection con = DriverManager.getConnection(url, "", "");
            checkForWarning(con.getWarnings ());

            insert = con.prepareStatement(insertStr);
            delete = con.prepareStatement(deleteStr);
            update = con.prepareStatement(updateStr);

            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE, -10);

            date = new java.sql.Date(today.get(Calendar.YEAR)-1900, today.get(Calendar.MONTH),
                                     today.get(Calendar.DATE));
            delete.setDate(1, date);

            dis = new BufferedReader(new FileReader("tlc22.dat"));
            delete.executeUpdate();

            while ((line = dis.readLine()) != null)
            {
               if (line.length() > 2)
               {
                   insert.setString(1, line.substring(0,4)); /* Proc_Center */
                   insert.setString(2, line.substring(4,39)); /* Order_Number */
                   update.setString(5, line.substring(4,39));

                   StringTokenizer st = new StringTokenizer(line.substring(4,39),".");
                   if (st.hasMoreTokens())
                   {
                       String nt = st.nextToken();
                       insert.setInt(3, Integer.parseInt(nt));
                   }
                   else
                   {
                       insert.setInt(3, 0);
                   }
                   insert.setString(4, line.substring(39,41)); /* State_Num */
                   update.setString(1, line.substring(39,41));

                   insert.setString(5, line.substring(41,44)); /* State_Tran */
                   update.setString(2, line.substring(41,44));

                   insert.setString(6, line.substring(44,45)); /* Message_Type */

                   year  = Integer.parseInt(line.substring(45,49))-1900;
                   month = Integer.parseInt(line.substring(49,51))-1;
                   day   = Integer.parseInt(line.substring(51,53));
                   date = new java.sql.Date(year, month, day);
                   insert.setDate(7, date); /* State_Date */
                   update.setDate(3, date);

                   time = new java.sql.Time(Integer.parseInt(line.substring(53,55)),
                                           Integer.parseInt(line.substring(55,57)),0);
                   insert.setTime(8,time); /* State_Time */
                   update.setTime(4,time);

                   try
                   {
                       insert.executeUpdate();
                   }
                   catch (SQLException ex)
                   {
                      if (ex.getSQLState().equals("S1000"))
                      {
                         update.executeUpdate();
                      }
                      else
                      {
                        throw(ex);
                      }
                   }
               }
           }
           dis.close();

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
        catch (FileNotFoundException e)
        {
            System.err.println("StateInto: " + e);
        }
        catch (IOException e)
        {
             System.err.println("StateInto: " + e);
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
        new StateInto();
    }

}
