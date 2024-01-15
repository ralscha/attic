import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.Translation;
import ch.ess.base.model.TranslationText;
import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.service.EventUtil;

public class Migrate {

  public static void main(String[] args) {

    try {
      Class.forName("com.mckoi.JDBCDriver");

      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml"});

      SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");

      Session session = SessionFactoryUtils.getSession(sf, true);
      session.setFlushMode(FlushMode.MANUAL);

      TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

      UserGroupDao userGroupDao = (UserGroupDao)ap.getBean("userGroupDao");
      UserDao userDao = (UserDao)ap.getBean("userDao");
      UserConfigurationDao userConfigurationDao = (UserConfigurationDao)ap.getBean("userConfigurationDao");
      EventDao eventDao = (EventDao)ap.getBean("eventDao");
      CategoryDao categoryDao = (CategoryDao)ap.getBean("categoryDao");
      GroupDao groupDao = (GroupDao)ap.getBean("groupDao");
      
      //Categories
      Map<String, String> categoryTranslateMap = new HashMap<String, String>();

      categoryTranslateMap.put("customer", "BUSINESS");
      categoryTranslateMap.put("work at home", "WORK AT HOME");
      categoryTranslateMap.put("other", "MISCELLANEOUS");
      categoryTranslateMap.put("exhibition", "BUSINESS");
      categoryTranslateMap.put("exhibtion", "BUSINESS");
      categoryTranslateMap.put("education", "EDUCATION");
      categoryTranslateMap.put("meeting", "MEETING");
      categoryTranslateMap.put("military", "MILITARY");
      categoryTranslateMap.put("holiday", "VACATION");
      categoryTranslateMap.put("private", "PERSONAL");

      //Group
      Group group = new Group();
      Email email = new Email();
      email.setUser(null);
      email.setGroup(group);
      email.setDefaultEmail(true);
      email.setEmail("office@ess.ch");
      email.setSequence(0);
      group.getEmails().add(email);

      Set<TranslationText> translations = new HashSet<TranslationText>();

      if (group.getTranslation() == null) {
        group.setTranslation(new Translation());
      }


      TranslationText tt = new TranslationText();
      tt.setLocale("de");
      tt.setText("Team");
      tt.setTranslation(group.getTranslation());
      translations.add(tt);

      tt = new TranslationText();
      tt.setLocale("en");
      tt.setText("Team");
      tt.setTranslation(group.getTranslation());
      translations.add(tt);

      
      group.getTranslation().getTranslationTexts().removeAll(group.getTranslation().getTranslationTexts());
      group.getTranslation().getTranslationTexts().addAll(translations);      
      
      groupDao.save(group);
      
      //User            
      group.getUsers().add(addUser("sr", "Ralph", "Schär", "mexiko", "schaer@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("si", "Sandra", "Isler", "skomite", "isler@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("dg", "Daniela", "Gfeller", "dream", "gfeller@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("mr", "Martin", "Riem", "urinal", "riem@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("dr", "Daniel", "Ramseier", "resett", "ramseier@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("sk", "Stefan", "Knochenhauer", "tulpe", "stefan@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("tl", "Tanja", "Loosli", "gotic04", "loosli@ess.ch", group, userGroupDao, userDao, userConfigurationDao));
      group.getUsers().add(addUser("he", "Hans", "Engler", "randensaft", "engler@ess.ch", group, userGroupDao, userDao, userConfigurationDao));

      Connection con = DriverManager.getConnection("jdbc:mckoi:local://C:/movies/webcaldb/webcaldb/db.conf", "admin",
          "admin");

      Statement stmt = con.createStatement();
      ResultSet rs = stmt
          .executeQuery("select userid,startdate,enddate,body,location,subject,alldayevent,importance,private,c.description,a.appointmentid from Appointments a, Categories c where a.categoryid =  c.categoryid");
      while (rs.next()) {

        String userid = rs.getString(1);
        Timestamp startdate = rs.getTimestamp(2);
        Timestamp enddate = rs.getTimestamp(3);
        String body = rs.getString(4);
        String location = rs.getString(5);
        String subject = rs.getString(6);
        String alldayevent = rs.getString(7);
        //String importance = rs.getString(8);
        //String privateflag = rs.getString(9);
        String categoryo = rs.getString(10);
        int appointmentid = rs.getInt(11);
        
        String newCat = categoryTranslateMap.get(categoryo);
        Category category = categoryDao.findByICalKey(newCat);
        
        User user = userDao.findById(userid);
        Group egroup = null;
        if ("group".equals(userid)) {
          egroup = group;
        }
        
        if ((user != null || egroup != null) && (category != null)) {
          Event event = new Event();
          
          Calendar start = new GregorianCalendar(TimeZone.getDefault());
          Calendar end = new GregorianCalendar(TimeZone.getDefault());
          start.setTimeInMillis(startdate.getTime());
          end.setTimeInMillis(enddate.getTime());
          
          event.setStartDate(start.getTimeInMillis());
          event.setEndDate(end.getTimeInMillis());
          if (user != null) {
            event.getUsers().add(user);
          } else {
            event.setGroup(egroup);
          }

          event.setAllDay("Y".equals(alldayevent));
          event.setLocation(location);
          event.setSubject(subject);
          event.setDescription(body);
          event.setImportance(ImportanceEnum.NORMAL);
          
          if ("PERSONAL".equals(newCat)) {
            event.setSensitivity(SensitivityEnum.PRIVATE);
          } else {
            event.setSensitivity(SensitivityEnum.PUBLIC);
          }
          event.setPriority(0);
          event.setSequence(0);
          
          EventCategory eventCategory = new EventCategory();
          eventCategory.setCategory(category);
          eventCategory.setEvent(event);
          event.getEventCategories().add(eventCategory);
          
          
          
          Statement stmtre = con.createStatement();
          ResultSet rs2 = stmtre
              .executeQuery("select until,every,everyperiod,repeaton,repeatonweekday,repeatonperiod from Repeaters where appointmentid =  " + appointmentid);
          if (rs2.next()) {
            Timestamp until = rs2.getTimestamp(1);
            int every = rs2.getInt(2);
            int everyperiod = rs2.getInt(3);
            int repeaton = rs2.getInt(4);
            int repeatonweekday = rs2.getInt(5);
            int repeatonperiod = rs2.getInt(6);
            
            addRecurrence(event, until, every,everyperiod,repeaton,repeatonweekday,repeatonperiod);
            
          }
          rs2.close();
          stmtre.close();
          
          stmtre = con.createStatement();
          rs2 = stmtre
              .executeQuery("select minutesbefore,email from Reminders where appointmentid =  " + appointmentid);
          if (rs2.next()) {
                        
            int minutesbefore = rs2.getInt(1);
            String remail = rs2.getString(2);
            
            addReminders(event, remail, minutesbefore);
            
          }
          rs2.close();
          stmtre.close();
          
          
          eventDao.save(event);
        } else {
          System.out.println(userid + " not found or");
          System.out.println(categoryo + " not found");
        }
      }

      rs.close();
      stmt.close();
      con.close();

      TransactionSynchronizationManager.unbindResource(sf);
      SessionFactoryUtils.releaseSession(session, sf);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  private static void addReminders(Event event, String email, int minutesbefore) {
      Reminder newReminder = new Reminder();
      newReminder.setEmail(email);
      newReminder.setEvent(event);
      
      if (minutesbefore < 60) {
        newReminder.setTime(minutesbefore);
        newReminder.setTimeUnit(TimeEnum.MINUTE);      
      } else if (minutesbefore < 1440) {
        newReminder.setTime(minutesbefore/60);
        newReminder.setTimeUnit(TimeEnum.HOUR);              
      } else {
        newReminder.setTime(minutesbefore/60/24);
        newReminder.setTimeUnit(TimeEnum.DAY);       
      }
      

      
      newReminder.setMinutesBefore(minutesbefore);
      newReminder.setLocale(new Locale("de"));
      event.getReminders().add(newReminder);
  }
  
  private static void addRecurrence(Event event, Timestamp until, int every, int everyperiod, int repeaton, int repeatonweekday, int repeatonperiod) {
    Recurrence recurrence = new Recurrence();
    recurrence.setEvent(event);
    recurrence.setExclude(false);

    recurrence.setActive(true);

    Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), TimeZone.getDefault());
    Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), TimeZone.getDefault());
    
    
    if (every == 1) {
      if (everyperiod == 1) {
        recurrence.setType(RecurrenceTypeEnum.DAILY);
        recurrence.setInterval(1);
        
      } else if (everyperiod == 2) {
        recurrence.setType(RecurrenceTypeEnum.WEEKLY);
        recurrence.setInterval(1);
        recurrence.setDayOfWeekMask(EventUtil.getDayOfWeekMask(new int[]{startCal.get(Calendar.DAY_OF_WEEK)}));        
      }
    } else {
      if (repeaton == 5) {
        recurrence.setType(RecurrenceTypeEnum.MONTHLY_NTH);
        recurrence.setInstance(PosEnum.LAST);
        recurrence.setInterval(1);

        recurrence.setDayOfWeekMask(32);        
      }
    }


    if (until == null) {
      recurrence.setAlways(true);
    } else {
      recurrence.setAlways(false);
      Calendar untilCal = new GregorianCalendar(TimeZone.getDefault());
      untilCal.setTimeInMillis(until.getTime());
      recurrence.setUntil(untilCal.getTimeInMillis());
    }

    String rule = EventUtil.getRfcRule(recurrence, Calendar.MONDAY);
    recurrence.setRfcRule(rule);

    long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
    diff = diff / 1000 / 60;

    recurrence.setDuration(new Long(diff));

    if (event.isAllDay()) {
      startCal.set(Calendar.HOUR_OF_DAY, 0);
      startCal.set(Calendar.MINUTE, 0);
      startCal.set(Calendar.SECOND, 0);
      startCal.set(Calendar.MILLISECOND, 0);
    } 

    List<Calendar> days = EventUtil.getDays(rule, startCal);
    if (!days.isEmpty()) {
      Calendar s = days.get(0);
      recurrence.setPatternStartDate(s.getTimeInMillis());

      if (recurrence.isAlways()) {
        recurrence.setPatternEndDate(null);
      } else {
        Calendar e = days.get(days.size() - 1);
        recurrence.setPatternEndDate(e.getTimeInMillis());
      }
      
      event.getRecurrences().removeAll(event.getRecurrences());
      event.getRecurrences().add(recurrence); 
    } else {
      System.out.println(rule);
      System.out.println(startCal.getTime());
    }

 
  }
  
  private static User addUser(String userName, String firstName, String name, String password, String emailStr,
      Group group, UserGroupDao userGroupDao, UserDao userDao, UserConfigurationDao userConfigurationDao) {
    User newUser = new User();
    newUser.setUserName(userName);
    newUser.setFirstName(firstName);
    newUser.setName(name);
    newUser.setLocale(Locale.GERMAN);
    newUser.setTimeZone(TimeZone.getDefault());
    newUser.setPasswordHash(DigestUtils.shaHex(password));
    newUser.getGroups().add(group);

    List<UserGroup> groupList = userGroupDao.find("administrator");
    if (!groupList.isEmpty()) {

      UserGroup userGroup = groupList.get(0);

      UserUserGroup userUserGroup = new UserUserGroup();
      userUserGroup.setUser(newUser);
      userUserGroup.setUserGroup(userGroup);

      newUser.getUserUserGroups().add(userUserGroup);
      userGroup.getUserUserGroups().add(userUserGroup);
    }

    Email email = new Email();
    email.setUser(newUser);
    email.setDefaultEmail(true);
    email.setEmail(emailStr);
    email.setSequence(0);
    newUser.getEmails().add(email);

    userDao.save(newUser);

    Config userConfig = userConfigurationDao.getUserConfig(newUser);
    userConfig.setProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
    userConfigurationDao.save(newUser, userConfig);
    return newUser;
  }
}
