package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;

import org.displaytag.decorator.ColumnDecorator;

import ch.ess.timetracker.Constants;

/**
 * @author sr
 */
public class DecimalWrapper implements ColumnDecorator {
  
  public String decorate(Object columnValue) {
    
    BigDecimal bd = (BigDecimal)columnValue;
    return Constants.DECIMAL_FORMAT.format(bd);
  }
}
