package gtf.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import common.util.*;
import java.text.*;
import java.sql.*;

public class ExchangeRateCheckServlet extends HttpServlet {

	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private Vector centers;
	
	private final static String selectDateSQL = "select max(rate_fixing_date) from gtflc.exchange_rate where iso_code_alpha = 'USD'";
	private final static String selectNoSQL = "select max(rate_fixing_number) from gtflc.exchange_rate where iso_code_alpha = 'USD' and rate_fixing_date in (select max(rate_fixing_date) from gtflc.exchange_rate where iso_code_alpha = 'USD')";
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	

    	res.setContentType( "text/html" );    	    	
    	HttpSession session = req.getSession(true);
    	
    	// Get the output stream
    	PrintWriter out = res.getWriter();
    	
    	// If the waitPage flag is not set, the wait page
    	// hasn't been displayed.
    	if (session.getValue("waitPage") == null) {
    		
    		// Make sure the waitPage flag is set in the session
    		session.putValue("waitPage", Boolean.TRUE);
    		
    		// Write the wait page directly to the output stream
    		out.println( "<html><head>" );
    		out.println( "<title>Please Wait...</title>" );
    		out.println( "<meta http-equiv=\"Refresh\" content=\"0\"" );
    		out.println( "</head><body>" );
	    	out.println( "<br><br><br>" );
	    	out.println( "<center><h1>checking exchange rate tables...<br>" );
	    	out.println( "Please wait.</h1></center>" );
	    	
			// Always close the output stream
	    	out.close();
    	} else {    	
    		session.removeValue( "waitPage" );
    		    		    		
    		out.println("<html>");
			out.println("<head>");
			out.println("<title>Exchange Rates Table</title>");
			out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<FONT SIZE=\"+1\"><b>Exchange Rates Table</b></FONT>");
	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");    		
			out.print(lastUpdateFormat.format(new java.util.Date()));
		
			out.println("</font>");
    		out.println("<hr>");
    		
    		out.println("<p>");
			out.println("<table border=\"1\">");
			out.println("<tr>");
			out.println("<th>Service Center</th>");
			out.println("<th>Rate Fixing Date</th>");
			out.println("</tr>");

			try {    		
    			String dbDriver = AppProperties.getStringProperty("db.driver").trim();
				Class.forName(dbDriver);
    			for (int i = 0; i < centers.size(); i++) {
    				String center = (String)centers.elementAt(i);    				
    				Connection conn = connect(center);	
    				String name = AppProperties.getStringProperty(center+".name").trim();
    				
    				Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(selectDateSQL);
    				
    				String dateStr = null;
    				int d, m, y;
    				d = m = y = 0;
    				if (rs.next()) {
    					java.sql.Date date = rs.getDate(1);
    					dateStr = dateFormat.format(date);
    					Calendar cal = new GregorianCalendar();
    					cal.setTime(date);
    					d = cal.get(Calendar.DAY_OF_MONTH);
    					m = cal.get(Calendar.MONTH);
    					y = cal.get(Calendar.YEAR);
    				}
    				rs.close();
    				String noStr = null;
    				int no = 0;
    				rs = stmt.executeQuery(selectNoSQL);
    				if (rs.next()) {
    					noStr = rs.getString(1).trim();
    					no = Integer.parseInt(noStr);
    				}
    				rs.close();
    				stmt.close();
    				conn.close();
    				
    				out.println("<tr>");
    				out.println("<td>"+name+"</td>");
    				
    				Calendar now = Calendar.getInstance();
    				int hour = now.get(Calendar.HOUR_OF_DAY);
    				
    				boolean ok = false;
    				
    				    				
    				if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && 
    					 (now.get(Calendar.YEAR) == y)) {
	    				if (((hour >= 8) && (hour < 11)) && (no == 1))
	    					ok = true;
	    				
	    				else if (((hour >= 11) && (hour < 15)) && (no == 2))
	    					ok = true;
	    				
	    				else if (((hour >= 15) && (hour < 17)) && (no == 3))
	    					ok = true;
	    				
	    				else if (((hour >= 17) || (hour < 8)) && (no == 4))
	    					ok = true;
    				} 
    				  
    				now.add(Calendar.DAY_OF_MONTH, -1);
   				if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && 
    					 (now.get(Calendar.YEAR) == y)) {
    					 	if (hour < 8) 
    					 		ok = true;
    				}

    				now.add(Calendar.DAY_OF_MONTH, -2);
   				if ((now.get(Calendar.DAY_OF_MONTH) == d) && (now.get(Calendar.MONTH) == m) && 
    					 (now.get(Calendar.YEAR) == y)) {
    					 	if (hour < 8) 
    					 		ok = true;
    				}
    				
    				
    				if (ok)
    					out.println("<td bgcolor=\"Lime\">"+dateStr+" ("+noStr+")</td>");
    				else
    					out.println("<td bgcolor=\"Red\">"+dateStr+" ("+noStr+")</td>");

    				out.println("</tr>");
    			}
			} catch (Exception e) {
				out.println(e.toString());
			}
                
			out.println("</table>");
			out.println("</head>");
    		out.println("</html>");
    		
    		out.close();
    	}
	}
			
	public void init(ServletConfig config) throws ServletException {
		super.init(config);	
		String datFile = getInitParameter("props.file");
		AppProperties.setFileName(datFile);		
		
		String c = AppProperties.getStringProperty("service.centers");		
		centers = new Vector();
		
		StringTokenizer st = new StringTokenizer(c, ",");
		while (st.hasMoreTokens())
			centers.addElement(st.nextToken());		
	}	
		
	public static Connection connect(String serviceCenter) throws SQLException {	
		String user = AppProperties.getStringProperty(serviceCenter+".db.user").trim();
		String pw = AppProperties.getStringProperty(serviceCenter+".db.password").trim();
		String dbURL = AppProperties.getStringProperty(serviceCenter+".db.url").trim();
		return(DriverManager.getConnection(dbURL, user, pw));
	}
}

    		
    		