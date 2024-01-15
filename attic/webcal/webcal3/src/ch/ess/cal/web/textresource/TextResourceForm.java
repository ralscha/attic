package ch.ess.cal.web.textresource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractNameEntryForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 *  
 * @struts.form name="textResourceForm"
 */
public class TextResourceForm extends AbstractNameEntryForm {

  private Map variables;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Map getVariables() {
    return variables;
  }

  public void setVariables(final Map map) {
    variables = map;
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
