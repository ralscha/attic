package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "weekOptions")
public class WeekOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {

    for (int i = 1; i <= 53; i++) {
      add(String.valueOf(i), String.valueOf(i));
    }

  }

}
