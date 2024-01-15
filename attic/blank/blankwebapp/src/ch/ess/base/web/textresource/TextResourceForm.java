package ch.ess.base.web.textresource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractNameEntryForm;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 10:25:05 $
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