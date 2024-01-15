package ch.ess.common.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.fgm.web.menu.MenuComponent;
import com.fgm.web.menu.displayer.MenuDisplayerMapping;
import com.fgm.web.menu.displayer.MessageResourcesMenuDisplayer;

public class TigraMenuDisplayer extends MessageResourcesMenuDisplayer {

  public void init(PageContext pageContext, MenuDisplayerMapping mapping) {
    super.init(pageContext, mapping);

    try {
      out.println(displayStrings.getMessage("javascript.start"));
      out.println(displayStrings.getMessage("items.start"));      
    } catch (Exception e) {
    }
  }

  public void display(MenuComponent menu) throws JspException, IOException {

    if (isAllowed(menu)) {
      
      displayComponents(menu, 0);
      
    }

  }

  public void end(PageContext pageContext) {
    try {
      out.println(displayStrings.getMessage("items.end"));
      out.print(displayStrings.getMessage("javascript.end"));
    } catch (Exception e) {
    }

  }

  protected void displayComponents(MenuComponent menu, int level) throws JspException, IOException {
    String title = super.getMessage(menu.getTitle());
    MenuComponent[] components = menu.getMenuComponents();
   
   
    if (components.length > 0) {

      out.print("['");
      out.print(title);
      out.println("', null, null,");

            
      for (int i = 0; i < components.length; i++) {
        // check the permissions on this component
        if (isAllowed(components[i])) {
          if (components[i].getMenuComponents().length > 0) {
            displayComponents(components[i], level + 1);
          } else {
            out.print("['");
            out.print(super.getMessage(components[i].getTitle()));
            out.print("', '");
            out.print(components[i].getLocation()); 
            out.println("'],");
          }
        }
      }

      out.println("],");

    } else {
            
      out.print("['");
      out.print(super.getMessage(menu.getTitle()));
      out.print("', '");
      out.print(menu.getLocation()); 
      out.println("', null],");
      
    }
    
    
  }

}
