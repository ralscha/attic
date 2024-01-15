package ch.ess.cal.xml.textresource;

import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 */

public class TextresourceRuleSet extends RuleSetBase {

  @Override
  public void addRuleInstances(final Digester digester) {

    digester.addObjectCreate("resources", ArrayList.class);

    digester.addObjectCreate("resources/resource", Resource.class);
    digester.addSetProperties("resources/resource", "key", "key");

    digester.addCallMethod("resources/resource/description", "addDescription", 2);
    digester.addCallParam("resources/resource/description", 0, "locale");
    digester.addCallParam("resources/resource/description", 1);

    digester.addCallMethod("resources/resource/text", "addText", 2);
    digester.addCallParam("resources/resource/text", 0, "locale");
    digester.addCallParam("resources/resource/text", 1);

    digester.addObjectCreate("resources/resource/variable", Variable.class);
    digester.addSetProperties("resources/resource/variable");

    digester.addCallMethod("resources/resource/variable/description", "addDescription", 2);
    digester.addCallParam("resources/resource/variable/description", 0, "locale");
    digester.addCallParam("resources/resource/variable/description", 1);

    digester.addSetNext("resources/resource/variable", "addVariable");

    digester.addSetNext("resources/resource", "add");

  }

}
