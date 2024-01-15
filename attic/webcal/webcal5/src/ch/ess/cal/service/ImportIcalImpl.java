package ch.ess.cal.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Trigger;

import org.apache.commons.codec.digest.DigestUtils;

import ch.ess.base.Constants;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.TransparencyEnum;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;

public class ImportIcalImpl implements ImportIcal {

  private EventDao eventDao;
  private UserDao userDao;
  private CategoryDao categoryDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public ImportStatus importIcal(String username, String password, String ical) {

    String encryptedPassword = DigestUtils.shaHex(password);
    User user = userDao.findByNameAndToken(username, encryptedPassword);
    
    System.out.println(ical);
    
    if (user != null) {      
      return importIcal(user, new StringInputStream(ical));
    }
    
    
    
    ImportStatus status = new ImportStatus();
    status.setNoOfInserted(0);
    status.setNoOfUpdated(0);
    status.setStatus(1);
    status.setStatusDescription("login failed");
    return status;
  }

  @SuppressWarnings("unchecked")
  public ImportStatus importIcal(User user, InputStream is) {

    ImportStatus status = new ImportStatus();
    int newCounter = 0;
    int updateCounter = 0;

    try {
      CalendarBuilder builder = new CalendarBuilder();
      Calendar calendar = builder.build(is);
      for (Iterator<Component> i = calendar.getComponents().iterator(); i.hasNext();) {

        Component aComponent = i.next();
        
        if (aComponent instanceof VEvent) {
          VEvent component = (VEvent)aComponent;
          Property uidProperty = component.getProperties().getProperty(Property.UID);
          Property dtstartProperty = component.getStartDate();
          Property dtendProperty = component.getEndDate();
          Property categoriesProperty = component.getProperties().getProperty(Property.CATEGORIES);
          Property summaryProperty = component.getProperties().getProperty(Property.SUMMARY);
          Property classProperty = component.getProperties().getProperty(Property.CLASS);
          Property lastProperty = component.getProperties().getProperty(Property.LAST_MODIFIED);
          Property dtstampProperty = component.getProperties().getProperty(Property.DTSTAMP);
          Property locationProperty = component.getProperties().getProperty(Property.LOCATION);
          Property rruleProperty = component.getProperties().getProperty(Property.RRULE);
          Property descriptionProperty = component.getProperties().getProperty(Property.DESCRIPTION);
          Property transpProperty = component.getProperties().getProperty(Property.TRANSP);
          Property sequenceProperty = component.getProperties().getProperty(Property.SEQUENCE);
          Property priorityProperty = component.getProperties().getProperty(Property.PRIORITY);
          Property importanceProperty = component.getProperties().getProperty("X-MICROSOFT-CDO-IMPORTANCE");
          
          String last = null;
          if (lastProperty != null) {
            last = lastProperty.getValue();
          } 
          
          Date lastModified = null;
          if (last != null) {
            if (last.length() == 8) {
              DateFormat df = new SimpleDateFormat(Constants.DATE_LOCAL_FORMAT);
              df.setTimeZone(Constants.UTC_TZ);
              lastModified = df.parse(last);          
            } else if (last.length() == 15) {
              DateFormat df = new SimpleDateFormat(Constants.DATETIME_LOCAL_FORMAT);
              df.setTimeZone(user.getTimeZone());
              lastModified = df.parse(last);
            } else {
              lastModified = Constants.DATE_UTC_FORMAT.parse(last);
            }
          }
          
          Date createDate = null;
          if (dtstampProperty != null) {
            String create = dtstampProperty.getValue();
            
            if (create.length() == 8) {
              DateFormat df = new SimpleDateFormat(Constants.DATE_LOCAL_FORMAT);
              df.setTimeZone(Constants.UTC_TZ);
              createDate = df.parse(create);          
            } else if (create.length() == 15) {
              DateFormat df = new SimpleDateFormat(Constants.DATETIME_LOCAL_FORMAT);
              df.setTimeZone(user.getTimeZone());
              createDate = df.parse(create);
            } else {
              createDate = Constants.DATE_UTC_FORMAT.parse(create);
            }
          }          
          
          Event event = eventDao.findEventByUID(uidProperty.getValue());
          if (event == null) {
            event = new Event();
            newCounter = newCounter + 1;
          } else {
            
            if ((lastModified != null) && (event.getModificationDate() != null)) {
              if (event.getModificationDate() >= lastModified.getTime()) {
                //no change
                continue;
              }
            } else if ((createDate != null) && (event.getCreateDate() != null)) {
              if (event.getCreateDate() >= createDate.getTime()) {
                //no change
                continue;
              }
            
            }
            
            updateCounter = updateCounter + 1;
          }
                 

          
          if (createDate != null) {
            event.setCreateDate(createDate.getTime());
          } else {
            event.setCreateDate(new Date().getTime());
          }
          
          if (lastModified != null) {
            event.setModificationDate(lastModified.getTime());
          } 
          
          event.setSubject(summaryProperty.getValue());
          
          if (uidProperty.getValue() != null) {
            event.setUid(uidProperty.getValue());
          } else {
            event.setUid(CalUtil.createUid(event));
          }
          
          if (importanceProperty != null) {
            if ("2".equals(importanceProperty.getValue())) {
              event.setImportance(ImportanceEnum.HIGH);
            } else if ("0".equals(importanceProperty.getValue())) {
              event.setImportance(ImportanceEnum.LOW);
            } else {
              event.setImportance(ImportanceEnum.NORMAL);
            }
          } else {
            event.setImportance(ImportanceEnum.NORMAL);
          }
  
          if ("OPAQUE".equals(transpProperty.getValue())) {
            event.setTransparency(TransparencyEnum.OPAQUE);
          } else {
            event.setTransparency(TransparencyEnum.TRANSPARENT);
          }
          
          if (priorityProperty != null) {
            event.setPriority(new Integer(priorityProperty.getValue()));
          } else {
            event.setPriority(0);
          }
          
          if (sequenceProperty != null) {
            event.setSequence(new Integer(sequenceProperty.getValue()));
          } else {
            event.setSequence(0);
          }
  
          if (locationProperty != null) {
            event.setLocation(locationProperty.getValue());
          } else {
            event.setLocation(null);
          }
  
          if (descriptionProperty != null) {
            event.setDescription(descriptionProperty.getValue());
          } else {
            event.setDescription(null);
          }
  
          if ("CONFIDENTIAL".equals(classProperty.getValue())) {
            event.setSensitivity(SensitivityEnum.CONFIDENTIAL);
          } else if ("PRIVATE".equals(classProperty.getValue())) {
            event.setSensitivity(SensitivityEnum.PRIVATE);
          } else {
            event.setSensitivity(SensitivityEnum.PUBLIC);
          }
  
          // Datum Start & Ende
          String dtstart = dtstartProperty.getValue();
          String dtend = dtendProperty.getValue();
          if (dtstart.length() == 8) {          
            DateFormat df = new SimpleDateFormat(Constants.DATE_LOCAL_FORMAT);
            df.setTimeZone(Constants.UTC_TZ);
  
            Date start = df.parse(dtstart);
            event.setStartDate(start.getTime());
            Date end = df.parse(dtend);
                      
            //end date minus one, ical end date is exclusive                     
            event.setEndDate(end.getTime() - 24*60*60*1000);
            event.setAllDay(true);
          } else if (dtstart.length() == 15) {
            
            DateFormat df = new SimpleDateFormat(Constants.DATETIME_LOCAL_FORMAT);
            df.setTimeZone(user.getTimeZone());
            
            Date start = df.parse(dtstart);
            event.setStartDate(start.getTime());
            Date end = df.parse(dtend);
            event.setEndDate(end.getTime());
            event.setAllDay(false);          
          } else {
            Date start = Constants.DATE_UTC_FORMAT.parse(dtstart);
            event.setStartDate(start.getTime());
            Date end = Constants.DATE_UTC_FORMAT.parse(dtend);
            event.setEndDate(end.getTime());
            event.setAllDay(false);
          }
  
          // Category
          event.getEventCategories().removeAll(event.getEventCategories());
          if (categoriesProperty != null) {
            StringTokenizer st = new StringTokenizer(categoriesProperty.getValue(), ",");
            while (st.hasMoreTokens()) {
              String categories = st.nextToken();
              System.out.println(categories);
              Category category = categoryDao.findByICalKey(categories);
              if (category != null) {
                EventCategory eventCategory = new EventCategory();
                eventCategory.setCategory(category);
                eventCategory.setEvent(event);
                event.getEventCategories().add(eventCategory);
              }
            }
          }
          
          //if empty, set default category
          if (event.getEventCategories().isEmpty()) {
            Category category = categoryDao.getDefaultCategory();
            EventCategory eventCategory = new EventCategory();
            eventCategory.setCategory(category);
            eventCategory.setEvent(event);
            event.getEventCategories().add(eventCategory);
          }
  
          // Benutzer zuweisen
          if (event.getUsers().isEmpty()) {
            event.getUsers().add(user);
          }
  
          // Wiederholende
          event.getRecurrences().removeAll(event.getRecurrences());
          if (rruleProperty != null) {
            Recurrence recur = EventUtil.getRecurrence(user.getTimeZone(), rruleProperty.getValue(), event);
            recur.setEvent(event);
            recur.setRfcRule(rruleProperty.getValue());
            event.getRecurrences().add(recur);
          }
  
          //Alarms
          ComponentList alarms = component.getAlarms();
          if (alarms != null) {
  
            String defaultEmail = user.getDefaultEmail();
            if (defaultEmail != null) {
              event.getReminders().removeAll(event.getReminders());
  
              for (Iterator it = alarms.iterator(); it.hasNext();) {
                VAlarm alarm = (VAlarm)it.next();
                Trigger trigger = (Trigger)alarm.getProperties().getProperty(Property.TRIGGER);
                if (trigger != null) {
                  Dur duration = trigger.getDuration();
                  if (duration != null) {
  
                    Reminder newReminder = new Reminder();
                    newReminder.setEmail(defaultEmail);
                    newReminder.setEvent(event);
                    
                    if (duration.getMinutes() > 0) {
                      
                      if (duration.getMinutes() < 60) {
                        newReminder.setTime(duration.getMinutes());
                        newReminder.setTimeUnit(TimeEnum.MINUTE);
                      } else if (duration.getMinutes() < 60*24) {
                        newReminder.setTime(duration.getMinutes() / 60);
                        newReminder.setTimeUnit(TimeEnum.HOUR);                      
                      } else if (duration.getMinutes() < 60*24*7) {
                        newReminder.setTime(duration.getMinutes() / (60 * 24));
                        newReminder.setTimeUnit(TimeEnum.DAY);                                            
                      } else {
                        newReminder.setTime(duration.getMinutes() / (60 * 24 * 7));
                        newReminder.setTimeUnit(TimeEnum.WEEK);                                                                  
                      }
                      
                      newReminder.setMinutesBefore(duration.getMinutes());
                    } else if (duration.getHours() > 0) {
                      newReminder.setTime(duration.getHours());
                      newReminder.setTimeUnit(TimeEnum.HOUR);
                      newReminder.setMinutesBefore(duration.getHours() * 60);
                    } else if (duration.getDays() > 0) {
                      newReminder.setTime(duration.getDays());
                      newReminder.setTimeUnit(TimeEnum.DAY);
                      newReminder.setMinutesBefore(duration.getDays() * 24 * 60);
                    } else if (duration.getWeeks() > 0) {
                      newReminder.setTime(duration.getWeeks());
                      newReminder.setTimeUnit(TimeEnum.WEEK);
                      newReminder.setMinutesBefore(duration.getWeeks() * 7 * 24 * 60);
                    }
  
                    newReminder.setLocale(user.getLocale());
                    event.getReminders().add(newReminder);
                  }
                }
              }
            }
          }
  
          // Event speichern
          eventDao.save(event);
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserException e) {
      e.printStackTrace();
    }

    status.setNoOfInserted(newCounter);
    status.setNoOfUpdated(updateCounter);
    status.setStatus(0);
    status.setStatusDescription("ok");
    return status;

  }

}
