package ch.ess.cal.persistence.hibernate;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import ch.ess.cal.model.ResourceGroup;
import ch.ess.cal.persistence.ResourceGroupDao;

/**
 * @author sr
 * @version $Revision: 1.5 $ $Date: 2005/04/04 11:31:10 $
 * 
 * @spring.bean id="resourceGroupDao" parent="txProxyTemplate"
 * @spring.property name="target" inner="true"
 * 
 * @spring.inner.property name="sessionFactory" ref="sessionFactory"
 * @spring.inner.property name="clazz" value="ch.ess.cal.model.ResourceGroup"
 */
public class ResourceGroupDaoHibernate extends AbstractDaoHibernate implements ResourceGroupDao {

  public List find(final Locale locale, final String resourceGroupName) {

    if (StringUtils.isNotBlank(resourceGroupName)) {

      HibernateCallback callback = new HibernateCallback() {

        public Object doInHibernate(Session session) throws HibernateException {

          Query queryObject = session
              .createQuery("select rg from ResourceGroup rg inner join rg.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
          queryObject.setLocale("locale", locale);
          queryObject.setString("text", resourceGroupName + "%");
          return queryObject.list();

        }
      };

      return getHibernateTemplate().executeFind(callback);
    }

    //wenn kein resourceGroupName angegeben wurde
    return list();
  }

  public int getResourcesSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        ResourceGroup resourceGroup = (ResourceGroup)get(id);
        return size(session, resourceGroup.getResources());
      }
    };

    return ((Integer)getHibernateTemplate().execute(callback)).intValue();
  }

  @Override
  public boolean canDelete(String id) {
    return (getResourcesSize(id) == 0);
  }
}