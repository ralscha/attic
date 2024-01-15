package ch.ess.base.web.options;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Constants;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.PermissionDao;
import ch.ess.base.xml.permission.Permission;
import ch.ess.base.xml.permission.Permissions;

@Option(id = "permissionOptions")
public class PermissionOptions extends AbstractOptions {

  private Permissions permissions;
  private PermissionDao permissionDao;
  
  public void setPermissions(final Permissions permissions) {
    this.permissions = permissions;
  }

  public void setPermissionDao(PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    Locale locale = getLocale(request);
    
    List<Permission> permissionList = permissions.getPermissions();
    Map<String,String> translationMap = new HashMap<String,String>();
    for (Permission permission : permissionList) {
      String translation = permission.getText().get(locale);
      translationMap.put(permission.getKey(), translation);      
    }
    
    List<ch.ess.base.model.Permission> dbPermissionList = permissionDao.findAll();

    for (ch.ess.base.model.Permission permission : dbPermissionList) {
      if (permission.getFeature() == null || Constants.hasFeature(permission.getFeature())) {
        add(translationMap.get(permission.getPermission()), permission.getId().toString());
      }
    }

  }

}
