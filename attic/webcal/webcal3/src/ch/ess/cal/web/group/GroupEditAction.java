package ch.ess.cal.web.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.Email;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.ResourceGroupDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractEditAction;
import ch.ess.cal.web.DynaListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.8 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/editGroup" name="groupForm" input="/groupedit.jsp" scope="session" validate="false" roles="$group" 
 * @struts.action-forward name="back" path="/listGroup.do" redirect="true"
 * 
 * @spring.bean name="/editGroup" lazy-init="true"
 * @spring.property name="clazz" value="ch.ess.cal.model.Group" 
 * @spring.property name="dao" reflocal="groupDao"
 **/
public class GroupEditAction extends AbstractEditAction {

  private UserDao userDao;
  private ResourceGroupDao resourceGroupDao;
  private TranslationService translationService;

  /** 
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /** 
   * @spring.property reflocal="resourceGroupDao"
   */
  public void setResourceGroupDao(final ResourceGroupDao resourceGroupDao) {
    this.resourceGroupDao = resourceGroupDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void emailList_onSort(final ControlActionContext ctx, final String column, final SortOrder direction) {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);
    ctx.control().execute(ctx, column, direction);
  }

  public void emailList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

    for (int i = 0; i < dataModel.size(); i++) {
      if (key.equals(dataModel.getUniqueKey(i))) {

        dataModel.remove(i);
        return;
      }
    }

  }

  public void emailList_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked)
      throws Exception {

    //unchecked requests werden nicht verarbeitet
    //bei einem checked alle anderen auf unchecked gesetzt

    if (checked) {
      DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

      for (int i = 0; i < dataModel.size(); i++) {
        DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
        if (key.equals(dataModel.getUniqueKey(i))) {
          dynaBean.set("default", Boolean.TRUE);
        } else {
          dynaBean.set("default", Boolean.FALSE);
        }
      }
    }

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

      DynaListDataModel dataModel = (DynaListDataModel)groupForm.getEmailList().getDataModel();

      int max = 0;
      for (int i = 0; i < dataModel.size(); i++) {
        String unique = dataModel.getUniqueKey(i);
        max = Math.max(max, Integer.parseInt(unique));
      }

      DynaBean dynaBean = new LazyDynaBean("emailList");
      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("email", groupForm.getAddEmail());

      if (dataModel.size() == 0) {
        dynaBean.set("default", Boolean.TRUE);
      } else {
        dynaBean.set("default", Boolean.FALSE);
      }

      dataModel.add(dynaBean);

      groupForm.setAddEmail(null);
    }
    ctx.forwardToInput();
  }

  @Override
  protected void newItem(final ActionContext ctx) {
    GroupForm groupForm = (GroupForm)ctx.form();

    clearForm(groupForm);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    groupForm.setEntry(translationService.getEmptyNameEntry(messages, locale));

    SimpleListControl emailListControl = new SimpleListControl();
    DynaListDataModel dataModel = new DynaListDataModel();
    emailListControl.setDataModel(dataModel);
    groupForm.setEmailList(emailListControl);

  }

  @Override
  public void formToModel(final ActionContext ctx, final Object model) {
    Group group = (Group)model;
    GroupForm groupForm = (GroupForm)ctx.form();

    //Users	
    Set<String> belongUsers = new HashSet<String>();

    group.getUsers().clear();

    String[] userIds = groupForm.getUserIds();
    if (userIds != null) {

      for (String id : userIds) {
        if (StringUtils.isNotBlank(id)) {
          belongUsers.add(id);
          User user = (User)userDao.get(id);
          group.getUsers().add(user);
        }
      }
    }

    //Access Users	    
    group.getAccessUsers().clear();

    String[] accessUserIds = groupForm.getAccessUserIds();
    List<String> newAccessUserIds = new ArrayList<String>();

    if (accessUserIds != null) {

      for (String id : accessUserIds) {
        if (StringUtils.isNotBlank(id)) {
          if (!belongUsers.contains(id)) {
            User user = (User)userDao.get(id);
            group.getAccessUsers().add(user);
            newAccessUserIds.add(id);
          }
        }
      }
    }
    groupForm.setAccessUserIds(newAccessUserIds.toArray(new String[newAccessUserIds.size()]));

    //ResourceGroups

    group.getResourceGroups().clear();

    String[] resourceGroupIds = groupForm.getResourceGroupIds();
    if (resourceGroupIds != null) {

      for (String id : resourceGroupIds) {
        if (StringUtils.isNotBlank(id)) {
          ResourceGroup resourceGroup = (ResourceGroup)resourceGroupDao.get(id);
          group.getResourceGroups().add(resourceGroup);
        }
      }
    }

    translationService.addTranslation(group, groupForm.getEntry());

    //email  
    Map<Integer, Email> idMap = new HashMap<Integer, Email>();
    for (Email email : group.getEmails()) {
      idMap.put(email.getId(), email);
    }

    List<Email> newEmailList = new ArrayList<Email>();
    int ix = 0;

    DynaListDataModel dataModel = (DynaListDataModel)groupForm.getEmailList().getDataModel();
    for (int i = 0; i < dataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);
      Boolean def = (Boolean)dynaBean.get("default");
      String emailStr = (String)dynaBean.get("email");
      Integer id = new Integer(dataModel.getUniqueKey(i));

      Email email = idMap.get(id);
      if (email != null) {
        email.setDefaultEmail(def.booleanValue());
        email.setEmail(emailStr);
        email.setSequence(ix++);
        newEmailList.add(email);
        idMap.remove(id);
      } else {
        Email e = new Email();
        e.setDefaultEmail(def.booleanValue());
        e.setEmail(emailStr);
        e.setSequence(ix++);
        e.setGroup(group);
        newEmailList.add(e);
      }
    }

    group.getEmails().clear();
    group.getEmails().addAll(newEmailList);

  }

  @Override
  public void modelToForm(final ActionContext ctx, final Object model) {
    Group group = (Group)model;
    GroupForm groupForm = (GroupForm)ctx.form();

    //Users
    Set<User> users = group.getUsers();
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
    } else {
      accessUserIds = null;
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
    } else {
      resourceGroupIds = null;
    }
    groupForm.setResourceGroupIds(resourceGroupIds);

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    groupForm.setEntry(translationService.getNameEntry(messages, locale, group));

    //email
    DynaListDataModel dataModel = new DynaListDataModel();

    Set<Email> emailList = group.getEmails();
    for (Email email : emailList) {

      DynaBean dynaBean = new LazyDynaBean("emailList");
      dynaBean.set("id", email.getId().toString());
      dynaBean.set("email", email.getEmail());
      dynaBean.set("default", Boolean.valueOf(email.isDefaultEmail()));

      dataModel.add(dynaBean);

    }

    SimpleListControl emailListControl = new SimpleListControl();
    emailListControl.setDataModel(dataModel);

    groupForm.setEmailList(emailListControl);
    groupForm.setAddEmail(null);

  }

}