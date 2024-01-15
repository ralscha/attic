package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeSingleton;

/**
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:51 $
  */
public abstract class VelocityTag extends TagSupport {

  protected String getMessage(String key) throws JspException {
    return TagUtils.getInstance().message(pageContext, Globals.MESSAGES_KEY, Globals.LOCALE_KEY, key);
  }
  
  protected void merge(String template, VelocityContext context) throws JspException {
    JspWriter writer = pageContext.getOut();    
    try {
      Velocity.mergeTemplate(template, 
                             RuntimeSingleton.getString(RuntimeConstants.INPUT_ENCODING, RuntimeConstants.ENCODING_DEFAULT),
                             context, writer);
    } catch (ResourceNotFoundException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    } catch (ParseErrorException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    } catch (MethodInvocationException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    } catch (Exception e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    }    
  }

}