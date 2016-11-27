package @packageProject@.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.granite.messaging.service.annotations.RemoteDestination;
import org.springframework.stereotype.Service;

@Service
@RemoteDestination(id="validators")
public class Validators {

  @PersistenceContext
  private EntityManager entityManager;

  public boolean isPropertyUnique(String entityName, String property, String value, Integer excludeMyselfId) {
    String queryString = "select e from " + entityName + " e where e." + property + " = :value";
    
    if (excludeMyselfId != null) {
      queryString += " and e.id != :id";
    }
    
    Query query = entityManager.createQuery(queryString);
    query.setParameter("value", value);

    if (excludeMyselfId != null) {
      query.setParameter("id", excludeMyselfId);
    }
    
    if (query.getResultList().isEmpty()) {
      return true;
    }
    
    return false;

  }

}
