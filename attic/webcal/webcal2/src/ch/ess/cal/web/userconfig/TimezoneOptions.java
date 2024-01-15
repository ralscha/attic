package ch.ess.cal.web.userconfig;

import java.util.Arrays;
import java.util.TimeZone;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

public class TimezoneOptions extends Options {

  public void init() {

    String[] ids = TimeZone.getAvailableIDs();
    Arrays.sort(ids);
    
    
    for (int i = 0; i < ids.length; i++) {
      getLabelValue().add(new LabelValueBean(ids[i], ids[i]));
    }

  }

}
