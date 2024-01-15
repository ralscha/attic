package ch.ess.base.xml.permission;

import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class PermissionRuleSet extends RuleSetBase {

  @Override
  public void addRuleInstances(final Digester digester) {

    digester.addObjectCreate("permissions", ArrayList.class);

    digester.addObjectCreate("permissions/permission", Permission.class);
    digester.addSetProperties("permissions/permission", "key", "key");
    digester.addSetProperties("permissions/permission", "feature", "feature");
    
    digester.addCallMethod("permissions/permission/text", "addText", 2);
    digester.addCallParam("permissions/permission/text", 0, "locale");
    digester.addCallParam("permissions/permission/text", 1);

    digester.addSetNext("permissions/permission", "add");

  }

}
