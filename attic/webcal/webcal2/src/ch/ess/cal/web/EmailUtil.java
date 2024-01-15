package ch.ess.cal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.HibernateException;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.Email;
import ch.ess.cal.db.User;

public class EmailUtil {

  public static DynaClass emailClass;

  static {
    emailClass =
      new BasicDynaClass(
        "Emails",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Integer.class),
          new DynaProperty("dbId", Long.class),
          new DynaProperty("email", String.class),
          new DynaProperty("default", Boolean.class),
          new DynaProperty("canDelete", Boolean.class)});

  }

  public static List getEmails(User user) throws HibernateException {
    return getEmails(user.getEmails());
  }

  public static List getEmails(Department dep) throws HibernateException {
    return getEmails(dep.getEmails());
  }

  private static List getEmails(List dbEmails) throws HibernateException {
    List emails = new ArrayList();
    try {
      int ix = 0;
      for (Iterator it = dbEmails.iterator(); it.hasNext();) {
        Email email = (Email)it.next();

        DynaBean dynaBean = emailClass.newInstance();
        dynaBean.set("id", new Integer(ix++));
        dynaBean.set("dbId", email.getId());
        dynaBean.set("email", email.getEmail());
        dynaBean.set("default", new Boolean(email.isDefaultEmail()));
        dynaBean.set("canDelete", new Boolean(email.canDelete()));
        emails.add(dynaBean);

      }

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
    return emails;
  }

  public static void addEmail(User user, List emails) {
    addEmail(user, null, user.getEmails(), emails);
  }

  public static void addEmail(Department dep, List emails) {
    addEmail(null, dep, dep.getEmails(), emails);
  }

  private static void addEmail(User user, Department dep, List dbEmails, List emails) {
    Map idMap = new HashMap();

    for (Iterator it = dbEmails.iterator(); it.hasNext();) {
      Email email = (Email)it.next();
      idMap.put(email.getId(), email);
    }

    int ix = 0;
    for (Iterator it = emails.iterator(); it.hasNext();) {
      DynaBean db = (DynaBean)it.next();
      Boolean def = (Boolean)db.get("default");
      Long id = (Long)db.get("dbId");

      if (id != null) {

        Email e = (Email)idMap.get(id);

        e.setDefaultEmail(def.booleanValue());
        e.setSequence(ix++);
        idMap.remove(id);
      } else {
        String emailStr = (String)db.get("email");
        Email e = new Email();
        e.setDefaultEmail(def.booleanValue());
        e.setDepartment(null);
        e.setEmail(emailStr);
        if (user != null) {
          e.setUser(user);
          e.setDepartment(null);
        } else if (dep != null) {
          e.setUser(null);
          e.setDepartment(dep);
        }
        e.setSequence(ix++);
        dbEmails.add(e);

      }
    }

    for (Iterator it = idMap.values().iterator(); it.hasNext();) {
      dbEmails.remove(it.next());
    }
  }

  public static boolean handleEmailRequest(Map params, String emailInput, List emails)
    throws IllegalAccessException, InstantiationException {

    Set parmKeys = params.keySet();

    if (parmKeys.contains("addemail")) {

      if (!GenericValidator.isBlankOrNull(emailInput)) {
        DynaBean dynaBean = EmailUtil.emailClass.newInstance();

        dynaBean.set("id", new Integer(emails.size()));
        dynaBean.set("canDelete", Boolean.TRUE);

        if (emails.isEmpty()) {
          dynaBean.set("default", Boolean.TRUE);
        } else {
          dynaBean.set("default", Boolean.FALSE);
        }

        dynaBean.set("dbId", null);

        dynaBean.set("email", emailInput);
        emails.add(dynaBean);
      }

      return true;

    } else if (parmKeys.contains("del")) {
      String idstr = (String)params.get("del");
      int index = Integer.parseInt(idstr);
      emails.remove(index);

      //re-index
      int ix = 0;
      for (Iterator it = emails.iterator(); it.hasNext();) {
        DynaBean db = (DynaBean)it.next();
        db.set("id", new Integer(ix++));
      }

      return true;
    } else if (parmKeys.contains("def")) {

      //set all to nondefault      
      for (Iterator it = emails.iterator(); it.hasNext();) {
        DynaBean db = (DynaBean)it.next();
        db.set("default", Boolean.FALSE);
      }

      String idstr = (String)params.get("def");
      int index = Integer.parseInt(idstr);
      DynaBean db = (DynaBean)emails.get(index);
      db.set("default", Boolean.TRUE);

      return true;
    }

    return false;
  }
}
