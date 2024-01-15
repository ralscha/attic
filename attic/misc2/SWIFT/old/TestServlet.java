import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class TestServlet extends HttpServlet 
{ 

    private Vector dates;
    private Vector branches;

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
        
        dates = new Vector();
        branches = new Vector();
        dates.addElement("01.04.1998");
        dates.addElement("02.04.1998");
        dates.addElement("03.04.1998");
        
        branches.addElement("Zurich");
        branches.addElement("Bern");
        branches.addElement("Zug");
    }
    
    public void destroy() 
    {/*
        try 
        {
            con.close();
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
        }*/
    }

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
        out.println("<FORM ACTION=\"/servlet/TestServlet\" METHOD=\"POST\">");
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
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        // set header field first
        res.setContentType("text/html");

        // then get the writer and write the response data
        PrintWriter out = res.getWriter();

        out.println("<HEAD><TITLE> SimpleServlet Output</TITLE></HEAD><BODY>");
        out.println("<h1> SimpleServlet Output </h1>");
        out.println("<P>This is output is from SimpleServlet.");
        
        
        Enumeration values = req.getParameterNames();
        while(values.hasMoreElements()) 
        {
            String name = (String)values.nextElement();
            String value = req.getParameterValues(name)[0];
            out.println("NAME  = "+name+"<BR>");
            out.println("VALUE = "+value+"<p>");
            
        }
        out.println("</BODY>");
        
        out.close();
    }

    public String getServletInfo() 
    {
        return "The SWIFT Input List written by RS 1998";
    }

}
