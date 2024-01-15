package ch.ess.cal.web.time;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class TimeProjectForm extends AbstractForm {

  private String projectNumber;
  private String name;
  private String description;
  private String customerId;
  private boolean active;
  private String hourRate;

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

  public String getCustomerId() {
    return customerId;
  }

  @ValidatorField(key = "time.customer", required = true)
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
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

  @ValidatorField(key = "time.hourRate", validators = @Validator(name="float"))
  public void setHourRate(String hourRate) {
    this.hourRate = hourRate;
  }

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	public String getProjectNumber() {
		return projectNumber;
	}

}
