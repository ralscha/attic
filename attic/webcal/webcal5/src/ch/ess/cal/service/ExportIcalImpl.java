package ch.ess.cal.service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.CategoryList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.TransparencyEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "exportIcalImpl", autowire = Autowire.BYTYPE)
public class ExportIcalImpl implements ExportIcal {

  private EventDao eventDao;
  private UserDao userDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }
  
  
  public String exportIcal(String username, String password, long start, long end) {

    String encryptedPassword = DigestUtils.shaHex(password);
    User user = userDao.findByNameAndToken(username, encryptedPassword);
    
    if (user != null) {      
      net.fortuna.ical4j.model.Calendar calendar = exportIcal(user, start, end);
      
      StringWriter stringWriter = new StringWriter();
      CalendarOutputter outputter = new CalendarOutputter();
      try {
        outputter.output(calendar, stringWriter);
        return stringWriter.toString();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ValidationException e) {
        e.printStackTrace();
      }
      

    }   
    
    return null;
    
//    ImportStatus status = new ImportStatus();
//    status.setNoOfInserted(0);
//    status.setNoOfUpdated(0);
//    status.setStatus(1);
//    status.setStatusDescription("login failed");
//    return status;
  }
  
  public net.fortuna.ical4j.model.Calendar exportIcal(User user, long start, long end) {
    
    //ical header
    net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
    calendar.getProperties().add(new ProdId("-//ESS//WebCal 5.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);
    calendar.getProperties().add(Method.PUBLISH);         
    
    Calendar firstDay = new GregorianCalendar();
    firstDay.setTimeInMillis(start);
    
    Calendar lastDay = new GregorianCalendar();
    lastDay.setTimeInMillis(end);
    
    //read all events for this user and write ical
    List<Event> events = eventDao.getUserNormalEvents(user, firstDay, lastDay);
    for (Event event : events) {
      
      VEvent vevent = createVEvent(event, null);      
      calendar.getComponents().add(vevent);
      
    }

    List<Object[]> recurEvents = eventDao.getUserRecurEvents(user, firstDay, lastDay);
    for (Object[] obj : recurEvents) {

      Event event = (Event)obj[0];
      Recurrence recur = (Recurrence)obj[1];
      
      VEvent vevent = createVEvent(event, recur);      
      calendar.getComponents().add(vevent);

    }
    
    try {
      calendar.validate(true);
    } catch (ValidationException e) {
      LogFactory.getLog(getClass()).error("exportIcal", e);
    }
        
    return calendar;
  }

  
  private VEvent createVEvent(Event event, Recurrence recur) {
    VEvent vevent = new VEvent();
          
    if (StringUtils.isNotBlank(event.getSubject())) {
      Summary summary = new Summary(event.getSubject());
      vevent.getProperties().add(summary);
    }
    
    if (StringUtils.isNotBlank(event.getLocation())) {
      Location location = new Location(event.getLocation());
      vevent.getProperties().add(location);
    }
    
    if (StringUtils.isNotBlank(event.getDescription())) {
      Description description = new Description(event.getDescription());
      vevent.getProperties().add(description);
    }

    vevent.getProperties().add(new Uid(event.getUid()));
    
    if (event.isAllDay()) {
      net.fortuna.ical4j.model.Date start = new net.fortuna.ical4j.model.Date(event.getStartDate());
      net.fortuna.ical4j.model.Date end = new net.fortuna.ical4j.model.Date(event.getEndDate() + 24*60*60*1000);
            
      DtStart dtStart = new DtStart(start);
      dtStart.getParameters().add(Value.DATE);
      vevent.getProperties().add(dtStart);
      
      DtEnd dtEnd = new DtEnd(end);
      dtEnd.getParameters().add(Value.DATE);
      vevent.getProperties().add(dtEnd);      
    } else {
      DateTime start = new DateTime(true);
      start.setTime(event.getStartDate());
      
      DateTime end = new DateTime(true);
      end.setTime(event.getEndDate());
      
      vevent.getProperties().add(new DtStart(start));
      vevent.getProperties().add(new DtEnd(end));
    }
    
    DateTime created = new DateTime(true);
    created.setTime(event.getCreateDate());
    vevent.getProperties().add(new DtStamp(created));
    
    
    if (event.getModificationDate() != null) {
      DateTime lastModified = new DateTime(true);
      lastModified.setTime(event.getModificationDate());
      vevent.getProperties().add(new LastModified(lastModified));  
    } 
        
    if (event.getTransparency() != null) {
      if (event.getTransparency() == TransparencyEnum.OPAQUE) {
        vevent.getProperties().add(Transp.OPAQUE);
      } else {
        vevent.getProperties().add(Transp.TRANSPARENT);
      }
    }
    
    if (event.getSensitivity() == SensitivityEnum.CONFIDENTIAL) {
      vevent.getProperties().add(Clazz.CONFIDENTIAL);
    } else if (event.getSensitivity() == SensitivityEnum.PRIVATE) {
      vevent.getProperties().add(Clazz.PRIVATE);
    } else {
      vevent.getProperties().add(Clazz.PUBLIC);
    }
    
    Sequence sequence = new Sequence(event.getSequence());
    vevent.getProperties().add(sequence);
    
    Priority priority = new Priority(event.getPriority());
    vevent.getProperties().add(priority);
    
    if (event.getImportance() != null) {
      XProperty property = new XProperty("X-MICROSOFT-CDO-IMPORTANCE");
      if (event.getImportance() == ImportanceEnum.HIGH) {
        property.setValue("2");
      } else if (event.getImportance() == ImportanceEnum.LOW) {
        property.setValue("0");
      } else {
        property.setValue("1");
      }
      vevent.getProperties().add(property);
    }
    
    Set<EventCategory> categories = event.getEventCategories();
    CategoryList categoryList = new CategoryList();
    for (EventCategory category : categories) {
      categoryList.add(category.getCategory().getIcalKey());
    }
    Categories categoriesProperty = new Categories(categoryList);
    vevent.getProperties().add(categoriesProperty);
    
    if (recur != null) {
      RRule rule = new RRule();
      try {
        rule.setValue(recur.getRfcRule());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      vevent.getProperties().add(rule);
    }
    
    
    Set<Reminder> reminders = event.getReminders();
    for (Reminder reminder : reminders) {
      
      String durationString = "-PT" + reminder.getTime();
      if (reminder.getTimeUnit() == TimeEnum.MINUTE) {
        durationString += "M"; 
      } else if (reminder.getTimeUnit() == TimeEnum.HOUR) {
        durationString += "H";
      } else if (reminder.getTimeUnit() == TimeEnum.DAY) {
        durationString += "D";
      } else if (reminder.getTimeUnit() == TimeEnum.WEEK) {
        durationString += "W";
      } else {
        durationString = "-PT" + reminder.getMinutesBefore() + "M";
      }
      
      
      VAlarm valarm = new VAlarm();

      Dur duration = new Dur(durationString);
      valarm.getProperties().add(new Trigger(duration));
      valarm.getProperties().add(Action.EMAIL);
      
      Attendee attendee;
      try {
        attendee = new Attendee("MAILTO:" + reminder.getEmail());
      } catch (URISyntaxException e) {
        attendee = new Attendee();
      }
      
      valarm.getProperties().add(attendee);
      
      Description description = new Description(event.getDescription());
      valarm.getProperties().add(description);
      
      Summary summary = new Summary(event.getSubject());
      valarm.getProperties().add(summary);

      vevent.getAlarms().add(valarm);
    }
    
    
    return vevent;
  }
}
