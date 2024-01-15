package ch.ess.cal.resource;

import java.util.*;

import com.ibm.icu.util.TimeZone;


import ch.ess.cal.db.*;
import ch.ess.cal.web.preferences.TimeEnum;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;


public final class UserConfig extends Config {
     
  public static final String LOGON_REMEMBER_NO  = "logon.remember.no";
  public static final String LOGON_REMEMBER_UNIT  = "logon.remember.unit";
  public static final String LOGON_REMEMBER_SECONDS  = "logon.remember.seconds";
  
  public static final String START_OF_WEEK  = "start.of.week";
  public static final String MIN_DAYS_IN_FIRST_WEEK = "minimal.days";
  public static final String TIMEZONE  = "timezone";
      
  private Long userId;  
    
  private UserConfig(User user) {    
    this.userId = user.getUserId();
    initialize();
  }

  public static UserConfig getUserConfig(User user) {    
    UserConfig config = new UserConfig(user);
    return config;
  }
        
  public void removeProperty(String key) {
    
    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      session.delete("from UserConfiguration as conf where conf.user = ? and conf.name = ?", 
                     new Object[]{userId, key}, new Type[]{Hibernate.LONG, Hibernate.STRING});          
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
      
      List result = session.find("from UserConfiguration as conf where conf.user = ? and conf.name = ?", 
                    new Object[]{userId, key}, new Type[]{Hibernate.LONG, Hibernate.STRING});
      
      if (!result.isEmpty()) {
        UserConfiguration oldConf = (UserConfiguration)result.get(0);
        oldConf.setPropValue(value);  
      } else {
        
        User user = (User)session.load(User.class, userId);
        
        UserConfiguration newConf = new UserConfiguration();
        newConf.setName(key);
        newConf.setPropValue(value);
        newConf.setUser(user);
        
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
      
      List result = session.find("from UserConfiguration as conf where conf.user = ?", userId, Hibernate.LONG);
      Iterator it = result.iterator();
      while (it.hasNext()) {
        UserConfiguration conf = (UserConfiguration) it.next();        
        setProperty(conf.getName(), conf.getPropValue());            
      }            
                  
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
    

    if (isEmpty()) {
      storeProperty(LOGON_REMEMBER_NO, 1);
      storeProperty(LOGON_REMEMBER_UNIT, TimeEnum.DAY.getName());
      storeProperty(LOGON_REMEMBER_SECONDS, 86400);
      storeProperty(TIMEZONE, TimeZone.getDefault().getID());
      storeProperty(START_OF_WEEK, Calendar.MONDAY);
      storeProperty(MIN_DAYS_IN_FIRST_WEEK, 4);
    }          
      
  }  
}
