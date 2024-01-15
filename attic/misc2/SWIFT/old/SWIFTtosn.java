import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.sql.*;

public class SWIFTtosn extends HttpServlet
{

    private DbAccess dba;
    private SWIFTHeader shObj;
    private SWIFTLines  slObj;
    private SWIFTHeaderDriver headerDriver;
    private SWIFTLinesDriver linesDriver;

    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        
        
        try
        {
        
        	res.setContentType("text/html");
            PrintWriter out = res.getWriter();
    
            String TOSN = req.getParameter("TOSN");    

        	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        	out.println("<HTML>");
        	out.println("<HEAD>");
        	out.println("	<TITLE>SWIFT Input</TITLE>");
        	out.println("</HEAD>");

        	out.println("<BODY>");
        	out.println("<H2>SWIFT Input  TOSN: "+TOSN+"</H1>");
        	out.println("<HR>");

            shObj = headerDriver.retrieve(new SWIFTHeader(TOSN));
            
            if (shObj != null)
            {
                out.println("<br>");
                out.println("<FONT SIZE=\"+2\"><B>MT "+shObj.getMessage_Type()+"</B></FONT>");
                out.println("<p>");
                out.println("<TABLE CELLPADDING=2>");
                out.println("<TR>");
                out.println("	<TD>Sender Address</TD>");
                out.println("	<TD><B>"+shObj.getAddress_Sender()+"</B></TD>");
                out.println("	<TD>&nbsp;</TD>");
                out.println("	<TD>Send Date</TD>");

                out.println("	<TD><B>"+shObj.getSend_Date()+"</B></TD>");
                out.println("</TR>");
                out.println("<TR>");
                out.println("	<TD>Receiver</TD>");
                out.println("	<TD><B>"+shObj.getAddress_Receiver()+"</B> ("+shObj.getProc_Center()+")</TD>");
                out.println("	<TD>&nbsp;</TD>");
                out.println("	<TD>Receive Date</TD>");

                out.println("	<TD><B>"+shObj.getReceive_Date()+shObj.getReceive_Date()+" &nbsp;"+shObj.getReceive_Time()+"</B></TD>");
                out.println("</TR>");

                out.println("<TR>");
                out.println("	<TD>Priority</TD>");

                if (shObj.getPriority().equals("U"))
                    out.println("	<TD><B>urgent</B></TD>");
                else
                    out.println("	<TD><B>normal</B></TD>");

                out.println("	<TD>&nbsp;</TD>");
                out.println("	<TD>Possible duplicate</TD>");

                if (shObj.getDuplicate().equals("Y"))
                    out.println("	<TD><B>yes</B></TD>");
                else
                    out.println("	<TD><B>no</B></TD>");

                out.println("</TR>");
                out.println("</TABLE>");
                out.println("<PRE>");


                StringBuffer line = new StringBuffer();
                String fTag;
                
                SWIFTLines[] sl = linesDriver.retrieve("TOSN = '"+TOSN+"'", "Lineno ASC");
                for (int i = 0; i < sl.length; i++)
                {
                    line.setLength(0);
                    fTag = sl[i].getField_Tag();
                    if (fTag.length() != 0)
                    {
                        line.append(fTag);
                        if (fTag.length() == 2)
                            line.append("  : ");
                        else
                            line.append(" : ");
                    }
                    else
                    {
                        line.append("      ");
                    }
                    line.append(sl[i].getMsg_Line());
                    out.println(line.toString());
                }
                out.println("</PRE>");
            }
        }        
        catch (SQLException sqlex)
        {
            while(sqlex != null)
            {
                log("SQLException: " + sqlex.getSQLState() + '\t' +
                    sqlex.getMessage() + '\t' +
                    sqlex.getErrorCode() + '\t');
                sqlex = sqlex.getNextException();
            }
        }
    }


    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        try
        {
            String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
      	    String url = "jdbc:odbc:SWIFTInput";
    	    dba = new DbAccess(drv, url, "", "");    	   
            headerDriver = new SWIFTHeaderDriver(dba); 
            linesDriver  = new SWIFTLinesDriver(dba);
            
            System.out.println("Lines "+dba);
        }
        catch (SQLException e)
        {
            while(e != null)
            {
                log("SQLException: " + e.getSQLState() + '\t' +
                    e.getMessage() + '\t' +
                    e.getErrorCode() + '\t');
                e = e.getNextException();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void destroy()
    {
        try
        {
            dba.getConnection().close();
        }
        catch (SQLException e)
        {
            while(e != null)
            {
                log("SQLException: " + e.getSQLState() + '\t' +
                    e.getMessage() + '\t' +
                    e.getErrorCode() + '\t');
                e = e.getNextException();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}