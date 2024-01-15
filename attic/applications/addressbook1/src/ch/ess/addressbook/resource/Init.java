package ch.ess.addressbook.resource;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;
import org.apache.struts.config.*;
import org.quartz.*;
import org.quartz.impl.*;

import ch.ess.addressbook.db.*;
import ch.ess.addressbook.search.*;

public class Init implements PlugIn {

  private String uploadDir;
  
  
  private Scheduler scheduler;
  
  public void destroy() {        

    SearchEngine.shutDown();
    
    try {
      scheduler.shutdown(true);
    } catch (SchedulerException e) {      
      e.printStackTrace();
    }    
    
    HibernateManager.destroy();

  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    Locale.setDefault(new Locale("de", "CH"));
    InitLog.init(servlet.getServletContext());
    UploadManager.setUploadDir(uploadDir);
    
    
    List classList = new ArrayList();
    classList.add(Contact.class);
    
    HibernateManager.init(servlet, config, classList);
    
    //CategoryCount
    try {
      initializeCategoryCount(servlet, HibernateManager.getFactory());
    } catch (Exception e) {
      throw new ServletException(e);
    }
    
    
    //SearchEngine
    try {
      InitSearchEngine.init(servlet.getServletContext());
    } catch (IOException e) {
      throw new ServletException(e);
    } catch (JobExecutionException e) {
      throw new ServletException(e);
    }


    //Job Engine
    try {
      InputStream is = Init.class.getResourceAsStream("/quartz.properties");
          
      StdSchedulerFactory factory = new StdSchedulerFactory();
      factory.initialize(is);
      is.close();
          
      scheduler = factory.getScheduler();        
      scheduler.start();
          
      JobDetail jobDetail = new JobDetail("indexAllJob", Scheduler.DEFAULT_GROUP, IndexAll.class);
            
      java.util.Calendar cal = new GregorianCalendar();
      cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
      cal.set(java.util.Calendar.MINUTE, 30);
      cal.set(java.util.Calendar.SECOND, 0);
      cal.set(java.util.Calendar.MILLISECOND, 0);
      
      java.util.Date startTime = cal.getTime();
       
      SimpleTrigger trigger = new SimpleTrigger("dailyTrigger", Scheduler.DEFAULT_GROUP,
                                                startTime,
                                                null,
                                                SimpleTrigger.REPEAT_INDEFINITELY,
                                                24 * 60 * 60 * 1000l);
      
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException e) {
      throw new ServletException(e);      
    } catch (IOException e) {
      throw new ServletException(e);      
    }    
    
     
  }


  public String getUploadDir() {
    return uploadDir;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }



  private void initializeCategoryCount(ActionServlet servlet, SessionFactory sf) throws HibernateException, SQLException {
    //CategoryCount
    
    CategoryCount cc = new CategoryCount();
    
    
    Session sess = null;

    try {
      
      sess = sf.openSession();
          
      for (char ch = 'a'; ch <= 'z'; ch++) {      
        List resultList = sess.find("select count(contact) from Contact as contact where contact.category = ?",
                               String.valueOf(ch), Hibernate.STRING);    
        
        if (resultList.size() > 0) {      
          Integer count = (Integer)resultList.get(0);         
          cc.setCount(String.valueOf(ch), count.intValue());
        }
        
      }

      sess.flush();
      sess.connection().commit();
      
    } catch (HibernateException e) {
      if (sess != null) {
        sess.connection().rollback();
      }
    
      throw e;   
    } finally {
      if (sess != null) {
        sess.close();
      }
    }    
    
    servlet.getServletContext().setAttribute("categoryCount", cc);     
  }

}