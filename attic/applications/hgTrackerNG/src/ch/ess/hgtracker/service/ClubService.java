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
public class ClubService {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional(readOnly = true) 
  public Club getClub(int clubId) {
    Criteria crit = sessionFactory.getCurrentSession().createCriteria(Club.class);
    crit.add(Restrictions.eq("id", clubId));
    return (Club)crit.uniqueResult();
  }
  
  @Transactional(readOnly = true) 
  public Club getClub(String webLogon) {
    Criteria crit = sessionFactory.getCurrentSession().createCriteria(Club.class);
    crit.add(Restrictions.eq("webLogin", webLogon));
    return (Club)crit.uniqueResult();
  }  
  
  @Transactional(readOnly = true)
  public boolean existsBenutzername(Club club, String benutzername) {    
    Criteria crit = sessionFactory.getCurrentSession().createCriteria(Club.class);
    crit.add(Restrictions.ne("id", club.getId()));
    crit.add(Restrictions.eq("benutzername", benutzername));
    return crit.uniqueResult() != null;
  }
  
  @Transactional
  public void update(Club club) {
    sessionFactory.getCurrentSession().merge(club);
  }
  
  @Transactional
  public void insert(Club club) {
    sessionFactory.getCurrentSession().merge(club);
  }
  
  
  @Transactional
  public void insertExampleArt(Club club) {
    
    Criteria crit = sessionFactory.getCurrentSession().createCriteria(Art.class);
    crit.add(Restrictions.eq("club", club));
    if (crit.list().isEmpty()) {
    
      Art art = new Art();
      art.setClub(club);
      art.setMeisterschaft(true);
      art.setSpielArt("Meisterschaft");
      sessionFactory.getCurrentSession().save(art);
      
      
      art = new Art();
      art.setClub(club);
      art.setMeisterschaft(false);
      art.setSpielArt("Freundschaftsspiel");
      sessionFactory.getCurrentSession().save(art);
    }    
  }
  
  @Transactional
  public void deleteClub(int id) {
    Club club = (Club)sessionFactory.getCurrentSession().load(Club.class, id);
    sessionFactory.getCurrentSession().delete(club);
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Club> getAll() {
    Criteria crit = sessionFactory.getCurrentSession().createCriteria(Club.class);
    return crit.list();
  }


  

  
}
