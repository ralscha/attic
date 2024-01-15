package ch.ess.cal.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import ch.ess.base.Constants;

public class TimeMonthViewForm extends ActionForm {

  private String from;
  private String to;
  private Date fromDate;
  private Date toDate;
  private String timeCustomerId;
  private String timeProjectId;
  private String timeTaskId;
  private String userId;
  private String groupId;
  private String week;
  private String month;
  private String year;
  private String scale;
  private String listType;
  private String fullTextSearch;
  private String searchWithInactive;
  private String searchWithCharges;
  private String searchWithActivity;
  private String searchWithComment;
  private String searchWithBudget;
  private String searchWithHour;
  private String searchWithCost;
  private String dateDailyReport;
  private String dateActualWeek;
  private String userLoggedIn;
  private String dailySum;
  private String grandTotal;

  public void clear() {
    from = null;
    to = null;
    fromDate = null;
    toDate = null;
    timeCustomerId = null;
    timeProjectId = null;
    timeTaskId = null;
    userId = null;
    groupId = null;
    week = null;
    month = null;
    year = null;
    scale = null;
    listType = null;
    fullTextSearch = null;
    searchWithInactive = null;
    searchWithCharges = null;
    searchWithActivity = null;
    searchWithComment = null;
    searchWithBudget = null;
    searchWithHour = null;
    searchWithCost = null;
  }
  
  public String getTimeCustomerId() {
    return timeCustomerId;
  }

  public void setTimeCustomerId(final String customerId) {
    this.timeCustomerId = customerId;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(final String from) {
    this.from = from;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(final String month) {
    this.month = month;
  }

  public String getTimeProjectId() {
    return timeProjectId;
  }

  public void setTimeProjectId(final String projectId) {
    this.timeProjectId = projectId;
  }

  public String getTimeTaskId() {
    return timeTaskId;
  }

  public void setTimeTaskId(final String taskId) {
    this.timeTaskId = taskId;
  }

  public String getTo() {
    return to;
  }

  public void setTo(final String to) {
    this.to = to;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(final String groupId) {
    this.groupId = groupId;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(final String week) {
    this.week = week;
  }

  public String getYear() {
    return year;
  }

  public void setYear(final String year) {
    this.year = year;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(final Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(final Date toDate) {
    this.toDate = toDate;
  }

  public String getScale() {
    return scale;
  }

  public void setScale(final String scale) {
    this.scale = scale;
  }

  public String getListType() {
    return listType;
  }

  public void setListType(String listTyp) {
    this.listType = listTyp;
  }
  
	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setSearchWithInactive(String searchWithInactive) {
		this.searchWithInactive = searchWithInactive;
	}

	public String getSearchWithInactive() {
		return searchWithInactive;
	}

	public void setSearchWithCharges(String searchWithCharges) {
		this.searchWithCharges = searchWithCharges;
	}

	public String getSearchWithCharges() {
		return searchWithCharges;
	}

	public void setSearchWithActivity(String searchWithActivity) {
		this.searchWithActivity = searchWithActivity;
	}

	public String getSearchWithActivity() {
		return searchWithActivity;
	}

	public void setSearchWithComment(String searchWithComment) {
		this.searchWithComment = searchWithComment;
	}

	public String getSearchWithComment() {
		return searchWithComment;
	}

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    Locale locale = RequestUtils.getUserLocale(request, null);
    MessageResources messages = ((MessageResources)request.getAttribute(Globals.MESSAGES_KEY));

    if ("timeList".equals(listType)) {
      //week: clear month, year required
      //month: clear week, year required
      //year: year required
      if (StringUtils.isNotBlank(week)) {
        if (StringUtils.isBlank(year)) {
          errors.add("year", new ActionMessage("errors.required", messages.getMessage(locale, "calendar.year")));
        } else {
          month = null;
        }
      } else  if (StringUtils.isNotBlank(month)) {
        if (StringUtils.isBlank(year)) {
          errors.add("year", new ActionMessage("errors.required", messages.getMessage(locale, "calendar.year")));
        } else {
          week = null;
        }
      }

  
      
      
      
      
    } else {
      //scale required
  
      //scale: day        required: week/year or month/year or year
      //scale: week       required: week/year or month/year or year
      //scale: month      required: month/year or year
      //scale: trimester  required: year
      //scale: semester   required: year
      //scale: year       required: year
  
      if (StringUtils.isBlank(getScale())) {
        errors.add("scale", new ActionMessage("errors.required", messages.getMessage(locale, "tasktime.scale")));
      }
  
      if ( StringUtils.isBlank(getYear()) && ( StringUtils.isBlank(getFrom()) && StringUtils.isBlank(getTo()) ) ) {
        errors.add("year", new ActionMessage("errors.required", messages.getMessage(locale, "calendar.year")));
      }
    }
    return errors;
  }

  public void calcTime(final int firstDayOfWeek) {

    fromDate = null;
    toDate = null;

    if (StringUtils.isBlank(from) && (StringUtils.isBlank(to))) {

      if (StringUtils.isNotBlank(year)) {
        Integer yearNo = new Integer(year);

        if (StringUtils.isNotBlank(week)) {

          Integer weekNo = new Integer(week);
          month = null;

          Calendar c = new GregorianCalendar(yearNo.intValue(), Calendar.JANUARY, 1);   
          c.setFirstDayOfWeek(firstDayOfWeek);
          c.setMinimalDaysInFirstWeek(4);
          c.set(Calendar.WEEK_OF_YEAR, weekNo.intValue());
          c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);

          fromDate = c.getTime();
          c.add(Calendar.DATE, +6);
          toDate = c.getTime();

        } else if (StringUtils.isNotBlank(month)) {
          Integer monthNo = new Integer(month);

          Calendar c = new GregorianCalendar(yearNo.intValue(), monthNo.intValue(), 1);
          fromDate = c.getTime();

          c = new GregorianCalendar(yearNo.intValue(), monthNo.intValue(), c.getActualMaximum(Calendar.DATE));
          toDate = c.getTime();

        } else {

          Calendar c = new GregorianCalendar(yearNo.intValue(), Calendar.JANUARY, 1);
          fromDate = c.getTime();

          c = new GregorianCalendar(yearNo.intValue(), Calendar.DECEMBER, 31);
          toDate = c.getTime();

        }

      }

    } else {
      week = null;
      month = null;
      year = null;

      SimpleDateFormat format = new SimpleDateFormat(Constants.getParseDateFormatPattern());

      if (StringUtils.isNotBlank(from)) {
        try {
          fromDate = format.parse(from);
        } catch (ParseException e) {
          //no action
        }
      }

      if (StringUtils.isNotBlank(to)) {
        try {
          toDate = format.parse(to);
        } catch (ParseException e1) {
          //no Action
        }
      }

    }

  }

	public void setDateDailyReport(String dateDailyReport) {
		this.dateDailyReport = dateDailyReport;
	}
	
	public String getDateDailyReport() {
		return dateDailyReport;
	}

	public void setDateActualWeek(Integer dateActualWeek) {
		this.dateActualWeek = dateActualWeek.toString();
	}

	public String getDateActualWeek() {
		return dateActualWeek;
	}

	public void setUserLoggedIn(String userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

	public String getUserLoggedIn() {
		return userLoggedIn;
	}

	public void setDailySum(String dailySum) {
		this.dailySum = dailySum;
	}

	public String getDailySum() {
		return dailySum;
	}

	public String getSearchWithBudget() {
		return searchWithBudget;
	}

	public void setSearchWithBudget(String searchWithBudget) {
		this.searchWithBudget = searchWithBudget;
	}

	public String getSearchWithHour() {
		return searchWithHour;
	}

	public void setSearchWithHour(String searchWithHour) {
		this.searchWithHour = searchWithHour;
	}

	public String getSearchWithCost() {
		return searchWithCost;
	}

	public void setSearchWithCost(String searchWithCost) {
		this.searchWithCost = searchWithCost;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

}