package ch.ess.cal.web.options;

import java.util.Arrays;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "timezoneOptions")
public class TimezoneOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {

    String[] ids = TimeZone.getAvailableIDs();
    Arrays.sort(ids);

    for (String element : ids) {
      add(new LabelValueBean(element, element));
    }

  }

}
