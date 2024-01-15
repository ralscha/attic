package ch.ess.common.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:14 $
 */
public class MenuBarDisplayer extends MessageResourcesMenuDisplayer {

  public void display(MenuComponent menu) throws JspException, IOException {

    if (isAllowed(menu)) {
      displayComponents(menu, 0);
    }

  }

  protected void displayComponents(MenuComponent menu, int level) throws JspException, IOException {

    MenuComponent[] components = menu.getMenuComponents();

    if (level == 0) {
      out.print("<div class=\"menuBar\" style=\"width:80%;\">");
      for (int i = 0; i < components.length; i++) {
        if (isAllowed(components[i])) {
          if (components[i].getMenuComponents().length > 0) {
            out.print("<a class=\"menuButton\" href=\"\" onclick=\"return buttonClick(event, '"
                + components[i].getName() + "');\" onmouseover=\"buttonMouseover(event, '" + components[i].getName()
                + "');\">");
          } else {
            out.print("<a class=\"menuButton\" href=\"" + components[i].getLocation() + "\">");
          }
          out.print(getMessage(components[i].getTitle()));
          out.println("</a>");
        }
      }
      out.print("</div>");

    } else {

      out.print("<div id=\"" + menu.getName() + "\" class=\"menu\" onmouseover=\"menuMouseover(event)\">");

      for (int i = 0; i < components.length; i++) {
        if (isAllowed(components[i])) {
          if (components[i].getMenuComponents().length > 0) {

            out
                .println("<a class=\"menuItem\" href=\"\" onclick=\"return false;\" onmouseover=\"menuItemMouseover(event, '"
                    + components[i].getName()
                    + "');\"><span class=\"menuItemText\">"
                    + getMessage(components[i].getTitle()) + "</span><span class=\"menuItemArrow\">&#9654;</span></a>");
          } else {
            out.println("<a class=\"menuItem\" href=\"" + components[i].getLocation() + "\">"
                + getMessage(components[i].getTitle()) + "</a>");
          }
        }
      }
      out.println("</div>");
    }

    //next level
    for (int i = 0; i < components.length; i++) {
      if (isAllowed(components[i])) {
        if (components[i].getMenuComponents().length > 0) {
          displayComponents(components[i], level + 1);
        }
      }
    }

  }

}