package ch.ess.base.web;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $
 * 
 * @spring.bean id="localeOptions" lazy-init="true" autowire="byName"
 */
public class LocaleOptions extends AbstractOptions {

  private Map<String, String> localeData;

  public void setLocaleData(Map<String, String> localeData) {
    this.localeData = localeData;
  }

  @Override
  public void init(final HttpServletRequest request) {

    for (Iterator<Map.Entry<String, String>> it = localeData.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, String> entry = it.next();
      addTranslate(request, entry.getValue(), entry.getKey());
    }

  }

}