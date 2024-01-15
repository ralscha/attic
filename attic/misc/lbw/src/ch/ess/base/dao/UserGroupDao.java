package ch.ess.base.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.UserGroup;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "userGroupDao", autowire = Autowire.BYTYPE)
public class UserGroupDao extends AbstractDao<UserGroup> {

  public UserGroupDao() {
    super(UserGroup.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<UserGroup> find(final String name) {
    Criteria criteria = getSession().createCriteria(UserGroup.class);

    if (StringUtils.isNotBlank(name)) {
      String str = name.trim();
      criteria.add(Restrictions.like("groupName", str, MatchMode.START));
    }

    return criteria.list();

  }

  @Override
  @Transactional(readOnly = true)
  public boolean canDelete(final UserGroup userGroup) {
    return size(userGroup.getUserUserGroups()) == 0;
  }
  
}