package ch.ess.cal.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.TimeTaskBudget;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "timeTaskBudgetDao", autowire = Autowire.BYTYPE)
public class TimeTaskBudgetDao extends AbstractDao<TimeTaskBudget> {

  public TimeTaskBudgetDao() {
    super(TimeTaskBudget.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<TimeTaskBudget> find(final Date from, final Date to, final String timeCustomerId, 
      final String timeProjectId, final String timeTaskId, final Boolean searchWithInactive) {
    
	  Criteria criteria = getSession().createCriteria(TimeTaskBudget.class);

    if (from != null) {
      criteria.add(Restrictions.ge("date", from));
    }    
    if (to != null) {
      criteria.add(Restrictions.le("date", to));    
    }
    
    // ALIASES anlegen
	criteria.createAlias("timeProject", "tp");
	criteria.createAlias("tp.timeCustomer", "tc");
    
    // filtern auf Aktive Customer / Task / Kunden
	// searchWithActive != searchWithInactive
    if(!searchWithInactive){
    	criteria.add(Restrictions.eq("tp.active", Boolean.TRUE));
    	criteria.add(Restrictions.eq("tc.active", Boolean.TRUE));
    }
    
    if (StringUtils.isNotBlank(timeProjectId)) {
      criteria.add(Restrictions.eq("timeProject.id", new Integer(timeProjectId)));
    } else if (StringUtils.isNotBlank(timeCustomerId)) {
      criteria.add(Restrictions.eq("tp.timeCustomer.id", new Integer(timeCustomerId)));
    }    

    return criteria.list();

  }

  @Transactional(readOnly=true)
  public boolean hasProjectBudget(final Integer projectId) {
    Criteria criteria = getSession().createCriteria(TimeTaskBudget.class);    
    criteria.add(Restrictions.eq("timeProject.id", projectId));   
    criteria.add(Restrictions.isNull("timeTask"));
    criteria.setProjection(Projections.count("amount"));
    
    Integer count =  (Integer)criteria.uniqueResult(); 
    return count > 0;
  }
  
  @Transactional(readOnly=true)
  public boolean hasTaskBudget(final Integer projectId) {
    Criteria criteria = getSession().createCriteria(TimeTaskBudget.class);    
    criteria.add(Restrictions.eq("timeProject.id", projectId));   
    criteria.add(Restrictions.isNotNull("timeTask"));
    criteria.setProjection(Projections.count("amount"));
    
    Integer count =  (Integer)criteria.uniqueResult(); 
    return count > 0;
  }
}