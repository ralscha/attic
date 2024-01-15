package ch.ess.cal.web.resourcegroup;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractNameEntryForm;

/** 
 * @author sr
 * @version $Revision: 1.5 $ $Date: 2005/04/04 11:31:12 $
 * 
 * @struts.form name="resourceGroupForm"
 */
public class ResourceGroupForm extends AbstractNameEntryForm {

  private String[] userIds;
  private String[] groupIds;
  private String[] resourceIds;
  private String tabset;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    userIds = null;
    groupIds = null;
    resourceIds = null;
  }

  public String[] getUserIds() {
    return (String[])ArrayUtils.clone(userIds);
  }

  public void setUserIds(final String[] userIds) {
    this.userIds = (String[])ArrayUtils.clone(userIds);
  }

  public String[] getGroupIds() {
    return (String[])ArrayUtils.clone(groupIds);
  }

  public void setGroupIds(final String[] groupIds) {
    this.groupIds = (String[])ArrayUtils.clone(groupIds);
  }

  public String[] getResourceIds() {
    return (String[])ArrayUtils.clone(resourceIds);
  }

  public void setResourceIds(String[] resourceIds) {
    this.resourceIds = (String[])ArrayUtils.clone(resourceIds);
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
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

}