package ch.ess.cal.web.app;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:11 $
 */
public class SensitivityOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    clear();
    addTranslate(request, SensitivityEnum.PUBLIC.getKey(), String.valueOf(SensitivityEnum.PUBLIC.getValue()));
    addTranslate(request, SensitivityEnum.PRIVATE.getKey(), String.valueOf(SensitivityEnum.PRIVATE.getValue()));
    addTranslate(request, SensitivityEnum.CONFIDENTIAL.getKey(), String
        .valueOf(SensitivityEnum.CONFIDENTIAL.getValue()));
  }

}