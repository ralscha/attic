package ch.ess.common.web;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.MenuDisplayerMapping;
import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.tools.VelocityFormatter;

/**
 * @author  <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version 1.0
 */
public class VelocityMenuDisplayer extends MessageResourcesMenuDisplayer {

  private Log log = LogFactory.getLog(VelocityMenuDisplayer.class);
  private PageContext pageContext = null;


  public void init(PageContext pc, MenuDisplayerMapping map) {
    super.init(pageContext, map);
    this.pageContext = pc;
  }
  
  public void display(MenuComponent menu) throws JspException {
    if (isAllowed(menu)) {
      displayComponents(menu);
    }
  }

  protected void displayComponents(MenuComponent menu) throws JspException {
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    Template t = null;
    boolean debug = Boolean.valueOf(request.getParameter("debug")).booleanValue();

    if (debug) {
      // Print this element to our output writer
      TagUtils.getInstance().write(pageContext, " [ " + getConfig() + " ] ");
    } else {
      try {
        String template = getConfig();

        if (template == null) {
          throw new JspException("You must specify a template using the 'config' attribute.");
        } else {
          log.debug("using template: " + template);
        }

        t = Velocity.getTemplate(template);
      } catch (Exception e) {
        String msg = "Error initializing Velocity: " + e.toString();
        log.error(msg, e);
        throw new JspException(msg, e);
      }

      StringWriter sw = new StringWriter();
      VelocityContext context = new VelocityContext();

      context.put("formatter", new VelocityFormatter(context));
      context.put("now", Calendar.getInstance().getTime());
      context.put("ctxPath", request.getContextPath());
      // add a helper class for string manipulation
      context.put("stringUtils", new StringUtils());

      // add a Map for use by the Explandable List Menu
      context.put("map", new HashMap());

      // see if a name and property were passed in
      if (!StringUtils.isEmpty(name)) {
        Object val1 = TagUtils.getInstance().lookup(pageContext, name, null, null);

        if (val1 != null) {
          context.put(name, val1);
        }
      }

      // request-scope attributes
      Enumeration e = request.getAttributeNames();

      while (e.hasMoreElements()) {
        String namel = (String)e.nextElement();
        Object value = request.getAttribute(namel);
        context.put(namel, value);
      }

      context.put("request", request);
      context.put("session", request.getSession());

      context.put("menu", menu);
      context.put("displayer", this);

      try {
        t.merge(context, sw);
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new JspException(ex);
      }

      String result = sw.getBuffer().toString();

      // Print this element to our output writer
      TagUtils.getInstance().write(pageContext, result);
    }
  }

  public void end(PageContext context) {
    this.pageContext = null;
  }
}
