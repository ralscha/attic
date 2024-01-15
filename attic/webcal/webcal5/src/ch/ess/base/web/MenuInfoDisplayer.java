package ch.ess.base.web;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.MenuDisplayerMapping;
import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

public class MenuInfoDisplayer extends MessageResourcesMenuDisplayer {

  private PageContext pageContext;

  @Override
  public void display(MenuComponent menu) throws JspException, IOException {

    if (isAllowed(menu)) {
      displayComponents(menu, 0);
    }
  }

  @Override
  public void init(PageContext ctx, MenuDisplayerMapping displayerMapping) {
    super.init(ctx, mapping);
    this.pageContext = ctx;
  }

  @Override
  public void end(PageContext ctx) {
    super.end(ctx);
    this.pageContext = null;
  }

  @Override
public String getMessage(String key) {
      String message = null;

      if (messages != null && messages instanceof ResourceBundle) {
          if (log.isDebugEnabled()) {
              log.debug("Looking up string '" + key + "' in ResourceBundle");
          }
          ResourceBundle bundle = (ResourceBundle) messages;
          try {
              message = bundle.getString(key);
          } catch (MissingResourceException mre) {
              message = null;
          }
      } else if (messages != null) {
          if (log.isDebugEnabled()) {
              log.debug("Looking up message '" + key + "' in Struts' MessageResources");
          }
          // this is here to prevent a non-struts webapp from throwing a NoClassDefFoundError
          if (org.apache.struts.util.PropertyMessageResources.class.isAssignableFrom(messages.getClass())) {
              MessageResources resources = (MessageResources) messages;
              try {
                  if (locale != null) {
                      //Method method = clazz.getMethod("getMessage", new Class[] {Locale.class, String.class});
                      message = resources.getMessage(locale, key);
                  } else {
                      message = resources.getMessage(key);
                  }
              } catch (Throwable t) {
                  message = null;
              }
          }
      } else {
          message = key;
      }

      if (message == null) {
          message = key;
      }

      return message;
  }

  protected void displayComponents(MenuComponent menu, int level) throws JspException, IOException {
    String path = (String)pageContext.getRequest().getAttribute("menuInfoDisplay");

    MenuComponent[] components = menu.getMenuComponents();
    boolean hasEntries = false;
    
    if (level > 0) {
    
      out.print("<table class=\"menuInfo\">");

      for (MenuComponent element : components) {        
        if (isAllowed(element)) {
          if (element.getMenuComponents().length > 0) {
            //sub menus
          } else {
            if (!element.getName().startsWith("seperator")) {
              if (path != null && element.getLocation().contains(path)) {
                out.println("<tr>");
                out.println("<td>");
                out.println("<a href=\"" + element.getLocation() + "\">" + getMessage(element.getTitle()) + "</a>");
                out.println("</td>");
                out.println("<td>");
                String msg = getMessage(element.getTitle()+".info");
                if (StringUtils.isNotBlank(msg) && !msg.equals(element.getTitle()+".info")) {
                  out.println(msg);
                }
                out.println("</td>");
                out.println("</tr>");
                hasEntries = true;
              }
            } else if (element.getName().startsWith("seperator")) {
              if (hasEntries) {
                out.println("<tr><td>&nbsp;</td></tr>");
              }
            }
          }
        }
      }
      out.println("</table>");
    }

    for (MenuComponent element : components) {
      if (isAllowed(element)) {
        if (element.getMenuComponents().length > 0) {
          displayComponents(element, level + 1);
        }
      }
    }

  }

}