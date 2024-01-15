package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:20 $
 */
public class Validator {

  private Validator() {
    //its a singleton
  }

  public static boolean validateIdentical(final Object bean, final ValidatorAction validatorAction, final Field field,
      final ActionMessages errors, final HttpServletRequest request) {

    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    String sProperty2 = field.getVarValue("secondProperty");
    String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
    if (!GenericValidator.isBlankOrNull(value)) {
      try {
        if (!value.equals(value2)) {
          errors.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
          return false;

        }
      } catch (Exception e) {
        errors.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
        return false;
      }
    }
    return true;
  }

  public static boolean validatePositive(final Object bean, final ValidatorAction validatorAction, final Field field,
      final ActionMessages errors, final HttpServletRequest request) {
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if (GenericValidator.isInt(value)) {
      if (!GenericValidator.isInRange(Integer.parseInt(value), 0, Integer.MAX_VALUE)) {
        errors.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
        return false;
      }
    }

    return true;
  }

  public static boolean validateContentType(final Object bean, final ValidatorAction validatorAction,
      final Field field, final ActionMessages errors, final HttpServletRequest request) {

    String accept = field.getVarValue("accept");
    if (accept == null) {
      accept = "";
    }

    FormFile formFile = getFormFile(bean, field.getProperty());

    if ((formFile != null) && (formFile.getFileSize() > 0)) {
      String contentType = formFile.getContentType();

      if ((contentType == null) || (accept.indexOf(contentType) == -1)) {
        errors.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
        formFile.destroy();
        return false;
      }
    }

    return true;
  }

  private static FormFile getFormFile(final Object bean, final String property) {
    Object value = null;

    try {
      value = PropertyUtils.getProperty(bean, property);
    } catch (Exception e) {
      LogFactory.getLog(Validator.class).error(e.getMessage(), e);
    }

    return (value != null ? (FormFile)value : null);
  }

}