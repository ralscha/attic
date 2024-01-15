package ch.ess.cal.web.document;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.CallStackObject;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.MapForm;
import ch.ess.cal.dao.DirectoryDao;
import ch.ess.cal.dao.FileDao;
import ch.ess.cal.model.Directory;
import ch.ess.cal.model.File;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.TreeGroupDataModel;

@StrutsAction(path = "/directoryList", 
    form = MapForm.class, 
    input = "/directorylist.jsp", 
    scope = ActionScope.SESSION, 
    roles = "$document", 
    forwards = {
      @Forward(name = "edit", path = "/directoryEdit.do?id={0}"), 
      @Forward(name = "create", path = "/directoryEdit.do"),
      @Forward(name = "editFile", path = "/fileEdit.do?id={0}"),
      @Forward(name = "createFile", path = "/fileEdit.do?directoryId={0}")
    })
public class DirectoryListAction extends AbstractTreeListAction {

  private DirectoryDao directoryDao;
  private FileDao fileDao;
  private UserDao userDao;

  public void setDirectoryDao(DirectoryDao directoryDao) {
    this.directoryDao = directoryDao;
  }

  public void setFileDao(FileDao fileDao) {
    this.fileDao = fileDao;
  }
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public TreeGroupDataModel getDataModel(ActionContext ctx) throws Exception {

    User user = Util.getUser(ctx.session(), userDao);
    MapForm searchForm = (MapForm)ctx.form();
    
    String directoryName = null;
    String fileName = null;
    if (searchForm != null) {
      directoryName = searchForm.getStringValue("directoryName");
      fileName = searchForm.getStringValue("fileName");
    }
    
    DynaTreeDataModel root = new DynaTreeDataModel("root");
    root.set("id", "ROOT");
    root.set("name", "<root>");
    root.setEditable(false);
    root.setDeletable(false);

    
    
    if (StringUtils.isNotBlank(directoryName) || StringUtils.isNotBlank(fileName)) {
      List<Directory> directories = new ArrayList<Directory>();
      if (StringUtils.isNotBlank(directoryName)) {
        directories = directoryDao.findDirectories(directoryName);                    
      }
      Set<Integer> foundFilesId = null;
      if (StringUtils.isNotBlank(fileName)) {      
        List<File> files = fileDao.findByCriteria(Restrictions.like("name", fileName, MatchMode.ANYWHERE));
        if (!files.isEmpty()) {
          foundFilesId = new HashSet<Integer>();
          for (File file : files) {
            directories.add(file.getDirectory()); 
            foundFilesId.add(file.getId());
          }
        }
      } 
      
      populateSelectedDirectories(root, directories, user, foundFilesId);
    } else {
      List<Directory> directories = directoryDao.findAllRootDirectories(null);
      populate(root, directories, user, null);
    }
    
    return root;

  }

  @Override
  protected void setTreeListControlAttributes(ActionContext ctx, TreelistControl listControl) {
    listControl.expandAll();
  }

  private boolean populate(DynaTreeDataModel parent, Collection<Directory> directories, User user, Map<String,DynaTreeDataModel> addedModels) {
    boolean deletable = true;
    
    for (Directory directory : directories) {      
      if (directoryDao.hasReadOrWritePermission(directory, user)) {
        DynaTreeDataModel dirModel = new DynaTreeDataModel(directory.getName());
        dirModel.set("id", directory.getId().toString());
        dirModel.set("name", directory.getName());
        boolean writePermission = directoryDao.hasWritePermission(directory, user);
        dirModel.set("uploadable", writePermission);
        dirModel.setAddable(writePermission);

        if (addedModels == null) {
          parent.addChild(dirModel);
        } else {
          if (!addedModels.containsKey(dirModel.getUniqueKey())) {
            parent.addChild(dirModel);
            addedModels.put(dirModel.getUniqueKey(), dirModel);
          }         
        }
        
        boolean subDeletable = true;
        if (!directory.getChildren().isEmpty()) {
          subDeletable = populate(dirModel, directory.getChildren(), user, null);
        }
  
        if (!directory.getFiles().isEmpty()) {
          subDeletable = populateFiles(dirModel, directory.getFiles(), user, null);
        }
        
        deletable = deletable && writePermission && subDeletable;
        
        dirModel.setDeletable(deletable);
        
      }
    }
    return deletable;
  }

  private boolean populateSelectedDirectories(DynaTreeDataModel parent, Collection<Directory> directories, User user, Set<Integer> foundFilesId) {
    boolean deletable = true;
    
    Map<String,DynaTreeDataModel> addedModels = new HashMap<String,DynaTreeDataModel>();
    
    for (Directory directory : directories) { 
      
      DynaTreeDataModel _parent = parent;
      
      if (directoryDao.hasReadOrWritePermission(directory, user)) {
        
        //Show all until root
        if (directory.getParent() != null) {
          Directory tmpDir = directory.getParent();
          Deque<DynaTreeDataModel> models = new ArrayDeque<DynaTreeDataModel>();
          
          do {            
            DynaTreeDataModel dirModel = new DynaTreeDataModel(tmpDir.getName());
            dirModel.set("id", tmpDir.getId().toString());
            dirModel.set("name", tmpDir.getName());
            boolean writePermission = directoryDao.hasWritePermission(tmpDir, user);
            dirModel.set("uploadable", writePermission);
            dirModel.setAddable(writePermission);
            models.addFirst(dirModel);
            
            tmpDir = tmpDir.getParent();
          } while(tmpDir != null);

          for (DynaTreeDataModel dynaTreeDataModel : models) {
            if (!addedModels.containsKey(dynaTreeDataModel.getUniqueKey())) {
              _parent.addChild(dynaTreeDataModel);
              addedModels.put(dynaTreeDataModel.getUniqueKey(), dynaTreeDataModel);
              _parent = dynaTreeDataModel;
            } else {
              _parent = addedModels.get(dynaTreeDataModel.getUniqueKey());
            }
            
          }
          
        }
                        
        DynaTreeDataModel dirModel = new DynaTreeDataModel(directory.getName());
        dirModel.set("id", directory.getId().toString());
        dirModel.set("name", directory.getName());
        boolean writePermission = directoryDao.hasWritePermission(directory, user);
        dirModel.set("uploadable", writePermission);
        dirModel.setAddable(writePermission);

        if (!addedModels.containsKey(dirModel.getUniqueKey())) {
          _parent.addChild(dirModel);
          addedModels.put(dirModel.getUniqueKey(), dirModel);
        }        
        
        boolean subDeletable = true;
        if (!directory.getChildren().isEmpty()) {
          subDeletable = populate(dirModel, directory.getChildren(), user, addedModels);
        }
  
        if (!directory.getFiles().isEmpty()) {
          subDeletable = populateFiles(dirModel, directory.getFiles(), user, foundFilesId);
        }
        
        deletable = deletable && writePermission && subDeletable;
        
        dirModel.setDeletable(deletable);
        
      }
    }
    return deletable;
  }
    
  
  private boolean populateFiles(DynaTreeDataModel parent, Collection<File> files, User user, Set<Integer> foundFilesId) {
    boolean deletable = true;
    
    for (File file : files) {
      if (fileDao.hasReadOrWritePermission(file, user)) {
        
        if (foundFilesId == null || foundFilesId.contains(file.getId())) {        
          DynaTreeDataModel fileModel = new DynaTreeDataModel(file.getName());
          fileModel.set("id", "file_" + file.getId().toString());
          fileModel.set("fileId", file.getId().toString());
          fileModel.set("name", file.getName());
          fileModel.set("image", "file");
          fileModel.set("fileSize", file.getDocument().getContentSize());
          fileModel.set("uploadable", false);
          fileModel.setAddable(false);
          boolean writePermission = fileDao.hasWritePermission(file, user);
          fileModel.setDeletable(writePermission);
          parent.addChild(fileModel);
          
          deletable = deletable && writePermission;
        }
      }
    }
    return deletable;
  }
  
  
  @Override
  public void onEdit(final ControlActionContext ctx, final String key) {
    if (key.startsWith("file_")) {
      ctx.forwardByName("editFile", key.substring(5));
    } else {
      ctx.forwardByName("edit", key);
    }
  }
  
  public void onUpload(final ControlActionContext ctx, final String key) {
    ctx.forwardByName("createFile", key);
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    if (key.startsWith("file_")) {
      fileDao.delete(key.substring(5));
    } else {
      directoryDao.delete(key);
    }
  }

  public void listControl_onAdd(ControlActionContext ctx, String key) throws Exception {
    ctx.session().setAttribute("parentId", key);
    onCreate(ctx);
  }

  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {

      CallStackObject top = CrumbsUtil.getCallStackTopObject(ctx);
      if (top.getPath().contains("fileEdit")) {
        File file = fileDao.findById(id);
        if (file != null) {
          return file.getName();
        }
      } else {
        Directory directory = directoryDao.findById(id);
        if (directory != null) {
          return directory.getName();
        }
      }
    }
    return null;
  }

}
