package ch.ess.cal.web.time;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class TimeTaskForm extends AbstractForm {

  private String name;
  private String description;
  private String projectId;
  private String customer;
  private String hourRate;
  private boolean active;

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

  public String getProjectId() {
    return projectId;
  }

  @ValidatorField(key = "time.project", required = true)
  public void setProjectId(String customerId) {
    this.projectId = customerId;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
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

}
