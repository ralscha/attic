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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.Vertrag;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.log.Logger;
import com.isomorphic.util.DataTools;
import com.isomorphic.util.ErrorReport;

@Named
public class VertragService {

  Logger log = new Logger(VertragService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Transactional
  public DSResponse fetch(DSRequest dsRequest) throws Exception {

    log.info("procesing DMI fetch operation");
    DSResponse dsResponse = new DSResponse();

    Session hibernateSession = (Session)entityManager.getDelegate();

    // DataSource protocol: get filter criteria
    String vertragsNummer = (String)dsRequest.getFieldValue("vertragsNummer");

    // DataSource protocol: get requested row range
    long startRow = (int)dsRequest.getStartRow();
    long endRow = (int)dsRequest.getEndRow();

    Criteria criteria = hibernateSession.createCriteria(Vertrag.class);
    Criterion vertragsNummerRestriction = null;
    if (vertragsNummer != null) {
      vertragsNummerRestriction = Restrictions.like("vertragsNummer", vertragsNummer, MatchMode.ANYWHERE);
      criteria.add(vertragsNummerRestriction);
    }

    // determine total available rows
    // this is used by e.g. the ListGrid to auto-size its scrollbar
    criteria.setProjection(Projections.rowCount());
    Long rowCount = (Long)criteria.uniqueResult();
    long totalRows = rowCount == null ? 0 : rowCount.intValue();

    // clamp endRow to available rows and slice out requested range
    endRow = Math.min(endRow, totalRows);

    // rebuilt the criteria minus the rowCount projection
    criteria = hibernateSession.createCriteria(Vertrag.class);
    if (vertragsNummer != null)
      criteria.add(vertragsNummerRestriction);

    // limit number of rows returned to just what the ListGrid asked for
    criteria.setFirstResult((int)startRow);
    criteria.setMaxResults((int)(endRow - startRow));
    List matchingItems = criteria.list();

    // DataSource protocol: return matching item beans
    dsResponse.setData(matchingItems);
    // tell client what rows are being returned, and what's available
    dsResponse.setStartRow(startRow);
    dsResponse.setEndRow(endRow);
    dsResponse.setTotalRows(totalRows);

    return dsResponse;
  }

  @Transactional
  public DSResponse add(DSRequest dsRequest, Vertrag item) throws Exception {

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
    Vertrag item = (Vertrag)hibernateSession.get(Vertrag.class, id);

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
  public Vertrag remove(Vertrag item) throws Exception {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Vertrag user = (Vertrag)hibernateSession.get(Vertrag.class, item.getId());
    hibernateSession.delete(user);
    //entityManager.remove(item);    
    return user;
  }

}
