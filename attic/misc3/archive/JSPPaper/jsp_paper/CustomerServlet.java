package jsp_paper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
 
public class CustomerServlet extends HttpServlet {
	
    // Forward http get calls to http post handler.
    public void doGet(HttpServletRequest request,
		                  HttpServletResponse response) 
	       throws ServletException, IOException {
	    doPost(request, response);
    }
  
    // Handle customer requests.
    public void doPost(HttpServletRequest request,
		                   HttpServletResponse response) 
	       throws ServletException, IOException {

	    // Fetch the session object for this request. 
	    HttpSession session = request.getSession(true);
	    BasketBean basket = null;
	
	    /*
	      Either create a new shopping cart or update
	      an existing shopping cart
	    */
	    basket = (BasketBean)session.getAttribute(BasketBean.BASKET);
	    if(basket == null) {
	      // New shopper. Create a basket.
	      basket = new BasketBean();
	      session.setAttribute(BasketBean.BASKET, basket);
	    }
	    else {
	      // Existing shopper. Save current contents of basket.
	      basket.savePurchases(request);
	    }

	    RequestDispatcher rd = null;
	    String nextPage = request.getParameter(BasketBean.PAGE);
	
	    /* 
	      Tightly Coupled Version:
 	      Depending upon state of customer workflow, select
	      next JSP page or end shopping session. The servlet
	      is aware of every JSP page in the workflow.
	    */

	    if (nextPage == null || nextPage.equals(BasketBean.UPDATE)) {
	      // select from inventory
	      rd = getServletConfig().getServletContext()
		     .getRequestDispatcher("/jsp/Inventory.jsp");
	    }
	    else if (nextPage.equals(BasketBean.PURCHASE)) { 
	      // provide purchase information
	      rd = getServletConfig().getServletContext()
		     .getRequestDispatcher("/jsp/Purchase.jsp");
	    }
	    else if (nextPage.equals(BasketBean.RECEIPT)) {
	      // provide purchase information
	      rd = getServletConfig().getServletContext()
		     .getRequestDispatcher("/jsp/Receipt.jsp");
	    }
		
	    // Route request to appropriate JSP page.
	    if (rd != null) {
	      rd.forward(request, response);
	    }
   }
}