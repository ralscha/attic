package ch.ess.cal.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.Email;
import ch.ess.cal.db.Role;
import ch.ess.cal.db.TextResource;
import ch.ess.cal.db.User;
import ch.ess.cal.resource.text.Resource;
import ch.ess.cal.resource.text.TextResources;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;

import com.ibm.icu.util.TimeZone;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $ 
  */
public class InitialDataLoad {

  public static void load(ServletContext context) throws HibernateException, IOException, SAXException {

    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();

      //Read roles from web.xml
      InputStream is = null;
      try {
        is = context.getResourceAsStream("/WEB-INF/web.xml");
        if (is != null) {
          Digester digester = new Digester();
          URL dtdUrl = InitialDataLoad.class.getResource("/javax/servlet/resources/web-app_2_3.dtd");
          digester.register("-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", dtdUrl.toString());

          WebXmlRole.addRules(digester);

          List roleList = (List)digester.parse(is);
          updateRoles(roleList);

        }
      } finally {
        if (is != null) {
          is.close();
        }
      }

      tx.commit();

      tx = sess.beginTransaction();

      //create users
      Iterator resultIt = User.findWithSearchtext(null, null);
      if (!resultIt.hasNext()) {

        Department department1 = new Department();
        department1.setTranslations(getTranslationMap("Büro 1", "Office 1"));  
        Email email = new Email();
        email.setDepartment(department1);
        email.setDefaultEmail(true);
        email.setEmail("office@ess.ch");
        email.setSequence(0);
        department1.getEmails().add(email);            
        department1.persist();

        Department department2 = new Department();
        department2.setTranslations(getTranslationMap("Büro 2", "Office 2"));
        email = new Email();
        email.setDepartment(department2);
        email.setDefaultEmail(true);
        email.setEmail("office@ess.ch");
        email.setSequence(0);
        department2.getEmails().add(email);                    
        department2.persist();

        Department department3 = new Department();
        department3.setTranslations(getTranslationMap("Büro 3", "Office 3"));
        email = new Email();
        email.setDepartment(department3);
        email.setDefaultEmail(true);
        email.setEmail("office@ess.ch");
        email.setSequence(0);
        department3.getEmails().add(email);                    
        department3.persist();

        Department department4 = new Department();
        department4.setTranslations(getTranslationMap("Büro 4", "Office 4"));
        email = new Email();
        email.setDepartment(department4);
        email.setDefaultEmail(true);
        email.setEmail("office@ess.ch");
        email.setSequence(0);
        department4.getEmails().add(email);                    
        department4.persist();

        User newUser = new User();
        Set departments = new HashSet();
        departments.add(department1);
        newUser.setDepartments(departments);

        departments = new HashSet();
        departments.add(department2);
        newUser.setAccessDepartments(departments);


        newUser.setUserName("admin");
        newUser.setFirstName("John");
        newUser.setName("Administrator");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setTimeZone(TimeZone.getDefault().getID());
        newUser.setPasswordHash(DigestUtils.shaHex("admin"));

        List roleList = sess.find("from Role r where r.name = ?", "admin", Hibernate.STRING);
        if (!roleList.isEmpty()) {
          newUser.addRole((Role)roleList.get(0));
        }

        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(false);
        email.setEmail("ralphschaer@yahoo.com");
        email.setSequence(1);
        newUser.getEmails().add(email);

        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(true);
        email.setEmail("schaer@ess.ch");
        email.setSequence(0);
        newUser.getEmails().add(email);
        
        newUser.persist();



        //normal user
        newUser = new User();
        departments = new HashSet();
        departments.add(department2);
        departments.add(department4);

        newUser.setDepartments(departments);

        departments = new HashSet();
        departments.add(department1);
        departments.add(department3);
        newUser.setAccessDepartments(departments);

        newUser.setUserName("user");
        newUser.setFirstName("Ralph");
        newUser.setName("User");

        newUser.setLocale(Locale.ENGLISH);
        newUser.setTimeZone(TimeZone.getDefault().getID());
        newUser.setPasswordHash(DigestUtils.shaHex("user"));

        roleList = sess.find("from Role r where r.name = ?", "user", Hibernate.STRING);
        if (!roleList.isEmpty()) {
          newUser.addRole((Role)roleList.get(0));
        }

        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(false);
        email.setEmail("ralphschaer@yahoo.com");
        email.setSequence(1);
        newUser.getEmails().add(email);
        
        email = new Email();
        email.setUser(newUser);
        email.setDefaultEmail(true);
        email.setEmail("schaer@ess.ch");
        email.setSequence(0);
        newUser.getEmails().add(email);

        newUser.persist();


        InitialDataLoadHoliday.initialLoad();
        InitialDataLoadResource.initialLoad();
        InitialDataLoadCategory.initialLoad();
      }
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

  }

  private static void updateRoles(List roleList) throws HibernateException {

    if ((roleList == null) || (roleList.size() == 0)) {
      return;
    }
    
    Map dbRoleMap = new HashMap();

    Session sess = HibernateSession.currentSession();

    List resultList = sess.find("from Role");
    for (Iterator it = resultList.iterator(); it.hasNext();) {
      Role role = (Role)it.next();
      dbRoleMap.put(role.getName(), role);
    }

    Set dbRoleKeySet = dbRoleMap.keySet();

    for (Iterator it = roleList.iterator(); it.hasNext();) {
      WebXmlRole xmlRole = (WebXmlRole)it.next();

      //is role already in db      
      if (dbRoleKeySet.contains(xmlRole.getRole())) {
        dbRoleMap.remove(xmlRole.getRole());
      } else {
        //not in db, insert
        Role userRole = new Role();
        userRole.setName(xmlRole.getRole());
        userRole.setDescription(xmlRole.getDescription());
        userRole.persist();
      }

    }

    //dbRoleSet contains now all roles no longer in web.xml
    for (Iterator it = dbRoleMap.values().iterator(); it.hasNext();) {
      Role dbRole = (Role)it.next();
      dbRole.delete();
    }
  }
  
public static void loadTextResources() throws HibernateException {
    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();

      Map trmap = TextResources.getResources();
      for (Iterator it = trmap.entrySet().iterator(); it.hasNext();) {
        Map.Entry entry = (Map.Entry)it.next();
        String key = (String)entry.getKey();
        Resource res = (Resource)entry.getValue();

        List l = TextResource.findName(key);
        if (l.isEmpty()) {
          //not exists on db
          TextResource tr = new TextResource();
          tr.setName(key);
          tr.setTranslations(res.getText());
          tr.persist();
        } else {
          //exists on db
          TextResource tr = (TextResource)l.get(0);
          Map dbTrans = tr.getTranslations();

          Map memTrans = res.getText();

          List delete = new ArrayList();
          for (Iterator itd = dbTrans.keySet().iterator(); itd.hasNext();) {
            Locale loc = (Locale)itd.next();
            Object o = memTrans.get(loc);
            if (o == null) {
              delete.add(loc);
            }
          }
          for (Iterator itd = delete.iterator(); itd.hasNext();) {
            Locale loc = (Locale)itd.next();
            dbTrans.remove(loc);
          }
          for (Iterator itd = memTrans.entrySet().iterator(); itd.hasNext();) {
            Map.Entry en = (Map.Entry)itd.next();
            Locale loc = (Locale)en.getKey();
            Object o = dbTrans.get(loc);
            if (o == null) {
              dbTrans.put(loc, en.getValue());
            }
          }

        }

      }
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

  }

  private static Map getTranslationMap(String german, String english) {
    Map m = new HashMap();
    m.put(Constants.GERMAN_LOCALE, german);
    m.put(Constants.ENGLISH_LOCALE, english);
    return m;
  } 
}
