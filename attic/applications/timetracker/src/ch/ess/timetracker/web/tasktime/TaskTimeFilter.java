package ch.ess.timetracker.web.tasktime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.resource.UserConfig;
import ch.ess.timetracker.web.WebUtils;

/**
 * @author sr
 */
public class TaskTimeFilter {

  private Date fromDate = null;
  private Date toDate = null;
  private int start;
  private String customerId = null;
  private String projectId = null;
  private String taskId = null;
  private Collection customers = null;
  private Long userId = null;
  
  public Long getUserId() {
    return userId;
  }
  
  public Collection getCustomers() {
    return customers;
  }
  
  public String getCustomerId() {
    return customerId;
  }
  
  public String getProjectId() {
    return projectId;
  }
  
  public String getTaskId() {
    return taskId;
  }
  
  public int getStart() {
    return start;
  }
  
  public Date getFromDate() {
    return fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public static TaskTimeFilter createFilter() throws HibernateException {
    
    WebContext ctx = WebContext.currentContext();    
    MapForm searchForm = (MapForm)ctx.getForm();
        
    User user = WebUtils.getUser(ctx.getRequest());
    UserConfig config = UserConfig.getUserConfig(user);
    int start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
    
    Date from = null;
    Date to = null;
    
    String yearStr = (String)searchForm.getValue("year");    
    if (!GenericValidator.isBlankOrNull(yearStr)) {
      Integer yearNo = new Integer(yearStr);
      
      String weekStr = (String)searchForm.getValue("week");
      String monthStr = (String)searchForm.getValue("month");
            
      if (!GenericValidator.isBlankOrNull(weekStr)) {
        
        Integer weekNo = new Integer(weekStr);
        searchForm.getValueMap().remove("month");
        

        Calendar c = new GregorianCalendar(yearNo.intValue(), Calendar.JANUARY, 1);
        c.set(Calendar.WEEK_OF_YEAR, weekNo.intValue());
        c.set(Calendar.DAY_OF_WEEK, start);

        from = c.getTime();
        c.add(Calendar.DATE, +6);
        to = c.getTime();

        
        
      } else if (!GenericValidator.isBlankOrNull(monthStr)) {
        Integer monthNo = new Integer(monthStr);

        Calendar c = new GregorianCalendar(yearNo.intValue(), monthNo.intValue(), 1);
        from = c.getTime();
        
        c = new GregorianCalendar(yearNo.intValue(), monthNo.intValue(), c.getActualMaximum(Calendar.DATE));
        to = c.getTime();
      } else {

        Calendar c = new GregorianCalendar(yearNo.intValue(), Calendar.JANUARY, 1);
        from = c.getTime();
        
        c = new GregorianCalendar(yearNo.intValue(), Calendar.DECEMBER, 31);
        to = c.getTime();        
        
      }
      
      searchForm.getValueMap().remove("from");
      searchForm.getValueMap().remove("to");
      
    } else {
      searchForm.getValueMap().remove("year");
      searchForm.getValueMap().remove("month");
      searchForm.getValueMap().remove("week");

      
      String fromStr = (String)searchForm.getValue("from");
      String toStr = (String)searchForm.getValue("to");
      

      
      if (!GenericValidator.isBlankOrNull(fromStr)) {
        try {
          from = ch.ess.timetracker.Constants.DATE_FORMAT.parse(fromStr);
        } catch (ParseException e) {
          //no action
        }
      }

      if (!GenericValidator.isBlankOrNull(toStr)) {      
        try {
          to = ch.ess.timetracker.Constants.DATE_FORMAT.parse(toStr);
        } catch (ParseException e1) {
          //no Action
        }
      }
      
      if (from == null) {
        searchForm.getValueMap().remove("from");
      }
      if (to == null) {
        searchForm.getValueMap().remove("to");
      }      
      
    }
    
    
    TaskTimeFilter ttf = new TaskTimeFilter();
    ttf.start = start;
    ttf.fromDate = from;
    ttf.toDate = to;
    
    ttf.customerId = (String)searchForm.getValue("customerId");
    ttf.projectId = (String)searchForm.getValue("projectId");
    ttf.taskId = (String)searchForm.getValue("taskId");
    
   
    if (!ctx.getRequest().isUserInRole("admin")) {      
      ttf.customers = user.getCustomers();
      ttf.userId = user.getId();
    }  
    
    
    return ttf;
  }
  
}
