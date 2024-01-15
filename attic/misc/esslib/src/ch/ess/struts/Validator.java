package ch.ess.struts;

import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.commons.logging.*;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;
import org.apache.struts.validator.*;


public class Validator {

  protected static Log log = LogFactory.getLog(Validator.class);

  public static boolean validateContentType(
    Object bean,
    ValidatorAction va,
    Field field,
    ActionErrors errors,
    HttpServletRequest request) {


    String accept = field.getVarValue("accept");
    if (accept == null) {
      accept = "";
    }
    
    FormFile ff = getFormFile(bean, field.getProperty());
        
      
    if ((ff != null) && (ff.getFileSize() > 0)) {
      String contentType = ff.getContentType();

      if ((contentType == null) || (accept.indexOf(contentType) == -1)) {          
        errors.add(field.getKey(), Resources.getActionError(request, va, field));
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
       log.error(e.getMessage(), e);
    }
      
    return (value != null ? (FormFile)value : null); 
  }  
  
}
