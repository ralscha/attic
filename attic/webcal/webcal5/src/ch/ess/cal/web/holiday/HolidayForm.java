package ch.ess.cal.web.holiday;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.base.web.AbstractForm;
import ch.ess.base.web.NameEntry;


public class HolidayForm extends AbstractForm {

  private String monthNo;
  private String dayOfMonth;
  private String dayOfWeek;
  private boolean after;
  private String fromYear;
  private String toYear;
  private boolean active;
  private String builtin;
  private NameEntry[] entries;

  @Override
  public void reset(final ActionMapping actionMapping, final HttpServletRequest request) {
    after = false;
    active = false;
    builtin = null;
  }

  public NameEntry[] getEntry() {
    return (NameEntry[])ArrayUtils.clone(entries);
  }

  public void setEntry(final NameEntry[] entries) {
    this.entries = (NameEntry[])ArrayUtils.clone(entries);
  }

  public void setEntry(final int index, final NameEntry entry) {
    entries[index] = entry;
  }

  public NameEntry getEntry(final int index) {
    return this.entries[index];
  }

  public String getMonthNo() {
    return monthNo;
  }

  @ValidatorField(key = "time.month", validators = {
      @Validator(name = "integer"),
      @Validator(name = "positive"),
      @Validator(name = "validwhen", vars = @Variable(name = "test", value = "((builtin != null) or (*this* != null))"))})  
  public void setMonthNo(final String monthNo) {
    this.monthNo = monthNo;
  }

  public String getDayOfMonth() {
    return dayOfMonth;
  }

  @ValidatorField(key = "holiday.dayOfMonth", validators = {
      @Validator(name = "integer"),
      @Validator(name = "positive"),
      @Validator(name = "validwhen", vars = @Variable(name = "test", value = "((builtin != null) or (*this* != null))"))})  
  
  public void setDayOfMonth(final String dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(final String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public boolean isAfter() {
    return after;
  }

  public void setAfter(final boolean after) {
    this.after = after;
  }

  public String getFromYear() {
    return fromYear;
  }

  public void setFromYear(final String fromYear) {
    this.fromYear = fromYear;
  }

  public String getToYear() {
    return toYear;
  }

  public void setToYear(final String toYear) {
    this.toYear = toYear;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public String getBuiltin() {
    return builtin;
  }

  public void setBuiltin(String builtin) {
    this.builtin = builtin;
  }
}
