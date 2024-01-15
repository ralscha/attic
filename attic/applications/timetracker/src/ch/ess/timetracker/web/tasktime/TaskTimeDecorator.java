package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import ch.ess.common.web.ListDecorator;
import ch.ess.timetracker.Constants;

public class TaskTimeDecorator extends ListDecorator {

  private BigDecimal totalHour = Constants.BIGDECIMAL_ZERO;
  private BigDecimal totalCost = Constants.BIGDECIMAL_ZERO;
  
  
  public String getEdit() throws MalformedURLException {
    return getEditString("TaskTime");
  }

  public String getDelete() throws MalformedURLException {        
    Boolean canDelete = getBooleanProperty("canDelete");
        
    if (canDelete.booleanValue()) {    
    return getDeleteString("TaskTime", "description");
    } else {
      return getEmptyString();
    }    
  }
  
  

  public String finishRow() {
    BigDecimal total = getBigDecimalProperty("total");
    BigDecimal hours = getBigDecimalProperty("hours");
    
    totalHour = totalHour.add(hours);
    totalCost = totalCost.add(total);
    
    
    if (getViewIndex() == ((List) getDecoratedObject()).size() - 1) {    
      StringBuffer buffer = new StringBuffer(200);    
      buffer.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td align=\"right\"><hr noshade size=\"1\"></td><td>&nbsp;</td><td align=\"right\"><hr noshade size=\"1\"></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
      buffer.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalHour)+"</b></td><td>&nbsp;</td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalCost)+"</b></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
  
      return buffer.toString();
    }
    return null;    
    
  }
  
}
