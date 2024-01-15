package ch.ess.cal.web.userconfig;

import java.util.Arrays;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.web.AbstractOptions;

public class TimezoneOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {

    String[] ids = TimeZone.getAvailableIDs();
    Arrays.sort(ids);

    for (int i = 0; i < ids.length; i++) {
      getLabelValue().add(new LabelValueBean(ids[i], ids[i]));
    }

  }

}
