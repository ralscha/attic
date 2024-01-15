package ch.ess.hgtracker.service;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spieler;

@Component
public class SpielerService {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Spieler> getAll(Club club) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spieler.class);
    criteria.add(Restrictions.eq("club", club));
    return criteria.list();
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Spieler> getAllOrder(Club club) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spieler.class);
    criteria.add(Restrictions.eq("club", club));
    criteria.addOrder(Order.asc("nachname"));
    criteria.addOrder(Order.asc("vorname"));    
    return criteria.list();
  }  

  @Transactional
  public void deleteSpieler(int id) {
    Spieler spieler = (Spieler)sessionFactory.getCurrentSession().load(Spieler.class, id);
    sessionFactory.getCurrentSession().delete(spieler);
  }

  @Transactional
  public void update(Spieler spieler) {
    if (spieler.getReihenfolge() != null && spieler.getReihenfolge().intValue() == 0) {
      spieler.setReihenfolge(null);
    }
    sessionFactory.getCurrentSession().merge(spieler);
  }

  @Transactional
  public void insert(Spieler spieler) {
    update(spieler);
  }
  
}
