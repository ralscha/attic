package ch.ess.base.web.options;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.xml.locale.Locales;

@Option(id = "localeOptions")
public class LocaleOptions extends AbstractOptions {

  private Locales locales;

  public void setLocales(Locales locales) {
    this.locales = locales;
  }

  @Override
  public void init(final HttpServletRequest request) {

    Locale locale = getLocale(request);

    for (ch.ess.base.xml.locale.Locale xmlLocale : locales.getLocales()) {
      add(xmlLocale.getText().get(locale), xmlLocale.getKey());
    }

  }

}