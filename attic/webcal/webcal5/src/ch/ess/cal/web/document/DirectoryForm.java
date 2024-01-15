package ch.ess.cal.web.document;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class DirectoryForm extends AbstractForm {

  private String name;
  private String description;
  private String parentId;
  private String tabset = "general";

  private Boolean userWritePermission;
  private Boolean userReadPermission;
  private String userId;
  private Boolean groupReadPermission;
  private Boolean groupWritePermission;
  private String groupId;

  private Boolean hasWritePermission;

  private DirectoryPermissionFormListControl userPermissionList;
  private DirectoryPermissionFormListControl groupPermissionList;

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    userWritePermission = false;
    userReadPermission = false;
    groupReadPermission = false;
    groupWritePermission = false;
  }

  public Boolean getHasWritePermission() {
    return hasWritePermission;
  }

  public void setHasWritePermission(Boolean hasWritePermission) {
    this.hasWritePermission = hasWritePermission;
  }

  public String getName() {
    return name;
  }

  @ValidatorField(key = "document.name", required = true)
  public void setName(String name) {
    this.name = name;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Boolean getGroupReadPermission() {
    return groupReadPermission;
  }

  public void setGroupReadPermission(Boolean groupLeserecht) {
    this.groupReadPermission = groupLeserecht;
  }

  public Boolean getGroupWritePermission() {
    return groupWritePermission;
  }

  public void setGroupWritePermission(Boolean groupSchreibrecht) {
    this.groupWritePermission = groupSchreibrecht;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Boolean getUserWritePermission() {
    return userWritePermission;
  }

  public void setUserWritePermission(Boolean userLeserecht) {
    this.userWritePermission = userLeserecht;
  }

  public Boolean getUserReadPermission() {
    return userReadPermission;
  }

  public void setUserReadPermission(Boolean userSchreibrecht) {
    this.userReadPermission = userSchreibrecht;
  }

  public DirectoryPermissionFormListControl getUserPermissionList() {
    return userPermissionList;
  }

  public void setUserPermissionList(DirectoryPermissionFormListControl userPermissionList) {
    this.userPermissionList = userPermissionList;
  }

  public DirectoryPermissionFormListControl getGroupPermissionList() {
    return groupPermissionList;
  }

  public void setGroupPermissionList(DirectoryPermissionFormListControl groupPermissionList) {
    this.groupPermissionList = groupPermissionList;
  }

}
