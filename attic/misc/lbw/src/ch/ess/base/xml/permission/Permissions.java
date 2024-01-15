package ch.ess.base.xml.permission;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.LogFactory;

import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "permissions", initMethod = "init")
public class Permissions {

  private List<Permission> permissions;

  public Permissions() {
    permissions = new ArrayList<Permission>();
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  @SuppressWarnings("unchecked")
  public void init() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/permission.xml");
      if (is != null) {
        Digester digester = new Digester();
        digester.setClassLoader(getClass().getClassLoader());

        digester.addRuleSet(new PermissionRuleSet());

        permissions = (List<Permission>)digester.parse(is);
      }
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("init", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("init", e);
        }
      }
    }
  }

}