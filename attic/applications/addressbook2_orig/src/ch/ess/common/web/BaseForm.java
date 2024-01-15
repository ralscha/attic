package ch.ess.common.web;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.validator.ValidatorForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class BaseForm extends ValidatorForm {

  //Utility method
  protected String getTrimmedString(String value) {
    if (!GenericValidator.isBlankOrNull(value)) {
      return value.trim();
    } else {
      return null;
    }
  }

}
