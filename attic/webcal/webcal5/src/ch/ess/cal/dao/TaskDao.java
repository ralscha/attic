package ch.ess.cal.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.Constants;
import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.enums.StatusEnum;
import ch.ess.cal.enums.TaskViewEnum;
import ch.ess.cal.model.Attachment;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.Task;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "taskDao", autowire = Autowire.BYTYPE)
public class TaskDao extends AbstractDao<Task> {

  public TaskDao() {
    super(Task.class);
  }  
  
  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Task> findUserTasks(final User user, final String subject, final String categoryId, final TaskViewEnum view, final String taskId) {
    Criteria criteria = getSession().createCriteria(Task.class);

    if (StringUtils.isNotBlank(subject)) {
      String str = subject.trim();
      criteria.add(Restrictions.like("subject", str, MatchMode.START));
    }

    if (StringUtils.isNotBlank(categoryId)) {
      String str = categoryId.trim();
      criteria.createCriteria("eventCategories").createCriteria("category").add(
          Restrictions.eq("id", new Integer(str)));
    }
    
    if (user != null) {
      if (user.getGroups().size() > 0) {
        criteria.add(Restrictions.or(Restrictions.isNull("group"), Restrictions.and(Restrictions.isNotNull("group"),
            Restrictions.in("group", user.getGroups()))));
      } else {
        criteria.add(Restrictions.isNull("group"));
      }
    }
    
    if (view == TaskViewEnum.ACTIVE_TASKS) {      
      List<StatusEnum> activeStatus = new ArrayList<StatusEnum>();
      activeStatus.add(StatusEnum.INPROCESS);
      activeStatus.add(StatusEnum.NOTSTARTED);
      activeStatus.add(StatusEnum.WAITING);
      
      criteria.add(Restrictions.in("status", activeStatus));      
    } else if (view == TaskViewEnum.NEXT_SEVEN_DAYS) {
      Calendar today = new GregorianCalendar(Constants.UTC_TZ);
      Calendar nextSeven = (Calendar)today.clone();
      nextSeven.add(Calendar.DATE, +7);
      criteria.add(Restrictions.ge("dueDate", today.getTimeInMillis()));
      criteria.add(Restrictions.le("dueDate", nextSeven.getTimeInMillis()));
    } else if (view == TaskViewEnum.OVERDUE_TASKS) {
      Calendar today = new GregorianCalendar(Constants.UTC_TZ);
      criteria.add(Restrictions.lt("dueDate", today.getTimeInMillis()));
    }
    
    if (StringUtils.isNotBlank(taskId)) {
      criteria.add(Restrictions.eq("id", new Integer(taskId)));      
    }
    
    criteria.addOrder(Order.asc("dueDate"));

    List<Task> resultList = criteria.list();
    ListIterator<Task> li = resultList.listIterator();
    while (li.hasNext()) {
      Task task = li.next();
      if (task.getGroup() == null) {
        if (!task.getUsers().contains(user)) {
          li.remove();
        }
      }
    }
    return resultList;
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly=true)
  public List<Reminder> findRemindersBetween(final long now, final long then) {
    Criteria criteria = getSession().createCriteria(Reminder.class);

    criteria.add(Restrictions.ge("reminderDate", now));
    criteria.add(Restrictions.le("reminderDate", then));
    List<Reminder> searchResult = criteria.list();
    
    List<Reminder> result = new ArrayList<Reminder>();
    
    for (Reminder reminder : searchResult) {
      
      Task task = (Task)reminder.getEvent();
      if ((task.getStatus() != StatusEnum.COMPLETED) && (task.getStatus() != StatusEnum.CANCELLED)) {
        result.add(reminder);
      }      
    }
    
    return result;
  }

  @Transactional(readOnly=true)
  public Attachment findAttachmentById(String attachmentId) {
    return (Attachment)getSession().load(Attachment.class, Integer.parseInt(attachmentId));
  }
  
}
