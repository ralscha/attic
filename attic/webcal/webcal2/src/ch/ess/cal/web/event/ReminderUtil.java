package ch.ess.cal.web.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.HibernateException;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.db.Event;
import ch.ess.cal.db.Reminder;
import ch.ess.cal.web.userconfig.TimeEnum;

public class ReminderUtil {

  public static DynaClass reminderClass;

  static {
    reminderClass =
      new BasicDynaClass(
        "Reminders",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Integer.class),
          new DynaProperty("dbId", Long.class),
          new DynaProperty("email", String.class),   
          new DynaProperty("minutes", Integer.class),
          new DynaProperty("timeUnit", TimeEnum.class),
          new DynaProperty("time", Integer.class),
          new DynaProperty("timeStr", String.class),
          new DynaProperty("canDelete", Boolean.class)});

  }



  public static List getReminders(Event event, MessageResources messages, Locale locale) throws HibernateException {
    Set dbReminders = event.getReminders();
    
    List reminders = new ArrayList();
    try {
      int ix = 0;
      for (Iterator it = dbReminders.iterator(); it.hasNext();) {
        Reminder reminder = (Reminder)it.next();

        TimeEnum te = TimeEnum.getEnum(reminder.getTimeUnit());
        
        DynaBean dynaBean = reminderClass.newInstance();
        dynaBean.set("id", new Integer(ix++));
        dynaBean.set("dbId", reminder.getId());
        dynaBean.set("email", reminder.getEmail());
        
        dynaBean.set("minutes", new Integer(reminder.getMinutesBefore()));
        dynaBean.set("time", new Integer(reminder.getTime()));
        dynaBean.set("timeUnit", te);
        dynaBean.set("timeStr", reminder.getTime() + " " + messages.getMessage(locale, reminder.getTimeUnit()));
        
        
        dynaBean.set("canDelete", new Boolean(reminder.canDelete()));
        reminders.add(dynaBean);

      }

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
    return reminders;
  }


  public static void addReminder(Event event, List reminders, Locale locale) {
    Map idMap = new HashMap();

    Set dbReminders = event.getReminders();
    
    for (Iterator it = dbReminders.iterator(); it.hasNext();) {
      Reminder reminder = (Reminder)it.next();
      idMap.put(reminder.getId(), reminder);
    }

    for (Iterator it = reminders.iterator(); it.hasNext();) {
      DynaBean db = (DynaBean)it.next();
    
      Long id = (Long)db.get("dbId");

      if (id != null) {        
        idMap.remove(id);
      } else {
        String emailStr = (String)db.get("email");
        Reminder re = new Reminder();
        re.setEmail(emailStr);
        re.setEvent(event);
        re.setTime(((Integer)db.get("time")).intValue());
        re.setTimeUnit(((TimeEnum)db.get("timeUnit")).getName());
        re.setMinutesBefore(((Integer)db.get("minutes")).intValue());
        re.setLocale(locale);
        dbReminders.add(re);
      }
    }

    for (Iterator it = idMap.values().iterator(); it.hasNext();) {
      dbReminders.remove(it.next());
    }
  }

  public static boolean handleReminderRequest(Map params, EventForm ef) 
    throws IllegalAccessException, InstantiationException {

    String emailInput = ef.getEmail();
    if (!GenericValidator.isBlankOrNull(ef.getEmailSelect())) {
      emailInput = ef.getEmailSelect();
    }

    List reminders = ef.getReminders();
    
    Set parmKeys = params.keySet();

    if (parmKeys.contains("addreminder")) {

      if (!GenericValidator.isBlankOrNull(emailInput) && (ef.getTime().intValue() > 0)) {

        TimeEnum te = TimeEnum.getEnum(ef.getTimeUnit());
        int minutes = 0;
        int time = ef.getTime().intValue();
    
        if (te.equals(TimeEnum.MINUTE)) {
          minutes = time;
        } else if (te.equals(TimeEnum.HOUR)) {
          minutes = time * 60;
        } else if (te.equals(TimeEnum.DAY)) {
          minutes = time * 60 * 24;
        } else if (te.equals(TimeEnum.WEEK)) {
          minutes = time * 60 * 24 * 7;
        } else if (te.equals(TimeEnum.MONTH)) {
          minutes = time * 60 * 24 * 31;
        } else if (te.equals(TimeEnum.YEAR)) {
          minutes = time * 60 * 24 * 365;
        }
        
        
        DynaBean dynaBean = ReminderUtil.reminderClass.newInstance();

        dynaBean.set("id", new Integer(reminders.size()));
        dynaBean.set("canDelete", Boolean.TRUE);

        dynaBean.set("dbId", null);

        dynaBean.set("email", emailInput);
        dynaBean.set("minutes", new Integer(minutes));
        dynaBean.set("time", ef.getTime());
        dynaBean.set("timeUnit", te);
        dynaBean.set("timeStr", ef.getTime() + " " + ef.getMessages().getMessage(ef.getLocale(), te.getName()));
        reminders.add(dynaBean);
      }

      ef.setEmail(null);
      ef.setEmailSelect(null);            
      
      return true;

    } else if (parmKeys.contains("del")) {
      String idstr = (String)params.get("del");
      int index = Integer.parseInt(idstr);
      reminders.remove(index);

      //re-index
      int ix = 0;
      for (Iterator it = reminders.iterator(); it.hasNext();) {
        DynaBean db = (DynaBean)it.next();
        db.set("id", new Integer(ix++));
      }

      return true;
    } 

    return false;
  }
}
