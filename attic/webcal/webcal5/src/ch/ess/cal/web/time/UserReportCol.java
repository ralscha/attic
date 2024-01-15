package ch.ess.cal.web.time;

import java.math.BigDecimal;

public class UserReportCol {
  private final static BigDecimal ZERO = new BigDecimal(0);
  
  private String property;
  private String title;
  private boolean holiday;
  private boolean weekend;
  private BigDecimal total;

  private boolean end;
  private BigDecimal weekTotal;

  public UserReportCol(final String property, final String title) {
    this(property, title, false, false, false);
  }

  public UserReportCol(final String property, final String title, final boolean holiday, final boolean weekend,
      boolean end) {
    this.property = property;
    this.title = title;
    this.holiday = holiday;
    this.weekend = weekend;
    this.end = end;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(final String property) {
    this.property = property;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public boolean isHoliday() {
    return holiday;
  }

  public void setHoliday(final boolean holiday) {
    this.holiday = holiday;
  }

  public boolean isWeekend() {
    return weekend;
  }

  public void setWeekend(final boolean weekend) {
    this.weekend = weekend;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getWeekTotal() {
    if (weekTotal != null) {
      if (weekTotal.compareTo(ZERO) == 0) {
        return null;
      }
    }
    return weekTotal;
  }

  public void setWeekTotal(BigDecimal weekTotal) {
    this.weekTotal = weekTotal;
  }

  public boolean isEnd() {
    return end;
  }

  public void setEnd(boolean end) {
    this.end = end;
  }
}