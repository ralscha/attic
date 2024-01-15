package ch.ess.cal.persistence.hibernate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.IntRange;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.EventDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="eventDao"
 * 
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.Event"
 */
public class EventDaoHibernate extends AbstractDaoHibernate<Event> implements EventDao {

  @Transactional(readOnly=true)
  public List<Event> getUserNormalEvents(final User user, final Calendar first, final Calendar last) {
    return getUserNormalEvents(user, first.getTimeInMillis(), last.getTimeInMillis());
  }

  @Transactional(readOnly=true)
  public List getUserRecurEvents(final User user, final Calendar first, final Calendar last) {
    return getUserRecurEvents(user, first.getTimeInMillis(), last.getTimeInMillis());
  }

  //  public List getTodayUserNormalEvents(final User user, final Calendar today) {
  //
  //    today.set(Calendar.HOUR_OF_DAY, 0);
  //    today.set(Calendar.MINUTE, 0);
  //    today.set(Calendar.SECOND, 0);
  //    today.set(Calendar.MILLISECOND, 0);
  //
  //    long start = today.getTimeInMillis();
  //
  //    today.set(Calendar.HOUR_OF_DAY, 23);
  //    today.set(Calendar.MINUTE, 59);
  //    today.set(Calendar.SECOND, 59);
  //    today.set(Calendar.MILLISECOND, 999);
  //
  //    long end = today.getTimeInMillis();
  //
  //    return getUserNormalEvents(user, start, end);
  //  }
  //
  //  public List getTodayUserRecurEvents(final User user, final Calendar today) {
  //    today.set(Calendar.HOUR_OF_DAY, 0);
  //    today.set(Calendar.MINUTE, 0);
  //    today.set(Calendar.SECOND, 0);
  //    today.set(Calendar.MILLISECOND, 0);
  //
  //    long start = today.getTimeInMillis();
  //
  //    today.set(Calendar.HOUR_OF_DAY, 23);
  //    today.set(Calendar.MINUTE, 59);
  //    today.set(Calendar.SECOND, 59);
  //    today.set(Calendar.MILLISECOND, 999);
  //
  //    long end = today.getTimeInMillis();
  //
  //    List firstList = getUserRecurEvents(user, start, end);
  //
  //    List<Event> secondList = new ArrayList<Event>();
  //
  //    Calendar firstDay = new GregorianCalendar(today.getTimeZone());
  //    firstDay.setTimeInMillis(start);
  //
  //    Calendar lastDay = new GregorianCalendar(today.getTimeZone());
  //    lastDay.setTimeInMillis(end);
  //
  //    for (Iterator it = firstList.iterator(); it.hasNext();) {
  //      Object[] obj = (Object[])it.next();
  //
  //      Event ev = (Event)obj[0];
  //      Recurrence recur = (Recurrence)obj[1];
  //
  //      List dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, firstDay, lastDay);
  //
  //      for (Iterator it2 = dates.iterator(); it2.hasNext();) {
  //        Calendar startRecur = (Calendar)it2.next();
  //        Calendar endRecur = (Calendar)startRecur.clone();
  //        endRecur.add(Calendar.MINUTE, recur.getDuration().intValue());
  //
  //        if (!ev.isAllDay()) {
  //          Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), ev.getTimeZone());
  //          Calendar endTime = Util.utcLong2UserCalendar(recur.getEndTime().longValue(), ev.getTimeZone());
  //          startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
  //          startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
  //          endRecur.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
  //          endRecur.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
  //        }
  //
  //        ev.setStartDate(startRecur.getTimeInMillis());
  //        ev.setEndDate(endRecur.getTimeInMillis());
  //
  //        secondList.add(ev);
  //
  //      }
  //
  //    }
  //
  //    return secondList;
  //
  //  }

  @Transactional(readOnly=true)
  private List<Event> getUserRecurEvents(final User user, final long start, final long end) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        Query q = session.createQuery("from Event as e inner join e.recurrences as recur "
            + "where ((recur.patternStartDate >= :startMillis AND recur.patternStartDate <= :endMillis) "
            + "OR (recur.patternStartDate <= :startMillis AND recur.patternEndDate >= :startMillis) "
            + "OR (recur.patternStartDate <= :endMillis AND recur.patternEndDate is null)) AND "
            + "recur.active = true AND " + "(((e.group is null) AND (:user IN elements(e.users))) OR "
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
    };

    return getHibernateTemplate().executeFind(callback);
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  private List<Event> getUserNormalEvents(final User user, final long start, final long end) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Query q = session
            .createQuery("from Event as e where ((e.startDate >= :startMillis AND e.startDate <= :endMillis) "
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
    };

    return getHibernateTemplate().executeFind(callback);

  }

  @Transactional(readOnly=true)
  public List<Event> getEventsWithReminders(final long now) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        Query q = session.createQuery("from Event as e where (e.startDate >= :nowMillis) AND "
            + "((size(e.recurrences) = 0) AND (size(e.reminders) > 0))");

        q.setLong("nowMillis", now);

        List<Event> events = q.list();

        q = session.createQuery("from Event as e inner join e.recurrences as recur "
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
    };

    return getHibernateTemplate().executeFind(callback);

  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Event> findUserEvents(final User user, final String subject, final String categoryId, final String month,
      final String year) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        Criteria criteria = session.createCriteria(Event.class);

        if (StringUtils.isNotBlank(subject)) {
          String str = subject.trim();
          criteria.add(Restrictions.like("subject", str, MatchMode.START));
        }

        if (StringUtils.isNotBlank(categoryId)) {
          String str = categoryId.trim();
          criteria.createCriteria("eventCategories").createCriteria("category").add(
              Restrictions.eq("id", new Integer(str)));
        }

        if (StringUtils.isNotBlank(year)) {
          int y = Integer.parseInt(year);

          Calendar start, end;

          if (StringUtils.isBlank(month)) {
            start = Util.newCalendar(user.getTimeZone(), y, Calendar.JANUARY, 1);
            end = Util.newCalendar(user.getTimeZone(), y, Calendar.DECEMBER, 31);
          } else {
            int m = Integer.parseInt(month);
            start = Util.newCalendar(user.getTimeZone(), y, m, 1);
            end = Util.newCalendar(user.getTimeZone(), y, m, start.getActualMaximum(Calendar.DAY_OF_MONTH));
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

        List resultList = criteria.list();
        ListIterator li = resultList.listIterator();
        while (li.hasNext()) {
          Event event = (Event)li.next();
          if (event.getGroup() == null) {
            if (!event.getUsers().contains(user)) {
              li.remove();
            }
          }
        }

        return resultList;
      }
    };

    return getHibernateTemplate().executeFind(callback);
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked") 
  public IntRange getMinMaxYear() {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        Calendar cal = new GregorianCalendar(Constants.UTC_TZ);

        int minYear = cal.get(Calendar.YEAR);
        int maxYear = cal.get(Calendar.YEAR);

        Query query = session
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
    };

    return (IntRange)getHibernateTemplate().execute(callback);

  }

  @Transactional(readOnly=true)
  public Object[] getEventAndReminder(final Integer reminderId) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Reminder reminder = (Reminder)session.get(Reminder.class, reminderId);
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
    };

    return (Object[])getHibernateTemplate().execute(callback);

  }

}
