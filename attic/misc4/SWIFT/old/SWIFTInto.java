import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import COM.objectspace.jgl.*;


public class SWIFTInto
{

    public SWIFTInto()
    {
        PreparedStatement insert1;
        PreparedStatement insert2;
        PreparedStatement ps;
        Statement st;
        ResultSet rs;
        java.sql.Date     date;
        java.sql.Time     time;
        int year,month,day;

        HashSet badTOSN = new HashSet();

        BufferedReader dis;
        String line;
        String url = "jdbc:odbc:SWIFTInput";

        String insert1Str = "INSERT INTO SWIFTHeader(TOSN,Send_Date,Address_Sender,"+
                            "Session_Number,Sequence_Number,Address_Receiver,Proc_Center,Message_Type,Duplicate"+
                            ",Priority,Receive_Date,Receive_Time) "+
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        String insert2Str = "INSERT INTO SWIFTLines(TOSN,Lineno,Field_Tag,Msg_Line) "+
                            "VALUES(?,?,?,?)";

        String checkStr =  "SELECT TOSN from SWIFTHeader order by TOSN asc";
        String selectForDeleteStr = "SELECT TOSN from SWIFTHeader where Receive_Date < ?";

        String delete1Str = "DELETE FROM SWIFTHeader WHERE Receive_Date < ?";
        String delete2Str = "DELETE FROM SWIFTLines WHERE TOSN = ?";



        try
        {

        	Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
       	    Connection con = DriverManager.getConnection(url, "", "");
            checkForWarning(con.getWarnings ());


            st = con.createStatement();
            rs = st.executeQuery(checkStr);
            int mtosn = 0;
            int tosnno = 0;

            if (rs.next())
                mtosn = Integer.parseInt(rs.getString(1));

            while(rs.next())
            {
                tosnno = Integer.parseInt(rs.getString(1));
                while(tosnno > mtosn + 1)
                {
                    mtosn++;
                    System.out.println("Es fehlt folgender SWIFT: "+mtosn);
                }
                mtosn++;
            }
            st.close();


            ps = con.prepareStatement(selectForDeleteStr);

            Vector tosnArray = new Vector();
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE, -30);

            date = new java.sql.Date(today.get(Calendar.YEAR)-1900, today.get(Calendar.MONTH),
                                     today.get(Calendar.DATE));

            ps.setDate(1, date);
            rs = ps.executeQuery();

            while(rs.next())
            {
                tosnArray.addElement(rs.getString(1));
            }

            ps.close();

            ps = con.prepareStatement(delete1Str);
            ps.setDate(1, date);
            ps.executeUpdate();
            ps.close();


            ps = con.prepareStatement(delete2Str);
            Enumeration e = tosnArray.elements();
            while(e.hasMoreElements())
            {
                ps.setString(1, (String)e.nextElement());
                ps.executeUpdate();
            }
            ps.close();


            insert1 = con.prepareStatement(insert1Str);
            insert2 = con.prepareStatement(insert2Str);

            dis = new BufferedReader(new FileReader("tlc21.dat"));

            while ((line = dis.readLine()) != null)
            {
               if (line.length() > 2)
               {
                   insert1.setString(1, line.substring(0,6)); /* TOSN */

                   year  = Integer.parseInt(line.substring(6,10))-1900;
                   month = Integer.parseInt(line.substring(10,12))-1;
                   day   = Integer.parseInt(line.substring(12,14));
                   date = new java.sql.Date(year, month, day);
                   insert1.setDate(2, date); /* Sender_Date */

                   insert1.setString(3, line.substring(14,26)); /* Address_Sender */
                   insert1.setString(4, line.substring(26,30)); /* Session Nummer */
                   insert1.setString(5, line.substring(30,36)); /* Sequence Nummer */
                   insert1.setString(6, line.substring(45,56)); /* Address_Receiver */
                   insert1.setString(7, line.substring(36,40)); /* Proc_Center */
                   insert1.setString(8, line.substring(40,43)); /* Message_Type */
                   insert1.setString(9, line.substring(44,45)); /* Duplicate */
                   insert1.setString(10, line.substring(43,44)); /* Priority */


                   date=null;
                   year  = Integer.parseInt(line.substring(56,60))-1900;
                   month = Integer.parseInt(line.substring(60,62))-1;
                   day   = Integer.parseInt(line.substring(62,64));
                   date = new java.sql.Date(year, month, day);
                   insert1.setDate(11, date); /* Receive_Date */

                   time=null;
                   time = new java.sql.Time(Integer.parseInt(line.substring(64,66)),
                                           Integer.parseInt(line.substring(66,68)),0);

                   insert1.setTime(12,time); /* Receive_Time */

                   try
                   {
                       insert1.executeUpdate();
                   }
                   catch (SQLException ex)
                   {
                      if (!ex.getSQLState().equals("S1000"))
                        throw(ex);
                      else
                        badTOSN.add(line.substring(0,6));

                   }
               }
           }
           dis.close();
           con.commit();

           dis = new BufferedReader(new FileReader("tlc24.dat"));
           String tosn;
           int to;

           while ((line = dis.readLine()) != null)
           {
               if (line.length() > 2)
               {

                   tosn = line.substring(0,6);

                   if (badTOSN.get(tosn) == null)
                   {
                       insert2.setString(1, tosn); /* TOSN */

                       insert2.setInt(2, Integer.parseInt(line.substring(6,10)));
                       insert2.setString(3, line.substring(10,13));
                       insert2.setString(4, line.substring(13));

                       try
                       {
                           insert2.executeUpdate();
                       }
                       catch (SQLException ex)
                       {
                          if (!ex.getSQLState().equals("S1000"))
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
            System.err.println("InTest: " + e);
        }
        catch (IOException e)
        {
             System.err.println("InTest: " + e);
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
        new SWIFTInto();
    }

}
