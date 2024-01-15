package ch.ess.ex4u.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.Role;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.log.Logger;
import com.isomorphic.util.DataTools;
import com.isomorphic.util.ErrorReport;

@Named
public class RoleService {

  Logger log = new Logger(RoleService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Transactional
  
  public DSResponse fetch(DSRequest dsRequest) throws Exception {

    log.info("procesing DMI fetch operation");
    DSResponse dsResponse = new DSResponse();

    Session hibernateSession = (Session)entityManager.getDelegate();

    // DataSource protocol: get filter criteria
    String name = (String)dsRequest.getFieldValue("name");

    Criteria criteria = hibernateSession.createCriteria(Role.class);
    
    Criterion nameRestriction = null;
    if (name != null) {
      nameRestriction = Restrictions.like("name", name, MatchMode.ANYWHERE);
      criteria.add(nameRestriction);
    }

    // DataSource protocol: get filter criteria "all roles assigned to a specific user"
    String userIdStr = (String)dsRequest.getFieldValue("userId");
    if (userIdStr != null) {
      Long userId = Long.parseLong(userIdStr);
      criteria.createCriteria("users").add(Restrictions.eq("id", userId));
    }

    // DataSource protocol: get filter criteria "all roles NOT assigned to a specific user"
    userIdStr = (String)dsRequest.getFieldValue("notUserId");
    if (userIdStr != null) {
      Long userId = Long.parseLong(userIdStr);
      
      criteria.add(Restrictions.sqlRestriction("not exists (select * from UserRoles where roleId = this_.id and userId = " + userId + ")"));
      
      //criteria.createCriteria("users", CriteriaSpecification.LEFT_JOIN).add(Restrictions.ne("id", userId));
      //criteria.createCriteria("users").add(Restrictions.ne("id", userId));
    }

    List<Role> matchingItems = criteria.list();
    
    String debugMsg = "Roles: ";
    for (Role role : matchingItems) {
      debugMsg += role.getName() + ", ";
    }
    System.out.println(debugMsg);

    // DataSource protocol: return matching item beans
    dsResponse.setData(matchingItems);
    // tell client what rows are being returned, and what's available
    dsResponse.setStartRow(0);
    dsResponse.setEndRow(matchingItems.size());
    dsResponse.setTotalRows(matchingItems.size());

    return dsResponse;
  }

  @Transactional

  public DSResponse add(DSRequest dsRequest, Role item) throws Exception {

    log.info("procesing DMI add operation");

    DSResponse dsResponse = new DSResponse();

    // perform validation
    ErrorReport errorReport = dsRequest.getDataSource().validate(DataTools.getProperties(item), false);
    if (errorReport != null) {
      dsResponse.setStatus(DSResponse.STATUS_VALIDATION_ERROR);
      dsResponse.setErrorReport(errorReport);
      System.out.println("Errors: " + DataTools.prettyPrint(errorReport));
      return dsResponse;
    }

    Session hibernateSession = (Session)entityManager.getDelegate();
    hibernateSession.saveOrUpdate(item);
    dsResponse.setData(item);
    return dsResponse;
  }

  @SuppressWarnings("unchecked")
  @Transactional
  
  public DSResponse update(DSRequest dsRequest, Map newValues) throws Exception {

    log.info("procesing DMI update operation");

    DSResponse dsResponse = new DSResponse();

    // perform validation
    ErrorReport errorReport = dsRequest.getDataSource().validate(newValues, false);
    if (errorReport != null) {
      dsResponse.setStatus(DSResponse.STATUS_VALIDATION_ERROR);
      dsResponse.setErrorReport(errorReport);
      System.out.println("Errors: " + DataTools.prettyPrint(errorReport));
      return dsResponse;
    }

    // primary key
    Serializable id = (Serializable)dsRequest.getFieldValue("id");

    Session hibernateSession = (Session)entityManager.getDelegate();
    Role item = (Role)hibernateSession.get(Role.class, id);

    log.warn("fetched item: " + DataTools.prettyPrint(item));

    // apply new values to the as-saved bean
    DataTools.setProperties(newValues, item);

    log.warn("Saving record: " + DataTools.prettyPrint(item));

    // persist
    hibernateSession.saveOrUpdate(item);
    dsResponse.setData(item);
    return dsResponse;
  }

  @Transactional
  
  public Role remove(Role item) throws Exception {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Role user = (Role)hibernateSession.get(Role.class, item.getId());
    hibernateSession.delete(user);
    //entityManager.remove(item);    
    return user;
  }

}
