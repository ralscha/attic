package ch.ess.cal.persistence.hibernate;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import ch.ess.cal.model.Resource;
import ch.ess.cal.persistence.ResourceDao;

/**
 * @author sr
 * @version $Revision: 1.5 $ $Date: 2005/04/04 11:31:10 $
 * 
 * @spring.bean id="resourceDao" parent="txProxyTemplate"
 * @spring.property name="target" inner="true"
 * 
 * @spring.inner.property name="sessionFactory" ref="sessionFactory"
 * @spring.inner.property name="clazz" value="ch.ess.cal.model.Resource"
 */
public class ResourceDaoHibernate extends AbstractDaoHibernate implements ResourceDao {
  public List find(final Locale locale, final String resourceName) {

    if (StringUtils.isNotBlank(resourceName)) {

      HibernateCallback callback = new HibernateCallback() {

        public Object doInHibernate(Session session) throws HibernateException {

          Query queryObject = session
              .createQuery("select r from Resource r inner join r.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
          queryObject.setLocale("locale", locale);
          queryObject.setString("text", resourceName + "%");
          return queryObject.list();

        }
      };

      return getHibernateTemplate().executeFind(callback);
    }

    //wenn kein resourceGroupName angegeben wurde
    return list();

  }

  public int getEventsSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Resource resource = (Resource)get(id);
        return size(session, resource.getEvents());
      }
    };

    return ((Integer)getHibernateTemplate().execute(callback)).intValue();
  }

  @Override
  public boolean canDelete(String id) {
    return (getEventsSize(id) == 0);
  }
}