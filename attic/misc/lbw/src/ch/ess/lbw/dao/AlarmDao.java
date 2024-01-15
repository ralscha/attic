package ch.ess.lbw.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.lbw.model.Alarm;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "alarmDao", autowire = Autowire.BYTYPE)
public class AlarmDao extends AbstractDao<Alarm> {

  public AlarmDao() {
    super(Alarm.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public Alarm find(final String targetId, final String typ) {
    Criteria criteria = getSession().createCriteria(Alarm.class);

    criteria.add(Restrictions.eq("targetId", new Integer(targetId)));
    if (StringUtils.isNotBlank(typ)) {
      criteria.add(Restrictions.eq("typ", typ));
    }
    
    return (Alarm)criteria.setMaxResults(1).uniqueResult();
  }

  @Transactional
  public void delete(final String targetId, final String typ) {
    Alarm alarm = find(targetId, typ);
    if (alarm != null) {
      delete(alarm);
    }
  }
  
}