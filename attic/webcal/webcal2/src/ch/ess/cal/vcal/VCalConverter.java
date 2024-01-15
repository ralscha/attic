package ch.ess.cal.vcal;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.cal.db.Category;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.EventProperty;
import ch.ess.cal.db.ImportanceEnum;
import ch.ess.cal.db.SensitivityEnum;
import ch.ess.cal.db.User;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateFactoryManager;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;

public class VCalConverter {

  public static void toDatabase(Session sess, User user, VCalendar calendar) throws HibernateException, ParseException {
        
    if (calendar == null) {
      return;
    }
        
    
    List events = calendar.getAllEvents();
    for (Iterator it = events.iterator(); it.hasNext();) {
      VEvent event = (VEvent)it.next();
      
      //1. events mit gleicher uid löschen
      String uid = event.getFieldValue(VCalendarConstants.FIELD_UID);      
      if (uid != null) {                
        int num = sess.delete("from Event as event where event.uid = ?", uid.trim(), Hibernate.STRING);
        System.out.println("deleted: " + num);        
      }
      
      
      //2. Event neu erstellen
      Event newEvent = new Event();
      newEvent.setCalVersion(VCalendarConstants.VCAL_VERSION);
      newEvent.setImportance(ImportanceEnum.NORMAL);
      Set userSet = new HashSet();
      User dbUser = (User)sess.load(User.class, user.getId());
      userSet.add(dbUser);
      newEvent.setUsers(userSet);
      
      Set properties = new HashSet();
      
      //3. Felder einfügen
      
      //3a. Attachment      
      Iterator lit = event.getFieldValueList(VCalendarConstants.FIELD_ATTACH);
      while (lit.hasNext()) {
        Field element = (Field)lit.next();
        addProperty(properties, VCalendarConstants.FIELD_ATTACH, element.getPropertyValueString());          
      }
      
      //3b. Attendee
      lit = event.getFieldValueList(VCalendarConstants.FIELD_ATTENDEE);
      while (lit.hasNext()) {
        Field element = (Field)lit.next();        
        addProperty(properties, VCalendarConstants.FIELD_ATTENDEE, element.getPropertyValueString());          
      }      
      
      
      //3c. Audio Reminder      
      addField(event, properties, VCalendarConstants.FIELD_AALARM);
      
      //3d. Categories      
      lit = event.getFieldValueList(VCalendarConstants.FIELD_CATEGORIES);
      Set categorySet = new HashSet();
      
      while (lit.hasNext()) {        
        Field catField = (Field)lit.next();
        String category = catField.getFieldValue();
        
        //Category suchen
        List l = sess.find("from Category as c where c.icalKey = ?", category.toUpperCase(), Hibernate.STRING);
        if (l.size() == 0) {
          Category newCat = new Category();
          newCat.setBwColour("3399BB");
          newCat.setColour("FFFFFF");
          newCat.setIcalKey(category.toUpperCase());
          
          Map m = new HashMap();
          m.put(Constants.GERMAN_LOCALE, category);
          m.put(Constants.ENGLISH_LOCALE, category);
          
          newCat.setTranslations(m);
                    
          newCat.setUnknown(false);
          
          sess.save(newCat);
        
        } else {
          categorySet.add(l.get(0));  
        }                          
      } 
      
      if (categorySet.isEmpty()) {
        //default category einfügen
        List l = sess.find("from Category as c where c.unknown = true");
        categorySet.add(l.get(0));
      }
      
      newEvent.setCategories(categorySet);
      
      
      
      
      
      //3e. Classification
      newEvent.setSensitivity(SensitivityEnum.NO_SENSITIVITY);
      
      Field f = event.getField(VCalendarConstants.FIELD_CLASS);
      if (f != null) {
        if (VCalendarConstants.CLASS_PUBLIC.equals(f.getFieldValue())) {
          newEvent.setSensitivity(SensitivityEnum.NO_SENSITIVITY);
        } else if (VCalendarConstants.CLASS_PRIVATE.equals(f.getFieldValue())) {
          newEvent.setSensitivity(SensitivityEnum.PRIVATE);
        } else if (VCalendarConstants.CLASS_CONFIDENTAIL.equals(f.getFieldValue())) {
          newEvent.setSensitivity(SensitivityEnum.CONFIDENTIAL);
        } 
      }
            
      //3f. Date/Time Created, immer zulu time abspeichern
      f = event.getField(VCalendarConstants.FIELD_DCREATED);            
      if (f != null) {
        Calendar cal = Util.convertToUTCCalendar(f.getFieldValue(), user.getTimeZoneObj());
        newEvent.setCreateDate(cal.getTimeInMillis());
      }
      
      
      //3g. Description
      f = event.getField(VCalendarConstants.FIELD_DESCRIPTION);
      if (f != null) {
        newEvent.setDescription(f.getFieldValue());
      }
      
      //3h. Display Reminder
      addField(event, properties, VCalendarConstants.FIELD_DALARM);
      
      //3i. End Date/Time
      f = event.getField(VCalendarConstants.FIELD_DTEND);
      if (f != null) {
        Calendar cal = Util.convertToUTCCalendar(f.getFieldValue(), user.getTimeZoneObj());        
        newEvent.setEndDate(cal.getTimeInMillis());
      }
      
      //3i. Exception Date
      //todo
      
      //3j. Exception Rule
      //todo
      
      //3k. Last Modified
      f = event.getField(VCalendarConstants.FIELD_LASTMODIFIED);            
      if (f != null) {
        Calendar cal = Util.convertToUTCCalendar(f.getFieldValue(), user.getTimeZoneObj());
        newEvent.setModificationDate(cal.getTimeInMillis());
      }
      
      //3l. Location
      f = event.getField(VCalendarConstants.FIELD_LOCATION);
      if (f != null) {
        newEvent.setLocation(f.getFieldValue());
      }      
      addField(event, properties, VCalendarConstants.FIELD_LOCATION);      
            
      
      //3m. Mail Reminder
      //todo
      
      //3n. Number Recurrences
      //todo
      
      //3o. Priority
      newEvent.setPriority(0);
      
      f = event.getField(VCalendarConstants.FIELD_PRIORITY);
      if (f != null) {
        try {
          newEvent.setPriority(Integer.parseInt(f.getFieldValue()));
        } catch (NumberFormatException nfe) {
          //no action
        }        
      }  
      
      
      //3p. Procedure Reminder
      addField(event, properties, VCalendarConstants.FIELD_PALARM);     
      
      //3q. Related To
      addField(event, properties, VCalendarConstants.FIELD_RELATEDTO);     
      
      //3r. Recurrence Date/Time
      //todo
      
      //3s. Recurrence Rule
      //todo
      
      //3t. Resources      
      lit = event.getFieldValueList(VCalendarConstants.FIELD_RESOURCES);
      while (lit.hasNext()) {
        Field element = (Field)lit.next();        
        addProperty(properties, VCalendarConstants.FIELD_RESOURCES, element.getPropertyValueString());          
      }      
      
      
      
      
      //3u. Sequence Number
      newEvent.setSequence(0);
      
      f = event.getField(VCalendarConstants.FIELD_SEQUENCE);
      if (f != null) {
        try {
          newEvent.setSequence(Integer.parseInt(f.getFieldValue()));
        } catch (NumberFormatException nfe) {
          //no action
        }        
      }      
      
      //3v. Start Date/Time
      f = event.getField(VCalendarConstants.FIELD_DTSTART);
      if (f != null) {
        Calendar cal = Util.convertToUTCCalendar(f.getFieldValue(), user.getTimeZoneObj());        
        newEvent.setStartDate(cal.getTimeInMillis());
      }      
      
      
      //3w. Status
      addField(event, properties, VCalendarConstants.FIELD_STATUS);
      
      
      //3x. Summary
      f = event.getField(VCalendarConstants.FIELD_SUMMARY);
      if (f != null) {
        newEvent.setSubject(f.getFieldValue());
      }      
      

      //3y. Status
      addField(event, properties, VCalendarConstants.FIELD_TRANSP);

      //3z. Status
      addField(event, properties, VCalendarConstants.FIELD_URL);
      
      //3aa. UID
      f = event.getField(VCalendarConstants.FIELD_UID);
      if (f != null) {
        newEvent.setUid(f.getFieldValue());
      }      
      
      
      //Alle X-Fields
      Iterator itx = event.getAllExistingFieldNames();
      while (itx.hasNext()) {
        String fieldName = (String)itx.next();
        
        if (fieldName.startsWith("X-")) {
          addField(event, properties, fieldName);
        
        }
        
      }
      
      
      
      newEvent.setProperties(properties);      
      System.out.println(newEvent);      
      sess.save(newEvent);
    }
    
    
    
  }

  private static void addField(VEvent event, Set properties, String field) {
    Field f = event.getField(field);
    if (f != null) {
      addProperty(properties, field, f.getPropertyValueString());
    }
  }
  
  private static void addProperty(Set properties, String name, String value) {
    EventProperty newProperty = new EventProperty();
    newProperty.setName(name);
    newProperty.setPropValue(value);
    properties.add(newProperty);
  }
  
  public static VEvent fromDatabase() {
    return null;
  }

  public static void main(String[] args) {
    
    Transaction tx = null;

    try {
      HibernateFactoryManager.initXML("??");

      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();


      List userList = sess.find("from User as u");
      User user = (User)userList.get(0);      
            
 
      //System.out.println(calUser.getTimezone());
      


      FoldBufferedReader br = new FoldBufferedReader(new FileReader("c:/temp/vcal.txt"));
      
      StringBuffer sb = new StringBuffer(1000);
      String line = null;
      while ((line = br.readLine()) != null) {   
        sb.append(VCalendarUtil.quodedPrintable2eightBit(line)).append("\r\n");                     
      }
      
      VCalendar vcal = VCalendarUtil.parseVCalendar(sb.toString());
      toDatabase(sess, user, vcal);
      

      
      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      e.printStackTrace();  
    } catch (ch.ess.cal.vcal.parser.ParseException e) {      
      e.printStackTrace();
    } catch (ParseException e) {      
      e.printStackTrace();
    } catch (IOException e) {      
      e.printStackTrace();
    } finally {
      HibernateSession.closeSession();
    }        
    
  }
}
