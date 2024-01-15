import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class SWIFTInputServlet extends HttpServlet
{

    private Vector dates = new Vector();
    private Vector branches = new Vector();
    private DbAccess dba;
    private SWIFTHeader shObj;
    private SWIFTHeaderDriver headerDriver;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        Enumeration e;
        String hStr;
              
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("	<TITLE>SWIFT Input</TITLE>");
        out.println("</HEAD>");

        out.println("<BODY>");
        out.println("<H2>SWIFT Input</H1>");

        out.println("<HR>");
        out.println("<FORM ACTION=\"/SWIFTInput\" METHOD=\"POST\">");
        out.println("<select name=\"from\" size=\"1\">");

        e = dates.elements();
        while (e.hasMoreElements())
        {
            hStr = (String)e.nextElement();
            out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");
        }
        out.println("</select>");
        out.println("<select name=\"to\" size=\"1\">");

        e = dates.elements();
        while (e.hasMoreElements())
        {
            hStr = (String)e.nextElement();
            out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");
        }
        out.println("</select>");
        out.println("<select name=\"branch\" size=\"1\">");


        e = branches.elements();
        while (e.hasMoreElements())
        {
            hStr = (String)e.nextElement();
            out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");
        }
        out.println("</select>");
        out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Show\">");
        out.println("</FORM>");
        out.println("<HR>");
                
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        Enumeration e;
        String hStr;
        java.sql.Date fromDate = null;
        java.sql.Date toDate   = null;
        String branch = null;
        String name, value;

        try
        {
            Enumeration values = req.getParameterNames();
            while(values.hasMoreElements())
            {
                name = (String)values.nextElement();
                value = req.getParameterValues(name)[0];
                if (name.equals("to"))
                    toDate = java.sql.Date.valueOf(value);
                else if (name.equals("from"))
                    fromDate = java.sql.Date.valueOf(value);
                else if (name.equals("branch"))
                    branch = value;
            }
           
            if (fromDate.after(toDate))
            {
                java.sql.Date h = fromDate;
                fromDate = toDate;
                toDate = h;
            }


            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
            out.println("<HTML>");
            out.println("<HEAD>");
            out.println("	<TITLE>SWIFT Input</TITLE>");
            out.println("</HEAD>");

            out.println("<BODY>");
            out.println("<H2>SWIFT Input</H1>");

            out.println("<HR>");
            out.println("<FORM ACTION=\"/SWIFTInput\" METHOD=\"POST\">");
            out.println("<select name=\"from\" size=\"1\">");

            e = dates.elements();
            while (e.hasMoreElements())
            {
                hStr = (String)e.nextElement();
                if (hStr.equals(fromDate.toString()))
                    out.println("   <option value=\""+hStr+"\" selected>"+hStr+"</option>");
                else
                    out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");
            }
            out.println("</select>");
            out.println("<select name=\"to\" size=\"1\">");

            e = dates.elements();
            while (e.hasMoreElements())
            {
                hStr = (String)e.nextElement();
                if (hStr.equals(toDate.toString()))
                    out.println("   <option value=\""+hStr+"\" selected>"+hStr+"</option>");
                else
                    out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");        
            }
            out.println("</select>");
            out.println("<select name=\"branch\" size=\"1\">");


            e = branches.elements();
            while (e.hasMoreElements())
            {
                hStr = (String)e.nextElement();
                if (hStr.equals(branch))
                    out.println("   <option value=\""+hStr+"\" selected>"+hStr+"</option>");
                else
                    out.println("   <option value=\""+hStr+"\">"+hStr+"</option>");        
            }
            out.println("</select>");
            out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Show\">");
            out.println("</FORM>");
            out.println("<HR>");
            
            out.println("<TABLE CELLPADDING=3 WIDTH=0 BORDER=1 ALIGN=\"LEFT\">");
          	out.println("<TR>");
           	out.println("<TH ALIGN=\"LEFT\">TOSN</TH>");
           	out.println("<TH ALIGN=\"LEFT\">MT</TH>");
           	out.println("<TH ALIGN=\"LEFT\">Sender</TH>");
           	out.println("<TH ALIGN=\"LEFT\">Priority</TH>");
       	    out.println("<TH ALIGN=\"LEFT\">Duplicate</TH>");
           	out.println("<TH ALIGN=\"LEFT\">Receiver</TH>");
           	out.println("<TH ALIGN=\"LEFT\">Receipt Date</TH>");
           	out.println("</TR>");
            
            ResultSet rs;
            if (branch.equals("ALL"))
                rs = headerDriver.query(fromDate, toDate);
            else
                rs = headerDriver.query(fromDate, toDate, branch);
                

            shObj = headerDriver.next(rs);
       	    if (shObj != null)    	        
            {                                
                while ((shObj = headerDriver.next(rs)) != null)
    	        {
                    out.println("<TR>");
                    out.println("	<TD ALIGN=\"LEFT\"><A HREF=\"/SWIFTtosn?TOSN="+shObj.getTOSN()+"\">"+shObj.getTOSN()+"</A></TD>");
                    out.println("   <TD ALIGN=\"LEFT\">"+shObj.getMessage_Type()+"</TD>");
                    out.println("	<TD ALIGN=\"LEFT\">"+shObj.getAddress_Sender()+"</TD>");
                    if (shObj.getPriority().equalsIgnoreCase("U"))
                        out.println("	<TD ALIGN=\"CENTER\">urgent</TD>");
                    else
                        out.println("	<TD ALIGN=\"CENTER\">normal</TD>");

                    if (shObj.getDuplicate().equalsIgnoreCase("Y"))
                        out.println("	<TD ALIGN=\"CENTER\">yes</TD>");
                    else
                        out.println("	<TD ALIGN=\"CENTER\">no</TD>");

                    out.println("	<TD ALIGN=\"LEFT\">"+shObj.getAddress_Receiver()+" ("+shObj.getProc_Center()+")</TD>");

                    out.println("	<TD ALIGN=\"LEFT\">"+shObj.getReceive_Date()+" &nbsp;"+shObj.getReceive_Time()+"</TD>");
                    out.println("</TR>"); 
                } 
         	}
         	else
         	{
         	    out.println("<b>Nothing found</b>");
            }
            out.println("</TABLE>");
            
            out.println("</BODY>");
            out.println("</HTML>");
            out.close();
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
        System.out.println("INIT: SWIFTInputServlet");
        try
        {
            String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
      	    String url = "jdbc:odbc:SWIFTInput";
    	    dba = new DbAccess(drv, url, "", "");    	   
            headerDriver = new SWIFTHeaderDriver(dba); 

            ResultSet rs = headerDriver.query(null, "Receive_Date ASC");
            java.sql.Date tempDate = null;
            
            shObj = headerDriver.next(rs);
   	        if (shObj != null)    	        
            {                                
                do
	            {
                    if (!shObj.getReceive_Date().equals(tempDate))
                    {
                        tempDate = shObj.getReceive_Date();
                        dates.addElement(tempDate.toString());                        
                    }
                } while ((shObj = headerDriver.next(rs)) != null);
     	    }
     	    else
     	    {
                Calendar today = Calendar.getInstance();
     	        dates.addElement(new java.sql.Date(today.get(Calendar.YEAR)-1900, 
     	                                           today.get(Calendar.MONTH), 
     	                                           today.get(Calendar.DATE)).toString());
     	    }
            
            branches.addElement("0835");
            branches.addElement("0823");
            branches.addElement("0060");        
            branches.addElement("ALL");          }
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
        System.out.println("DESTROY: SWIFTInputServlet");
        
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
