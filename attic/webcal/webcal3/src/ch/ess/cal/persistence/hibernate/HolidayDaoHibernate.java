package ch.ess.cal.persistence.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.Holiday;
import ch.ess.cal.persistence.HolidayDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="holidayDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.Holiday"
 *
 */
public class HolidayDaoHibernate extends AbstractDaoHibernate<Holiday> implements HolidayDao {
  
  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")   
  public List<Holiday> find(final Integer monthNo) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Holiday.class);

        if (monthNo != null) {
          criteria.add(Restrictions.eq("monthNo", monthNo));
        }

        return criteria.list();

      }

    };

    return getHibernateTemplate().executeFind(callback);

  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Holiday> findActiveHolidays() {
    return getHibernateTemplate().find("from Holiday as hol where hol.active = true");
  }

  @Transactional(readOnly=true)
  public Holiday findWithBuiltin(final String builtin) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(Holiday.class);

        criteria.add(Restrictions.eq("builtin", builtin));
        return criteria.setMaxResults(1).uniqueResult();

      }

    };

    return (Holiday)getHibernateTemplate().execute(callback);

  }

}
