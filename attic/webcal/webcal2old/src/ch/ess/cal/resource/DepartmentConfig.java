package ch.ess.cal.resource;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;
import ch.ess.cal.db.Department;
import ch.ess.cal.db.DepartmentConfiguration;


import com.ibm.icu.util.TimeZone;


public final class DepartmentConfig extends Config {
     
  public static final String START_OF_WEEK  = "start.of.week";
  public static final String TIMEZONE  = "timezone";
  
    
  private Long departmentId;  
    
  private DepartmentConfig(Department department) {    
    this.departmentId = department.getDepartmentId();
    initialize();
  }

  public static DepartmentConfig getDepartmentConfig(Department department) {    
    DepartmentConfig config = new DepartmentConfig(department);
    return config;
  }

      
  public void removeProperty(String key) {
    
    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      session.delete("from DepartmentConfiguration as conf where conf.department = ? and conf.name = ?", 
                     new Object[]{departmentId, key}, new Type[]{Hibernate.LONG, Hibernate.STRING});          
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
    
    
    remove(key);
  }
  
  public void storeProperty(String key, String value) {
    

    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      
      List result = session.find("from DepartmentConfiguration as conf where conf.department = ? and conf.name = ?", 
                    new Object[]{departmentId, key}, new Type[]{Hibernate.LONG, Hibernate.STRING});
      
      if (!result.isEmpty()) {
        DepartmentConfiguration oldConf = (DepartmentConfiguration)result.get(0);
        oldConf.setPropValue(value);  
      } else {
        
        Department department = (Department)session.load(Department.class, departmentId);
        
        DepartmentConfiguration newConf = new DepartmentConfiguration();
        newConf.setName(key);
        newConf.setPropValue(value);
        newConf.setDepartment(department);
        
        session.save(newConf);
      }
                  
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
        
    setProperty(key, value);   
  }
  
  
  protected void load() {
    
            
    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      
      List result = session.find("from DepartmentConfiguration as conf where conf.department = ?", 
                                  departmentId, Hibernate.LONG);
      Iterator it = result.iterator();
      while (it.hasNext()) {
        DepartmentConfiguration conf = (DepartmentConfiguration) it.next();        
        setProperty(conf.getName(), conf.getPropValue());            
      }            
                  
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
    

    if (isEmpty()) {
      storeProperty(TIMEZONE, TimeZone.getDefault().getID());
      storeProperty(START_OF_WEEK, Calendar.MONDAY);
    }          
      
  }  
}
