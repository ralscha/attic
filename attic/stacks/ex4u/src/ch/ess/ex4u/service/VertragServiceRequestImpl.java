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
import ch.ess.ex4u.entity.User;
import ch.ess.ex4u.entity.Vertrag;
import ch.ess.ex4u.entity.Zeit;
import ch.ess.ex4u.entity.Zielgefaess;
import ch.ess.ex4u.shared.PrincipalDetail;
import ch.ess.ex4u.shared.VertragRPC;
import ch.ess.ex4u.shared.VertragServiceRequest;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.ex4u.shared.ZielgefaessRPC;
import com.isomorphic.log.Logger;

// server data request handler

@SuppressWarnings({"unused", "unchecked"})
@Named
public class VertragServiceRequestImpl implements VertragServiceRequest {

  Logger log = new Logger(RoleService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public ArrayList<VertragRPC> fetch(Long userId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Vertrag.class);
    if (userId != null) {
      criteria.createCriteria("emas").add(Restrictions.eq("id", userId));
    }
    ArrayList<VertragRPC> vertraegeRPC = new ArrayList<VertragRPC>();
    List<Vertrag> matchingItems = criteria.list();

    for (Vertrag vertrag : matchingItems) {
      VertragRPC vertragRPC = vertrag.getVertragRPC();
      vertraegeRPC.add(vertragRPC);
    }
    return vertraegeRPC;
  }

  @Override
  @Transactional(readOnly = true)
  public HashMap<String, String> fetchAsValueMap(Long userId) {
    HashMap<String, String> valueMap = new HashMap<String, String>();
    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Vertrag.class);
    if (userId != null) {
      criteria.createCriteria("emas").add(Restrictions.eq("id", userId));
    }
    ArrayList<Vertrag> matchingItems = (ArrayList<Vertrag>)criteria.list();
    for (Vertrag vertrag : matchingItems) {
      valueMap.put(vertrag.getId().toString(), vertrag.getVertragsnummer() + ", " + vertrag.getVertragsname());
    }
    return valueMap;
  }

  @Override
  public Boolean remove(Long vertragId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean add(VertragRPC vertrag) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean update(VertragRPC vertrag) {
    // TODO Auto-generated method stub
    return null;
  }

}
