package ch.ess.cal.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "timeCustomerDao", autowire = Autowire.BYTYPE)
public class TimeCustomerDao extends AbstractDao<TimeCustomer> {

  public TimeCustomerDao() {
    super(TimeCustomer.class);
  }

  @Transactional
  public void setActive(String customerId, boolean flag) {

    TimeCustomer customer = findById(customerId);
    customer.setActive(flag);

    Set<TimeProject> projects = customer.getTimeProjects();
    for (TimeProject project : projects) {

      project.setActive(flag);

      Set<TimeTask> tasks = project.getTimeTasks();
      for (TimeTask task : tasks) {
        task.setActive(flag);
      }
    }
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Object[]> findWithTasks() {
    Query query = getSession().createQuery(
        "from TimeCustomer as c left outer join c.timeProjects as p left outer join p.timeTasks as t order by c.customerNumber, c.name, p.projectNumber, p.name, t.name ");

    return query.list();
  }  

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Object[]> findWithActiveTasks() {
    Query query = getSession().createQuery(
        "from TimeCustomer as c left outer join c.timeProjects as p left outer join p.timeTasks as t WHERE t.active = 1 OR p.active = 1 order by c.customerNumber, c.name, p.projectNumber, p.name, t.name ");

    return query.list();
  }  

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Object[]> findWithTasks(User user) {
    Query query = getSession()
        .createQuery(
            "from TimeCustomer as c left outer join c.timeProjects as p left outer join p.timeTasks as t inner join c.userTimeCustomers as utc where utc.user = :user order by c.customerNumber, c.name, p.projectNumber, p.name, t.name ");

    query.setEntity("user", user);
    return query.list();
  } 

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Time> findWithTasksUserDate(User user, String date) {
    Query query = getSession()
        .createQuery(
            "from Time as t where t.user = :user and t.taskTimeDate = :date");

    query.setEntity("user", user);
    query.setString("date", date);
    return query.list();
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<TimeCustomer> findCustomerProjectTime(String customerString, String searchWithInactive) {
	  String strQuery = 
          "SELECT *" +
          " from cal_time_customer c" +
          " LEFT OUTER JOIN cal_time_project p" +
          " ON c.Id = time_customer_id";
	  
	  	if(Boolean.parseBoolean(searchWithInactive)){ 
	  		if(customerString != null) {
        	  strQuery += " WHERE (c.name LIKE '%"+customerString+"%'" +
        	  		" OR c.customer_number LIKE '%"+customerString+"%')";
	  		}
	  	}else{
	  		strQuery += " WHERE (c.active = true";
	  	
          if(customerString != null) {
        	  strQuery += " AND (c.name LIKE '%"+customerString+"%'" +
        	  		" OR c.customer_number LIKE '%"+customerString+"%'";
          }
          strQuery += ")";
	  	}
          
      
      SQLQuery sqlQuery = getSession().createSQLQuery(strQuery).addEntity(TimeCustomer.class);
    return sqlQuery.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  //Customer & Project, damit bereits alles geladen ist!!!
  public List<TimeCustomer> findWithCustomerProject(String customerString, String projectString) {
    Criteria critCustomer = getSession().createCriteria(TimeCustomer.class);
    Criteria critProject = critCustomer.createCriteria("timeProjects", "p");

    if (StringUtils.isNotBlank(customerString)) {
      Criterion lhs = Restrictions.like("name", customerString, MatchMode.ANYWHERE);
      Criterion rhs = Restrictions.like("customerNumber", customerString, MatchMode.ANYWHERE);
      critCustomer.add(Restrictions.or(lhs, rhs));
    }

    if (StringUtils.isNotBlank(projectString)) {
    	Criterion lhs = Restrictions.like("name", projectString, MatchMode.ANYWHERE);
    	Criterion rhs = Restrictions.like("projectNumber", projectString, MatchMode.ANYWHERE);
    	critProject.add(Restrictions.or(lhs, rhs));
    }

    //damit Root nicht mehrmals aufgeführt wird.
    critCustomer.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    // add orders
    critCustomer.addOrder(Order.asc("customerNumber"));
    critCustomer.addOrder(Order.asc("name"));
    critProject.addOrder(Order.asc("projectNumber"));
    critProject.addOrder(Order.asc("name"));
    return critCustomer.list();
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<TimeCustomer> findForDailyReport(String userId) {
    Criteria criteria = getSession().createCriteria(TimeCustomer.class);
	
	// only active customers
	criteria.add(Restrictions.eq("active", Boolean.TRUE.booleanValue()));

    criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    // add orders
    criteria.addOrder(Order.desc("name"));
    return criteria.list();
  }

  @Transactional(readOnly = true)
  public BigDecimal getCost(final Integer customerId) {
    Criteria criteria = getSession().createCriteria(Time.class);
    criteria.createCriteria("timeTask").createCriteria("timeProject").createCriteria("timeCustomer").add(Restrictions.eq("id", customerId));
    criteria.setProjection(Projections.sum("cost"));

    BigDecimal totalAmount = (BigDecimal)criteria.uniqueResult();
    return totalAmount;
  }
}
