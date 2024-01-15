package ch.ess.cal.dao;

import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.cal.model.TimeTaskBudget;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "timeTaskDao", autowire = Autowire.BYTYPE)
public class TimeTaskDao extends AbstractDao<TimeTask> {

  public TimeTaskDao() {
    super(TimeTask.class);
  }

  @Transactional
  public void setActive(String taskId, boolean flag) {

    TimeTask task = findById(taskId);
    task.setActive(flag);

    boolean hasActiveTasks = false;
    TimeProject proj = task.getTimeProject();
    for (TimeTask ta : proj.getTimeTasks()) {
      hasActiveTasks = hasActiveTasks || ta.isActive();
    }
    proj.setActive(hasActiveTasks);

    boolean hasActiveProjects = false;
    TimeCustomer customer = proj.getTimeCustomer();
    for (TimeProject pr : customer.getTimeProjects()) {
      hasActiveProjects = hasActiveProjects || pr.isActive();
    }
    customer.setActive(hasActiveProjects);
  }

  @Transactional(readOnly=true)
  public BigDecimal getBudget(final Integer taskId) {
    Criteria criteria = getSession().createCriteria(TimeTaskBudget.class);    
    criteria.add(Restrictions.eq("timeTask.id", taskId));    
    criteria.setProjection(Projections.sum("amount"));
    
    BigDecimal totalAmount =  (BigDecimal)criteria.uniqueResult(); 
    return totalAmount;
  }  
  
  @Transactional(readOnly=true)
  public BigDecimal getCost(final Integer taskId) {
    Criteria criteria = getSession().createCriteria(Time.class);    
    criteria.add(Restrictions.eq("timeTask.id", taskId));    
    criteria.setProjection(Projections.sum("cost"));
    
    BigDecimal totalAmount =  (BigDecimal)criteria.uniqueResult(); 
    return totalAmount;
  }   
  
  @Override
  @Transactional(readOnly = true)
  public boolean canDelete(TimeTask baseObject) {
    return size(baseObject.getTimes()) == 0;
  }

}
