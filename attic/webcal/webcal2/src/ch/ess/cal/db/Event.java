package ch.ess.cal.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.commons.validator.GenericValidator;

import ch.ess.cal.event.EventUtil;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;
import ch.ess.common.util.Util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

/** A business entity class representing an Event
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calEvent" lazy="true"
  */
public class Event extends Persistent {

  private long startDate;
  private long endDate;
  private boolean allDay;
  private SensitivityEnum sensitivity;
  private ImportanceEnum importance;
  private String description;
  private String location;
  private String subject;
  private String uid;
  private long createDate;
  private long modificationDate;
  private String calVersion;
  private int sequence;
  private int priority;
  private Resource resource;
  private Department department;
  private Set properties;
  private Set users;
  private Set recurrences;
  private Set reminders;
  private Set attachments;
  private Set categories;
  private String timeZone;

  /** 
  * @hibernate.property not-null="true"
  */
  public long getStartDate() {
    return this.startDate;
  }

  public void setStartDate(long startDate) {
    this.startDate = startDate;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public long getEndDate() {
    return this.endDate;
  }

  public void setEndDate(long endDate) {
    this.endDate = endDate;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isAllDay() {
    return this.allDay;
  }

  public void setAllDay(boolean allDay) {
    this.allDay = allDay;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public SensitivityEnum getSensitivity() {
    return this.sensitivity;
  }

  public void setSensitivity(SensitivityEnum sensitivity) {
    this.sensitivity = sensitivity;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public ImportanceEnum getImportance() {
    return this.importance;
  }

  public void setImportance(ImportanceEnum importance) {
    this.importance = importance;
  }

  /** 
  * @hibernate.property length="1000" not-null="false"
  */
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getUid() {
    return this.uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public long getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(long createDate) {
    this.createDate = createDate;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public long getModificationDate() {
    return this.modificationDate;
  }

  public void setModificationDate(long modificationDate) {
    this.modificationDate = modificationDate;
  }

  /** 
  * @hibernate.property length="5" not-null="false"
  */
  public String getCalVersion() {
    return this.calVersion;
  }

  public void setCalVersion(String calVersion) {
    this.calVersion = calVersion;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public int getSequence() {
    return this.sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  /** 
   * @hibernate.property length="50" not-null="true"
   */
  public String getTimeZone() {
    return timeZone;
  }

  public TimeZone getTimeZoneObj() {
    return TimeZone.getTimeZone(getTimeZone());
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Resource"
  * @hibernate.column name="resourceId" not-null="false" index="FK_Event_Resource"  
  */
  public Resource getResource() {
    return this.resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Department"
  * @hibernate.column name="departmentId" not-null="false" index="FK_Event_Department"  
  */
  public Department getDepartment() {
    return this.department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  /**      
   * @hibernate.set lazy="true" cascade="all-delete-orphan" table="calEventProp"
   * @hibernate.collection-key  column="eventId"     
   * @hibernate.collection-composite-element class="ch.ess.cal.db.EventProperty"
   */
  public Set getProperties() {
    return this.properties;
  }

  public void setProperties(Set properties) {
    this.properties = properties;
  }

  /**      
   * @hibernate.set lazy="true" table="calUserEvents"
   * @hibernate.collection-key column="eventId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.User" column="userId"
   */
  public Set getUsers() {
    if (users == null) {
      users = new HashSet();
    }
    return this.users;
  }

  public void setUsers(Set users) {
    this.users = users;
  }

  /**      
   * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="eventId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Recurrence"
   */
  public Set getRecurrences() {
    if (recurrences == null) {
      recurrences = new HashSet();
    }
    return this.recurrences;
  }

  public void setRecurrences(Set recurrences) {
    this.recurrences = recurrences;
  }

  /**      
   * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="eventId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Reminder"
   */
  public Set getReminders() {
    if (reminders == null) {
      reminders = new HashSet();
    }
    return this.reminders;
  }

  public void setReminders(Set reminders) {
    this.reminders = reminders;
  }

  /**      
   * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="eventId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Attachment"
   */
  public Set getAttachments() {
    return this.attachments;
  }

  public void setAttachments(Set attachments) {
    this.attachments = attachments;
  }

  /**      
   * @hibernate.set lazy="true" table="calEventCategories"
   * @hibernate.collection-key column="eventId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.Category" column="categoryId"
   */
  public Set getCategories() {
    if (categories == null) {
      categories = new HashSet();
    }
    return this.categories;
  }

  public void setCategories(Set categories) {
    this.categories = categories;
  }

  //can delete
  public boolean canDelete() {
    return true;
  }

  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from Event");
  }

  public static List getMonthUserNormalEvents(User user, Calendar first, Calendar last) throws HibernateException {

    return getUserNormalEvents(user, first.getTimeInMillis(), last.getTimeInMillis());

  }

  public static List getMonthUserRecurEvents(User user, Calendar first, Calendar last) throws HibernateException {

    return getUserRecurEvents(user, first.getTimeInMillis(), last.getTimeInMillis());

  }

  public static List getTodayUserNormalEvents(User user, Calendar today) throws HibernateException {

    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);

    long start = today.getTimeInMillis();

    today.set(Calendar.HOUR_OF_DAY, 23);
    today.set(Calendar.MINUTE, 59);
    today.set(Calendar.SECOND, 59);
    today.set(Calendar.MILLISECOND, 999);

    long end = today.getTimeInMillis();

    return getUserNormalEvents(user, start, end);
  }

  public static List getTodayUserRecurEvents(User user, Calendar today) throws HibernateException {
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);

    long start = today.getTimeInMillis();

    today.set(Calendar.HOUR_OF_DAY, 23);
    today.set(Calendar.MINUTE, 59);
    today.set(Calendar.SECOND, 59);
    today.set(Calendar.MILLISECOND, 999);

    long end = today.getTimeInMillis();

    List firstList = getUserRecurEvents(user, start, end);

    List secondList = new ArrayList();

    Calendar firstDay = new GregorianCalendar(today.getTimeZone());
    firstDay.setTimeInMillis(start);

    Calendar lastDay = new GregorianCalendar(today.getTimeZone());
    lastDay.setTimeInMillis(end);

    for (Iterator it = firstList.iterator(); it.hasNext();) {
      Object[] obj = (Object[])it.next();

      Event ev = ((Event)obj[0]).miniCopy();
      Recurrence recur = (Recurrence)obj[1];

      List dates = EventUtil.getDaysBetween(recur.getRfcRule(), recur, firstDay, lastDay);

      for (Iterator it2 = dates.iterator(); it2.hasNext();) {
        Calendar startRecur = (Calendar)it2.next();
        Calendar endRecur = (Calendar)startRecur.clone();
        endRecur.add(Calendar.MINUTE, recur.getDuration().intValue());

        if (!ev.isAllDay()) {
          Calendar startTime = Util.utcLong2UserCalendar(recur.getStartTime().longValue(), ev.getTimeZoneObj());
          Calendar endTime = Util.utcLong2UserCalendar(recur.getEndTime().longValue(), ev.getTimeZoneObj());
          startRecur.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
          startRecur.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
          endRecur.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
          endRecur.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
        }

        ev.setStartDate(startRecur.getTimeInMillis());
        ev.setEndDate(endRecur.getTimeInMillis());

        secondList.add(ev);

      }

    }

    return secondList;

  }

  public static List getUserRecurEvents(User user, long start, long end) throws HibernateException {
    Query q =
      HibernateSession.currentSession().createQuery(
        "from Event as e inner join e.recurrences as recur "
          + "where ((recur.patternStartDate >= :startMillis AND recur.patternStartDate <= :endMillis) "
          + "OR (recur.patternStartDate <= :startMillis AND recur.patternEndDate >= :startMillis) "
          + "OR (recur.patternStartDate <= :endMillis AND recur.patternEndDate is null)) AND "
          + "recur.active = true AND "
          + "(((e.department is null) AND (:user IN elements(e.users))) OR "
          + "((e.department is not null) AND e.department IN (:departments)))");

    q.setLong("startMillis", start);

    q.setLong("endMillis", end);

    if (!user.getDepartments().isEmpty()) {
      q.setParameterList("departments", user.getDepartments());
    } else {
      q.setParameter("departments", null, Hibernate.LONG);
    }
    q.setEntity("user", user);

    return q.list();

  }

  public static List getUserNormalEvents(User user, long start, long end) throws HibernateException {

    Query q =
      HibernateSession.currentSession().createQuery(
        "from Event as e where ((e.startDate >= :startMillis AND e.startDate <= :endMillis) "
          + "OR (e.startDate <= :startMillis AND e.endDate >= :startMillis)) AND "
          + "(((e.department is null) AND (:user IN elements(e.users))) OR "
          + "((e.department is not null) AND e.department IN (:departments))) AND "
          + "(size(e.recurrences) = 0)");

    q.setLong("startMillis", start);

    q.setLong("endMillis", end);

    if (!user.getDepartments().isEmpty()) {
      q.setParameterList("departments", user.getDepartments());
    } else {
      q.setParameter("departments", null, Hibernate.LONG);
    }
    q.setEntity("user", user);

    return q.list();

  }

  public static List getEventsWithReminders(long now) throws HibernateException {
    Query q =
      HibernateSession.currentSession().createQuery(
        "from Event as e where (e.startDate >= :nowMillis) AND "
          + "((size(e.recurrences) = 0) AND (size(e.reminders) > 0))");

    q.setLong("nowMillis", now);

    List events = q.list();

    q =
      HibernateSession.currentSession().createQuery(
        "from Event as e inner join e.recurrences as recur "
          + "where ((recur.patternEndDate >= :nowMillis) "
          + "OR (recur.patternEndDate is null)) AND "
          + "(recur.active = true) AND (size(e.reminders) > 0)");

    q.setLong("nowMillis", now);

    List e = q.list();
    for (Iterator it = e.iterator(); it.hasNext();) {
      Object[] objs = (Object[])it.next();
      events.add(objs[0]);
    }

    return events;

  }

  public static Iterator findUserEvents(User user, String subject, String categoryId, String month, String year)
    throws HibernateException {
    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Event.class);

    if (!GenericValidator.isBlankOrNull(subject)) {
      subject = "%" + subject + "%";
      crit.add(Expression.like("subject", subject));
    }

    if (!GenericValidator.isBlankOrNull(categoryId)) {
      crit.createCriteria("categories").add(Expression.eq("id", new Long(categoryId)));
    }

    if (!GenericValidator.isBlankOrNull(year)) {
      int y = Integer.parseInt(year);

      Calendar start, end;

      if (GenericValidator.isBlankOrNull(month)) {
        start = new GregorianCalendar(y, Calendar.JANUARY, 1);
        end = new GregorianCalendar(y, Calendar.DECEMBER, 31);
      } else {
        int m = Integer.parseInt(month);
        start = new GregorianCalendar(y, m, 1);
        end = new GregorianCalendar(y, m, start.getActualMaximum(Calendar.DAY_OF_MONTH));
      }

      Long startMillis = new Long(start.getTimeInMillis());
      Long endMillis = new Long(end.getTimeInMillis());

      crit.add(
        Expression.or(
          Expression.and(Expression.ge("startDate", startMillis), Expression.le("startDate", endMillis)),
          Expression.and(Expression.le("startDate", startMillis), Expression.ge("endDate", startMillis))));

    }

    crit.add(
      Expression.or(
        Expression.isNull("department"),
        Expression.and(Expression.isNotNull("department"), Expression.in("department", user.getDepartments()))));

    crit.addOrder(Order.asc("startDate"));

    List resultList = crit.list();
    ListIterator li = resultList.listIterator();
    while (li.hasNext()) {
      Event event = (Event)li.next();
      if (event.getDepartment() == null) {
        if (!event.getUsers().contains(user)) {
          li.remove();
        }
      }
    }

    return resultList.iterator();
  }

  //load
  public static Event load(Long eventId) throws HibernateException {
    return (Event)HibernateSession.currentSession().load(Event.class, eventId);
  }

  //delete method  
  public static int delete(Long eventId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Event as e where e.id = ?", eventId, Hibernate.LONG);
  }

  public Event miniCopy() {

    Event newEvent = new Event();
    newEvent.setAllDay(isAllDay());
    newEvent.setCategories(categories);
    newEvent.setDescription(getDescription());
    newEvent.setEndDate(getEndDate());
    newEvent.setImportance(getImportance());
    newEvent.setLocation(getLocation());
    newEvent.setPriority(getPriority());
    newEvent.setSensitivity(getSensitivity());
    newEvent.setStartDate(getStartDate());
    newEvent.setSubject(getSubject());
    newEvent.setReminders(reminders);
    newEvent.setRecurrences(recurrences);
    newEvent.setTimeZone(getTimeZone());
    newEvent.setId(getId());

    return newEvent;
  }

}
