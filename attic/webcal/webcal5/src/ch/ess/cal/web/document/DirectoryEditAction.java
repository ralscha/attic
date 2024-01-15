package ch.ess.cal.web.document;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.DirectoryDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.Directory;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;

@Role("$document")
public class DirectoryEditAction extends AbstractEditAction<Directory> {

  private UserDao userDao;
  private GroupDao groupDao;  
  private TranslationService translationService;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setGroupDao(GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    DirectoryForm directoryForm = (DirectoryForm)form;
    directoryForm.setParentId((String)ctx.session().getAttribute("parentId"));
    ctx.session().removeAttribute("parentId");

    DirectoryPermissionFormListControl userPermissionListControl = new DirectoryPermissionFormListControl(userDao);
    userPermissionListControl.populateEmptyList();
    userPermissionListControl.addUser(Util.getUser(ctx.session(), userDao));
    directoryForm.setUserPermissionList(userPermissionListControl);

    DirectoryPermissionFormListControl groupPermissionListControl = new DirectoryPermissionFormListControl(groupDao,
        translationService);
    groupPermissionListControl.populateEmptyList();
    directoryForm.setGroupPermissionList(groupPermissionListControl);

    directoryForm.setHasWritePermission(true);
  }

  @Override
  public void modelToForm(ActionContext ctx, Directory directory) {
    DirectoryForm directoryForm = (DirectoryForm)ctx.form();
    directoryForm.setName(directory.getName());
    directoryForm.setDescription(directory.getDescription());
    if (directory.getParent() != null) {
      directoryForm.setParentId(directory.getParent().getId().toString());
    } else {
      directoryForm.setParentId(null);
    }

    DirectoryPermissionFormListControl userPermissionListControl = new DirectoryPermissionFormListControl(userDao);
    userPermissionListControl.populateList(ctx, directory.getDirectoryPermission());
    directoryForm.setUserPermissionList(userPermissionListControl);

    DirectoryPermissionFormListControl groupPermissionListControl = new DirectoryPermissionFormListControl(groupDao,
        translationService);
    groupPermissionListControl.populateList(ctx, directory.getDirectoryPermission());
    directoryForm.setGroupPermissionList(groupPermissionListControl);

    directoryForm.setUserId(null);
    directoryForm.setUserReadPermission(false);
    directoryForm.setUserWritePermission(false);

    directoryForm.setGroupId(null);
    directoryForm.setGroupReadPermission(false);
    directoryForm.setGroupWritePermission(false);

    directoryForm.setHasWritePermission(((DirectoryDao)getDao()).hasWritePermission(directory, Util.getUser(ctx.session(), userDao)));

    directoryForm.setTabset("general");
  }

  @Override
  public void formToModel(ActionContext ctx, Directory directory) {

    DirectoryForm directoryForm = (DirectoryForm)ctx.form();
    directory.setName(directoryForm.getName());
    directory.setDescription(directoryForm.getDescription());

    if (StringUtils.isNotBlank(directoryForm.getParentId()) && StringUtils.isNumeric(directoryForm.getParentId())) {
      directory.setParent(getDao().findById(directoryForm.getParentId()));
    } else {
      directory.setParent(null);
    }

    directoryForm.getUserPermissionList().formToModel(directory, directory.getDirectoryPermission());
    directoryForm.getGroupPermissionList().formToModel(directory, directory.getDirectoryPermission());

  }

  public void add_onClick(final FormActionContext ctx) {

    DirectoryForm directoryForm = (DirectoryForm)ctx.form();
    if (StringUtils.isNotBlank(directoryForm.getUserId())
        && (directoryForm.getUserReadPermission() || directoryForm.getUserWritePermission())) {
      directoryForm.getUserPermissionList().add(ctx);
      directoryForm.setUserId(null);
      directoryForm.setUserReadPermission(false);
      directoryForm.setUserWritePermission(false);
    } else if (StringUtils.isNotBlank(directoryForm.getGroupId())
        && (directoryForm.getGroupReadPermission() || directoryForm.getGroupWritePermission())) {
      directoryForm.getGroupPermissionList().add(ctx);
      directoryForm.setGroupId(null);
      directoryForm.setGroupReadPermission(false);
      directoryForm.setGroupWritePermission(false);
    }

    directoryForm.setTabset("permission");

    ctx.forwardToInput();
  }

  public void userPermissionList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    deletePermission(ctx, key, true);
  }

  public void groupPermissionList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    deletePermission(ctx, key, false);
  }

  public void deletePermission(final ControlActionContext ctx, final String key, boolean user) throws Exception {
    DirectoryForm directoryForm = (DirectoryForm)ctx.form();
    SimpleListDataModel userDataModel = (SimpleListDataModel)directoryForm.getUserPermissionList().getDataModel();
    SimpleListDataModel groupDataModel = (SimpleListDataModel)directoryForm.getGroupPermissionList().getDataModel();

    Set<String> writePermissionIds = new HashSet<String>();


    for (int i = 0; i < userDataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)userDataModel.getElementAt(i);
      Boolean write = (Boolean)dynaBean.get("write");
      if (write) {
        writePermissionIds.add("u"+userDataModel.getUniqueKey(i));
      }
    }
    for (int i = 0; i < groupDataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)groupDataModel.getElementAt(i);
      Boolean write = (Boolean)dynaBean.get("write");
      if (write) {
        writePermissionIds.add("g"+groupDataModel.getUniqueKey(i));
      }
    }


    SimpleListDataModel dataModel = (SimpleListDataModel)ctx.control().getDataModel();

    for (int i = 0; i < dataModel.size(); i++) {
      String uniqueKey = dataModel.getUniqueKey(i);
      if (key.equals(uniqueKey)) {
        if (user) {
          uniqueKey = "u"+uniqueKey;
        } else {
          uniqueKey = "g"+uniqueKey;
        }
        if (!(writePermissionIds.contains(uniqueKey) && writePermissionIds.size() == 1)) {
          dataModel.remove(i);
          return;
        }
      }
    }
    ctx.addGlobalError("directory.permissiondeleteerror");

  }

}
