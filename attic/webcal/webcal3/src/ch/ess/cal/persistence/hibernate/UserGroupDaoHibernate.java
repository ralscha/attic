package ch.ess.cal.persistence.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.UserGroup;
import ch.ess.cal.persistence.UserGroupDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="userGroupDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.UserGroup"
 */
public class UserGroupDaoHibernate extends AbstractDaoHibernate<UserGroup> implements UserGroupDao {

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<UserGroup> find(final String name) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(UserGroup.class);

        if (StringUtils.isNotBlank(name)) {
          String str = name.trim();
          criteria.add(Restrictions.like("groupName", str, MatchMode.START));
        }

        return criteria.list();

      }

    };

    return getHibernateTemplate().executeFind(callback);

  }

  @Transactional(readOnly=true)
  public int getUsersSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        UserGroup userGroup = get(id);
        return size(session, userGroup.getUserUserGroups());
      }
    };

    return ((Integer)getHibernateTemplate().execute(callback)).intValue();
  }

  @Override
  @Transactional(readOnly=true)
  public boolean canDelete(String id) {
    return (getUsersSize(id) == 0);
  }
}
