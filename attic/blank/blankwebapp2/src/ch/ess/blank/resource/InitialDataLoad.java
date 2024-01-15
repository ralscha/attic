package ch.ess.blank.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import ch.ess.blank.db.Permission;
import ch.ess.blank.db.TextResource;
import ch.ess.blank.db.User;
import ch.ess.blank.db.UserGroup;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.xml.permission.PermissionRuleSet;
import ch.ess.common.xml.textresource.Resource;
import ch.ess.common.xml.textresource.TextResources;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:17 $
 */
public class InitialDataLoad {

  private static final Log LOG = LogFactory.getLog(InitialDataLoad.class);

  public static void load(ServletContext context) throws HibernateException, IOException, SAXException {

    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();
      tx = sess.beginTransaction();
      updatePermissions();
      tx.commit();

      tx = sess.beginTransaction();

      //create usergroup
      List grouplist = UserGroup.find(null);
      if (grouplist.isEmpty()) {
        UserGroup adminGroup = new UserGroup();
        adminGroup.setGroupName("administrator");
        Set permissionSet = new HashSet();
        permissionSet.addAll(Permission.find());
        adminGroup.setPermissions(permissionSet);
        adminGroup.persist();

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("user");
        userGroup.setPermissions(null);
        userGroup.persist();

      }

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

        grouplist = UserGroup.find("administrator");
        if (!grouplist.isEmpty()) {
          Set groupSet = new HashSet();
          groupSet.addAll(grouplist);
          newUser.setUserGroups(groupSet);
        }

        newUser.persist();

        newUser = new User();
        newUser.setUserName("user");
        newUser.setFirstName("Ralph");
        newUser.setName("User");
        newUser.setEmail("schaer@ess.ch");
        newUser.setLocale(Locale.ENGLISH);
        newUser.setPasswordHash(DigestUtils.shaHex("user"));

        grouplist = UserGroup.find("user");
        if (!grouplist.isEmpty()) {
          Set groupSet = new HashSet();
          groupSet.addAll(grouplist);
          newUser.setUserGroups(groupSet);
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

  private static void updatePermissions() throws HibernateException {

    //read permissions from file
    List permissionList = null;

    InputStream is = null;
    try {
      is = InitialDataLoad.class.getResourceAsStream("/permissions.xml");
      if (is != null) {
        Digester digester = new Digester();
        digester.addRuleSet(new PermissionRuleSet());
        permissionList = (List) digester.parse(is);
      }
    } catch (Exception e) {
      LOG.error("updatePermissions", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          LOG.error("updatePermissions", e1);
        }
      }
    }

    if (permissionList == null) {
      return;
    }

    //read all permissions from db
    Map dbPermissionMap = new HashMap();
    Session sess = HibernateSession.currentSession();
    List resultList = sess.find("from Permission");
    for (Iterator it = resultList.iterator(); it.hasNext();) {
      Permission permission = (Permission) it.next();
      dbPermissionMap.put(permission.getPermission(), permission);
    }

    for (Iterator it = permissionList.iterator(); it.hasNext();) {
      String permission = (String) it.next();

      //is permission already in db
      if (dbPermissionMap.containsKey(permission)) {
        dbPermissionMap.remove(permission);
      } else {
        //not in db, insert
        Permission newPermission = new Permission();
        newPermission.setPermission(permission);
        newPermission.persist();
      }

    }

    //dbPermissionMap contains now all permissions no longer in xml file
    for (Iterator it = dbPermissionMap.values().iterator(); it.hasNext();) {
      Permission dbPermission = (Permission) it.next();
      dbPermission.delete();
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