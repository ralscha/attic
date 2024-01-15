package lottowin.resource;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.log4j.*;

public class AppConfigServlet extends HttpServlet {
 
	public void destroy() {
 		super.destroy();
 	}

	public void init() throws ServletException {				
		String schema = getServletConfig().getInitParameter("schema");	
		log(schema);	
    AppConfig.setSchema(schema);
	}

	public void doGet(HttpServletRequest request,
                  	HttpServletResponse response) throws java.io.IOException, ServletException {
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	}


}
