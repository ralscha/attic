package ch.ess.cal.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.IntRange;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.Constants;
import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.CalUtil;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.service.EventUtil;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "eventDao", autowire = Autowire.BYTYPE)
public class EventDao extends AbstractDao<Event> {

  public EventDao() {
    super(Event.class);
  }
  
  @Transactional(readOnly=true)
  public List<Event> getUserNormalEvents(final User user, final Calendar first, final Calendar last) {
    return getUserNormalEvents(user, first.getTimeInMillis(), last.getTimeInMillis());
  }

  @Transactional(readOnly=true)
  public List<Object[]> getUserRecurEvents(final User user, final Calendar first, final Calendar last) {
    return getUserRecurEvents(user, first.getTimeInMillis(), last.getTimeInMillis());
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly=true)
  private List<Object[]> getUserRecurEvents(final User user, final long start, final long end) {
    Query q = getSession().createQuery("from Event as e inner join e.recurrences as recur "
        + "where ((recur.patternStartDate >= :startMillis AND recur.patternStartDate <= :endMillis) "
        + "OR (recur.patternStartDate <= :startMillis AND recur.patternEndDate >= :startMillis) "
        + "OR (recur.patternStartDate <= :endMillis AND recur.patternEndDate is null)) AND "
        + "recur.active = true AND e.deleted = false AND  " + "(((e.group is null) AND (:user IN elements(e.users))) OR "
        + "((e.group is not null) AND e.group IN (:groups)))");
  
    q.setLong("startMillis", start);
  
    q.setLong("endMillis", end);
  
    if (!user.getGroups().isEmpty()) {
      q.setParameterList("groups", user.getGroups());
    } else {
      q.setParameter("groups", null, Hibernate.INTEGER);
    }
    q.setEntity("user", user);
  
    return q.list();
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  private List<Event> getUserNormalEvents(final User user, final long start, final long end) {
    Query q = getSession()
        .createQuery("from Event as e where (e.deleted = false AND (e.startDate >= :startMillis AND e.startDate <= :endMillis) "
            + "OR (e.startDate <= :startMillis AND e.endDate >= :startMillis)) AND "
            + "(((e.group is null) AND (:user IN elements(e.users))) OR "
            + "((e.group is not null) AND e.group IN (:groups))) AND (size(e.recurrences) = 0)");
  
    q.setLong("startMillis", start);
  
    q.setLong("endMillis", end);
  
    if (!user.getGroups().isEmpty()) {
      q.setParameterList("groups", user.getGroups());
    } else {
      q.setParameter("groups", null, Hibernate.LONG);
    }
    q.setEntity("user", user);
  
    return q.list();

  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly=true)
  public List<Event> getEventsWithReminders(final long now) {

    Query q = getSession().createQuery("from Event as e where (e.startDate >= :nowMillis) AND "
        + "((size(e.recurrences) = 0) AND (size(e.reminders) > 0))");
  
    q.setLong("nowMillis", now);
  
    List<Event> events = q.list();
  
    q = getSession().createQuery("from Event as e inner join e.recurrences as recur "
        + "where ((recur.patternEndDate >= :nowMillis) " + "OR (recur.patternEndDate is null)) AND "
        + "(recur.active = true) AND (size(e.reminders) > 0)");
  
    q.setLong("nowMillis", now);
  
    List e = q.list();
    for (Iterator it = e.iterator(); it.hasNext();) {
      Object[] objs = (Object[])it.next();
      events.add((Event)objs[0]);
    }
    for (Event event : events) {
      Hibernate.initialize(event.getRecurrences());
      Hibernate.initialize(event.getReminders());
    }
  
    return events;

  }

  @Transactional(readOnly=true)
  public List<Event> findAllEventsWithNoUID() {
    return findByCriteria(Restrictions.isNull("uid"));
  }
  
  
  @Transactional(readOnly=true)
  public List<Event> findAllEventsWithNoCreateDate() {
    return findByCriteria(Restrictions.isNull("createDate"));
  }
  
  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Event> findUserEvents(final User user, final String subject, final String categoryId, final String day, final String month,
      final String year, final String eventId) {
    Criteria criteria = getSession().createCriteria(Event.class);
 
    if (StringUtils.isNotBlank(subject)) {
      String str = subject.trim();
      criteria.add(Restrictions.like("subject", str, MatchMode.START));
    }

    if (StringUtils.isNotBlank(categoryId)) {
      String str = categoryId.trim();
      criteria.createCriteria("eventCategories").createCriteria("category").add(
          Restrictions.eq("id", new Integer(str)));
    }
    
    if (StringUtils.isNotBlank(eventId)) {
      criteria.add(Restrictions.eq("id", new Integer(eventId)));
    }
        
    criteria.add(Restrictions.eq("deleted",false));
    Calendar start = null;
    Calendar end = null;
    
    if (StringUtils.isNotBlank(year)) {
      int y = Integer.parseInt(year);

      if (StringUtils.isBlank(month)) {
        start = CalUtil.newCalendar(user.getTimeZone(), y, Calendar.JANUARY, 1);
        end = CalUtil.newCalendar(user.getTimeZone(), y, Calendar.DECEMBER, 31, 23, 59, 59, 999);
      } else {
        int m = Integer.parseInt(month);
        
        int d = -1;
        if (StringUtils.isNotBlank(day) && StringUtils.isNumeric(day)) {
          d = Integer.parseInt(day);
          if (d <= 0) {
            d = -1;
          } else {
            Calendar testCal = new GregorianCalendar(y, m, 1);
            if (d > testCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
              d = testCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
          }
        }
        
        if (d == -1) {
          start = CalUtil.newCalendar(user.getTimeZone(), y, m, 1);
          end = CalUtil.newCalendar(user.getTimeZone(), y, m, start.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59, 999);
        } else {          
          start = CalUtil.newCalendar(user.getTimeZone(), y, m, d);
          end = CalUtil.newCalendar(user.getTimeZone(), y, m, d, 23, 59, 59, 999);        
        }
      }

      Long startMillis = new Long(start.getTimeInMillis());
      Long endMillis = new Long(end.getTimeInMillis());

      criteria.add(Restrictions.or(Restrictions.and(Restrictions.ge("startDate", startMillis), Restrictions.le(
          "startDate", endMillis)), Restrictions.and(Restrictions.le("startDate", startMillis), Restrictions.ge(
          "endDate", startMillis))));

    }

    if (user.getGroups().size() > 0) {
      criteria.add(Restrictions.or(Restrictions.isNull("group"), Restrictions.and(Restrictions.isNotNull("group"),
          Restrictions.in("group", user.getGroups()))));
    } else {
      criteria.add(Restrictions.isNull("group"));
    }
    criteria.addOrder(Order.asc("startDate"));

    List<Event> resultList = criteria.list();
    ListIterator<Event> li = resultList.listIterator();
    while (li.hasNext()) {
      Event event = li.next();
      if (event.getGroup() == null) {
        if (!event.getUsers().contains(user)) {
          li.remove();
        }
      }
    }
    
    Set<Event> resultSet = new HashSet<Event>(resultList);
    
    //Events with Recurrence
    if (start != null) {
      
      Long startMillis = new Long(start.getTimeInMillis());
      Long endMillis;
      
      if (end != null) {
        endMillis = new Long(end.getTimeInMillis());
      } else {
        endMillis = startMillis;
      }
      
      List<Object[]> events = getUserRecurEvents(user, startMillis, endMillis);
      if (!events.isEmpty()) {
        for (Object[] objects : events) {
          Event ev = (Event)objects[0];  
          Recurrence recur = (Recurrence)objects[1];
            List<Calendar> dates = EventUtil.getDaysBetween(recur, start, end, user.getTimeZone());

            if (!dates.isEmpty()) {
              resultSet.add(ev);
            }
          }
      }
    }
    
    
    resultList = new ArrayList<Event>(resultSet);          
    return resultList;

  }

  @Transactional(readOnly=true)
  public Event findEventByUID(final String uid) {

    Criteria criteria = getSession().createCriteria(Event.class);
    criteria.add(Restrictions.eq("uid", uid));
    criteria.add(Restrictions.eq("deleted",false));
    Event event = (Event)criteria.setMaxResults(1).uniqueResult();
    if (event != null){
      Hibernate.initialize(event.getEventCategories());
      Hibernate.initialize(event.getUsers());
      Hibernate.initialize(event.getReminders());
      Hibernate.initialize(event.getRecurrences());
    }
    return event;
  }
  
  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked") 
  public IntRange getMinMaxYear() {

    Calendar cal = new GregorianCalendar(Constants.UTC_TZ);

    int minYear = cal.get(Calendar.YEAR);
    int maxYear = cal.get(Calendar.YEAR);

    Query query = getSession()
        .createQuery("select min(e.startDate), max(e.startDate), min(e.endDate), max(e.endDate) from Event as e");
    List resultList = query.list();

    if (!resultList.isEmpty()) {
      Object[] res = (Object[])resultList.get(0);

      if (res[0] != null) {
        cal.setTimeInMillis(((Long)res[0]).longValue());
        minYear = cal.get(Calendar.YEAR);
      }
      if (res[2] != null) {
        cal.setTimeInMillis(((Long)res[2]).longValue());

        int y = cal.get(Calendar.YEAR);
        if (y < minYear) {
          minYear = y;
        }
      }

      if (res[1] != null) {
        cal.setTimeInMillis(((Long)res[1]).longValue());
        maxYear = cal.get(Calendar.YEAR);
      }
      if (res[3] != null) {
        cal.setTimeInMillis(((Long)res[3]).longValue());
        int y = cal.get(Calendar.YEAR);
        if (y > maxYear) {
          maxYear = y;
        }
      }

    }

    IntRange intRange = new IntRange(minYear, maxYear);
    return intRange;

  }

  @Transactional(readOnly=true)
  public Object[] getEventAndReminder(final Integer reminderId) {
    Reminder reminder = (Reminder)getSession().get(Reminder.class, reminderId);
    if (reminder != null) {
      Object[] result = new Object[2];
      Hibernate.initialize(reminder);
      Hibernate.initialize(reminder.getEvent());

      Set<EventCategory> categories = reminder.getEvent().getEventCategories();
      for (EventCategory eventCategory : categories) {
        Hibernate.initialize(eventCategory.getCategory());
        Hibernate.initialize(eventCategory.getCategory().getTranslation().getTranslationTexts());
      }

      result[1] = reminder;
      result[0] = reminder.getEvent();
      return result;
    }

    return null;

  }
  
  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Event> getNormalEventsByUser(final User user) {
	    Query q = getSession()
	            .createQuery("from Event as e where"
	                    + "(((e.group is null) AND (:user IN elements(e.users))) OR "
	                    + "((e.group is not null) AND e.group IN (:groups))) AND (size(e.recurrences) = 0)");
    if (!user.getGroups().isEmpty()) {
      q.setParameterList("groups", user.getGroups());
    } else {
      q.setParameter("groups", null, Hibernate.LONG);
    }
    q.setEntity("user", user);
 
    return q.list();
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly=true)
  public List<Object[]> getRecurEventsByUser(final User user) {

	  Query q = getSession().createQuery("from Recurrence as r inner join r.event as e "
        + "where r.active = true AND (:user IN elements(e.users))");

    q.setEntity("user", user);
  
    return q.list();
  }
  
 
  @Override
  public void delete(final Event event) {
	  Query query = getSession().createQuery("update Event set deleted = true" +
				" where id = :eventId");
	  query.setParameter("eventId", event.getId());
	  query.executeUpdate();
  }
 
  @Override
  public void delete(final String id) {
	  Query query = getSession().createQuery("update Event set deleted = true" +
				" where id = :eventId");
	  query.setParameter("eventId", Integer.valueOf(id));
	  query.executeUpdate();
  }
  
  
  public void removeFromDatabase(final Event event) {
	  super.delete(event);
  }

}
