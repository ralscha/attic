package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.PosEnum;

@Option(id = "posOptions")
public class PosOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, PosEnum.FIRST.getKey(), PosEnum.FIRST.getValue());
    addTranslate(request, PosEnum.SECOND.getKey(), PosEnum.SECOND.getValue());
    addTranslate(request, PosEnum.THIRD.getKey(), PosEnum.THIRD.getValue());
    addTranslate(request, PosEnum.FOURTH.getKey(), PosEnum.FOURTH.getValue());
    addTranslate(request, PosEnum.LAST.getKey(), PosEnum.LAST.getValue());
  }

}