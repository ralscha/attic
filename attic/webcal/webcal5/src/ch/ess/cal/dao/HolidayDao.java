package ch.ess.cal.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.Holiday;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "holidayDao", autowire = Autowire.BYTYPE)
public class HolidayDao extends AbstractDao<Holiday> {

  public HolidayDao() {
    super(Holiday.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Holiday> find(final Integer monthNo) {

    Criteria criteria = getSession().createCriteria(Holiday.class);

    if (monthNo != null) {
      criteria.add(Restrictions.eq("monthNo", monthNo));
    }

    return criteria.list();

  }

  @Transactional(readOnly = true)
  public List<Holiday> findActiveHolidays() {
    return findByCriteria(Restrictions.eq("active", Boolean.TRUE));
  }

  @Transactional(readOnly = true)
  public Holiday findWithBuiltin(final String builtin) {
    return findByCriteriaUnique(Restrictions.eq("builtin", builtin));
  }

  @Override
  public boolean canDelete(Holiday baseObject) {
    return (baseObject.getBuiltin() == null);
  }

}
