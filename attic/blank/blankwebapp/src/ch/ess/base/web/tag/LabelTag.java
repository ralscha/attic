package ch.ess.base.web.tag;

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.validator.ValidatorPlugIn;

/**
 * <p>This class is designed to render a <label> tag for labeling your forms and
 * adds an asterik (*) for required fields.  It was originally written by Erik
 * Hatcher (http://www.ehatchersolutions.com/JavaDevWithAnt/).</p>
 *
 * <p>It is designed to be used as follows:
 * <pre>&lt;appfuse:label key="userForm.username" /&gt;</pre>
 * </p>
 *
 * @jsp.tag name="label" bodycontent="empty"
 */
public class LabelTag extends TagSupport {

  private String key = null;
  private String property = null;
  private String requiredStyleClass = null;
  private String optionalStyleClass = null;
  private String errorClass = null;
  private String prefix = ":";

  @Override
  public int doStartTag() throws JspException {
    // Look up this key to see if its a field of the current form
    boolean requiredField = false;
    boolean validationError = false;

    ValidatorResources resources = (ValidatorResources) pageContext.getServletContext().getAttribute(
        ValidatorPlugIn.VALIDATOR_KEY);

    Locale locale = (Locale) pageContext.getAttribute(Globals.LOCALE_KEY, PageContext.SESSION_SCOPE);

    if (locale == null) {
      locale = Locale.getDefault();
    }

    FormTag formTag = (FormTag) pageContext.getAttribute(Constants.FORM_KEY, PageContext.REQUEST_SCOPE);
    String formName = formTag.getBeanName();
    String fieldName = property;
    String messageKey = (key != null) ? key : property;

    if (resources != null) {
      Form form = resources.getForm(locale, formName);

      if (form != null) {
        Field field = form.getField(fieldName);

        if (field != null) {
          if (field.isDependency("required")) {
            requiredField = true;
          }
        }
      }
    }

    ActionMessages errors = (ActionMessages) pageContext.getAttribute(Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);

    if (errors != null) {
      Iterator errorIterator = errors.get(fieldName);

      if (errorIterator.hasNext()) {
        validationError = true;
      }
    }

    // Retrieve the message string we are looking for
    String message = TagUtils.getInstance().message(pageContext, Globals.MESSAGES_KEY, Globals.LOCALE_KEY, messageKey);

    if (message == null) {
      JspException e = new JspException("Cannot find message for key: " + messageKey);
      TagUtils.getInstance().saveException(pageContext, e);
      throw e;
    }

    String cssRequiredClass = (requiredStyleClass != null) ? requiredStyleClass : "required";
    String cssOptionalClass = (optionalStyleClass != null) ? optionalStyleClass : "optional";
    String cssErrorClass = (errorClass != null) ? errorClass : "error";

    StringBuffer label = new StringBuffer();

    if ((message == null) || "".equals(message.trim())) {
      label.append("");
    } else {
      label.append("<label for=\"" + fieldName + "\"");
      if (validationError) {
        label.append(" class=\"" + cssErrorClass + "\"");
      } else if (requiredField) {
        label.append(" class=\"" + cssRequiredClass + "\"");
      } else {
        label.append(" class=\"" + cssOptionalClass + "\"");
      }
      label.append(">" + message);
      label.append((requiredField ? " *" : "") + prefix + "</label>");
    }

    // Print the retrieved message to our output writer
    TagUtils.getInstance().write(pageContext, label.toString());

    // Continue processing this page
    return (SKIP_BODY);
  }

  /**
   * @jsp.attribute required="false"
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @jsp.attribute required="true"
   */
  public void setProperty(String prop) {
    this.property = prop;
  }

  /**
   * Setter for assigning a CSS class when required, default is required
   *
   * @jsp.attribute required="false"
   */
  public void setRequiredClass(String styleClass) {
    this.requiredStyleClass = styleClass;
  }

  /**
   * Setter for assigning a CSS class when optional, default is optional
   *
   * @jsp.attribute required="false"
   */
  public void setOptionalClass(String styleClass) {
    this.optionalStyleClass = styleClass;
  }

  /**
   * Setter for assigning a CSS class when errors occur,
   * defaults to error.
   *
   * @jsp.attribute required="false"
   */
  public void setErrorClass(String errorClass) {
    this.errorClass = errorClass;
  }

  /**
   * Setter for the charachter after the text   
   *
   * @jsp.attribute required="false"
   */
  public void setPrefix(String string) {
    prefix = string;
  }

}