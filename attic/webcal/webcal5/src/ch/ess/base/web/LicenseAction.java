package ch.ess.base.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.annotation.spring.Autowire;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.PermissionDao;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.Permission;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

  @SpringAction(autowire=Autowire.BYTYPE)
  @StrutsAction(path = "/license", 
      form = LicenseForm.class, 
      input = "/license.jsp", 
      scope = ActionScope.REQUEST,  
      forwards = {@Forward(name = "success", path = "/default.do"),
                  @Forward(name = "license", path = "/license.jsp")})
      
public class LicenseAction extends FWAction {
 
  private Config appConfig;
  private UserGroupDao userGroupDao;
  private PermissionDao permissionDao;

  public void setAppConfig(final Config appConfig) {
    this.appConfig = appConfig;
  }
  
  public void setUserGroupDao(UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  public void setPermissionDao(PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  public void doExecute(final ActionContext ctx) throws Exception {

    LicenseForm form = (LicenseForm)ctx.form();
    if (StringUtils.isNotBlank(form.getLicense())) {
      appConfig.setProperty(AppConfig.LICENSE, form.getLicense());
      Constants.setFeatures(form.getLicense());
      appConfig.saveAll();
      
           
      //create usergroup
      List<UserGroup> groupList = userGroupDao.find("administrator");
      if (!groupList.isEmpty()) {
        UserGroup adminGroup = groupList.get(0);
        adminGroup.getUserGroupPermissions().clear();
        
        for (Permission permission : permissionDao.findAll()) {
          if (permission.getFeature() == null || (Constants.hasFeatures() && Constants.hasFeature(permission.getFeature()))) {
            UserGroupPermission userGroupPermission = new UserGroupPermission();
            userGroupPermission.setUserGroup(adminGroup);
            userGroupPermission.setPermission(permission);
    
            adminGroup.getUserGroupPermissions().add(userGroupPermission);
          }
        }

        userGroupDao.save(adminGroup);
      }

      
      
      
      ctx.forwardByName("success");
    } else {
      ctx.forwardByName("license");
    }
  }

}
