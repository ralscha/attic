package ch.ess.addressbook.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.digester.Digester;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.velocity.app.Velocity;

import ch.ess.addressbook.Constants;
import ch.ess.addressbook.db.CategoryCount;
import ch.ess.addressbook.resource.text.RuleSet;
import ch.ess.addressbook.resource.text.TextResources;
import ch.ess.addressbook.util.UploadManager;
import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:26 $ 
  */
public class Init implements PlugIn {

  public void destroy() {
    HibernateFactoryManager.destroy();
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    try {

      HibernateFactoryManager.initLookForDialect(Constants.DATASOURCE_NAME);
      AppConfig.init();

      InitialDataLoad.load(servlet.getServletContext());

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

      initVelocity(servlet.getServletContext());

      //Init Text Resources     
      initTextResources();
      InitialDataLoad.loadTextResources();

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

  private void initVelocity(ServletContext context) throws Exception {
    
    Velocity.setApplicationAttribute(ServletContext.class.getName(), context);
    
    InputStream is = getClass().getResourceAsStream("/velocity.properties");
    Properties props = new Properties();
    props.load(is);
    is.close();    
    Velocity.init(props);  
  }
  
  private void initTextResources() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/textresource.xml");
      if (is != null) {
        Digester digester = new Digester();

        digester.addRuleSet(new RuleSet());

        List l = (List)digester.parse(is);
        TextResources.addResource(l);        

      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }
}