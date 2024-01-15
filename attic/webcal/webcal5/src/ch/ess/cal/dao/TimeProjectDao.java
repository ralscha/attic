package ch.ess.cal.dao;

import java.math.BigDecimal;
import java.util.Set;

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

@SpringBean(id = "timeProjectDao", autowire = Autowire.BYTYPE)
public class TimeProjectDao extends AbstractDao<TimeProject> {

  public TimeProjectDao() {
    super(TimeProject.class);
  }

  @Transactional
  public void setActive(String projectId, boolean flag) {

    TimeProject project = findById(projectId);
    project.setActive(flag);
    
    Set<TimeTask> tasks = project.getTimeTasks();
    for (TimeTask task : tasks) {
      task.setActive(flag);
    }
    
    boolean hasActiveProjects = false;
    TimeCustomer customer = project.getTimeCustomer();
    for (TimeProject proj : customer.getTimeProjects()) {
      hasActiveProjects = hasActiveProjects || proj.isActive();
    }
    customer.setActive(hasActiveProjects);
  }
  
  @Transactional(readOnly=true)
  public BigDecimal getBudget(final Integer projectId) {
    Criteria criteria = getSession().createCriteria(TimeTaskBudget.class);    
    criteria.createCriteria("timeProject").add(Restrictions.eq("id", projectId));   
    criteria.add(Restrictions.isNull("timeTask"));
    criteria.setProjection(Projections.sum("amount"));
    
    BigDecimal totalAmount =  (BigDecimal)criteria.uniqueResult(); 
    return totalAmount;
  }  
  
  @Transactional(readOnly=true)
  public BigDecimal getCost(final Integer projectId) {
    Criteria criteria = getSession().createCriteria(Time.class);    
    criteria.createCriteria("timeProject").add(Restrictions.eq("id", projectId));    
    criteria.setProjection(Projections.sum("cost"));
    
    BigDecimal totalAmount =  (BigDecimal)criteria.uniqueResult(); 
    return totalAmount;
  }    
  
  @Override
  @Transactional(readOnly = true)
  public boolean canDelete(TimeProject baseObject) {
    return size(baseObject.getTimes()) == 0;
  }

  
}
