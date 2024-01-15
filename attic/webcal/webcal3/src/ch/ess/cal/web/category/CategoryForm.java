package ch.ess.cal.web.category;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractNameEntryForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 * 
 * @struts.form name="categoryForm"
 */
public class CategoryForm extends AbstractNameEntryForm {

  private String icalKey;
  private String colour;
  private boolean unknown;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    super.reset(mapping, request);
    unknown = false;
  }

  public String getIcalKey() {
    return icalKey;
  }

  public void setIcalKey(final String icalKey) {
    this.icalKey = icalKey;
  }

  public String getColour() {
    return colour;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="category.colour"
   */
  public void setColour(final String colour) {
    this.colour = colour;
  }

  public boolean isUnknown() {
    return unknown;
  }

  public void setUnknown(final boolean unknown) {
    this.unknown = unknown;
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
