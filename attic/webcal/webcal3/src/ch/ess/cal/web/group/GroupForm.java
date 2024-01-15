package ch.ess.cal.web.group;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractNameEntryForm;

import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.8 $ $Date: 2005/04/04 11:31:12 $
 * 
 * @struts.form name="groupForm"
 */
public class GroupForm extends AbstractNameEntryForm {

  private String[] userIds;
  private String[] accessUserIds;
  private String[] resourceGroupIds;

  private String tabset;
  private String addEmail;
  private SimpleListControl emailList;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    userIds = null;
    accessUserIds = null;
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

  public SimpleListControl getEmailList() {
    return emailList;
  }

  public void setEmailList(SimpleListControl emailList) {
    this.emailList = emailList;
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