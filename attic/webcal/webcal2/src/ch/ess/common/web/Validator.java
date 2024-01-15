package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:50 $ 
  */
public class Validator {

  private static Log LOG = LogFactory.getLog(Validator.class);

  public static boolean validateIdentical(
    Object bean,
    ValidatorAction va,
    Field field,
    ActionMessages errors,
    HttpServletRequest request) {

    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    String sProperty2 = field.getVarValue("secondProperty");
    String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
    if (!GenericValidator.isBlankOrNull(value)) {
      try {
        if (!value.equals(value2)) {
          errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
          return false;

        }
      } catch (Exception e) {
        errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
        return false;
      }
    }
    return true;
  }

  public static boolean validateContentType(
    Object bean,
    ValidatorAction va,
    Field field,
    ActionMessages errors,
    HttpServletRequest request) {

    String accept = field.getVarValue("accept");
    if (accept == null) {
      accept = "";
    }

    FormFile ff = getFormFile(bean, field.getProperty());

    if ((ff != null) && (ff.getFileSize() > 0)) {
      String contentType = ff.getContentType();

      if ((contentType == null) || (accept.indexOf(contentType) == -1)) {
        errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
        ff.destroy();
        return false;
      }
    }

    return true;
  }

  private static FormFile getFormFile(Object bean, String property) {
    Object value = null;

    try {
      value = PropertyUtils.getProperty(bean, property);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }

    return (value != null ? (FormFile)value : null);
  }

}
