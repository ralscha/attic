package ch.ess.blank.web.usergroup;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.blank.db.Permission;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:16 $
 */
public class PermissionOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList = HibernateSession.currentSession().find("from Permission as p order by p.permission asc");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Permission permission = (Permission) it.next();
        getLabelValue().add(new LabelValueBean(permission.getPermission(), permission.getId().toString()));
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