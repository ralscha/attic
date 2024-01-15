package ch.ess.cal.web.category;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractNameEntryForm;


public class CategoryForm extends AbstractNameEntryForm {

  private String icalKey;
  private String colour;
  private boolean defaultCategory;
  private String sequence;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    super.reset(mapping, request);
    defaultCategory = false;
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

  @ValidatorField(key = "category.colour", required = true)
  public void setColour(final String colour) {
    this.colour = colour;
  }

  public boolean isDefaultCategory() {
    return defaultCategory;
  }

  public void setDefaultCategory(boolean defaultCategory) {
    this.defaultCategory = defaultCategory;
  }

    
  public String getSequence() {
    return sequence;
  }

 
  @ValidatorField(key = "category.sequenz", required = false, validators = @Validator(name = "integer"))
  public void setSequence(String sequenz) {
    this.sequence = sequenz;
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
