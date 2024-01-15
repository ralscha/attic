package ch.ess.base.web;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

import org.apache.struts.util.MessageResources;

public class MenuBarDisplayer extends MessageResourcesMenuDisplayer {

  @Override
  public void display(MenuComponent menu) throws JspException, IOException {

    if (isAllowed(menu)) {
      displayComponents(menu, 0);
    }

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

    MenuComponent[] components = menu.getMenuComponents();

    if (level == 0) {
      out.print("<div class=\"menuBar\" style=\"width:80%;\">");
      for (MenuComponent element : components) {
        if (isAllowed(element)) {
          if (element.getMenuComponents().length > 0) {
            out.print("<a class=\"menuButton\" href=\"\" onclick=\"return buttonClick(event, '"
                + element.getName() + "');\" onmouseover=\"buttonMouseover(event, '" + element.getName()
                + "');\">");
          } else {
            out.print("<a class=\"menuButton\" href=\"" + element.getLocation() + "\">");
          }
          out.print(getMessage(element.getTitle()));
          out.println("</a>");
        }
      }
      out.print("</div>");

    } else {

      out.print("<div id=\"" + menu.getName() + "\" class=\"menu\" onmouseover=\"menuMouseover(event)\">");

      for (MenuComponent element : components) {
        if (isAllowed(element)) {
          if (element.getMenuComponents().length > 0) {

            out
                .println("<a class=\"menuItem\" href=\"\" onclick=\"return false;\" onmouseover=\"menuItemMouseover(event, '"
                    + element.getName()
                    + "');\"><span class=\"menuItemText\">"
                    + getMessage(element.getTitle()) + "</span><span class=\"menuItemArrow\">&#9654;</span></a>");
          } else {
            if (element.getName().startsWith("seperator")) {
              out.println("<div class=\"menuItemSep\"></div>");
            } else {
              out.println("<a class=\"menuItem\" href=\"" + element.getLocation() + "\">"
                + getMessage(element.getTitle()) + "</a>");
            }
          }
        }
      }
      out.println("</div>");
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