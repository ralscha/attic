package ch.ess.cal.web.view;

import org.apache.struts.action.ActionForm;


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
