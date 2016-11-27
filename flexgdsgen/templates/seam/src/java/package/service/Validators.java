package @packageProject@.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

@TideEnabled
@Name("validators")
public class Validators {

  @In
  private EntityManager entityManager;

  @Transactional
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
