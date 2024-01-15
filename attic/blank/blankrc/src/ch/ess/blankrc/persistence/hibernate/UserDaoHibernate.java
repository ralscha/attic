package ch.ess.blankrc.persistence.hibernate;

import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate.HibernateCallback;

import ch.ess.blankrc.model.User;
import ch.ess.blankrc.persistence.UserDao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="userDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.blankrc.model.User"
 */
public class UserDaoHibernate extends BaseDaoHibernate implements UserDao {

  public User find(String userName, String token) throws DataAccessException {

    List userList = getHibernateTemplate().findByNamedQuery("findWithUserNameAndPassword",
        new String[]{userName, token});

    if (userList.size() > 0) {
      return (User)userList.get(0);
    }
    return null;
  }

  public User find(String userName) throws DataAccessException {
    List userList = getHibernateTemplate().findByNamedQuery("findWithUserName", userName);
    if (userList.size() > 0) {
      return (User)userList.get(0);
    }
    return null;
  }

  public User findExcludeId(final String userName, final Integer id) throws DataAccessException {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = getHibernateTemplate().createCriteria(session, User.class);

        criteria.add(Expression.eq("userName", userName));

        if (id != null) {
          criteria.add(Expression.not(Expression.eq("id", id)));
        }

        return criteria.list();

      }

    };

    List userList = getHibernateTemplate().executeFind(callback);

    if (userList.size() > 0) {
      return (User)userList.get(0);
    }
    return null;

  }

}