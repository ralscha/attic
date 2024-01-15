package ch.ess.base.persistence.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.persistence.UserDao;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $
 * 
 * @spring.bean id="userDao" autowire="byType"
 */
public class UserDaoHibernate extends AbstractDaoHibernate<User> implements UserDao {
    
  @Override
  public Class<User> getClazz() {
    return User.class;
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<User> listOrderByCantonAndName() {
    return getHibernateTemplate().find("from User as u order by u.canton, u.name");
  }

  @Transactional(readOnly=true)
  public User findWithLoginToken(final String token) {
    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query queryObject = session.createQuery("from User as u where u.loginToken = :loginToken");
        queryObject.setString("loginToken", token);
        return queryObject.setMaxResults(1).uniqueResult();
      }
    };

    return (User)getHibernateTemplate().execute(callback);

  }

  @Transactional(readOnly=true)
  public User find(final String userName, final String token) {
    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query queryObject = session
            .createQuery("from User as u where u.userName = :userName AND u.passwordHash = :passwordHash");
        queryObject.setString("userName", userName);
        queryObject.setString("passwordHash", token);
        return queryObject.setMaxResults(1).uniqueResult();
      }
    };

    return (User)getHibernateTemplate().execute(callback);

  }

  @Transactional(readOnly=true)
  public User find(final String userName) {
    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query queryObject = session.createQuery("from User as u where u.userName = :userName");
        queryObject.setString("userName", userName);
        return queryObject.setMaxResults(1).uniqueResult();
      }
    };

    return (User)getHibernateTemplate().execute(callback);

  }
  
  @Transactional(readOnly=true)
  public User findExcludeId(final String userName, final String id) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(User.class);

        criteria.add(Restrictions.eq("userName", userName));

        if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
          criteria.add(Restrictions.not(Restrictions.eq("id", new Integer(id))));
        }

        return criteria.setMaxResults(1).uniqueResult();

      }

    };

    return (User)getHibernateTemplate().execute(callback);
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<User> findWithSearchText(final String searchText, final String userGroupId) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = session.createCriteria(User.class);

        if (StringUtils.isNotBlank(searchText)) {
          String str = searchText.trim();
          criteria.add(Restrictions.or(Restrictions.like("name", str, MatchMode.START), Restrictions.like("userName",
              str, MatchMode.START)));
        }

        if (StringUtils.isNotBlank(userGroupId)) {
          criteria.createCriteria("userUserGroups").createCriteria("userGroup").add(
              Restrictions.eq("id", new Integer(userGroupId)));
        }
        return criteria.list();

      }

    };

    return getHibernateTemplate().executeFind(callback);

  }

  @Transactional(readOnly=true)
  public Set<String> getPermission(final String id) {
    User user = get(id);

    Set<String> permissionsSet = new HashSet<String>();

    Set<UserUserGroup> userUserGroups = user.getUserUserGroups();
    for (UserUserGroup userUserGroup : userUserGroups) {

      UserGroup userGroup = userUserGroup.getUserGroup();
      Set<UserGroupPermission> userGroupPermissions = userGroup.getUserGroupPermissions();
      
      for (UserGroupPermission userGroupPermission : userGroupPermissions) {
        permissionsSet.add(userGroupPermission.getPermission().getPermission());
      }

    }

    return permissionsSet;

  }

}