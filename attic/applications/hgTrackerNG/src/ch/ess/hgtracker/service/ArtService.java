package ch.ess.hgtracker.service;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;

@Component
public class ArtService {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Art> getAll(Club club) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Art.class);
    criteria.add(Restrictions.eq("club", club));
    List<Art> artList = criteria.list();
    for (Art art : artList) {
      art.setAnzahlSpiele(art.getSpiele().size());
    }
    return artList;
  }

  @Transactional
  public void deleteArt(int id) {
    Art art = (Art)sessionFactory.getCurrentSession().load(Art.class, id);
    sessionFactory.getCurrentSession().delete(art);
  }
  
  @Transactional
  public void update(Art art) {
    sessionFactory.getCurrentSession().merge(art);
  }
  
  @Transactional
  public void insert(Art art) {
    update(art);
  }
  
  
  
}
