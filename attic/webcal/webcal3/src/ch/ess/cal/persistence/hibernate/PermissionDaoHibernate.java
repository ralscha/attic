package ch.ess.cal.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.Permission;
import ch.ess.cal.model.UserGroupPermission;
import ch.ess.cal.persistence.PermissionDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="permissionDao" 
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.Permission"
 */
public class PermissionDaoHibernate extends AbstractDaoHibernate<Permission> implements PermissionDao {

  @Override
  @Transactional
  public boolean canDelete(String id) {
    return false;
  }

  @Override
  @Transactional
  public void delete(final Permission permission) {    
    getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        
        for (UserGroupPermission userGroupPermission : permission.getUserGroupPermissions()) {
          session.delete(userGroupPermission);
        }
        session.delete(permission);
        return null;
      }
    });
  }
  
  
}
