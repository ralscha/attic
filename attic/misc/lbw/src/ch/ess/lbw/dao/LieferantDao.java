package ch.ess.lbw.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.lbw.model.Lieferant;
import ch.ess.lbw.model.Werk;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "lieferantDao", autowire = Autowire.BYTYPE)
public class LieferantDao extends AbstractDao<Lieferant> {

  public LieferantDao() {
    super(Lieferant.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Lieferant> find(final String name, final String nr, final Werk werk) {
    Criteria criteria = getSession().createCriteria(Lieferant.class);

    if (StringUtils.isNotBlank(name)) {
      String str = name.trim();
      criteria.add(Restrictions.like("name", str, MatchMode.ANYWHERE));
    }
    if (StringUtils.isNotBlank(nr)) {
      String str = nr.trim();
      criteria.add(Restrictions.like("nr", str, MatchMode.START));
    }
    
    if (werk != null) {
      criteria.createCriteria("lieferantWerke").add(Restrictions.eq("werk", werk));
    }
    
    criteria.addOrder(Order.asc("kurz"));

    criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    
    return criteria.list();

  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public Lieferant find(final String nr) {
    Criteria criteria = getSession().createCriteria(Lieferant.class);

    String str = nr.trim();
    criteria.add(Restrictions.eq("nr", str));

    return (Lieferant)criteria.setMaxResults(1).uniqueResult();

  }

}