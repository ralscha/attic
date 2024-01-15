package ch.ess.timetracker.web;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.web.Options;

public class WeekOptions extends Options {

  public void init() {

    for (int i = 1; i <= 53; i++) {
      getLabelValue().add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
    }

  }

}
