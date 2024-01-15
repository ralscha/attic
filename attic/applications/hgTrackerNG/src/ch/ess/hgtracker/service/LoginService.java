package ch.ess.hgtracker.service;

import java.util.Date;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hgtracker.db.AccessLog;
import ch.ess.hgtracker.db.Club;

@Component
public class LoginService {

  @Autowired
  private SessionFactory sessionFactory;
  
  @Transactional
  public Club login(String username, String password) {
    
    if ("admin".equals(username) && "gorgonzola7".equals(password)) {
      Club c = new Club();
      c.setAdmin(true);
      return c;
    }
    
    
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Club.class);
    criteria.add(Restrictions.eq("benutzername", username));
    criteria.add(Restrictions.eq("passwort", password));
    Club club = (Club)criteria.uniqueResult();

    
    if (club != null) {
      AccessLog log = new AccessLog();
      log.setClub(club);
      log.setSessionId(UUID.randomUUID().toString());
      club.setSessionId(log.getSessionId());
      log.setLogIn(new Date());
      log.setLogOut(null);
      sessionFactory.getCurrentSession().save(log);
    }
    
    return club;
  }
  
  @Transactional
  public void doLogout(String sessionId) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccessLog.class);
    criteria.add(Restrictions.eq("sessionId", sessionId));
    AccessLog log = (AccessLog)criteria.uniqueResult();
    
    if (log != null) {
      log.setLogOut(new Date());
    }    
  }
  
}
