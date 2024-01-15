package ch.ess.base.web.applink;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.PermissionDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.AppLink;
import ch.ess.base.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$admin")
public class AppLinkEditAction extends AbstractEditAction<AppLink> {

  private PermissionDao permissionDao;
  private UserDao userDao;

  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void formToModel(final ActionContext ctx, AppLink appLink) {
    AppLinkForm appLinkForm = (AppLinkForm)ctx.form();
    appLink.setLinkName(appLinkForm.getLinkName());
    appLink.setAppLink(appLinkForm.getAppLink());
  }

  @Override
  public void modelToForm(final ActionContext ctx, final AppLink appLink) {
    AppLinkForm appLinkForm = (AppLinkForm)ctx.form();
    appLinkForm.setLinkName(appLink.getLinkName());
    appLinkForm.setAppLink(appLink.getAppLink());
  }
}
