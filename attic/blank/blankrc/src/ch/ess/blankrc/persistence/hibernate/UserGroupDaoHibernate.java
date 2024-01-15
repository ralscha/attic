package ch.ess.blankrc.persistence.hibernate;

import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate.HibernateCallback;

import ch.ess.blankrc.model.UserGroup;
import ch.ess.blankrc.persistence.UserGroupDao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="userGroupDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.blankrc.model.UserGroup"
 */
public class UserGroupDaoHibernate extends BaseDaoHibernate implements UserGroupDao {

  public List find(final String name) throws DataAccessException {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = getHibernateTemplate().createCriteria(session, UserGroup.class);
        if (StringUtils.isNotBlank(name)) {
          String n = "%" + name.trim() + "%";
          criteria.add(Expression.like("groupName", n));
        }

        return criteria.list();

      }

    };

    return getHibernateTemplate().executeFind(callback);

  }

}