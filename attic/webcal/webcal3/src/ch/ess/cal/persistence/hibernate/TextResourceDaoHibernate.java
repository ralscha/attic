package ch.ess.cal.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.TextResource;
import ch.ess.cal.persistence.TextResourceDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="textResourceDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.TextResource"
 */
public class TextResourceDaoHibernate extends AbstractDaoHibernate<TextResource> implements TextResourceDao {

  @Transactional(readOnly=true)
  public TextResource find(final String name) {
    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query queryObject = session.createQuery("from TextResource tr where tr.name = :name");
        queryObject.setString("name", name);
        return queryObject.setMaxResults(1).uniqueResult();
      }
    };

    return (TextResource)getHibernateTemplate().execute(callback);

  }

  @Transactional(readOnly=true)
  public String getText(final String name, final String locale) {
    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query queryObject = session
            .createQuery("select tt.text from TextResource tr inner join tr.translation t inner join t.translationTexts tt where tr.name = :name and tt.locale = :locale");
        queryObject.setString("name", name);
        queryObject.setString("locale", locale);
        return queryObject.setMaxResults(1).uniqueResult();
      }
    };

    return (String)getHibernateTemplate().execute(callback);

  }

}
