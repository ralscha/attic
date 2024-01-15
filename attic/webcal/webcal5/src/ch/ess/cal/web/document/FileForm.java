package ch.ess.cal.web.document;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class FileForm extends AbstractForm {

  private String name;
  private String description;
  private String fileSize;
  private String directoryId;
  private FormFile formFile;
  private String tabset = "general";

  private Boolean userWritePermission;
  private Boolean userReadPermission;
  private String userId;
  private Boolean groupReadPermission;
  private Boolean groupWritePermission;
  private String groupId;

  private Boolean hasWritePermission;

  private FilePermissionFormListControl userPermissionList;
  private FilePermissionFormListControl groupPermissionList;
  
  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    userWritePermission = false;
    userReadPermission = false;
    groupReadPermission = false;
    groupWritePermission = false;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDirectoryId() {
    return directoryId;
  }

  @ValidatorField(key = "directory.title", required = true)
  public void setDirectoryId(String parentId) {
    this.directoryId = parentId;
  }

  public FormFile getFormFile() {
    return formFile;
  }

  public void setFormFile(FormFile formFile) {
    this.formFile = formFile;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public FilePermissionFormListControl getGroupPermissionList() {
    return groupPermissionList;
  }

  public void setGroupPermissionList(FilePermissionFormListControl groupPermissionList) {
    this.groupPermissionList = groupPermissionList;
  }

  public Boolean getGroupReadPermission() {
    return groupReadPermission;
  }

  public void setGroupReadPermission(Boolean groupReadPermission) {
    this.groupReadPermission = groupReadPermission;
  }

  public Boolean getGroupWritePermission() {
    return groupWritePermission;
  }

  public void setGroupWritePermission(Boolean groupWritePermission) {
    this.groupWritePermission = groupWritePermission;
  }

  public Boolean getHasWritePermission() {
    return hasWritePermission;
  }

  public void setHasWritePermission(Boolean hasWritePermission) {
    this.hasWritePermission = hasWritePermission;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public FilePermissionFormListControl getUserPermissionList() {
    return userPermissionList;
  }

  public void setUserPermissionList(FilePermissionFormListControl userPermissionList) {
    this.userPermissionList = userPermissionList;
  }

  public Boolean getUserReadPermission() {
    return userReadPermission;
  }

  public void setUserReadPermission(Boolean userReadPermission) {
    this.userReadPermission = userReadPermission;
  }

  public Boolean getUserWritePermission() {
    return userWritePermission;
  }

  public void setUserWritePermission(Boolean userWritePermission) {
    this.userWritePermission = userWritePermission;
  }

}
