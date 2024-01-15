package ch.ess.cal.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.dao.UserDao;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.RecurrenceExceptionDao;

/**
 * Webservice-Class
 * @author  ESS Development AG
 * @version  1.0
 */
@WebService
@SOAPBinding(style=Style.RPC) 

public class CalService {
	@Resource
	private WebServiceContext wsContext;
	/**
	 * @uml.property  name="h"
	 * @uml.associationEnd  
	 */
	@SuppressWarnings("unused")
	private Helper h;
	/**
	 * @uml.property  name="s"
	 * @uml.associationEnd  
	 */
	@SuppressWarnings("unused")
	private Synch s;

	/**
	 * Validates userlogin
	 * @param username username from synchrocal
	 * @param password SHA-1 password from synchrocal
	 * @return true if correct, false if wrong
	 */
	public boolean validateLogin(String username, String password) {
		boolean loggedIn = false;
		if(getHelper().getUserObj(getUserDao(), username, password) != null){
			loggedIn = true;
		}
		return loggedIn;
	}
	
	
	public void saveExceptions(String xml){
		
	}
	/**
	 * Kicks off the synchronisation of normal events
	 * @param username username from synchrocal
	 * @param password password from synchrocal
	 * @param xml string that represents the xml with all normal outlook events
	 */
	public String synchNormalEvents(String username, String password, String xml){
		return getSynch().synchNormalEvents(xml, getEventDao(), getHelper().getUserObj(getUserDao(), username, password),  getCategoryDao());
	}

	/**
	 * Kicks off the synchronisation of recurring events
	 * @param username username from synchrocal
	 * @param password password from synchrocal
	 * @param xml string that represents the xml with all recurring outlook events
	 */
	public String synchRecurEvents(String username, String password, String xml){
		return getSynch().synchRecurEvents(xml, getEventDao(), getHelper().getUserObj(getUserDao(), username, password),  getCategoryDao());
	}
	
	/**
	 * Kicks off the synchronisation of recur exceptions
	 * @param username username from synchrocal
	 * @param password password from synchrocal
	 * @param xml string that represents the xml with all recurring exceptions
	 */
	public void synchExceptions(String username, String password, String xml){
		getSynch().synchExceptions(xml, getRecurrenceExceptionDao());
	}
	
	/**
	 * collects all WebCal-Categories and returns them with a xml-String
	 * @param username username from synchrocal
	 * @param password password from synchrocal
	 * @return string that represents a xml-file of all categories
	 */
	public String getCatgories(String username, String password){
		return getHelper().getCatgories(getCategoryDao());	
	}

	/**
	 * Reads the HTTP-request given from the client-call
	 * @return the request
	 */
	private HttpServletRequest getRequest(){
		MessageContext msgContext = wsContext.getMessageContext();
		HttpServletRequest request = (HttpServletRequest) msgContext.get(MessageContext.SERVLET_REQUEST);
		return request;
	}
	
	/**
	 * returns the UserDao-Instance from the request
	 * @return UserDao instance
	 */
	private UserDao getUserDao(){
		UserDao userDao = (UserDao) WebApplicationContextUtils.getRequiredWebApplicationContext(getRequest().getSession().getServletContext()).getBean("userDao", UserDao.class);
		return userDao;
	}
	
	/**
	 * returns the AbstractDao-Instance from the request
	 * @return UserDao instance
	 */
	private RecurrenceExceptionDao getRecurrenceExceptionDao(){
		RecurrenceExceptionDao recurrenceExceptionDao = (RecurrenceExceptionDao) WebApplicationContextUtils.getRequiredWebApplicationContext(getRequest().getSession().getServletContext()).getBean("recurrenceExceptionDao", RecurrenceExceptionDao.class);
		return recurrenceExceptionDao;
	}
	
	/**
	 * returns the EventDao-Instance from the request
	 * @return EventDao instance
	 */
	private EventDao getEventDao(){
		EventDao eventDao = (EventDao) WebApplicationContextUtils.getRequiredWebApplicationContext(getRequest().getSession().getServletContext()).getBean("eventDao", EventDao.class);
		return eventDao;
	}
	
	/**
	 * returns the CategoryDao-Instance from the request
	 * @return CategoryDao instance
	 */
	private CategoryDao getCategoryDao(){
		return (CategoryDao) WebApplicationContextUtils.getRequiredWebApplicationContext(getRequest().getSession().getServletContext()).getBean("categoryDao", CategoryDao.class);
	}
	
	/**
	 * creates a new instance of Helper.Class
	 * @return Helper instance
	 */
	private Helper getHelper(){
		return h = new Helper();
	}
	
	/**
	 * creates a new instance of Synch.Class
	 * @return Synch instance
	 */
	private Synch getSynch(){
		return s = new Synch();
	}
}
