package ch.ess.ex4u.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.Vertrag;
import ch.ess.ex4u.entity.Zeit;
import ch.ess.ex4u.entity.Zielgefaess;
import ch.ess.ex4u.shared.VertragRPC;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.ex4u.shared.ZielgefaessRPC;
import ch.ess.ex4u.shared.ZielgefaessServiceRequest;
import com.isomorphic.log.Logger;

// server data request handler

@SuppressWarnings({"unused", "unchecked"})
@Named
public class ZielgefaessServiceRequestImpl implements ZielgefaessServiceRequest {
                                                        
  Logger log = new Logger(RoleService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public ArrayList<ZielgefaessRPC> fetch(Long vertragId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Zielgefaess.class);
    if (vertragId != null) {
      criteria.createCriteria("vertraege").add(Restrictions.eq("id", vertragId));
    }
    ArrayList<ZielgefaessRPC> zielgefaesseRPC = new ArrayList<ZielgefaessRPC>();
    List<Zielgefaess> matchingItems = criteria.list();
    
    for (Zielgefaess zielgefaess : matchingItems) {
      ZielgefaessRPC zielgefaessRPC = zielgefaess.getZielgefaessRPC();
      zielgefaesseRPC.add(zielgefaessRPC);
    }
    return zielgefaesseRPC;
  }

  @Override
  @Transactional(readOnly = true)
  public HashMap<String, String> fetchAsValueMap(Long vertragId) {
    HashMap<String, String> valueMap = new HashMap<String, String>();
    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Zielgefaess.class);
    if (vertragId != null) {
      criteria.createCriteria("vertraege").add(Restrictions.eq("id", vertragId));
    }
    ArrayList<Zielgefaess> matchingItems = (ArrayList<Zielgefaess>)criteria.list();
    for (Zielgefaess zielgefaess : matchingItems) {
      valueMap.put(zielgefaess.getId().toString(), zielgefaess.getNummer() + ", " + zielgefaess.getBeschreibung());
    }
    return valueMap;
  }

  @Override
  public Boolean add(ZielgefaessRPC zielgefaess) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean remove(Long zielgefaessId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean update(ZielgefaessRPC zielgefaess) {
    // TODO Auto-generated method stub
    return null;
  }

}
