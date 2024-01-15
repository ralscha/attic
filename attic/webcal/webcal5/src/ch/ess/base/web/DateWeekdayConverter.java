package ch.ess.base.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ch.ess.base.Constants;

import com.cc.framework.adapter.RequestContext;
import com.cc.framework.convert.Converter;

public class DateWeekdayConverter implements Converter {

  @Override
  public Object getAsObject(RequestContext ctx, String value) {
    return null;
  }

  @Override
  public String getAsString(RequestContext ctx, Object value) {
    if (value != null) {
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
    String datum = df.format(value);
    
    Calendar cal = new GregorianCalendar();
    /*
    set(int year, int month, int date)
    Jan=0,Feb=1,Mar=2...
    */
    cal.set(Integer.parseInt(datum.substring(6, 10)),
    		Integer.parseInt(datum.substring(3, 5))-1,
    		Integer.parseInt(datum.substring(0, 2)));
    int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK);
    
    /*
    Sunday Day of Week 1
    Monday Day of Week 2
    Tuesday Day of Week 3
    Wednesday Day of Week 4
    Thursday Day of Week 5
    Friday Day of Week 6
    Saturday Day of Week 7
    */
    
    String[] dayofweek_de = {"","So","Mo","Di","Mi","Do","Fr","Sa"};
    String[] dayofweek_en = {"","Sun","Mon","Tue","Wed","Thu","Fru","Sat"};
    
    String language = ctx.getLocale().getLanguage();
    if(language.equalsIgnoreCase("de")){
    	return datum+" ("+dayofweek_de[DAY_OF_WEEK]+")";
    }
    else if(language.equalsIgnoreCase("en")){
    	return datum+" ("+dayofweek_en[DAY_OF_WEEK]+")";
    }
    else {
        return datum;
    }

  }
    return null;
  }

}
