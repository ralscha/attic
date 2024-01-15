package ch.ess.blank.resource;

import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 12:24:17 $
 */
public class WebXmlRole {

  private String role;
  private String description;

  public String getDescription() {
    return description;
  }

  public String getRole() {
    return role;
  }

  public void setDescription(String string) {
    description = string;
  }

  public void setRole(String string) {
    role = string;
  }

  public static void addRules(Digester digester) {

    digester.addObjectCreate("web-app", ArrayList.class);

    digester.addObjectCreate("web-app/security-role", WebXmlRole.class);

    digester.addBeanPropertySetter("web-app/security-role/description");
    digester.addBeanPropertySetter("web-app/security-role/role-name", "role");

    digester.addSetNext("web-app/security-role", "add");

  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}