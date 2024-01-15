package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import ch.ess.common.web.ListDecorator;
import ch.ess.timetracker.Constants;

public class TaskTimeReportDecorator extends ListDecorator {

  private BigDecimal totalProjectHour = Constants.BIGDECIMAL_ZERO;
  private BigDecimal totalProjectCost = Constants.BIGDECIMAL_ZERO;
  
  private BigDecimal totalHour = Constants.BIGDECIMAL_ZERO;
  private BigDecimal totalCost = Constants.BIGDECIMAL_ZERO;
  

  public String finishRow() {
    
    BigDecimal hour = getBigDecimalProperty("totalHours");
    BigDecimal cost = getBigDecimalProperty("totalCost");
    
    totalHour = totalHour.add(hour);
    totalCost = totalCost.add(cost);
    
    StringBuffer buffer = new StringBuffer();
    
    //project total
    String nextProject = "";

    totalProjectHour = totalProjectHour.add(hour);
    totalProjectCost = totalProjectCost.add(cost);
    

    if (getViewIndex() == ((List) getDecoratedObject()).size() - 1) { 
      nextProject = "XXXXXX";
    } else {
      DynaBean d = (DynaBean)((List) getDecoratedObject()).get(getViewIndex() + 1);
      nextProject = (String)d.get("project");
    }

    if (!nextProject.equals(getStringProperty("project"))) {    
      buffer.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align=\"right\"><hr noshade size=\"1\"></td><td align=\"right\"><hr noshade size=\"1\"></td></tr>");
      buffer.append("<tr><td>&nbsp;</td><td><b>Total "+getStringProperty("project")+"</b></td><td>&nbsp;</td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalProjectHour)+"</b></td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalProjectCost)+"</b></td></tr>");
      buffer.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
      
      totalProjectHour = Constants.BIGDECIMAL_ZERO;
      totalProjectCost = Constants.BIGDECIMAL_ZERO;
    }    

    
    if (getViewIndex() == ((List) getDecoratedObject()).size() - 1) {    
      buffer.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align=\"right\"><hr noshade size=\"1\"></td><td align=\"right\"><hr noshade size=\"1\"></td></tr>");
      buffer.append("<tr><td><b>Total</b></td><td>&nbsp;</td><td>&nbsp;</td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalHour)+"</b></td><td align=\"right\"><b>"+Constants.DECIMAL_FORMAT.format(totalCost)+"</b></td></tr>");
    }
    
    
    return buffer.toString();
       
    
  }
  
  
}
