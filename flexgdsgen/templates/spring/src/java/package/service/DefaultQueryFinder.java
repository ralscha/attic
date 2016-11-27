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
import @packageProject@.entity.User;

@TideEnabled
public class DefaultQueryFinder {

  private static final Pattern PARAMETER_NAME_PATTERN = Pattern.compile(":\\w*");

  @PersistenceContext
  private EntityManager entityManager;

  private String select;
  private String from;

  private Map<String, String> parameterRestrictionMap;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<User> find(Map<String, Object> filter) {

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

    Query resultQuery = entityManager.createQuery("select " + select + " from " + from + where);
    for (String parameter : parameters) {
      resultQuery.setParameter(parameter, filter.get(parameter));
    }

    return resultQuery.getResultList();

  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
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
