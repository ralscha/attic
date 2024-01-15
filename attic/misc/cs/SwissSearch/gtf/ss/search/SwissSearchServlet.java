package gtf.ss.search;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.*;

import common.util.AppProperties;

public class SwissSearchServlet extends HttpServletJXGB {

	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    

	
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		System.out.println("super");
	}


	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		setLastUpdate(req);
			
		String jsp = req.getParameter("jsp");
		String url = null;
		if (jsp != null) {
			url = "/"+jsp;
		} else {
			url = "/SwissSearch.jsp";
		}
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, res);
	}


	public void doPost(HttpServletRequest req,
                    	HttpServletResponse res) throws ServletException, IOException {


		Connection conn = null;

		setLastUpdate(req);
		
		try {

			String action = req.getParameter("action");

			if (action.equals("SEARCH")) {
			
				conn = myBroker.getConnection();
				
				String requesttype = req.getParameter("requesttype");	
				String requeststring = req.getParameter("requeststring");
				String requeststring2 = req.getParameter("requeststring2");
				
				if (requesttype != null)
					requesttype = requesttype.trim();
					
				if (requeststring != null)
					requeststring = requeststring.trim();
					
				if (requeststring2 != null)
					requeststring2 = requeststring2.trim();				
								
				req.setAttribute("ss.search.request.name", "");
				req.setAttribute("ss.search.request.ref", "");
				req.setAttribute("ss.search.request.amount", "");
				req.setAttribute("ss.search.request.amount.from", "");
				req.setAttribute("ss.search.request.amount.to", "");				
				
				DossierSearch ds = new DossierSearch(conn);
				
				if (!requeststring.equals("")) {
					if (requesttype.equalsIgnoreCase("bankref")) {
						req.setAttribute("ss.search.request.ref", requeststring);
						req.setAttribute("ss.search.result", ds.searchWithRef(requeststring));
					} else if (requesttype.equalsIgnoreCase("bankname")) {
						req.setAttribute("ss.search.request.name", requeststring);
						req.setAttribute("ss.search.result", ds.searchWithName(requeststring));
					} else if (requesttype.equalsIgnoreCase("amount")) {
						req.setAttribute("ss.search.request.amount", requeststring);
						req.setAttribute("ss.search.result", ds.searchWithAmount(requeststring));					
					} else if (requesttype.equalsIgnoreCase("amountbetween")) {
						req.setAttribute("ss.search.request.amount.from", requeststring);
						if ((requeststring2 != null) && !requeststring2.trim().equals("")) {
							req.setAttribute("ss.search.result", ds.searchWithAmount(requeststring, requeststring2));					
							req.setAttribute("ss.search.request.amount.to", requeststring2);
						} else {
							req.setAttribute("ss.search.result", ds.searchWithAmount(requeststring));
						}
					}   
				}
				
				String url = req.getParameter("jsp.page");
				if (url == null) 
					url = "/SwissSearch.jsp";
					
				ServletContext sc = getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher(url);
				rd.forward(req, res);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log(e.toString());
		} finally {			
			if (conn != null)
				myBroker.freeConnection(conn);  // Release connection back to pool
		}

		
	}
	
	private void setLastUpdate(HttpServletRequest req) {
		try {
			FileInputStream is = new FileInputStream(propsFile);
	
			Properties props = new Properties();
			props.load(is);
			is.close();
			
			Long lastUpdate = new Long(props.getProperty("last.update"));
			if (lastUpdate != null) {
				java.util.Date lastUpdateDate = new java.util.Date(lastUpdate.longValue());
				req.setAttribute("ss.last.update", lastUpdateFormat.format(lastUpdateDate));
			}
		} catch (Exception e) {
			log(e.toString());
		}
	}
}