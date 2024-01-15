package ch.ess.base.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

public class MenuBarDisplayer extends MessageResourcesMenuDisplayer {

  @Override
  public void display(MenuComponent menu) throws JspException, IOException {

    if (isAllowed(menu)) {
      displayComponents(menu, 0);
    }

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