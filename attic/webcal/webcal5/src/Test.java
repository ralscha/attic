import java.util.Calendar;
import java.util.Locale;


/**
 * @author sr
 */
@SuppressWarnings("unused")
public class Test {

  public static void main(String[] args) throws Exception {
System.out.println(Locale.getDefault());
    Calendar today = Calendar.getInstance(Locale.US);
    today.setMinimalDaysInFirstWeek(4);
    System.out.println(today.get(Calendar.YEAR));
    System.out.println(today.get(Calendar.WEEK_OF_YEAR));
    
//    ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
//        "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml", "/spring-last.xml"});
//
//    SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");
////session
//    Session session = SessionFactoryUtils.getSession(sf, true);
//    session.setFlushMode(FlushMode.MANUAL);
//
//    TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));
//    
//
//    GroupDao groupDao = (GroupDao)ap.getBean("groupDao");
//    List<Group> groups = groupDao.findAll();
//    
//    Set<User> usersSet = new TreeSet<User>(new UserComparator());
//    
//    for (Group group : groups) {
//      System.out.println(group.getId());
//      System.out.println(group.getUsers().size());
//      System.out.println();
    }
    
    
    
//    
//    
//    
//    
//    List results = session.createCriteria(File.class, "file")
//    .setProjection( Projections.projectionList()
//    .add( Projections.property("file.name"), "fileName" )
//    .add( Projections.property("file.description"), "fileDescription" )
//    .add( Projections.property("file.document.contentSize"), "size" )
//    )
//    .list();
//    
//    for (Iterator it = results.iterator(); it.hasNext();) {
//      Object[] obj = (Object[])it.next();
//      for (int i = 0; i < obj.length; i++) {
//        System.out.println(obj[i]);
//      }
//      System.out.println("----");
//      
//      
//    }

//    Calendar firstDay = CalUtil.newCalendar(TimeZone.getDefault(), 2004, Calendar.JANUARY, 1);
//    Calendar lastDay = CalUtil.newCalendar(TimeZone.getDefault(), 2005, Calendar.DECEMBER, 31, 23, 59, 59, 999);
    
//    EventDao eventDao = (EventDao)ap.getBean("eventDao");
//    TaskDao taskDao = (TaskDao)ap.getBean("taskDao");
//    List<Event> events = eventDao.findAll();
//    List<Task> tasks = taskDao.findAll();
//    for (Task task : tasks) {
//      Set<Attachment> attachments = taskDao.getAttachmentsFileName(task);
//      for (Attachment attachment : attachments) {
//        System.out.println(attachment);
//      }
//    }
    //for (Event event : events) {
      
      
//      if (event.isAllDay()) {
//        Calendar start = new GregorianCalendar();
//        start.setTimeInMillis(event.getStartDate());
//        Calendar end = new GregorianCalendar();
//        end.setTimeInMillis(event.getEndDate());
//
//        System.out.print(Constants.DATE_UTC_FORMAT.format(start.getTime()));
//        System.out.print("  ");
//        System.out.print(Constants.DATE_UTC_FORMAT.format(end.getTime()));
//        System.out.print("  ");
//        System.out.println(event.getSubject());
//      }
      
//      Set<Recurrence> recurrences = event.getRecurrences();
//      for (Recurrence recurrence : recurrences) {
//        
//        
//        List<Calendar> newList = EventUtil.getDaysBetween(recurrence, firstDay, lastDay, TimeZone.getDefault());
            
//        List<Calendar> oldList = EventUtil.getDaysBetween(recurrence.getRfcRule(), recurrence, firstDay, lastDay, TimeZone.getDefault());
        
//       if (newList.size() != oldList.size()) {
//          System.out.println(recurrence.getRfcRule());
//          System.out.println("OLD");
//          for (Calendar calendar : oldList) {
//            //System.out.println(Constants.DATE_UTC_LONG_FORMAT.format(calendar.getTime()));
//            System.out.println(calendar.getTime());
//          }
//          System.out.println("NEW");
//          for (Calendar calendar : newList) {
//            //System.out.println(Constants.DATE_UTC_LONG_FORMAT.format(calendar.getTime()));
//            System.out.println(calendar.getTime());
//          }
//          System.out.println("----------------");
//        } else {
//          for (int i = 0; i < newList.size(); i++) {
//            Calendar newCal = newList.get(i);
//            Calendar oldCal = oldList.get(i);
//            
//            if (newCal.getTimeInMillis() != oldCal.getTimeInMillis()) {
//              System.out.println("NEW: " + newCal.getTime());
//              System.out.println("OLD: " + oldCal.getTime());
//            }
//            
//          }
//       }
        
//      }
    //}
   
//    System.out.println("END");
//    UserDao userDao = (UserDao)ap.getBean("userDao");
//    User user = userDao.get("1");
//    EventDao eventDao = (EventDao)ap.getBean("eventDao");
//    CategoryDao categoryDao = (CategoryDao)ap.getBean("categoryDao");

//
//    CalendarBuilder builder = new CalendarBuilder();
//    FileInputStream fin = new FileInputStream("C:/kalender.ics");
//    Calendar calendar = builder.build(fin);
//
//    for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
//      VEvent component = (VEvent)i.next();
//      Property uidProperty = component.getProperties().getProperty(Property.UID);
//      Property dtstartProperty = component.getStartDate();
//      Property dtendProperty = component.getEndDate();
//      Property categoriesProperty = component.getProperties().getProperty(Property.CATEGORIES);
//      Property summaryProperty = component.getProperties().getProperty(Property.SUMMARY);
//      Property classProperty = component.getProperties().getProperty(Property.CLASS);
//      Property lastProperty = component.getProperties().getProperty(Property.LAST_MODIFIED);
//      Property locationProperty = component.getProperties().getProperty(Property.LOCATION);
//      Property rruleProperty = component.getProperties().getProperty(Property.RRULE);
//      Property descriptionProperty = component.getProperties().getProperty(Property.DESCRIPTION);
//
//      Event event = eventDao.findEventByUID(uidProperty.getValue());
//      if (event == null) {
//        event = new Event();
//      }
//      
//      event.setSubject(summaryProperty.getValue());
//      event.setUid(uidProperty.getValue());
//      event.setImportance(ImportanceEnum.NORMAL);
//      event.setPriority(0);
//      event.setSequence(0);
//
//      if (locationProperty != null) {
//        event.setLocation(locationProperty.getValue());
//      } else {
//        event.setLocation(null);
//      }
//
//      if (descriptionProperty != null) {
//        event.setDescription(descriptionProperty.getValue());
//      } else {
//        event.setDescription(null);
//      }
//            
//      if ("CONFIDENTIAL".equals(classProperty.getValue())) {
//        event.setSensitivity(SensitivityEnum.CONFIDENTIAL);
//      } else if ("PRIVATE".equals(classProperty.getValue())) {
//        event.setSensitivity(SensitivityEnum.PRIVATE);
//      } else {
//        event.setSensitivity(SensitivityEnum.PUBLIC);
//      }
//
//      Date lastModified = Constants.DATE_LOCAL_FORMAT.parse(lastProperty.getValue());
//      event.setModificationDate(lastModified.getTime());
//
//      // Datum Start & Ende
//      String dtstart = dtstartProperty.getValue();
//      String dtend = dtendProperty.getValue();
//      if (dtstart.length() == 8) {
//        Date start = Constants.SIMPLE_DATE_LOCAL_FORMAT.parse(dtstart);
//        event.setStartDate(start.getTime());
//        Date end = Constants.SIMPLE_DATE_LOCAL_FORMAT.parse(dtend);
//        event.setEndDate(end.getTime());
//        event.setAllDay(true);
//      } else {
//        Date start = Constants.DATE_LOCAL_FORMAT.parse(dtstart);
//        event.setStartDate(start.getTime());
//        Date end = Constants.DATE_LOCAL_FORMAT.parse(dtend);
//        event.setEndDate(end.getTime());
//        event.setAllDay(false);
//      }
//
//      // Category
//      event.getEventCategories().removeAll(event.getEventCategories());
//      StringTokenizer st = new StringTokenizer(categoriesProperty.getValue(), ",");
//      while (st.hasMoreTokens()) {
//        String categories = st.nextToken();
//        Category category = categoryDao.find(categories);
//        if (category != null) {
//          EventCategory eventCategory = new EventCategory();
//          eventCategory.setCategory(category);
//          eventCategory.setEvent(event);
//          event.getEventCategories().add(eventCategory);
//        }
//      }
//      if (event.getEventCategories().isEmpty()) {
//        Category category = categoryDao.getDefaultCategory();
//        EventCategory eventCategory = new EventCategory();
//        eventCategory.setCategory(category);
//        eventCategory.setEvent(event);
//        event.getEventCategories().add(eventCategory);
//      }
//
//      // Benutzer zuweisen
//      if (event.getUsers().isEmpty()) {
//        event.getUsers().add(user);
//      }
//
//      // Wiederholende
//      event.getRecurrences().removeAll(event.getRecurrences());
//      if (rruleProperty != null) {
//        Recurrence recur = EventUtil.getRecurrence(rruleProperty.getValue(), event);
//        recur.setEvent(event);
//        recur.setRfcRule(rruleProperty.getValue());
//        event.getRecurrences().add(recur);        
//      }
//      
//      //Alarms
//      ComponentList alarms = component.getAlarms();
//      if (alarms != null) {
//        
//        String defaultEmail = user.getDefaultEmail();
//        if (defaultEmail != null) {
//          event.getReminders().removeAll(event.getReminders());
//          
//          for (Iterator it = alarms.iterator(); it.hasNext();) {
//            VAlarm alarm = (VAlarm)it.next();
//            Trigger trigger = (Trigger)alarm.getProperties().getProperty(Property.TRIGGER);
//            if (trigger != null) {
//              Dur duration = trigger.getDuration();
//              if (duration != null) {
//                
//                
//                Reminder newReminder = new Reminder();
//                newReminder.setEmail(defaultEmail);
//                newReminder.setEvent(event);
//                
//                if (duration.getMinutes() > 0) {
//                  newReminder.setTime(duration.getMinutes());
//                  newReminder.setTimeUnit(TimeEnum.MINUTE);
//                  newReminder.setMinutesBefore(duration.getMinutes());
//                } else if (duration.getHours() > 0) {
//                  newReminder.setTime(duration.getHours());
//                  newReminder.setTimeUnit(TimeEnum.HOUR);
//                  newReminder.setMinutesBefore(duration.getHours() * 60);                  
//                } else if (duration.getDays() > 0) {
//                  newReminder.setTime(duration.getDays());
//                  newReminder.setTimeUnit(TimeEnum.DAY);
//                  newReminder.setMinutesBefore(duration.getDays() * 24 * 60);
//                } else if (duration.getWeeks() > 0) {
//                  newReminder.setTime(duration.getWeeks());
//                  newReminder.setTimeUnit(TimeEnum.WEEK);
//                  newReminder.setMinutesBefore(duration.getWeeks() * 7 * 24 * 60);
//                }
//                
//                newReminder.setLocale(user.getLocale());
//                event.getReminders().add(newReminder);                
//              }            
//            }          
//          }
//        }
//      }
//
//      // Event speichern
//      eventDao.saveOrUpdate(event);
//    }

//    TransactionSynchronizationManager.unbindResource(sf);
//    SessionFactoryUtils.releaseSession(session, sf);

    //    ApplicationContext ap = new ClassPathXmlApplicationContext("/spring-data.xml");
    //    PermissionData permissions = (PermissionData)ap.getBean("permissionData");
    //
    //    Set set = permissions.getPermissions();
    //    for (Iterator it = set.iterator(); it.hasNext();) {
    //      System.out.println(it.next());
    //
    //    }

    //    try {
    //      List testList = new ArrayList();
    //      
    //      DynaBean dynaBean = resultClass.newInstance();
    //      dynaBean.set("id", new Integer(1));
    //      dynaBean.set("userName", "sr");
    //      dynaBean.set("name", "Sch�r");
    //      dynaBean.set("firstName", "Ralph");
    //      
    //      testList.add(dynaBean);
    //      
    //      dynaBean = resultClass.newInstance();
    //      dynaBean.set("id", new Integer(2));
    //      dynaBean.set("userName", "aa");
    //      dynaBean.set("name", "Arnold");
    //      dynaBean.set("firstName", "Achim");
    //      
    //      testList.add(dynaBean);
    //      
    //      SortOrder direction = SortOrder.ASCENDING;
    //      
    //      if (direction.equals(SortOrder.ASCENDING)) {
    //        Collections.sort(testList, new PropertyComparator("name"));
    //      } else {
    //        Collections.sort(testList, new PropertyComparator("name", new
    // ReverseComparator()));
    //      }
    //      
    //      
    //      
    //      
    //      for (Iterator it = testList.iterator(); it.hasNext();) {
    //        DynaBean db = (DynaBean)it.next();
    //        System.out.println(db.get("id"));
    //        System.out.println(db.get("name"));
    //      }
    //      
    //      
    //      
    //    } catch (IllegalAccessException e) {
    //      e.printStackTrace();
    //    } catch (InstantiationException e) {
    //      e.printStackTrace();
    //    }

//  }
}
