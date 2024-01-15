package ch.ess.cal.web.timetaskbudget;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskBudgetDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.TimeTask;
import ch.ess.cal.model.TimeTaskBudget;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$time")
public class TimeTaskBudgetEditAction extends AbstractEditAction<TimeTaskBudget> {

  private TimeTaskDao timeTaskDao;
  private TimeProjectDao timeProjectDao;

  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
    this.timeTaskDao = timeTaskDao;
  }

  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }

  @Override
  protected ActionErrors callValidate(final ActionContext ctx, final ActionForm form) {
	  String amountForm = ((TimeTaskBudgetForm) form).getAmount();
	  int idx = amountForm.indexOf(".");
	  if(idx>=0){
		  String amount = amountForm.substring(0, idx).replace("'", "");
		  ((TimeTaskBudgetForm) form).setAmount(amount);		  
	  }
    TimeTaskBudgetForm timeTaskBudgetForm = (TimeTaskBudgetForm)ctx.form();
    return timeTaskBudgetForm.validate((TimeTaskBudgetDao)getDao(), ctx.mapping(), ctx.request());
  }

  @Override
  public void formToModel(final ActionContext ctx, TimeTaskBudget timeTaskBudget) {

    TimeTaskBudgetForm timeTaskBudgetForm = (TimeTaskBudgetForm)ctx.form();

    DateFormat df = new SimpleDateFormat(Constants.getParseDateFormatPattern());

    if (StringUtils.isNotBlank(timeTaskBudgetForm.getTimeTaskId())) {
      TimeTask timeTask = timeTaskDao.findById(timeTaskBudgetForm.getTimeTaskId());
      timeTaskBudget.setTimeTask(timeTask);
      timeTaskBudget.setTimeProject(timeTask.getTimeProject());
    } else {
      timeTaskBudget.setTimeTask(null);
      timeTaskBudget.setTimeProject(timeProjectDao.findById(timeTaskBudgetForm.getTimeProjectId()));
    }

    timeTaskBudget.setComment(timeTaskBudgetForm.getComment());

    if (StringUtils.isNotBlank(timeTaskBudgetForm.getDate())) {
      try {
        timeTaskBudget.setDate(df.parse(timeTaskBudgetForm.getDate()));
      } catch (ParseException e) {
        //no action
      }
    } else {
      timeTaskBudget.setDate(null);
    }
    if (StringUtils.isNotBlank(timeTaskBudgetForm.getAmount())) {
      timeTaskBudget.setAmount(new BigDecimal(timeTaskBudgetForm.getAmount()));
      DecimalFormat decFormat = new DecimalFormat("###,##0.00");
      timeTaskBudgetForm.setAmount(decFormat.format(Double.parseDouble(timeTaskBudgetForm.getAmount())));
    } else {
      timeTaskBudget.setAmount(null);
    }

  }

  @Override
  public void modelToForm(final ActionContext ctx, final TimeTaskBudget timeTaskBudget) {

    TimeTaskBudgetForm timeTaskBudgetForm = (TimeTaskBudgetForm)ctx.form();

    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());

    if (timeTaskBudget.getTimeTask() != null) {
      timeTaskBudgetForm.setTimeTaskId(timeTaskBudget.getTimeTask().getId().toString());
      timeTaskBudgetForm.setTimeProjectId(timeTaskBudget.getTimeTask().getTimeProject().getId().toString());
      timeTaskBudgetForm.setTimeCustomerId(timeTaskBudget.getTimeTask().getTimeProject().getTimeCustomer().getId().toString());
    } else {
      timeTaskBudgetForm.setTimeTaskId(null);
      timeTaskBudgetForm.setTimeProjectId(timeTaskBudget.getTimeProject().getId().toString());
      timeTaskBudgetForm.setTimeCustomerId(timeTaskBudget.getTimeProject().getTimeCustomer().getId().toString());
    }
    if (timeTaskBudget.getDate() != null) {
      timeTaskBudgetForm.setDate(df.format(timeTaskBudget.getDate()));
    } else {
      timeTaskBudgetForm.setDate(null);
    }
    if (timeTaskBudget.getAmount() != null) {
      timeTaskBudgetForm.setAmount(Constants.TWO_DECIMAL_FORMAT.format(timeTaskBudget.getAmount()));
    } else {
      timeTaskBudgetForm.setAmount(null);
    }

    timeTaskBudgetForm.setComment(timeTaskBudget.getComment());
  }

}
