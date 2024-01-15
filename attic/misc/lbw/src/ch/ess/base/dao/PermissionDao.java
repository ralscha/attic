package ch.ess.base.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.Permission;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "permissionDao", autowire = Autowire.BYTYPE)
public class PermissionDao extends AbstractDao<Permission> {

  public PermissionDao() {
    super(Permission.class);
  }
  
  @Override
  public boolean canDelete(final Permission permission) {
    return false;
  }
  
  @Transactional(readOnly = true)
  public Permission findByPermission(final String permission) {
    return findByCriteriaUnique(Restrictions.eq("permission", permission));
  }

  @Override
  @Transactional
  public void delete(final Permission permission) {
    
    for (UserGroupPermission userGroupPermission : permission.getUserGroupPermissions()) {
      getSession().delete(userGroupPermission);
    }
   
    getSession().delete(permission);
  }

}