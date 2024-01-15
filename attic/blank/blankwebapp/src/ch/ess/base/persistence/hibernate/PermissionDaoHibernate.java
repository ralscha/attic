package ch.ess.base.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.Permission;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.persistence.PermissionDao;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $
 * 
 * @spring.bean id="permissionDao" autowire="byType"
 */
public class PermissionDaoHibernate extends AbstractDaoHibernate<Permission> implements PermissionDao {

  @Override
  public Class<Permission> getClazz() {
    return Permission.class;
  }

  @Override
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