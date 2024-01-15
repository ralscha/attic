package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

import ch.ess.base.model.TranslateObject;


@Entity
@Proxy(lazy = false)
public class Holiday extends TranslateObject {

  private Integer monthNo;
  private Integer dayOfMonth;
  private Integer dayOfWeek;
  private Boolean afterDay;
  private Integer fromYear;
  private Integer toYear;
  private String builtin;
  private Boolean active;

  public Integer getMonthNo() {
    return this.monthNo;
  }

  public void setMonthNo(Integer month) {
    this.monthNo = month;
  }

  public Integer getDayOfMonth() {
    return this.dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Integer getDayOfWeek() {
    return this.dayOfWeek;
  }

  public void setDayOfWeek(Integer dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Boolean getAfterDay() {
    return afterDay;
  }

  public void setAfterDay(Boolean after) {
    this.afterDay = after;
  }

  public Integer getFromYear() {
    return this.fromYear;
  }

  public void setFromYear(Integer fromYear) {
    this.fromYear = fromYear;
  }

  public Integer getToYear() {
    return this.toYear;
  }

  public void setToYear(Integer toYear) {
    this.toYear = toYear;
  }

  @Column(length = 255)
  public String getBuiltin() {
    return this.builtin;
  }

  public void setBuiltin(String builtin) {
    this.builtin = builtin;
  }

  @Column(nullable = false)
  public Boolean getActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

}
