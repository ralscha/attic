package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.enums.StatusEnum;

@Option(id="statusOptions")
public class StatusOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, StatusEnum.NOTSTARTED.getKey(), StatusEnum.NOTSTARTED.getValue());
    addTranslate(request, StatusEnum.WAITING.getKey(), StatusEnum.WAITING.getValue());
    addTranslate(request, StatusEnum.DEFERRED.getKey(), StatusEnum.DEFERRED.getValue());
    addTranslate(request, StatusEnum.INPROCESS.getKey(), StatusEnum.INPROCESS.getValue());
    addTranslate(request, StatusEnum.COMPLETED.getKey(), StatusEnum.COMPLETED.getValue());
    addTranslate(request, StatusEnum.CANCELLED.getKey(), StatusEnum.CANCELLED.getValue());
  }

}