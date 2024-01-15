import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class StateServlet extends HttpServlet
{

    private DbAccess dba;
    private StateDriver stateDriver;

    private Hashtable descr;


    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

       	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");

    	out.println("<HTML>");
    	out.println("<HEAD>");
    	out.println("	<TITLE>SWIFT/Telex Status</TITLE>");
    	out.println("</HEAD>");
    	out.println("<BODY>");
    	out.println("<P>");
    	out.println("<H2>SWIFT/Telex Status</H1>");
    	out.println("<HR>");

        out.println("<FORM ACTION=\"/State\" METHOD=\"POST\">");
        out.println("<P>Gtf Number<INPUT TYPE=\"TEXT\" NAME=\"gtfno\" VALUE=\"\" SIZE=\"14\">");
        out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show\">");
        out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show all\"></P>");
        out.println("</FORM>");
        out.println("<HR>");

        out.println("</BODY>");
        out.println("</HTML>");
        
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {

        int gtf_Number = 0;
        String submit = null;
        String value, name;
        State stateObj;
   	    
        
        try
        {
            Enumeration values = req.getParameterNames();
            while(values.hasMoreElements())
            {
                name = (String)values.nextElement();
                value = req.getParameterValues(name)[0];
                if (name.equals("gtfno"))
                    gtf_Number = Integer.parseInt(value);
                else if (name.equalsIgnoreCase("submit"))
                    submit = value;
            }
           
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
               
           	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
    
        	out.println("<HTML>");
        	out.println("<HEAD>");
        	out.println("	<TITLE>SWIFT/Telex Status</TITLE>");
    	    out.println("</HEAD>");
        	out.println("<BODY>");
            
        	out.println("<P>");
        	out.println("<H2>SWIFT/Telex Status</H1>");
        	out.println("<HR>");

            out.println("<FORM ACTION=\"/State\" METHOD=\"POST\">");
            out.println("<P>Gtf Number<INPUT TYPE=\"TEXT\" NAME=\"gtfno\" VALUE=\""+gtf_Number+"\" SIZE=\"14\">");
            out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show\">");
            out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show all\"></P>");
            out.println("</FORM>");
            out.println("<HR>");

            out.println("<P>");

            out.println("<table width=\"100%\" border=\"1\">");
         	out.println("<TR>");
    		out.println("<TD WIDTH=\"8%\"><B>Gtf Nr</B></TD>");
    		out.println("<TD WIDTH=\"8%\"><B>Typ</B></TD>");
    		out.println("<TD WIDTH=\"8%\"><B>Status</B></TD>");
        	out.println("<TD WIDTH=\"15%\"><B>Datum</B></TD>");
    		out.println("<TD WIDTH=\"61%\"><B>Beschreibung</B></TD>");
        	out.println("</TR>");


            ResultSet rs;
            if (submit.equalsIgnoreCase("show all") || (gtf_Number == 0))
            {
                rs = stateDriver.query();
            }
            else
            {
                rs = stateDriver.query(gtf_Number);
            }
            
            while ((stateObj = stateDriver.next(rs)) != null)
            {
                StateDescription sd = (StateDescription)descr.get(stateObj.getState_Num());

                switch(sd.getColour())
                {
                    case 1 : out.println("<TR bgcolor=\"Lime\">"); break;
                    case 2 : out.println("<TR bgcolor=\"Yellow\">"); break;
                    case 3 : out.println("<TR bgcolor=\"Red\">"); break;
                    default:  out.println("<TR>");
                }

		        out.println("<TD>"+stateObj.getOrder_Number()+"</TD>");
		        if (stateObj.getMessage_Type().equalsIgnoreCase("S"))
            		out.println("<TD>SWIFT</TD>");
            	else
            	    out.println("<TD>TELEX</TD>");

		        out.println("<TD>"+stateObj.getState_Num()+" "+stateObj.getState_Tran()+"</TD>");
		        out.println("<TD>"+stateObj.getState_Date()+" &nbsp;"+stateObj.getState_Time()+"</TD>");
        		out.println("<TD>"+sd.getDescription()+"</TD>");
            	out.println("</TR>");
            }
            out.println("</TABLE>");
            out.println("</BODY>");
            out.println("</HTML>");

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
        System.out.println("INIT: StateServlet");
        descr = new Hashtable();
        
        try
        {
            String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
      	    String url = "jdbc:odbc:SWIFTInput";
    	    dba = new DbAccess(drv, url, "", "");    	   
            StateDescriptionDriver descriptionDriver = new StateDescriptionDriver(dba); 
            stateDriver = new StateDriver(dba);

            ResultSet rs = descriptionDriver.query();            
            StateDescription sd;
   	        while ((sd = descriptionDriver.next(rs)) != null)
                descr.put(sd.getCode(), sd);
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
        catch (ClassNotFoundException e)
        {
            log("ClassNotFoundException "+e);
        }
    }

    public void destroy()
    {
        System.out.println("DESTROY: StateServlet");
        
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

