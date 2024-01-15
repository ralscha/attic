package ch.ess.cal.web.app;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $
 * 
 * @spring.bean id="localeOptions" lazy-init="true"
 */
public class LocaleOptions extends AbstractOptions {

  private Map<String, String> localeData;

  /**    
   * @spring.property ref="localeData"
   */
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
