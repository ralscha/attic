package ch.ess.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.model.Permission;
import ch.ess.base.persistence.PermissionDao;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean id="permissionOptions" singleton="false" autowire="byType"
 */
public class PermissionOptions extends AbstractOptions {

  private PermissionDao permissionDao;

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