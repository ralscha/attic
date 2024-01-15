package ch.ess.task.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.velocity.app.Velocity;

import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.task.Constants;
import ch.ess.task.resource.text.RuleSet;
import ch.ess.task.resource.text.TextResources;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $ 
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

      
      //Velocity

      initVelocity(servlet.getServletContext());

      //Init Text Resources     
      initTextResources();
      InitialDataLoad.loadTextResources();

    } catch (Exception e) {
      throw new ServletException(e);
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