package ch.ess.lbw.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ch.ess.base.dao.AbstractDao;
import ch.ess.lbw.model.Bewertung;
import ch.ess.lbw.model.Lieferant;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "bewertungDao", autowire = Autowire.BYTYPE)
public class BewertungDao extends AbstractDao<Bewertung> {
  
  public BewertungDao() {
    super(Bewertung.class);
  }

  public Bewertung find(Lieferant lieferant, int quartal, int jahr, String werkId) {
    
    Criteria criteria = getSession().createCriteria(Bewertung.class);

    criteria.add(Restrictions.eq("quartal", quartal));
    criteria.add(Restrictions.eq("jahr", jahr));
    criteria.add(Restrictions.eq("lieferant", lieferant));
    criteria.add(Restrictions.eq("werk.id", Integer.parseInt(werkId)));
    
    
    return (Bewertung)criteria.setMaxResults(1).uniqueResult();
    
  }

}
