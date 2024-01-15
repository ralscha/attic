package ch.ess.base.xml.locale;

import java.util.ArrayList;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class LocaleRuleSet extends RuleSetBase {

  @Override
  public void addRuleInstances(final Digester digester) {

    digester.addObjectCreate("locales", ArrayList.class);

    digester.addObjectCreate("locales/locale", Locale.class);
    digester.addSetProperties("locales/locale", "key", "key");
    digester.addSetProperties("locales/locale", "default", "defaultLocale");

    digester.addCallMethod("locales/locale/text", "addText", 2);
    digester.addCallParam("locales/locale/text", 0, "locale");
    digester.addCallParam("locales/locale/text", 1);

    digester.addSetNext("locales/locale", "add");

  }

}
