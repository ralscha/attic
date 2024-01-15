package ch.ess.cal.web.event;

import org.apache.struts.action.ActionForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 04.10.2003 
  * @struts.form name="eventListForm"
  */
public class EventListForm extends ActionForm {
  private String subject;
  private String categoryId;
  private String year;
  private String month; 
  
  
  public EventListForm() {
    //Calendar cal = new GregorianCalendar(Constants.UTC_TZ);
    //year = String.valueOf(cal.get(Calendar.YEAR));
    //month = String.valueOf(cal.get(Calendar.MONTH));
  }
  
  public String getCategoryId() {
    return categoryId;
  }

  public String getMonth() {
    return month;
  }

  public String getSubject() {
    return subject;
  }

  public String getYear() {
    return year;
  }

  public void setCategoryId(String string) {
    categoryId = string;
  }

  public void setMonth(String string) {
    month = string;
  }

  public void setSubject(String string) {
    subject = string;
  }

  public void setYear(String string) {
    year = string;
  }

}
