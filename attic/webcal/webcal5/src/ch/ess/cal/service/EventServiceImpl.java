package ch.ess.cal.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import ch.ess.base.Constants;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;

public class EventServiceImpl implements EventService {

  private final static String PARSE_DATE_PATTERN = "yyyy-MM-dd";

  private EventDao eventDao;
  private UserDao userDao;
  private CategoryDao categoryDao;

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  @Override
  public String insertEvent(EventTransportObject obj) {
    Event event = createEvent(obj);

    if (StringUtils.isBlank(obj.getEntryId())) {
      event.setUid(CalUtil.createUid(event));
    } else {
      event.setUid(obj.getEntryId());
    }
    
    eventDao.save(event);
    
    return event.getUid();
  }

  private Event createEvent(EventTransportObject obj) {

    User user = userDao.findByName(obj.getUser());
    TimeZone timeZone = user.getTimeZone();

    //Event suchen

    Event queryEvent = eventDao.findByCriteriaUnique(Restrictions.eq("uid", obj.getEntryId()));
    Event event;
    if (queryEvent != null) {
      event = queryEvent;
      event.setModificationDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    } else {
      event = new Event();
      event.setSequence(0);
      event.setPriority(0);
      event.setCreateDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    }

    event.setSubject(obj.getSubject());
    event.setLocation(obj.getLocation());
    event.setDescription(obj.getBody());

    event.getUsers().removeAll(event.getUsers());
    event.getUsers().add(user);

    if (StringUtils.isNotBlank(obj.getCategories())) {
      event.getEventCategories().removeAll(event.getEventCategories());
      String[] categories = obj.getCategories().split(",");
      for (String cat : categories) {

        Category category = categoryDao.findByCriteriaUnique(Restrictions.eq("icalKey", cat));
        if (category != null) {
          EventCategory eventCategory = new EventCategory();
          eventCategory.setCategory(category);
          eventCategory.setEvent(event);
          event.getEventCategories().add(eventCategory);
        } else {
          //find default cat
          EventCategory eventCategory = new EventCategory();
          eventCategory.setCategory(categoryDao.getDefaultCategory());
          eventCategory.setEvent(event);
          event.getEventCategories().add(eventCategory);

        }
      }
    }

    ImportanceEnum importance = StringValuedEnumReflect.getEnum(ImportanceEnum.class, obj.getImportance());
    SensitivityEnum sensitivity = StringValuedEnumReflect.getEnum(SensitivityEnum.class, obj.getSensivity());

    event.setImportance(importance);
    event.setSensitivity(sensitivity);

    boolean allDay = obj.isAllDay();

    Integer startHour = obj.getStartHour();
    Integer startMinute = obj.getStartMinute();
    Integer endHour = obj.getEndHour();
    Integer endMinute = obj.getEndMinute();
    String start = obj.getStart();
    String end = obj.getEnd();

    //Dates
    if (startHour == null && startMinute == null && endHour == null && endMinute == null) {
      allDay = true;
    }

    event.setAllDay(allDay);

    Calendar startCal;
    Calendar endCal;

    if (allDay) {
      startCal = CalUtil.string2Calendar(start, Constants.UTC_TZ, PARSE_DATE_PATTERN);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = CalUtil.string2Calendar(end, Constants.UTC_TZ, PARSE_DATE_PATTERN);
      } else {
        endCal = (Calendar)startCal.clone();
      }
      event.setEndDate(endCal.getTimeInMillis());

    } else {
      startCal = CalUtil.string2Calendar(start, startHour, startMinute, timeZone, PARSE_DATE_PATTERN);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = CalUtil.string2Calendar(end, endHour, endMinute, timeZone, PARSE_DATE_PATTERN);
        event.setEndDate(endCal.getTimeInMillis());
      } else {

        Integer h = startHour;
        Integer m = startMinute;

        if (endHour != null || endMinute != null) {
          h = endHour;
          m = endMinute;
        }

        endCal = CalUtil.string2Calendar(start, h, m, timeZone, PARSE_DATE_PATTERN);
        event.setEndDate(endCal.getTimeInMillis());
      }

    }

    if (event.getEndDate() < event.getStartDate()) {
      long temp = event.getStartDate();
      Calendar tempCal = startCal;

      event.setStartDate(event.getEndDate());
      startCal = endCal;

      event.setEndDate(temp);
      endCal = tempCal;
    }

    return event;
  }

}
