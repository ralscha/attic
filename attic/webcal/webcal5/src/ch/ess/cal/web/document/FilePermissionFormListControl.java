package ch.ess.cal.web.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.base.Util;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.File;
import ch.ess.cal.model.FilePermission;
import ch.ess.cal.model.Group;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

public class FilePermissionFormListControl extends SimpleListControl {
  
  private UserDao userDao;
  private GroupDao groupDao;
  private TranslationService translateService;
  private boolean userPermission;

  public FilePermissionFormListControl(UserDao userDao) {
    this.userDao = userDao;
    this.groupDao = null;
    this.translateService = null;
    this.userPermission = true;
  }    

  public FilePermissionFormListControl(GroupDao groupDao, TranslationService translateService) {
    this.userDao = null;
    this.groupDao = groupDao;
    this.userPermission = false;
    this.translateService = translateService;
  }    

  
  
  protected FilePermission newModelItem(DynaBean dynaBean, File file, Map<String, Object> parameters) {
    FilePermission permission = new FilePermission();
    permission.setFile(file);
    if (userPermission) {
      permission.setUser(userDao.findById((String)dynaBean.get("userId")));
      permission.setGroup(null);
    } else {
      permission.setUser(null);
      permission.setGroup(groupDao.findById((String)dynaBean.get("groupId")));
    }
    permission.setReadPermission((Boolean)dynaBean.get("read"));
    permission.setWritePermission((Boolean)dynaBean.get("write"));
    return permission;
  }

  
  public void newRowItem(ActionContext ctx, DynaBean dynaBean, FilePermission child) {
    if (child.getUser() != null) {
      dynaBean.set("name", child.getUser().getName());
      dynaBean.set("firstName", child.getUser().getFirstName());
      dynaBean.set("userId", child.getUser().getId().toString());
    } else {
      dynaBean.set("name", translateService.getText(child.getGroup(), Util.getLocale(ctx.request())));
      dynaBean.set("groupId", child.getGroup().getId().toString());            
    }
    dynaBean.set("read", child.getReadPermission());
    dynaBean.set("write", child.getWritePermission());
  }

  public void addUser(final User user) {
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

    int max = getMaxIndex(dataModel);

    DynaBean dynaBean = new LazyDynaBean("fileUserPermission");
    dynaBean.set("id", String.valueOf(max + 1));
    dynaBean.set("name", user.getName());
    dynaBean.set("firstName", user.getFirstName());
    dynaBean.set("userId", user.getId().toString());
    dynaBean.set("read", true);
    dynaBean.set("write", true);

    dataModel.add(dynaBean);    
  }
  
  public void add(final FormActionContext ctx) {

    FileForm fileForm = (FileForm)ctx.form();
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

    if (userPermission) {
      String userId = fileForm.getUserId();
      for (int i = 0; i < dataModel.size(); i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        String listUserId = (String)dynaBean.get("userId");
        if (listUserId.equals(userId)) {
          return;
        }
      }
    } else {
      String groupId = fileForm.getGroupId();
      for (int i = 0; i < dataModel.size(); i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        String listGroupId = (String)dynaBean.get("groupId");
        if (listGroupId.equals(groupId)) {
          return;
        }
      }
    }
   
    
    
    int max = getMaxIndex(dataModel);

    DynaBean dynaBean = new LazyDynaBean("fileUserPermission");
    dynaBean.set("id", String.valueOf(max + 1));
    if (userPermission) { 
      User user = userDao.findById(fileForm.getUserId());
      dynaBean.set("name", user.getName());
      dynaBean.set("firstName", user.getFirstName());
      dynaBean.set("userId", fileForm.getUserId());
    
      if (fileForm.getUserWritePermission()) {
        dynaBean.set("read", true);
      } else {
        dynaBean.set("read", fileForm.getUserReadPermission());
      }
      dynaBean.set("write", fileForm.getUserWritePermission());
    } else {
      Group group = groupDao.findById(fileForm.getGroupId());
      dynaBean.set("name", translateService.getText(group, Util.getLocale(ctx.request())));
      dynaBean.set("groupId", fileForm.getGroupId()); 
    
      if (fileForm.getGroupWritePermission()) {
        dynaBean.set("read", true);
      } else {
        dynaBean.set("read", fileForm.getGroupReadPermission());
      }
      dynaBean.set("write", fileForm.getGroupWritePermission());
    }
    dataModel.add(dynaBean);
  }

  
  public void formToModel(File parentObject, Set<FilePermission> permissions, Map<String, Object> parameters) {
    SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();
    
    Map<Integer, FilePermission> idMap = new HashMap<Integer, FilePermission>();
  
    for (FilePermission permission : permissions) {
      if (userPermission) {
        if (permission.getUser() != null) {
          idMap.put(permission.getId(), permission);
        }
      } else {
        if (permission.getGroup() != null) {
          idMap.put(permission.getId(), permission);
        }      
      }
    }
  
    for (int i = 0; i < dataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
  
      Integer id = (Integer)dynaBean.get("dbId");
  
      if (id != null) {  
        FilePermission permission = idMap.get(id);
        permission.setReadPermission((Boolean)dynaBean.get("read"));
        permission.setWritePermission((Boolean)dynaBean.get("write"));

        idMap.remove(id);        
      } else {
        FilePermission newPermission = newModelItem(dynaBean, parentObject, parameters);
        permissions.add(newPermission);
      }
    }
  
    for (FilePermission newPermission : idMap.values()) {      
      permissions.remove(newPermission);      
    }
  }

  
  public void populateList(ActionContext ctx, Set<FilePermission> children) {
    SimpleListDataModel dataModel = new SimpleListDataModel();
    
    int ix = 0;
  
    for (FilePermission child : children) {
      if (userPermission) {
        if (child.getUser() == null) {
          continue;
        }
      } else {
        if (child.getGroup() == null) {
          continue;
        }
      }
      DynaBean dynaBean = new LazyDynaBean();
      dynaBean.set("id", String.valueOf(ix++));  
      dynaBean.set("dbId", child.getId());
      newRowItem(ctx, dynaBean, child);
      
      dataModel.add(dynaBean);
    }
  
    setDataModel(dataModel);
  }

  
  public void formToModel(File parentObject, Set<FilePermission> children) {
    formToModel(parentObject, children, null);
  }

  
  
  protected int getMaxIndex(ListDataModel dataModel) {
    int max = 0;
    for (int i = 0; i < dataModel.size(); i++) {
      String unique = dataModel.getUniqueKey(i);
      max = Math.max(max, Integer.parseInt(unique));
    }
    return max;
  }

    
  public void populateEmptyList() {
    SimpleListDataModel dataModel = new SimpleListDataModel();
    setDataModel(dataModel);
  }

  
  
}
