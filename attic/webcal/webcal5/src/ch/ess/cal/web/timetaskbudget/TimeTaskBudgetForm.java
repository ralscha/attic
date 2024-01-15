package ch.ess.cal.web.timetaskbudget;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.web.AbstractForm;
import ch.ess.cal.dao.TimeTaskBudgetDao;

public class TimeTaskBudgetForm extends AbstractForm {

  private String timeTaskId;
  private String timeCustomerId;
  private String timeProjectId;
  private String date;
  private String amount;
  private String comment;
  private String searchWithInactive;

  public ActionErrors validate(final TimeTaskBudgetDao timeTaskBudgetDao, final ActionMapping mapping, final HttpServletRequest request) {

    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    if (errors.isEmpty()) {
      if (StringUtils.isNotBlank(timeTaskId)) {
        if (timeTaskBudgetDao.hasProjectBudget(new Integer(timeProjectId))) {
          errors.add("timeProjectId", new ActionMessage("timeTaskBudget.project.error"));
        }
      } else {
        if (timeTaskBudgetDao.hasTaskBudget(new Integer(timeProjectId))) {
          errors.add("timeProjectId", new ActionMessage("timeTaskBudget.task.error"));
        }
      }

    }

    return errors;
  }

  public void setTimeTaskId(final String timeTaskId) {
    this.timeTaskId = timeTaskId;
  }

  public String getTimeTaskId() {
    return timeTaskId;
  }

  @ValidatorField(key = "timeTaskBudget.date", required = true, validators = @Validator(name = "date", vars = @Variable(name = "datePatternStrict", value = "dd.MM.yyyy")))
  public void setDate(final String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  @ValidatorField(key = "timeTaskBudget.amount", required = true, validators = @Validator(name = "float"))
  public void setAmount(final String amount) {
    this.amount = amount;
  }

  public String getAmount() {
    return amount;
  }

  public String getTimeCustomerId() {
    return timeCustomerId;
  }

  public void setTimeCustomerId(String timeCustomerId) {
    this.timeCustomerId = timeCustomerId;
  }

  public String getTimeProjectId() {
    return timeProjectId;
  }

  @ValidatorField(key = "time.project", required = true)
  public void setTimeProjectId(String timeProjectId) {
    this.timeProjectId = timeProjectId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

	public void setSearchWithInactive(String searchWithInactive) {
		this.searchWithInactive = searchWithInactive;
	}

	public String getSearchWithInactive() {
		return searchWithInactive;
	}
}
