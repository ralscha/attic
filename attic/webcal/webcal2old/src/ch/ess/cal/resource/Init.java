package ch.ess.cal.resource;

import java.io.*;
import java.util.*;

import javax.servlet.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;
import org.apache.struts.config.*;
import org.quartz.*;
import org.quartz.impl.*;

import ch.ess.cal.mail.*;

public class Init implements PlugIn {

  
  
  private Scheduler scheduler;
  
  public void destroy() {        

    try {
      scheduler.shutdown(true);
    } catch (SchedulerException e) {      
      e.printStackTrace();
    }    
    
    HibernateManager.destroy();

  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    InitLog.init(servlet.getServletContext());
    
    HibernateManager.init(servlet);
    AppConfig.init();
    
    Locale.setDefault(new Locale("de", "CH"));
    
    
    
    
    try {
      InitialDataLoad.load();
      
      //Holidays
      HolidayRegistry.init();
      
      //Job Engine
      InputStream is = Init.class.getResourceAsStream("/quartz.properties");
          
      StdSchedulerFactory factory = new StdSchedulerFactory();
      factory.initialize(is);
      is.close();
          
      scheduler = factory.getScheduler();        
      //scheduler.start();
      
      
      //Mail
      InitMail.init(false);
      
    } catch (HibernateException e) {
      throw new ServletException(e);
    } catch (SchedulerException e) {
      throw new ServletException(e);      
    } catch (IOException e) {
      throw new ServletException(e);      
    }    
    
     
  }






}