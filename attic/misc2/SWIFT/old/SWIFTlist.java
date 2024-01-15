import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.sql.*;

public class SWIFTlist extends HttpServlet
{

    String url = "jdbc:odbc:SWIFTInput";
    private final static String jdbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";

    private final static String selectHeaderStr =
                        "SELECT TOSN,Send_Date,Address_Sender,"+
                        "Session_Number,Sequence_Number,Address_Receiver,Proc_Center"+
                        ",Message_Type,Duplicate,Priority,Receive_Date,Receive_Time "+
                        "from SWIFTHeader";
    private final static String selectHeaderWithDateStr =
                        "SELECT TOSN,Send_Date,Address_Sender,"+
                        "Session_Number,Sequence_Number,Address_Receiver,Proc_Center"+
                        ",Message_Type,Duplicate,Priority,Receive_Date,Receive_Time "+
                        "from SWIFTHeader "+
                        "WHERE Receive_Date >= ? AND Receive_Date <= ? ";

    private final static String orderByTOSN = "ORDER BY TOSN ";
    private final static String orderByMT   = "ORDER BY Message_Type ";
    private final static String orderByAbsender = "ORDER BY Address_Sender ";
    private final static String orderAddTOSN = ", TOSN ";

    private Connection con;

   public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        java.sql.Date vonDate, bisDate, date;
        java.sql.Time time;
        int year, month, day;
        StringBuffer sqlStmt, dateStr;
        String timeStr;
        String TOSN;
        String rDup, rPri;

        boolean onlyUrgent = false;
        boolean noDup      = false;

    	String vonDatum = req.getParameter("vonDatum");
    	String bisDatum = req.getParameter("bisDatum");
    	String sort     = req.getParameter("sortiert");
    	String order    = req.getParameter("order");
    	String pri      = req.getParameter("Prioritaet");
    	String dup      = req.getParameter("Duplikat");

    	if (dup != null)
    	    if (dup.equalsIgnoreCase("on"))
    	        noDup = true;
    	else
    	    dup = "";

    	if (pri != null)
    	    if (pri.equalsIgnoreCase("on"))
    	        onlyUrgent = true;
    	else
    	    pri = "";

        ResultSet rs;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        res.setContentType("text/html");

    	ServletOutputStream out = res.getOutputStream();

    	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
    	out.println("<HTML>");
    	out.println("<HEAD>");
    	out.println("	<TITLE>SWIFT Input</TITLE>");
    	out.println("</HEAD>");
    	out.println("<BODY>");
    	out.println("<H2>SWIFT Input</H1>");
    	out.println("<HR>");

    	out.println("<FORM ACTION=\"/servlet/SWIFTlist\" METHOD=\"GET\">");
    	out.println("Empfangsdatum von &nbsp; <INPUT TYPE=\"TEXT\" NAME=\"vonDatum\" VALUE=\""+vonDatum+"\" SIZE=10 MAXLENGTH=10>");
    	out.println("bis &nbsp; <INPUT TYPE=\"TEXT\" NAME=\"bisDatum\"  VALUE=\""+bisDatum+"\" SIZE=10 MAXLENGTH=10>");
    	out.println("&nbsp;&nbsp;sortiert nach");
    	out.println("<SELECT NAME=\"sortiert\">");

    	if (sort.equalsIgnoreCase("TOSN"))
    	{
        	out.println("	<OPTION>TOSN");
        	out.println("	<OPTION>Meldungstyp");
    	    out.println("	<OPTION>Absender");
    	}
    	else if (sort.equalsIgnoreCase("Meldungstyp"))
    	{
        	out.println("	<OPTION>Meldungstyp");
        	out.println("	<OPTION>TOSN");
    	    out.println("	<OPTION>Absender");
    	}
    	else
    	{
     	    out.println("	<OPTION>Absender");
     	    out.println("	<OPTION>TOSN");
        	out.println("	<OPTION>Meldungstyp");
    	}
    	out.println("</SELECT>");

    	out.println("<SELECT NAME=\"order\">");

    	if (order.equals("aufsteigend"))
    	{
        	out.println("	<OPTION>aufsteigend");
        	out.println("	<OPTION>absteigend");
        }
        else
        {
        	out.println("	<OPTION>absteigend");
        	out.println("	<OPTION>aufsteigend");

        }
    	out.println("</SELECT>");
    	out.println("<br><br>");
    	if (onlyUrgent)
        	out.println("<INPUT TYPE=\"CHECKBOX\" CHECKED NAME=\"Prioritaet\">nur URGENT ");
        else
            out.println("<INPUT TYPE=\"CHECKBOX\" NAME=\"Prioritaet\">nur URGENT ");

    	out.println("&nbsp;&nbsp;");

    	if (noDup)
        	out.println("<INPUT TYPE=\"CHECKBOX\" CHECKED NAME=\"Duplikat\">ohne Duplikate ");
        else
        	out.println("<INPUT TYPE=\"CHECKBOX\" NAME=\"Duplikat\">ohne Duplikate ");

    	out.println("<br><br>");
    	out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Anzeigen\">");
    	out.println("</FORM>");

    	out.println("<HR>");

       	try
        {

            if ((bisDatum.length() == 10) && (vonDatum.length() == 10))
            {
                day = Integer.parseInt(vonDatum.substring(0,2));
                month = Integer.parseInt(vonDatum.substring(3,5))-1;
                year = Integer.parseInt(vonDatum.substring(6, 10))-1900;
                vonDate = new java.sql.Date(year, month, day);

                day = Integer.parseInt(bisDatum.substring(0,2));
                month = Integer.parseInt(bisDatum.substring(3,5))-1;
                year = Integer.parseInt(bisDatum.substring(6, 10))-1900;
                bisDate = new java.sql.Date(year, month, day);

                sqlStmt = new StringBuffer(selectHeaderWithDateStr);

                if      (sort.equals("TOSN"))
                    sqlStmt.append(orderByTOSN);
                else if (sort.equals("Meldungstyp"))
                    sqlStmt.append(orderByMT);
                else if (sort.equals("Absender"))
                    sqlStmt.append(orderByAbsender);

                if      (order.equals("aufsteigend"))
                    sqlStmt.append("ASC");
                else if (order.equals("absteigend"))
                    sqlStmt.append("DESC");

                if ((sort.equals("Meldungstyp")) || (sort.equals("Absender")))
                {
                    sqlStmt.append(orderAddTOSN);

                    if      (order.equalsIgnoreCase("aufsteigend"))
                        sqlStmt.append("ASC");
                    else if (order.equalsIgnoreCase("absteigend"))
                        sqlStmt.append("DESC");
                }

                pstmt = con.prepareStatement(sqlStmt.toString());
                pstmt.setDate(1, vonDate);
                pstmt.setDate(2, bisDate);
                rs = pstmt.executeQuery();

            }
            else
            {
                sqlStmt = new StringBuffer(selectHeaderStr);

                if      (sort.equalsIgnoreCase("TOSN"))
                    sqlStmt.append(orderByTOSN);
                else if (sort.equalsIgnoreCase("Meldungstyp"))
                    sqlStmt.append(orderByMT);
                else if (sort.equalsIgnoreCase("Absender"))
                    sqlStmt.append(orderByAbsender);

                if      (order.equalsIgnoreCase("aufsteigend"))
                    sqlStmt.append("ASC");
                else if (order.equalsIgnoreCase("absteigend"))
                    sqlStmt.append("DESC");

                if ((sort.equals("Meldungstyp")) || (sort.equals("Absender")))
                {
                    sqlStmt.append(orderAddTOSN);

                    if      (order.equalsIgnoreCase("aufsteigend"))
                        sqlStmt.append("ASC");
                    else if (order.equalsIgnoreCase("absteigend"))
                        sqlStmt.append("DESC");
                }

                stmt = con.createStatement();
                rs = stmt.executeQuery(sqlStmt.toString());
            }


        	out.println("<TABLE CELLPADDING=3 WIDTH=0 BORDER=1 ALIGN=\"LEFT\">");
        	out.println("<TR>");
        	out.println("<TH ALIGN=\"LEFT\">TOSN</TH>");
        	out.println("<TH ALIGN=\"LEFT\">MT</TH>");
        	out.println("<TH ALIGN=\"LEFT\">Absender</TH>");
        	out.println("<TH ALIGN=\"LEFT\">Priorit&auml;t</TH>");
    	    out.println("<TH ALIGN=\"LEFT\">Duplikat</TH>");
        	out.println("<TH ALIGN=\"LEFT\">Empf&auml;nger</TH>");
        	out.println("<TH ALIGN=\"LEFT\">Empfangsdatum</TH>");
        	out.println("</TR>");

            while(rs.next())
            {
                rDup = rs.getString(9);
                rPri = rs.getString(10);

                if (((noDup && rDup.equalsIgnoreCase("N")) || (!noDup)) &&
                    ((onlyUrgent && rPri.equalsIgnoreCase("U")) || (!onlyUrgent)))
                {

                    TOSN = rs.getString(1);
                    out.println("<TR>");
                    out.println("	<TD ALIGN=\"LEFT\"><A HREF=\"/servlet/SWIFTtosn?TOSN="+TOSN+"\">"+TOSN+"</A></TD>");
                    out.println("   <TD ALIGN=\"LEFT\">"+rs.getString(8)+"</TD>");
                    out.println("	<TD ALIGN=\"LEFT\">"+rs.getString(3)+"</TD>");
                    if (rPri.equalsIgnoreCase("U"))
                        out.println("	<TD ALIGN=\"CENTER\">URGENT</TD>");
                    else
                        out.println("	<TD ALIGN=\"CENTER\">normal</TD>");

                    if (rDup.equalsIgnoreCase("Y"))
                        out.println("	<TD ALIGN=\"CENTER\">ja</TD>");
                    else
                        out.println("	<TD ALIGN=\"CENTER\">nein</TD>");

                    out.println("	<TD ALIGN=\"LEFT\">"+rs.getString(6)+" ("+rs.getString(7)+")</TD>");


                    dateStr = new StringBuffer(22);
                    date = rs.getDate(11);
                    if (date.getDate() < 10) dateStr.append("0");
                    dateStr.append(date.getDate());
                    dateStr.append(".");
                    if (date.getMonth()+1 < 10) dateStr.append("0");
                    dateStr.append(date.getMonth()+1);
                    dateStr.append(".");
                    dateStr.append(date.getYear()+1900);
                    dateStr.append(" &nbsp;");
                    time = rs.getTime(12);
                    if (time.getHours() < 10) dateStr.append("0");
                    dateStr.append(time.getHours());
                    dateStr.append(":");
                    if (time.getMinutes() < 10) dateStr.append("0");
                    dateStr.append(time.getMinutes());

                    out.println("	<TD ALIGN=\"LEFT\">"+dateStr.toString()+"</TD>");
                    out.println("</TR>");
                }
            }
            out.println("</TABLE>");

            rs.close();

            if (pstmt != null) pstmt.close();
            if (stmt  != null) stmt.close();
        }
        catch (NumberFormatException nfe)
        {
            out.println("<b>Datum falsch<b>");
        }
        catch (SQLException ex)
        {
            while (ex != null)
            {
              out.println ("SQLState: " + ex.getSQLState ());
              out.println ("Message:  " + ex.getMessage ());
              out.println ("Vendor:   " + ex.getErrorCode ());
              ex = ex.getNextException ();
              out.println ("");
            }
        }
        catch (IOException e)
        {
            out.println("IOException: " + e);
        }


        out.println("</BODY>");
        out.println("</HTML>");

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


    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
       	try
        {
           	Class.forName (jdbcDriver);
  	        con = DriverManager.getConnection(url, "loebner", "loebner");
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

    public void destroy()
    {
        try
        {
            con.close();
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

        super.destroy();
    }

}