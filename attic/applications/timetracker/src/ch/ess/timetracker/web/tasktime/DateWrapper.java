package ch.ess.timetracker.web.tasktime;

import java.util.Date;

import org.displaytag.decorator.ColumnDecorator;

import ch.ess.timetracker.Constants;

/**
 * @author sr
 */
public class DateWrapper implements ColumnDecorator {
  
  public String decorate(Object columnValue) {
    Date d = (Date)columnValue;
    return Constants.DATE_FORMAT.format(d);
  }
}
