package ch.ess.lbw.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.lbw.model.Werk;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "werkDao", autowire = Autowire.BYTYPE)
public class WerkDao extends AbstractDao<Werk> {

  public WerkDao() {
    super(Werk.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Werk> find(final String name) {
    Criteria criteria = getSession().createCriteria(Werk.class);

    if (StringUtils.isNotBlank(name)) {
      String str = name.trim();
      criteria.add(Restrictions.like("name", str, MatchMode.START));
    }
    
    return criteria.list();

  }

  
}