package ch.ess.cal.web.resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractNameEntryForm;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:12 $
 * 
 * @struts.form name="resourceForm"
 */
public class ResourceForm extends AbstractNameEntryForm {

  private String resourceGroupId;

  public String getResourceGroupId() {
    return resourceGroupId;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="resourceGroup"
   */
  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
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