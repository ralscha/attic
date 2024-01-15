package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.ImportanceEnum;

@Option(id = "importanceOptions")
public class ImportanceOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, ImportanceEnum.NORMAL.getKey(), ImportanceEnum.NORMAL.getValue());
    addTranslate(request, ImportanceEnum.HIGH.getKey(), ImportanceEnum.HIGH.getValue());
    addTranslate(request, ImportanceEnum.LOW.getKey(), ImportanceEnum.LOW.getValue());
  }

}