package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.SensitivityEnum;

@Option(id="sensitivityOptions")
public class SensitivityOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, SensitivityEnum.PUBLIC.getKey(), SensitivityEnum.PUBLIC.getValue());
    addTranslate(request, SensitivityEnum.PRIVATE.getKey(),SensitivityEnum.PRIVATE.getValue());
    addTranslate(request, SensitivityEnum.CONFIDENTIAL.getKey(), SensitivityEnum.CONFIDENTIAL.getValue());
  }

}