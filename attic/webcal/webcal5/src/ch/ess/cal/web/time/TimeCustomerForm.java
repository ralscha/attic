package ch.ess.cal.web.time;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class TimeCustomerForm extends AbstractForm {

  private String name;
  private String description;
  private boolean active;
  private String hourRate;
  private String customerNumber;
  private String tabset;
  private String[] userIds;

  public TimeCustomerForm() {
    tabset = "general";
  }
  
  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    active = false;
  }

  public String getName() {
    return name;
  }

  @ValidatorField(key = "time.name", required = true)
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getHourRate() {
    return hourRate;
  }

  @ValidatorField(key = "time.hourRate", validators = @Validator(name = "float"))
  public void setHourRate(String hourRate) {
    this.hourRate = hourRate;
  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }
  
  public String[] getUserIds() {
    return (String[])ArrayUtils.clone(userIds);
  }

  public void setUserIds(final String[] userIds) {
    this.userIds = (String[])ArrayUtils.clone(userIds);
  }

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
}
