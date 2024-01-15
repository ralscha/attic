package ch.ess.cal.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.enums.TimeRangeEnum;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.web.time.CalendarRange;
import ch.ess.cal.web.time.CalendarRangeIterator;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "timeDao", autowire = Autowire.BYTYPE)
public class TimeDao extends AbstractDao<Time> {

  public TimeDao() {
    super(Time.class);
  }
  
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Time> find(final Date from, final Date to, final String timeCustomerId, final String timeProjectId,
      final String timeTaskId, final Set<TimeCustomer> customers, final String userId, final String groupId, final String fullTextSearch,
      final Boolean searchWithInactive) {

	String timeTaskIdDummy = timeTaskId;
    if ((customers != null) && (customers.size() == 0)) {
      //nicht admin und keine customer
      return Collections.EMPTY_LIST;
    }
    
    Criteria criteria = getSession().createCriteria(Time.class);
    if (StringUtils.isNotBlank(userId)) {
      criteria.add(Restrictions.eq("user.id", new Integer(userId)));
    }
    
    if (StringUtils.isNotBlank(groupId)) {
      criteria.createCriteria("user").createCriteria("groups").add(Restrictions.eq("id", new Integer(groupId)));
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


    // Suchkriterien Customer/Project/Task oder Volltextsuche
    if (StringUtils.isNotBlank(timeProjectId)) {
      criteria.add(Restrictions.eq("tp.id", new Integer(timeProjectId)));
    } else if (StringUtils.isNotBlank(timeCustomerId)) {
      criteria.add(Restrictions.eq("tc.id", new Integer(timeCustomerId)));
    } else if (customers != null) {
      criteria.add(Restrictions.in("tp.timeCustomer", customers));
    } else if (StringUtils.isNotBlank(fullTextSearch)){
    	Criterion activity = Restrictions.like("activity", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion comment = Restrictions.like("comment", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion chargesStyle = Restrictions.like("chargesStyle", fullTextSearch, MatchMode.ANYWHERE);


    	// ALIAS für TimeProject
    	Criterion tpNumber = Restrictions.like("tp.projectNumber", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion tpName = Restrictions.like("tp.name", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion tpDescription = Restrictions.like("tp.description", fullTextSearch, MatchMode.ANYWHERE);
    	
    	// ALIAS für TimeCustomer
    	Criterion tcNumber = Restrictions.like("tc.customerNumber", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion tcName = Restrictions.like("tc.name", fullTextSearch, MatchMode.ANYWHERE);
    	Criterion tcDescription = Restrictions.like("tc.description", fullTextSearch, MatchMode.ANYWHERE);
    
    	// OR-Verknüpfungen
    	criteria.add(Restrictions.disjunction().add(activity).add(comment).add(chargesStyle)
    			.add(tpNumber).add(tpName).add(tpDescription)
    			.add(tcNumber).add(tcName).add(tcDescription));
    	
    	if(StringUtils.isNotBlank(timeTaskIdDummy)){
    		criteria.createAlias("timeTask", "tt");
    		if(!searchWithInactive){
    			criteria.add(Restrictions.eq("tt.active", Boolean.TRUE));
    		}
    		if (StringUtils.isNotBlank(timeTaskId)) {
    		      criteria.add(Restrictions.eq("timeTask.id", new Integer(timeTaskId)));
    		} 
    		
    		Criterion ttName = Restrictions.like("tt.name", fullTextSearch, MatchMode.ANYWHERE);
    	   	Criterion ttDescription = Restrictions.like("tt.description", fullTextSearch, MatchMode.ANYWHERE);
    		
    		criteria.add(ttName).add(ttDescription);
    	}

    }
    if (from != null) {
      criteria.add(Restrictions.ge("taskTimeDate", from));
    }

    if (to != null) {
      criteria.add(Restrictions.le("taskTimeDate", to));
    }

    criteria.addOrder(Order.asc("taskTimeDate"));
    
    return criteria.list();

  }
  
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Time> findByDate(final Date from, String userId, final Boolean searchWithInactive) {

    Criteria criteria = getSession().createCriteria(Time.class);
   
    if (StringUtils.isNotBlank(userId)) {
        criteria.add(Restrictions.eq("user.id", new Integer(userId)));
      }
    
	criteria.createAlias("timeProject", "tp");
	criteria.createAlias("tp.timeCustomer", "tc");
    
    // filtern auf Aktive Customer / Task / Kunden
	// searchWithActive != searchWithInactive
    if(!searchWithInactive){
    	criteria.add(Restrictions.eq("tp.active", Boolean.TRUE));
    	criteria.add(Restrictions.eq("tc.active", Boolean.TRUE));
    }

    if (from != null) {
      criteria.add(Restrictions.ge("taskTimeDate", from));
    }

    criteria.addOrder(Order.asc("taskTimeDate"));
    
    return criteria.list();

  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Object[]> findSum(final Date from, final Date to, final String timeCustomerId, final String timeProjectId,
      final String timeTaskId, final Set<TimeCustomer> customers, final String userId, final String groupId, final Boolean searchWithInactive) {

    if ((customers != null) && (customers.size() == 0)) {
      //nicht admin und keine customer
      return Collections.EMPTY_LIST;
    }    

    Criteria criteria = getSession().createCriteria(Time.class);
    // ALIASES anlegen
	criteria.createAlias("timeTask", "tt");
	criteria.createAlias("timeProject", "tp");
	criteria.createAlias("tp.timeCustomer", "tc");
    
    if (StringUtils.isNotBlank(userId)) {
      criteria.add(Restrictions.eq("user.id", new Integer(userId)));
    }    
    if (StringUtils.isNotBlank(groupId)) {
      criteria.createCriteria("user").createCriteria("groups").add(Restrictions.eq("id", new Integer(groupId)));
    }
    
    
    if (StringUtils.isNotBlank(timeTaskId)) {
      criteria.add(Restrictions.eq("tt.id", new Integer(timeTaskId)));
    } else if (StringUtils.isNotBlank(timeProjectId)) {
      criteria.add(Restrictions.eq("tp.id", new Integer(timeProjectId)));
    } else if (StringUtils.isNotBlank(timeCustomerId)) {
      criteria.add(Restrictions.eq("tp.timeCustomer.id", new Integer(timeCustomerId)));
    } else if (customers != null) {
      criteria.add(Restrictions.in("tp.timeCustomer", customers));
    }
    
    // filtern auf Aktive Customer / Task / Kunden
	// searchWithActive != searchWithInactive
    if(!searchWithInactive){
    	criteria.add(Restrictions.eq("tt.active", Boolean.TRUE));
    	criteria.add(Restrictions.eq("tp.active", Boolean.TRUE));
    	criteria.add(Restrictions.eq("tc.active", Boolean.TRUE));
    }

    if (from != null) {
      criteria.add(Restrictions.ge("taskTimeDate", from));
    }
    if (to != null) {
      criteria.add(Restrictions.le("taskTimeDate", to));
    }

    
    ProjectionList pl = Projections.projectionList();
    pl.add(Projections.groupProperty("tc.id"));
    pl.add(Projections.groupProperty("tc.name"));
    pl.add(Projections.groupProperty("tc.customerNumber"));
    pl.add(Projections.groupProperty("tp.id"));
    pl.add(Projections.groupProperty("tp.name"));
    pl.add(Projections.groupProperty("tp.projectNumber"));
    pl.add(Projections.groupProperty("tt.id"));
    pl.add(Projections.groupProperty("tt.name"));
    pl.add(Projections.sum("workInHour"));
    pl.add(Projections.sum("cost"));
    
    
    criteria.setProjection(pl);
    
    criteria.addOrder(Order.asc("tc.name"));
    criteria.addOrder(Order.asc("tc.id"));
    criteria.addOrder(Order.asc("tp.name"));
    criteria.addOrder(Order.asc("tp.id"));
    criteria.addOrder(Order.asc("tp.name"));
    
    return criteria.list();
  }
  
  
  public Map<MultiKey,Map<String,Object>> findSum(Date from, Date to, final TimeRangeEnum timeRange, final int firstDayOfWeek, final String timeCustomerId,
      final String timeProjectId, final String timeTaskId, final Set<TimeCustomer> customers, final String userId, final String groupId, final Boolean searchWithInactive) throws ParseException {

    Map<MultiKey,Map<String,Object>> dayAllMap = new HashMap<MultiKey,Map<String,Object>>();

	  if(from == null && to == null) {
		  to = new java.util.Date();
		  SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy HH:mm" );
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(new Date()); //heute
		  int jahr = cal.get(Calendar.YEAR); 
		  from = sdf.parse( "01.01."+jahr+" 00:00" );
	  }
	  
	  Calendar fromCal = new GregorianCalendar();
	  fromCal.setTime(from);

	  Calendar toCal = new GregorianCalendar();
	  toCal.setTime(to);

    Iterator<CalendarRange> it = new CalendarRangeIterator(fromCal, toCal, timeRange, firstDayOfWeek);
    while (it.hasNext()) {
      CalendarRange range = it.next();

      List<Time> dayResult = find(range.getFrom().getTime(), range.getTo().getTime(), timeCustomerId, timeProjectId, timeTaskId,
          customers, userId, groupId, null,searchWithInactive);

      for (Time result : dayResult) {
        if (result.getWorkInHour() != null) {
          Object[] keyValues = new Object[8];
          keyValues[0] = result.getTimeProject().getTimeCustomer().getId();
          keyValues[1] = result.getTimeProject().getTimeCustomer().getName();
          keyValues[2] = result.getTimeProject().getTimeCustomer().getCustomerNumber();
          keyValues[3] = result.getTimeProject().getId();
          keyValues[4] = result.getTimeProject().getName();
          keyValues[5] = result.getTimeProject().getProjectNumber();
          if(result.getTimeTask() != null){
        	  keyValues[6] = result.getTimeTask().getId();
        	  keyValues[7] = result.getTimeTask().getName();
          }else{
        	  keyValues[6] = -1;
        	  keyValues[7] = "-";
          }
          
          MultiKey key = new MultiKey(keyValues);
          Map<String,Object> dayMap = dayAllMap.get(key);

          if (dayMap == null) {
            dayMap = new HashMap<String,Object>();
            dayAllMap.put(key, dayMap);
          }

          BigDecimal b = (BigDecimal) dayMap.get(range.getInternalDescription());
          if(b == null){
        	  b = result.getWorkInHour();
          }else{
        	  b = b.add(result.getWorkInHour());
          }
          dayMap.put(range.getInternalDescription(), b);
        }
      }
    }

    return dayAllMap;
  }
  

  @SuppressWarnings("unchecked")
  public Date[] getMinMaxDate() {
    Query query = getSession().createQuery("select min(tt.taskTimeDate), max(tt.taskTimeDate) from Time as tt");
    List<Object[]> result = query.list();
    if ((result != null) && (result.size() == 1)) {
      Object[] o = result.get(0);
      if ((o.length == 2) && (o[0] != null)) {
        Timestamp timestampMin = (Timestamp)o[0];
        Timestamp timestampMax = (Timestamp)o[1];
        Date[] resultDate = new Date[2];
        resultDate[0] = new Date(timestampMin.getTime());
        resultDate[1] = new Date(timestampMax.getTime());
        return resultDate;
      }
    }
    return null;
  }

  @Transactional(readOnly = true)
  public List<Integer> getYears() {
    Date[] dates = getMinMaxDate();

    List<Integer> yearList = new ArrayList<Integer>();

    Calendar cal = Calendar.getInstance();

    int minYear = cal.get(Calendar.YEAR);
    int maxYear = cal.get(Calendar.YEAR);

    if (dates != null) {

      Calendar c = new GregorianCalendar();
      c.setTime(dates[0]);
      minYear = Math.min(minYear, c.get(Calendar.YEAR));

      c = new GregorianCalendar();
      c.setTime(dates[1]);
      maxYear = Math.max(maxYear, c.get(Calendar.YEAR));

      for (int i = maxYear; i >= minYear; i--) {
        yearList.add(new Integer(i));
      }

    } else {
      yearList.add(new Integer(minYear));
    }

    return yearList;
  }

  

}