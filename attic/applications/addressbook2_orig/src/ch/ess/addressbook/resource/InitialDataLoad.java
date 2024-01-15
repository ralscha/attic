package ch.ess.addressbook.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
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

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import ch.ess.addressbook.db.Role;
import ch.ess.addressbook.db.User;
import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
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
      Iterator resultIt = User.findAll();
      if (!resultIt.hasNext()) {

        User newUser = new User();
        newUser.setUserName("admin");
        newUser.setFirstName("John");
        newUser.setName("Administrator");
        newUser.setEmail("schaer@ess.ch");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setPasswordHash(ch.ess.common.util.PasswordDigest.getDigestString("admin"));

        List roleList = sess.find("from Role r where r.name = ?", "admin", Hibernate.STRING);
        if (!roleList.isEmpty()) {        
          newUser.addRole((Role)roleList.get(0));
        }

        newUser.persist();

        newUser = new User();
        newUser.setUserName("user");
        newUser.setFirstName("Ralph");
        newUser.setName("User");
        newUser.setEmail("schaer@ess.ch");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setPasswordHash(ch.ess.common.util.PasswordDigest.getDigestString("user"));

        roleList = sess.find("from Role r where r.name = ?", "user", Hibernate.STRING);
        if (!roleList.isEmpty()) {        
          newUser.addRole((Role)roleList.get(0));
        }

        newUser.persist();

      }
      Hibernate.close(resultIt);
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

  }

  private static void updateRoles(List roleList) throws HibernateException {
    
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

}
