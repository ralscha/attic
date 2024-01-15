package ch.ess.addressbook.resource;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import ch.ess.addressbook.db.CategoryCount;
import ch.ess.addressbook.mail.InitMail;
import ch.ess.addressbook.util.UploadManager;
import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class Init implements PlugIn {

  public void destroy() {
    HibernateFactoryManager.destroy();
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    try {

      HibernateFactoryManager.initLookForDialect("java:comp/env/jdbc/addressbook");
      AppConfig.init();

      InitialDataLoad.load(servlet.getServletContext());

      //Mail
      InitMail.init(false);

      //Upload Manager
      UploadManager.setUploadDir(AppConfig.getStringProperty(AppConfig.UPLOAD_PATH));

      //Init Counter
      //CategoryCount
      try {
        initializeCategoryCount(servlet);
      } catch (Exception e) {
        throw new ServletException(e);
      }

      //Velocity
      Properties props = new Properties();
      props.put(Velocity.RESOURCE_LOADER, "classpath");
      props.put("classpath." + Velocity.RESOURCE_LOADER + ".class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      props.put(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
            "org.apache.velocity.runtime.log.SimpleLog4JLogSystem" );

      props.put("velocimacro.library", "");
      props.put("runtime.log.logsystem.log4j.category", "velocity");
      Velocity.init(props);

    } catch (Exception e) {
      throw new ServletException(e);
    }

  }

  private void initializeCategoryCount(ActionServlet servlet) throws HibernateException {
    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();

      CategoryCount cc = new CategoryCount();

      for (char ch = 'a'; ch <= 'z'; ch++) {
        List resultList =
          sess.find(
            "select count(contact) from Contact as contact where contact.category = ?",
            String.valueOf(ch),
            Hibernate.STRING);

        if (resultList.size() > 0) {
          Integer count = (Integer)resultList.get(0);
          cc.setCount(String.valueOf(ch), count.intValue());
        }

      }

      servlet.getServletContext().setAttribute("categoryCount", cc);
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

    
  }

}
