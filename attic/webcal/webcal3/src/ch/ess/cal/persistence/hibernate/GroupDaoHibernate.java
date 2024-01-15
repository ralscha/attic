package ch.ess.cal.persistence.hibernate;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import ch.ess.cal.model.Group;
import ch.ess.cal.persistence.GroupDao;

/**
 * @author sr
 * @version $Revision: 1.6 $ $Date: 2005/04/04 11:31:10 $
 * 
 * @spring.bean id="groupDao" parent="txProxyTemplate"
 * @spring.property name="target" inner="true"
 * 
 * @spring.inner.property name="sessionFactory" ref="sessionFactory"
 * @spring.inner.property name="clazz" value="ch.ess.cal.model.Group"
 */
public class GroupDaoHibernate extends AbstractDaoHibernate implements GroupDao {
  public List find(final Locale locale, final String groupName) {

    if (StringUtils.isNotBlank(groupName)) {

      HibernateCallback callback = new HibernateCallback() {

        public Object doInHibernate(Session session) throws HibernateException {

          Query queryObject = session
              .createQuery("select g from Group g inner join g.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
          queryObject.setLocale("locale", locale);
          queryObject.setString("text", groupName + "%");
          return queryObject.list();

        }
      };

      return getHibernateTemplate().executeFind(callback);
    }

    //wenn kein groupName angegeben wurde
    return list();
  }

  public int getReferenceSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Group group = (Group)get(id);
        int events = size(session, group.getEvents());
        return new Integer(events);
      }
    };
    return ((Integer)getHibernateTemplate().execute(callback)).intValue();
  }

  @Override
  public boolean canDelete(String id) {
    return (getReferenceSize(id) == 0);
  }
}