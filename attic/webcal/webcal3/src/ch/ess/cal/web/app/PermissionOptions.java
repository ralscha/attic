package ch.ess.cal.web.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.Permission;
import ch.ess.cal.persistence.PermissionDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean id="permissionOptions" singleton="false"
 */
public class PermissionOptions extends AbstractOptions {

  private PermissionDao permissionDao;

  /** 
   * @spring.property reflocal="permissionDao"
   */
  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Permission> permissionList = permissionDao.list();

    for (Permission permission : permissionList) {
      addTranslate(request, "permission." + permission.getPermission(), permission.getId().toString());
    }

  }

}
