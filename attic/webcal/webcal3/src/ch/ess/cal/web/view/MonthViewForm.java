package ch.ess.cal.web.view;

import org.apache.struts.action.ActionForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 *  
 * @struts.form name="monthViewForm"
 */
public class MonthViewForm extends ActionForm {

  private MonthBean monthBean;
  private String[] monthNames;

  //Inputforms
  private String inputMonth;
  private String inputYear;

  public MonthBean getMonthBean() {
    return monthBean;
  }

  public void setMonthBean(MonthBean monthBean) {
    this.monthBean = monthBean;
  }

  public String[] getMonthNames() {
    return monthNames;
  }

  public void setMonthNames(String[] monthNames) {
    this.monthNames = monthNames;
  }

  public String getInputMonth() {
    return inputMonth;
  }

  public void setInputMonth(String inputMonth) {
    this.inputMonth = inputMonth;
  }

  public String getInputYear() {
    return inputYear;
  }

  public void setInputYear(String inputYear) {
    this.inputYear = inputYear;
  }

}
