package ch.ess.cal.web.event;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.web.AbstractFormListControl;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.enums.TaskReminderBeforeEnum;
import ch.ess.cal.model.BaseEvent;
import ch.ess.cal.model.Reminder;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

public class ReminderFormListControl extends AbstractFormListControl<BaseEvent, Reminder> {
    
  @Override
  public void newRowItem(ActionContext ctx, DynaBean dynaBean, Reminder reminder) {
    dynaBean.set("email", reminder.getEmail());

    dynaBean.set("minutes", reminder.getMinutesBefore());
    dynaBean.set("time", reminder.getTime());

    TimeEnum timeEnum = reminder.getTimeUnit();
    dynaBean.set("timeUnit", timeEnum);

    dynaBean.set("timeStr", reminder.getTime() + " " + translate(ctx.request(), timeEnum.getKey()));
    
    if (reminder.getTaskBefore() != null) {
      dynaBean.set("beforeStr", translate(ctx.request(), reminder.getTaskBefore().getKey()));
    }
  }

  
  @Override
  protected Reminder newModelItem(DynaBean dynaBean, BaseEvent event, Map<String, Object> parameters) {
    Reminder newReminder = new Reminder();
    newReminder.setEmail((String)dynaBean.get("email"));
    newReminder.setEvent(event);
    newReminder.setTime(((Integer)dynaBean.get("time")).intValue());
    newReminder.setTimeUnit((TimeEnum)dynaBean.get("timeUnit"));
    newReminder.setMinutesBefore(((Integer)dynaBean.get("minutes")));
    newReminder.setLocale((Locale)parameters.get("locale"));    
    TaskReminderBeforeEnum before = (TaskReminderBeforeEnum)dynaBean.get("before");
    newReminder.setTaskBefore(before);
    return newReminder;
  }
  

  public void add(final FormActionContext ctx) {

    ReminderForm reminderForm = (ReminderForm)ctx.form();
    if ((StringUtils.isNotBlank(reminderForm.getReminderEmail()) || StringUtils.isNotBlank(reminderForm
        .getReminderEmailSelect()))
        && (StringUtils.isNotBlank(reminderForm.getReminderTime()) && StringUtils.isNotBlank(reminderForm
            .getReminderTimeUnit()))) {

      Locale locale = getLocale(ctx.request());
      MessageResources messages = getResources(ctx.request());

      ActionErrors errors = new ActionErrors();

      if (StringUtils.isNotBlank(reminderForm.getReminderEmail())
          && !GenericValidator.isEmail(reminderForm.getReminderEmail())) {
        errors.add("reminderEmail", new ActionMessage("errors.email", messages.getMessage(locale, "user.eMail")));
      }

      if (!GenericValidator.isInt(reminderForm.getReminderTime())) {
        errors.add("reminderTime", new ActionMessage("errors.integer", messages.getMessage(locale,
            "event.reminder.time")));
      } else {
        int reminderTime = Integer.parseInt(reminderForm.getReminderTime());
        if (reminderTime <= 0) {
          errors.add("reminderTime", new ActionMessage("errors.positive", messages.getMessage(locale,
              "event.reminder.time")));
        }
      }

      ctx.addErrors(errors);

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

      int max = getMaxIndex(dataModel);

      String email;
      if (StringUtils.isNotBlank(reminderForm.getReminderEmail())) {
        email = reminderForm.getReminderEmail();
      } else {
        email = reminderForm.getReminderEmailSelect();
      }

      DynaBean dynaBean = new LazyDynaBean();

      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("email", email);
      dynaBean.set("dbId", null);

      TimeEnum timeEnum = StringValuedEnumReflect.getEnum(TimeEnum.class, reminderForm.getReminderTimeUnit());
      int minutes = 0;
      int time = Integer.parseInt(reminderForm.getReminderTime());

      switch (timeEnum) {
        case MINUTE :
          minutes = time;
          break;
        case HOUR :
          minutes = time * 60;
          break;
        case DAY :
          minutes = time * 60 * 24;
          break;
        case WEEK :
          minutes = time * 60 * 24 * 7;
          break;
        case MONTH :
          minutes = time * 60 * 24 * 31;
          break;
        case YEAR :
          minutes = time * 60 * 24 * 365;
          break;
      }

      dynaBean.set("minutes", new Integer(minutes));
      dynaBean.set("time", new Integer(reminderForm.getReminderTime()));
      dynaBean.set("timeUnit", timeEnum);

      dynaBean.set("timeStr", reminderForm.getReminderTime() + " " + messages.getMessage(locale, timeEnum.getKey()));
      
      if (StringUtils.isNotBlank(reminderForm.getReminderBefore())) {
        TaskReminderBeforeEnum before = StringValuedEnumReflect.getEnum(TaskReminderBeforeEnum.class, reminderForm.getReminderBefore());
        dynaBean.set("beforeStr", translate(ctx.request(), before.getKey()));
        dynaBean.set("before", before);
      } 
       
      dataModel.add(dynaBean);

      reminderForm.setReminderEmail(null);
      reminderForm.setReminderEmailSelect(null);
      reminderForm.setReminderTime(null);
      reminderForm.setReminderTimeUnit(null);      
    }
    ctx.forwardToInput();
  }
}
