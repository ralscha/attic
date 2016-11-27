package @packageProject@.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.granite.tide.annotations.TideEnabled;
import org.springframework.transaction.annotation.Transactional;

@TideEnabled
public class DefaultPagedQueryFinder implements PagedQueryFinder {

  private static final Pattern PARAMETER_NAME_PATTERN = Pattern.compile(":\\w*");

  @PersistenceContext
  private EntityManager entityManager;

  private int maxResults;
  private String select;
  private String from;

  private Map<String, String> parameterRestrictionMap;

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> find(Map<String, Object> filter, int first, int max, String order, boolean desc) {

    int newMax = max;
    if (newMax == 0) {
      newMax = maxResults;
    }

    StringBuilder whereSB = new StringBuilder();

    Set<String> parameters = new HashSet<String>();

    for (String parameter : filter.keySet()) {
      if (filter.get(parameter) != null) {
        String restriction = parameterRestrictionMap.get(parameter);
        if (restriction != null) {
  
          if (whereSB.length() > 0) {
            whereSB.append(" and ");
          }
          whereSB.append(restriction);
  
          parameters.add(parameter);
        }
      }
    }

    String where; 
    if (whereSB.length() > 0) {
      where = " where " + whereSB;
    } else {
      where = "";
    }

    Query countQuery = entityManager.createQuery("select count(" + select + ") from " + from + where);
    for (String parameter : parameters) {
      countQuery.setParameter(parameter, filter.get(parameter));
    }
    long resultCount = (Long)countQuery.getSingleResult();

    String orderBy = order != null ? " order by " + select + "." + order + (desc ? " desc" : "") : "";

    Query resultQuery = entityManager.createQuery("select " + select + " from " + from + where + orderBy);
    resultQuery.setFirstResult(first);
    resultQuery.setMaxResults(newMax);
    for (String parameter : parameters) {
      resultQuery.setParameter(parameter, filter.get(parameter));
    }

    List resultList = resultQuery.getResultList();
    Map<String, Object> result = new HashMap<String, Object>(4);
    result.put("firstResult", first);
    result.put("maxResults", newMax);
    result.put("resultCount", resultCount);
    result.put("resultList", resultList);

    return result;

  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }

  public void setRestrictions(List<String> restrictions) {
    parameterRestrictionMap = new HashMap<String, String>();
    for (String restriction : restrictions) {
      Matcher m = PARAMETER_NAME_PATTERN.matcher(restriction);
      if (m.find()) {
        parameterRestrictionMap.put(m.group().substring(1), restriction);
      }
    }
  }

  public void setSelect(String select) {
    this.select = select;
  }

  public void setFrom(String from) {
    this.from = from;
  }

}
