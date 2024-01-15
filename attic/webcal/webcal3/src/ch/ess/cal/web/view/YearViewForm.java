package ch.ess.cal.web.view;

import org.apache.struts.action.ActionForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $
 *  
 * @struts.form name="yearViewForm"
 */
public class YearViewForm extends ActionForm {

  private MonthBean[] monthBean;
  private String inputYear;

  public String getInputYear() {
    return inputYear;
  }

  public void setInputYear(String inputYear) {
    this.inputYear = inputYear;
  }

  public MonthBean[] getMonthBean() {
    return monthBean;
  }

  public void setMonthBean(MonthBean[] monthBeans) {
    this.monthBean = monthBeans;
  }
}
