package ch.ess.ex4u.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.User;
import ch.ess.ex4u.entity.Vertrag;
import ch.ess.ex4u.entity.Zeit;
import ch.ess.ex4u.entity.Zielgefaess;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.ex4u.shared.ZeitServiceRequest;
import com.isomorphic.log.Logger;

// server data request handler

@SuppressWarnings("unchecked")
@Named
public class ZeitServiceRequestImpl implements ZeitServiceRequest {

  Logger log = new Logger(RoleService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public ArrayList<ZeitRPC> fetch(Long userId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    ArrayList<ZeitRPC> zeitenRPC = new ArrayList<ZeitRPC>();
    List<Zeit> matchingItems = null;
    Criteria criteria = hibernateSession.createCriteria(Zeit.class);
    if (userId != null) {
      criteria.createCriteria("user").add(Restrictions.eq("id", userId));
    }
    matchingItems = criteria.list();
    for (Zeit zeit : matchingItems) {
      ZeitRPC zeitRPC = zeit.getZeitRPC();
      zeitenRPC.add(zeitRPC);
    }
    return zeitenRPC;
  }

  @Override
  @Transactional
  public Boolean update(ZeitRPC zeitRPC) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    saveOrUpdate(hibernateSession, zeitRPC);
    return true;
  }

  @Override
  @Transactional
  public Boolean add(ZeitRPC zeitRPC) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    saveOrUpdate(hibernateSession, zeitRPC);
    return true;
  }
  
  private void saveOrUpdate(Session hibernateSession, ZeitRPC zeitRPC) {
    Zeit zeit = new Zeit(zeitRPC); 
    User u = (User)hibernateSession.get(User.class, zeitRPC.getUserId());
    zeit.setUser(u);
    Vertrag v = (Vertrag)hibernateSession.get(Vertrag.class, zeitRPC.getVertragId());
    zeit.setVertrag(v);
    Zielgefaess z = (Zielgefaess)hibernateSession.get(Zielgefaess.class, zeitRPC.getZielgefaessId());
    zeit.setZielgefaess(z);
    hibernateSession.saveOrUpdate(zeit);
  }

  @Override
  @Transactional
  public Boolean remove(Long zeitId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Zeit zeit = (Zeit)hibernateSession.get(Zeit.class, zeitId);
    if (zeit != null) {
      hibernateSession.delete(zeit);
      return true;
    }
    return false;
  }

}
