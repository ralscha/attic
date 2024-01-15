package ch.ess.common.xml.permission;

import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:16 $
 */
public class PermissionRuleSet extends RuleSetBase {

  public void addRuleInstances(Digester digester) {

    digester.addObjectCreate("permissions", ArrayList.class);

    digester.addCallMethod("permissions/permission", "add", 1);
    digester.addCallParam("permissions/permission", 0);

  }

}