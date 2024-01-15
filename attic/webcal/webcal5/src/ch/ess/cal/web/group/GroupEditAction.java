package ch.ess.cal.web.group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.dao.ResourceGroupDao;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.ResourceGroup;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@Role("$admin")
public class GroupEditAction extends AbstractEditAction<Group> {

  private UserDao userDao;
  private ResourceGroupDao resourceGroupDao;
  private TranslationService translationService;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setResourceGroupDao(final ResourceGroupDao resourceGroupDao) {
    this.resourceGroupDao = resourceGroupDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }



  public void add_onClick(final FormActionContext ctx) {

    GroupForm groupForm = (GroupForm)ctx.form();
    if (StringUtils.isNotBlank(groupForm.getAddEmail())) {
      ActionErrors errors = callValidate(ctx, groupForm);
      ctx.addErrors(errors);

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      groupForm.getEmailList().add(ctx, groupForm.getAddEmail());
      groupForm.setAddEmail(null);
    }
    ctx.forwardToInput();
  }
  
  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    GroupForm groupForm = (GroupForm)form;

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    groupForm.setEntry(translationService.getEmptyNameEntry(messages, locale));

    GroupEmailFormListControl emailListControl = new GroupEmailFormListControl();
    emailListControl.populateEmptyList();
    groupForm.setEmailList(emailListControl);
    
  }

  @Override
  public void formToModel(ActionContext ctx, Group group) {

    GroupForm groupForm = (GroupForm)ctx.form();
    
    group.setTaskGroup(groupForm.getTaskGroup());
    group.setDocumentGroup(groupForm.getDocumentGroup());
    group.setEventGroup(groupForm.getEventGroup());
    group.setTimeGroup(groupForm.getTimeGroup());

    //Users	
    Set<String> belongUsers = new HashSet<String>();

    Set<User> users = group.getUsers();
    for (User user : users) {
      user.getGroups().remove(group);
    }
    
    group.getUsers().clear();

    String[] userIds = groupForm.getUserIds();
    if (userIds != null) {

      for (String id : userIds) {
        if (StringUtils.isNotBlank(id)) {
          belongUsers.add(id);
          User user = userDao.findById(id);
          group.getUsers().add(user);
          user.getGroups().add(group);
        }
      }
    }

    //Access Users	    
    users = group.getAccessUsers();
    
    for (User user : users) {
      user.getAccessGroups().remove(group);
    }
    
    group.getAccessUsers().clear();

    String[] accessUserIds = groupForm.getAccessUserIds();
    List<String> newAccessUserIds = new ArrayList<String>();

    if (accessUserIds != null) {

      for (String id : accessUserIds) {
        if (StringUtils.isNotBlank(id)) {
          if (!belongUsers.contains(id)) {
            User user = userDao.findById(id);
            group.getAccessUsers().add(user);
            user.getAccessGroups().add(group);
            newAccessUserIds.add(id);
          }
        }
      }
    }
    groupForm.setAccessUserIds(newAccessUserIds.toArray(new String[newAccessUserIds.size()]));

    //ResourceGroups

    group.getResourceGroups().removeAll(group.getResourceGroups());

    String[] resourceGroupIds = groupForm.getResourceGroupIds();
    if (resourceGroupIds != null) {

      for (String id : resourceGroupIds) {
        if (StringUtils.isNotBlank(id)) {
          ResourceGroup resourceGroup = resourceGroupDao.findById(id);
          group.getResourceGroups().add(resourceGroup);
        }
      }
    }

    translationService.addTranslation(group, groupForm.getEntry());

    //emails
    groupForm.getEmailList().formToModel(group, group.getEmails());
    int ix = 0;
    for (Email email : group.getEmails()) {
      email.setSequence(ix++);
    }

  }

  @Override
  public void modelToForm(final ActionContext ctx, final Group group) {

    GroupForm groupForm = (GroupForm)ctx.form();
    groupForm.setTaskGroup(group.getTaskGroup());
    groupForm.setEventGroup(group.getEventGroup());
    groupForm.setDocumentGroup(group.getDocumentGroup());
    groupForm.setTimeGroup(group.getTimeGroup());

    //Users
    Set<User> users = group.getUsers();
    String[] userIds = null;

    if (!users.isEmpty()) {
      userIds = new String[users.size()];

      int ix = 0;
      for (User user : users) {
        userIds[ix++] = user.getId().toString();
      }
    } 
    
    groupForm.setUserIds(userIds);

    //Access Users
    Set<User> accessUsers = group.getAccessUsers();
    String[] accessUserIds = null;

    if (!accessUsers.isEmpty()) {
      accessUserIds = new String[accessUsers.size()];

      int ix = 0;
      for (User user : accessUsers) {
        accessUserIds[ix++] = user.getId().toString();
      }
    } 
    
    groupForm.setAccessUserIds(accessUserIds);

    //Resourcegroups
    Set<ResourceGroup> resourceGroups = group.getResourceGroups();
    String[] resourceGroupIds = null;

    if (!users.isEmpty()) {
      resourceGroupIds = new String[resourceGroups.size()];

      int ix = 0;
      for (ResourceGroup resourceGroup : resourceGroups) {
        resourceGroupIds[ix++] = resourceGroup.getId().toString();
      }
    }
    
    groupForm.setResourceGroupIds(resourceGroupIds);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    groupForm.setEntry(translationService.getNameEntry(messages, locale, group));

    //email
    GroupEmailFormListControl emailListControl = new GroupEmailFormListControl();
    emailListControl.populateList(ctx, group.getEmails());
    groupForm.setEmailList(emailListControl);
    
    groupForm.setAddEmail(null);
  }

  
}