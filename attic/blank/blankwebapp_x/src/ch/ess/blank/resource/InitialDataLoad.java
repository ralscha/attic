package ch.ess.blank.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import ch.ess.blank.db.Role;
import ch.ess.blank.db.TextResource;
import ch.ess.blank.db.User;
import ch.ess.blank.resource.text.Resource;
import ch.ess.blank.resource.text.TextResources;
import ch.ess.common.db.HibernateSession;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
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

          List roleList = (List) digester.parse(is);
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

        User newUser = new User();
        newUser.setUserName("admin");
        newUser.setFirstName("John");
        newUser.setName("Administrator");
        newUser.setEmail("schaer@ess.ch");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setPasswordHash(DigestUtils.shaHex("admin"));

        List roleList = sess.find("from Role r where r.name = ?", "admin", Hibernate.STRING);
        if (!roleList.isEmpty()) {
          newUser.addRole((Role) roleList.get(0));
        }

        newUser.persist();

        newUser = new User();
        newUser.setUserName("user");
        newUser.setFirstName("Ralph");
        newUser.setName("User");
        newUser.setEmail("schaer@ess.ch");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setPasswordHash(DigestUtils.shaHex("user"));

        roleList = sess.find("from Role r where r.name = ?", "user", Hibernate.STRING);
        if (!roleList.isEmpty()) {
          newUser.addRole((Role) roleList.get(0));
        }

        newUser.persist();

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
      Role role = (Role) it.next();
      dbRoleMap.put(role.getName(), role);
    }

    Set dbRoleKeySet = dbRoleMap.keySet();

    for (Iterator it = roleList.iterator(); it.hasNext();) {
      WebXmlRole xmlRole = (WebXmlRole) it.next();

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
      Role dbRole = (Role) it.next();
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
        Map.Entry entry = (Map.Entry) it.next();
        String key = (String) entry.getKey();
        Resource res = (Resource) entry.getValue();

        List l = TextResource.findName(key);
        if (l.isEmpty()) {
          //not exists on db
          TextResource tr = new TextResource();
          tr.setName(key);
          tr.setTranslations(res.getText());
          tr.persist();
        } else {
          //exists on db
          TextResource tr = (TextResource) l.get(0);
          Map dbTrans = tr.getTranslations();

          Map memTrans = res.getText();

          List delete = new ArrayList();
          for (Iterator itd = dbTrans.keySet().iterator(); itd.hasNext();) {
            Locale loc = (Locale) itd.next();
            Object o = memTrans.get(loc);
            if (o == null) {
              delete.add(loc);
            }
          }
          for (Iterator itd = delete.iterator(); itd.hasNext();) {
            Locale loc = (Locale) itd.next();
            dbTrans.remove(loc);
          }
          for (Iterator itd = memTrans.entrySet().iterator(); itd.hasNext();) {
            Map.Entry en = (Map.Entry) itd.next();
            Locale loc = (Locale) en.getKey();
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

}