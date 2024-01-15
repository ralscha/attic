package ch.ess.base.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.AppLink;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "appLinkDao", autowire = Autowire.BYTYPE)
public class AppLinkDao extends AbstractDao<AppLink> {

  public AppLinkDao() {
    super(AppLink.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<AppLink> find(final String name) {
    Criteria criteria = getSession().createCriteria(AppLink.class);

    if (StringUtils.isNotBlank(name)) {
      String str = name.trim();
      criteria.add(Restrictions.like("linkName", str, MatchMode.START));
    }

    return criteria.list();

  }

  @Override
  @Transactional(readOnly = true)
  public boolean canDelete(final AppLink appLink) {
    return true;
  }
  
}
