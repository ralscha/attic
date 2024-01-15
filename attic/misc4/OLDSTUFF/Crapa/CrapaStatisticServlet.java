
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import common.util.*;

public class CrapaStatisticServlet extends HttpServlet {


	public void destroy() {
		try {
			CrapaDbManager.closeDb();
		} catch (SQLException sql) {
			log(sql.toString());
		}
		super.destroy();
	}

	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {
                   		

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		int year = 0, month = 0;
		String branch = null;
				
		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = req.getParameterValues(name)[0];
			
			if ("Year".equals(name.trim())) {
				year = Integer.parseInt(value);
			} else if ("Month".equals(name.trim())) {
				month = Integer.parseInt(value);
			} else if ("Branch".equals(name.trim())) {
				branch = value;
			}	
		}
		
		new CrapaPageCreator(branch, year, month).producePage(out);
		out.close();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String propsFile = getInitParameter("props.file");
		AppProperties.setFileName(propsFile);	
		try {
			CrapaDbManager.openDb();
		} catch (SQLException sql) {
			log(sql.toString());
		}
	
	}
}