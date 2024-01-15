package ch.ess.cal.web.group;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractNameEntryForm;

public class GroupForm extends AbstractNameEntryForm {

  private String[] userIds;
  private String[] accessUserIds;
  private String[] resourceGroupIds;

  private String tabset;
  private String addEmail;
  private GroupEmailFormListControl emailList;

  private Boolean taskGroup;
  private Boolean documentGroup;
  private Boolean eventGroup;
  private Boolean timeGroup;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    userIds = null;
    accessUserIds = null;

    taskGroup = false;
    documentGroup = false;
    eventGroup = false;
    timeGroup = false;
  }

  public String[] getUserIds() {
    return (String[])ArrayUtils.clone(userIds);
  }

  public void setUserIds(final String[] userIds) {
    this.userIds = (String[])ArrayUtils.clone(userIds);
  }

  public String[] getAccessUserIds() {
    return (String[])ArrayUtils.clone(accessUserIds);
  }

  public void setAccessUserIds(final String[] accessUserIds) {
    this.accessUserIds = (String[])ArrayUtils.clone(accessUserIds);
  }

  public String[] getResourceGroupIds() {
    return (String[])ArrayUtils.clone(resourceGroupIds);
  }

  public void setResourceGroupIds(String[] resourceGroupIds) {
    this.resourceGroupIds = (String[])ArrayUtils.clone(resourceGroupIds);
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public void setAddEmail(final String email) {
    this.addEmail = email;
  }

  public String getAddEmail() {
    return addEmail;
  }

  public GroupEmailFormListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(GroupEmailFormListControl emailList) {
    this.emailList = emailList;
  }

  public Boolean getDocumentGroup() {
    return documentGroup;
  }

  public void setDocumentGroup(Boolean documentGroup) {
    this.documentGroup = documentGroup;
  }

  public Boolean getEventGroup() {
    return eventGroup;
  }

  public void setEventGroup(Boolean eventGroup) {
    this.eventGroup = eventGroup;
  }

  public Boolean getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup(Boolean taskGroup) {
    this.taskGroup = taskGroup;
  }

  public Boolean getTimeGroup() {
    return timeGroup;
  }

  public void setTimeGroup(Boolean timeGroup) {
    this.timeGroup = timeGroup;
  }

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {

    if (GenericValidator.isBlankOrNull(getAddEmail())) {

      ActionErrors errors = super.validate(mapping, request);
      if (errors == null) {
        errors = new ActionErrors();
      }

      if (TranslationService.isNameEntryBlankOrNull(getEntry())) {
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.fillallrequiredfields"));
      }

      if (errors.isEmpty()) {
        return null;
      }
      return errors;
    }

    if (!GenericValidator.isEmail(getAddEmail())) {
      ActionErrors errors = new ActionErrors();
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.email", translate(request, "user.eMail")));
      return errors;
    }

    return null;

  }

}