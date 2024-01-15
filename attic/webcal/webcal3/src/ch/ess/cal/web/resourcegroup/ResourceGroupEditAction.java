package ch.ess.cal.web.resourcegroup;

import java.util.Locale;
import java.util.Set;

import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.Group;
import ch.ess.cal.model.Resource;
import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.GroupDao;
import ch.ess.cal.persistence.ResourceDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractEditAction;

import com.cc.framework.adapter.struts.ActionContext;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/editResourceGroup" name="resourceGroupForm" input="/resourcegroupedit.jsp" scope="session" validate="false" roles="$resourcegroup" 
 * @struts.action-forward name="back" path="/listResourceGroup.do" redirect="true"
 * 
 * @spring.bean name="/editResourceGroup" lazy-init="true"
 * @spring.property name="clazz" value="ch.ess.cal.model.ResourceGroup" 
 * @spring.property name="dao" reflocal="resourceGroupDao"
 **/
public class ResourceGroupEditAction extends AbstractEditAction {

  private UserDao userDao;
  private GroupDao groupDao;
  private ResourceDao resourceDao;
  private TranslationService translationService;

  /** 
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /** 
   * @spring.property reflocal="groupDao"
   */
  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  /** 
   * @spring.property reflocal="resourceDao"
   */
  public void setResourceDao(final ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  protected void newItem(final ActionContext ctx) {
    ResourceGroupForm resourceGroupFrom = (ResourceGroupForm)ctx.form();

    clearForm(resourceGroupFrom);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    resourceGroupFrom.setEntry(translationService.getEmptyNameEntry(messages, locale));

  }

  public void formToModel(final ActionContext ctx, final Object model) {
    ResourceGroup resourceGroup = (ResourceGroup)model;
    ResourceGroupForm resourceGroupForm = (ResourceGroupForm)ctx.form();

    //Users	    
    resourceGroup.getUsers().clear();

    String[] userIds = resourceGroupForm.getUserIds();
    if (userIds != null) {

      for (String id : userIds) {
        User user = (User)userDao.get(id);
        resourceGroup.getUsers().add(user);
      }
    }

    //Groups	    
    resourceGroup.getGroups().clear();

    String[] groupIds = resourceGroupForm.getGroupIds();
    if (groupIds != null) {

      for (String id : groupIds) {
        Group group = (Group)groupDao.get(id);
        resourceGroup.getGroups().add(group);
      }
    }

    //Resources    
    String[] resourceIds = resourceGroupForm.getResourceIds();
    if (resourceIds != null) {
      for (String id : resourceIds) {
        Resource resource = (Resource)resourceDao.get(id);
        resourceGroup.getResources().add(resource);
      }
    }
    setResource(resourceGroup, resourceGroupForm);

    translationService.addTranslation(resourceGroup, resourceGroupForm.getEntry());

  }

  public void modelToForm(final ActionContext ctx, final Object model) {
    ResourceGroup resourceGroup = (ResourceGroup)model;
    ResourceGroupForm resourceGroupForm = (ResourceGroupForm)ctx.form();

    //Users
    Set<User> users = resourceGroup.getUsers();
    String[] userIds = null;

    if (!users.isEmpty()) {
      userIds = new String[users.size()];

      int ix = 0;
      for (User user : users) {
        userIds[ix++] = user.getId().toString();
      }
    } else {
      userIds = null;
    }
    resourceGroupForm.setUserIds(userIds);

    //Groups
    Set<Group> groups = resourceGroup.getGroups();
    String[] groupIds = null;

    if (!groups.isEmpty()) {
      groupIds = new String[groups.size()];

      int ix = 0;
      for (Group group : groups) {
        groupIds[ix++] = group.getId().toString();
      }
    } else {
      groupIds = null;
    }
    resourceGroupForm.setGroupIds(groupIds);

    setResource(resourceGroup, resourceGroupForm);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    resourceGroupForm.setEntry(translationService.getNameEntry(messages, locale, resourceGroup));

  }

  private void setResource(ResourceGroup resourceGroup, ResourceGroupForm resourceGroupForm) {
    //Resources
    Set<Resource> resources = resourceGroup.getResources();
    String[] resourceIds = null;
    if (!resources.isEmpty()) {
      resourceIds = new String[resources.size()];

      int ix = 0;
      for (Resource resource : resources) {
        resourceIds[ix++] = resource.getId().toString();
      }

    } else {
      resourceIds = null;
    }
    resourceGroupForm.setResourceIds(resourceIds);
  }

}