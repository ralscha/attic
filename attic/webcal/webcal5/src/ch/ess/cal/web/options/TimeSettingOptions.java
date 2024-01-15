package ch.ess.cal.web.options;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "timeSettingOptions")
public class TimeSettingOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
	  
	  String language = request.getLocale().getLanguage();
	  
	  if(language.equalsIgnoreCase("en")) {
		  add(new LabelValueBean("actual day", "1"));
		  add(new LabelValueBean("actual week", "2"));
		  add(new LabelValueBean("actuel month", "3"));
		  add(new LabelValueBean("actuel year", "4"));
	  }
	  else {
		  add(new LabelValueBean("Aktueller Tag", "1"));
		  add(new LabelValueBean("Aktuelle Woche", "2"));
		  add(new LabelValueBean("Aktueller Monat", "3"));
		  add(new LabelValueBean("Aktuelles Jahr", "4"));
	  }
  }
}