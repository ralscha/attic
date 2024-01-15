import java.net.URL;
import java.sql.*;
import java.util.*;
import java.text.*;
import COM.objectspace.voyager.*;
import COM.objectspace.voyager.space.*;


public class SWIFTListServer
{
    private final static String url = "jdbc:odbc:SWIFTInput";
    private final static String jdbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";

    private final static String selectHeaderStr =
                        "SELECT TOSN,Address_Sender,"+
                        "Message_Type,Priority,Duplicate"+
                        ",Receive_Date,Receive_Time FROM SWIFTHeader ";

    private final static String selectHeaderWithDateStr =
                        "SELECT TOSN,Address_Sender,"+
                        "Message_Type,Priority,Duplicate"+
                        ",Receive_Date,Receive_Time FROM SWIFTHeader "+
                        "WHERE Receive_Date >= ? AND Receive_Date <= ? ";

    private final static String selectLinesStr =
                        "SELECT TOSN,Lineno,Field_Tag,Msg_Line FROM SWIFTLines WHERE TOSN = ? " +
                        "ORDER BY Lineno ASC";




    private Connection con;

    java.sql.Date fromDate, toDate, date;
    java.sql.Time time;
    int year, month, day;
    StringBuffer dateStr, timeStr;
    String TOSN;

    ResultSet rs;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PreparedStatement lstmt = null;

    public SWIFTListServer()
    {
        init();
    }

    public Vector selectSWIFTMsg(String TOSN)
    {
        String lastTag, cTag;
        StringBuffer line;
        Vector msg = new Vector();

        System.out.println(TOSN);
        if (TOSN.length() == 6)
        {
        	try
            {
                lstmt = con.prepareStatement(selectLinesStr);
                lstmt.setString(1, TOSN);


                rs = lstmt.executeQuery();

                line = new StringBuffer();
                lastTag = "   ";

                while (rs.next())
                {
                    line.setLength(0);
                    cTag = rs.getString(3);
                    if (!cTag.equals(lastTag))
                    {
                        lastTag = cTag;
                        line.append(cTag);
                        if (cTag.length() == 2)
                            line.append("  : ");
                        else
                            line.append(" : ");
                    }
                    else
                    {
                        line.append("      ");
                    }

                    line.append(rs.getString(4));
                    msg.addElement(line.toString());
                }
                rs.close();
                lstmt.close();

            }
            catch (SQLException ex)
            {
                while (ex != null)
                {
                  System.out.println ("SQLState: " + ex.getSQLState ());
                  System.out.println ("Message:  " + ex.getMessage ());
                  System.out.println ("Vendor:   " + ex.getErrorCode ());
                  ex = ex.getNextException ();
                  System.out.println();
                }
            }
        }
        return(msg);
    }


    public Vector selectSWIFTHeaders(String fromDateStr, String toDateStr)
    {
        Vector rows = new Vector();
        Object aRow[];

        try
        {
            day = Integer.parseInt(fromDateStr.substring(0,2));
            month = Integer.parseInt(fromDateStr.substring(3,5))-1;
            year = Integer.parseInt(fromDateStr.substring(6, 10))-1900;
            fromDate = new java.sql.Date(year, month, day);

            day = Integer.parseInt(toDateStr.substring(0,2));
            month = Integer.parseInt(toDateStr.substring(3,5))-1;
            year = Integer.parseInt(toDateStr.substring(6, 10))-1900;
            toDate = new java.sql.Date(year, month, day);

            pstmt = con.prepareStatement(selectHeaderWithDateStr);

            if (fromDate.before(toDate))
            {
                pstmt.setDate(1, fromDate);
                pstmt.setDate(2, toDate);
            }
            else
            {
                pstmt.setDate(1, toDate);
                pstmt.setDate(2, fromDate);
            }
            rs = pstmt.executeQuery();


            dateStr = new StringBuffer(10);
            timeStr = new StringBuffer(5);

            while(rs.next())
            {
                aRow = new Object[7];

                aRow[0] = rs.getString(1);
                aRow[1] = rs.getString(2);
                aRow[2] = rs.getString(3);
                aRow[3] = rs.getString(4);
                aRow[4] = rs.getString(5);

                dateStr.setLength(0);
                timeStr.setLength(0);

                date = rs.getDate(6);
                if (date.getDate() < 10) dateStr.append("0");
                dateStr.append(date.getDate());
                dateStr.append(".");
                if (date.getMonth()+1 < 10) dateStr.append("0");
                dateStr.append(date.getMonth()+1);
                dateStr.append(".");
                dateStr.append(date.getYear()+1900);

                time = rs.getTime(7);
                if (time.getHours() < 10) timeStr.append("0");
                timeStr.append(time.getHours());
                timeStr.append(":");
                if (time.getMinutes() < 10) timeStr.append("0");
                timeStr.append(time.getMinutes());

                aRow[5] = dateStr.toString();
                aRow[6] = timeStr.toString();

                rows.addElement(aRow);
            }
            rs.close();

            if (pstmt != null) pstmt.close();
            if (stmt  != null) stmt.close();

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

        return(rows);
    }

    public void init()
    {
       	try
        {
           	Class.forName (jdbcDriver);
  	        con = DriverManager.getConnection(url, "", "");
  	        checkForWarning(con.getWarnings());
  	    }
        catch (SQLException ex)
        {
            while (ex != null)
            {
              System.err.println ("SQLState: " + ex.getSQLState ());
              System.err.println ("Message:  " + ex.getMessage ());
              System.err.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              System.err.println ("");
            }
        }
        catch (ClassNotFoundException cne)
        {
            System.err.println("Class "+jdbcDriver+" not found "+cne);
        }
    }


    public void checkForWarning(SQLWarning warn)
    {
        if (warn != null)
        {
            System.err.println ("\n *** Warning ***\n");
            while (warn != null)
            {
                System.err.println ("SQLState: " + warn.getSQLState ());
                System.err.println ("Message:  " + warn.getMessage ());
                System.err.println ("Vendor:   " + warn.getErrorCode ());
                System.err.println ("");
                warn = warn.getNextWarning ();
            }
        }
    }


    public static void main( String args[] )
    {

        try
        {
            Voyager.startup(7500);
	        VSWIFTListServer vss = new VSWIFTListServer("localhost:7500/SWIFTListServer");
          	vss.liveForever();
	    	//Voyager.shutdown();
        }
        catch( VoyagerException exception )
        {
            System.err.println( exception );
        }
    }

}
