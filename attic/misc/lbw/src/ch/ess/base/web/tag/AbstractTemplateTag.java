package ch.ess.base.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractTemplateTag extends BaseTag {

  protected String getMessage(final String key) throws JspException {
    return TagUtils.getInstance().message(pageContext, Globals.MESSAGES_KEY, Globals.LOCALE_KEY, key);
  }

  protected void merge(final String templateName, final Object context) throws JspException {

    try {
      Configuration configuration = (Configuration)getBean("freemarkerConfiguration");
      configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), ""));
      Template template = configuration.getTemplate(templateName);

      String output = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
      JspWriter writer = pageContext.getOut();

      writer.write(output);
    } catch (IOException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    } catch (TemplateException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    }

  }

}
