package ch.ess.cal.web.holiday;

import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.model.Holiday;
import ch.ess.cal.service.HolidayRegistry;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@Role("$admin")
public class HolidayEditAction extends AbstractEditAction<Holiday> {

  private HolidayRegistry holidayRegistry;
  private TranslationService translationService;

  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    HolidayForm holidayForm = (HolidayForm)form;

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    holidayForm.setEntry(translationService.getEmptyNameEntry(messages, locale));
    holidayForm.setActive(true);
  }

  @Override
  public void formToModel(final ActionContext ctx, Holiday holiday) {
    
    HolidayForm holidayForm = (HolidayForm)ctx.form();

    if (GenericValidator.isBlankOrNull(holidayForm.getBuiltin())) {
      if (GenericValidator.isInt(holidayForm.getMonthNo())) {
        holiday.setMonthNo(new Integer(holidayForm.getMonthNo()));
      } else {
        holiday.setMonthNo(null);
      }
      if (GenericValidator.isInt(holidayForm.getDayOfMonth())) {
        holiday.setDayOfMonth(new Integer(holidayForm.getDayOfMonth()));
      } else {
        holiday.setDayOfMonth(null);
      }
      if (GenericValidator.isInt(holidayForm.getDayOfWeek())) {
        holiday.setDayOfWeek(new Integer(holidayForm.getDayOfWeek()));
      } else {
        holiday.setDayOfWeek(null);
      }
      holiday.setAfterDay(Boolean.valueOf(holidayForm.isAfter()));
      if (GenericValidator.isInt(holidayForm.getFromYear())) {
        holiday.setFromYear(new Integer(holidayForm.getFromYear()));
      } else {
        holiday.setFromYear(null);
      }
      if (GenericValidator.isInt(holidayForm.getToYear())) {
        holiday.setToYear(new Integer(holidayForm.getToYear()));
      } else {
        holiday.setToYear(null);
      }
      holiday.setBuiltin(null);
    } else {
      holiday.setBuiltin(holidayForm.getBuiltin());
    }

    holiday.setActive(Boolean.valueOf(holidayForm.isActive()));
    translationService.addTranslation(holiday, holidayForm.getEntry());

  }

  @Override
  public void modelToForm(final ActionContext ctx, final Holiday holiday) {
    
    HolidayForm holidayForm = (HolidayForm)ctx.form();

    if (holiday.getMonthNo() != null) {
      holidayForm.setMonthNo(holiday.getMonthNo().toString());
    } else {
      holidayForm.setMonthNo(null);
    }
    if (holiday.getDayOfMonth() != null) {
      holidayForm.setDayOfMonth(holiday.getDayOfMonth().toString());
    } else {
      holidayForm.setDayOfMonth(null);
    }
    if (holiday.getDayOfWeek() != null) {
      holidayForm.setDayOfWeek(holiday.getDayOfWeek().toString());
    } else {
      holidayForm.setDayOfWeek(null);
    }
    if (holiday.getAfterDay() != null) {
      holidayForm.setAfter(holiday.getAfterDay());
    } else {
      holidayForm.setAfter(false);
    }
    if (holiday.getFromYear() != null) {
      holidayForm.setFromYear(holiday.getFromYear().toString());
    } else {
      holidayForm.setFromYear(null);
    }
    if (holiday.getToYear() != null) {
      holidayForm.setToYear(holiday.getToYear().toString());
    } else {
      holidayForm.setToYear(null);
    }
    if (holiday.getActive() != null) {
      holidayForm.setActive(holiday.getActive());
    } else {
      holidayForm.setActive(false);
    }

    holidayForm.setBuiltin(holiday.getBuiltin());

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    holidayForm.setEntry(translationService.getNameEntry(messages, locale, holiday));

  }

  @Override
  protected void afterSave(FormActionContext ctx, Holiday holiday) {
    holidayRegistry.init();
  }
  
  

}