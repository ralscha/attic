package ch.ess.lbw.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "quartalOptions")
public class QuartalOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    addTranslate(request, "quartal.1", "1");
    addTranslate(request, "quartal.2", "2");
    addTranslate(request, "quartal.3", "3");
    addTranslate(request, "quartal.4", "4");
  }

}
