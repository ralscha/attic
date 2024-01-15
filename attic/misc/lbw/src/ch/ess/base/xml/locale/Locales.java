package ch.ess.base.xml.locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.LogFactory;

import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "locales", initMethod = "init")
public class Locales {

  private List<Locale> locales;

  public Locales() {
    locales = new ArrayList<Locale>();
  }

  public List<Locale> getLocales() {
    return locales;
  }

  public int size() {
    return locales.size();
  }

  @SuppressWarnings("unchecked")
  public void init() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/locale.xml");
      if (is != null) {
        Digester digester = new Digester();
        digester.setClassLoader(getClass().getClassLoader());

        digester.addRuleSet(new LocaleRuleSet());

        locales = (List<Locale>)digester.parse(is);
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