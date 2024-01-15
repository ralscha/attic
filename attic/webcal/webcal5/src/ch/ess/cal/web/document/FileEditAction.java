package ch.ess.cal.web.document;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.Document;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.DocumentSizeConverter;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.DirectoryDao;
import ch.ess.cal.dao.FileDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.File;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;

@StrutsAction(path = "/fileEdit", 
    form = FileForm.class,
    input = "/fileedit.jsp", 
    scope = ActionScope.SESSION, 
    roles = "$document", 
    forwards = @Forward(name = "back", path = "/directoryList.do", redirect=true))
public class FileEditAction extends AbstractEditAction<File> {

  private DirectoryDao directoryDao;
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
  
  public void setDirectoryDao(DirectoryDao directoryDao) {
    this.directoryDao = directoryDao;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    FileForm fileForm = (FileForm)form;
    fileForm.setDirectoryId(ctx.request().getParameter("directoryId"));
    
    FilePermissionFormListControl userPermissionListControl = new FilePermissionFormListControl(userDao);
    userPermissionListControl.populateEmptyList();
    userPermissionListControl.addUser(Util.getUser(ctx.session(), userDao));
    fileForm.setUserPermissionList(userPermissionListControl);

    FilePermissionFormListControl groupPermissionListControl = new FilePermissionFormListControl(groupDao,
        translationService);
    groupPermissionListControl.populateEmptyList();
    fileForm.setGroupPermissionList(groupPermissionListControl);

    fileForm.setHasWritePermission(true);
  }

  @Override
  public void modelToForm(ActionContext ctx, File file) {
    FileForm fileForm = (FileForm)ctx.form();
    fileForm.setName(file.getName());
    fileForm.setDirectoryId(file.getDirectory().getId().toString());
    fileForm.setDescription(file.getDescription());
    DocumentSizeConverter converter = new DocumentSizeConverter();
    fileForm.setFileSize(converter.getAsString(null, file.getDocument().getContentSize()));
    
    
    FilePermissionFormListControl userPermissionListControl = new FilePermissionFormListControl(userDao);
    userPermissionListControl.populateList(ctx, file.getFilePermission());
    fileForm.setUserPermissionList(userPermissionListControl);

    FilePermissionFormListControl groupPermissionListControl = new FilePermissionFormListControl(groupDao,
        translationService);
    groupPermissionListControl.populateList(ctx, file.getFilePermission());
    fileForm.setGroupPermissionList(groupPermissionListControl);

    fileForm.setUserId(null);
    fileForm.setUserReadPermission(false);
    fileForm.setUserWritePermission(false);

    fileForm.setGroupId(null);
    fileForm.setGroupReadPermission(false);
    fileForm.setGroupWritePermission(false);
    
    fileForm.setHasWritePermission(((FileDao)getDao()).hasWritePermission(file, Util.getUser(ctx.session(), userDao)));

    fileForm.setTabset("general");
  }

  @Override
  public void formToModel(ActionContext ctx, File file) throws Exception {

    FileForm fileForm = (FileForm)ctx.form();
    file.setName(fileForm.getName());
    file.setDescription(fileForm.getDescription());
    file.setDirectory(directoryDao.findById(fileForm.getDirectoryId()));
    
    if ((fileForm.getFormFile() != null) && (fileForm.getFormFile().getFileSize() > 0)) {
      Document document = new Document(fileForm.getFormFile());
      file.setName(document.getFileName());      
    
      file.setDocument(document);
      
      DocumentSizeConverter converter = new DocumentSizeConverter();
      fileForm.setFileSize(converter.getAsString(null, document.getContentSize()));
      
     
    }
    
    fileForm.getFormFile().destroy();    

    if (StringUtils.isBlank(file.getName())) {
      file.setName(file.getDocument().getFileName());
    }
    
    fileForm.setName(file.getName());
    
    
    fileForm.getUserPermissionList().formToModel(file, file.getFilePermission());
    fileForm.getGroupPermissionList().formToModel(file, file.getFilePermission());
    
  }

  
  public void add_onClick(final FormActionContext ctx) {

    FileForm fileForm = (FileForm)ctx.form();
    if (StringUtils.isNotBlank(fileForm.getUserId())
        && (fileForm.getUserReadPermission() || fileForm.getUserWritePermission())) {
      fileForm.getUserPermissionList().add(ctx);
      fileForm.setUserId(null);
      fileForm.setUserReadPermission(false);
      fileForm.setUserWritePermission(false);
    } else if (StringUtils.isNotBlank(fileForm.getGroupId())
        && (fileForm.getGroupReadPermission() || fileForm.getGroupWritePermission())) {
      fileForm.getGroupPermissionList().add(ctx);
      fileForm.setGroupId(null);
      fileForm.setGroupReadPermission(false);
      fileForm.setGroupWritePermission(false);
    }

    fileForm.setTabset("permission");

    ctx.forwardToInput();
  }
  
  public void userPermissionList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    deletePermission(ctx, key, true);
  }

  public void groupPermissionList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    deletePermission(ctx, key, false);
  }

  public void deletePermission(final ControlActionContext ctx, final String key, boolean user) throws Exception {
    FileForm fileForm = (FileForm)ctx.form();
    SimpleListDataModel userDataModel = (SimpleListDataModel)fileForm.getUserPermissionList().getDataModel();
    SimpleListDataModel groupDataModel = (SimpleListDataModel)fileForm.getGroupPermissionList().getDataModel();

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
