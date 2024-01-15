package ch.ess.lbw.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "einstufungOptions")
public class EinstufungOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    add("A", "A");
    add("AB", "AB");
    add("B", "B");
    add("C", "C");
  }

}
