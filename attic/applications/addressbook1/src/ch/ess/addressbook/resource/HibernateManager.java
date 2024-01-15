package ch.ess.addressbook.resource;

import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.config.*;

/**
 * Initialize the Hibernate SessionFactory for this project
 * as an application scope object.
 *
 * @author Ted Husted
 * @version $Revision: 1.1 $ $Date: 2003/03/31 06:38:35 $
 */
public class HibernateManager {

  private static final Log logger = LogFactory.getLog(HibernateManager.class);


  private static HibernateManager instance = new HibernateManager();

  /**
   * A field to store the reference to our SessionFactory.
   * Can close and dispose if not null.
   */
  private SessionFactory sf;
  
  private List classList;

  /**
   * A public identifer for the persistence session,
   * kept in servlet session ("client") scope
   * ["HIBERNATE_SESSION"].
   */
  public static String SESSION = "HIBERNATE_SESSION";

  /**
   * A public identifer for the session factory,
   * kept in application ("global") scope
   * ["HIBERNATE_SESSION_FACTORY"].
   */
  public static String SESSION_FACTORY = "HIBERNATE_SESSION_FACTORY";


  private HibernateManager() {  
    //do nothing
  }


  /**
   * Fetch the SessionFactory from application scope.
   * @param request The requeste we are servicing
   * @return The SessionFactory for this application session
   * @throws HibernateException
   */
  public static SessionFactory sessionFactory(HttpServletRequest request) throws HibernateException {
    Object sf = request.getSession().getServletContext().getAttribute(SESSION_FACTORY);
    if (null == sf) {
      throw new HibernateException(SESSION_FACTORY);
    }
    return (SessionFactory) sf;
  }

  /**
   * Open a new session with the application-scope SessionFactory.
   * Session is not retained, only returned.
   *
   * @param request The requeset we are servicing
   * @return An open session
   * @throws HibernateException
   */
  public static Session open(HttpServletRequest request) throws HibernateException {
    return sessionFactory(request).openSession();
  }
  

  public static Session open() throws HibernateException {
    return instance.sf.openSession();
  }  

  /**
   * Open a new Session and cache it in the HttpSession or
   * fetch the existing Session.
   *
   * @param request The requeset we are servicing
   * @return An open session
   * @throws net.sf.hibernate.HibernateException if session cannot be instantiated
   */
  public static Session reconnect(HttpServletRequest request) throws HibernateException {

    Session s = (Session) request.getSession(true).getAttribute(SESSION);
    if (null != s) {
      s.reconnect();
    } else {
      s = open(request);
      request.getSession().setAttribute(SESSION, s);
    }
    return s;
  }

  /**
   * Expire the Session, to ensure fresh data or to switch approaches.
   *
   * @param request The requeset we are servicing
   * @return An open session
   * @throws net.sf.hibernate.HibernateException if session cannot be instantiated
   */
  public static void expire(HttpServletRequest request) throws HibernateException {

    HttpSession httpSession = request.getSession();
    if (null != httpSession) {
      Session s = (Session) httpSession.getAttribute(SESSION);
      if (null != s) {
        s.close();
        httpSession.removeAttribute(SESSION);
      }
    }
  }

  /**
   * The classes with mappings to add to the Configuration are enumerated here.
   * There should be a "${class}.hbm.xml" mapping file for each class
   * stored with each compiled class file.
   * <p>
   * To complete the Hibernate setup, there must be a valid "hiberate.properties"
   * file under the "classes" folder (root of the classpath),
   * which specifies the details of the database hookup.
   * <p>
   * The mapping documents and properties file is all that Hibernate requires.
   * <p>
   * A JDBC Driver is not included in this distribution and *must* be
   * available on your server's or container's classpath
   * (e.g., the Tomcat common/lib directory).
   *
   * @return A Configuration object
   * @throws net.sf.hibernate.MappingException if any the mapping documents can be rendered.
   */
  public static final Configuration createConfiguration() throws MappingException {
    Configuration conf = new Configuration();
    if (instance.classList != null) {
      Iterator it = instance.classList.iterator();
      while (it.hasNext()) {
        Class clazz = (Class)it.next();
        conf.addClass(clazz);        
      }
    }
    return conf;
  }


  public static SessionFactory getFactory() {
    return instance.sf;
  }

  public static void init(ActionServlet servlet, ModuleConfig config, List classList) throws ServletException {

    instance.classList = classList;

    SessionFactory exists = (SessionFactory) servlet.getServletContext().getAttribute(SESSION_FACTORY);
    if (null != exists)
      return; // already got one

    try {
      instance.sf = createConfiguration().buildSessionFactory();
    } catch (HibernateException e) {
      e.printStackTrace();
      servlet.log(e.toString());
      throw new ServletException(e);
    }

    servlet.getServletContext().setAttribute(SESSION_FACTORY, instance.sf);
  }
  
  public static void init(List classList) {
    instance.classList = classList;
    try {
      instance.sf = createConfiguration().buildSessionFactory();
    } catch (HibernateException e) {
      e.printStackTrace();
      logger.error("init", e);      
    }
    
  }

  public static void destroy() {
    if (null != instance.sf) {
      try {
        instance.sf.close();
      } catch (HibernateException e) {
        // too late now
      }
    }
    instance.sf = null;
  }
  
  public static void exceptionHandling(Session session) {
    if (session != null) {
      try {
        session.connection().rollback();
      } catch (HibernateException he) {
        logger.error("HibernateManager: exceptionHandling", he);
      } catch (SQLException sqle) {
        logger.error("HibernateManager: exceptionHandling", sqle);
      }
    }  
  }
  
  
  public static void finallyHandling(Session session) {
    if (session != null) {
      try {
        session.close();
      } catch (HibernateException e) {
        logger.error("HibernateManager:  finallyHandling", e);
      }
    }
  }
  
  public static void commit(Session session) throws HibernateException, SQLException {    
    session.flush();
    session.connection().commit();
  
  }

}
