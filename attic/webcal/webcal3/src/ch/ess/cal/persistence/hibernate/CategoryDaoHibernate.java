package ch.ess.cal.persistence.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.Category;
import ch.ess.cal.persistence.CategoryDao;

/**
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/06/07 18:42:53 $
 * 
 * @spring.bean id="categoryDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.Category"
 */
public class CategoryDaoHibernate extends AbstractDaoHibernate<Category> implements CategoryDao {

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<Category> find(final Locale locale, final String categoryName) {

    if (StringUtils.isNotBlank(categoryName)) {

      HibernateCallback callback = new HibernateCallback() {

        public Object doInHibernate(Session session) throws HibernateException {

          Query queryObject = session
              .createQuery("select c from Category c inner join c.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
          queryObject.setLocale("locale", locale);
          queryObject.setString("text", categoryName + "%");
          return queryObject.list();

        }
      };

      return getHibernateTemplate().executeFind(callback);
    }

    //wenn kein resourceGroupName angegeben wurde
    return list();

  }

  @Transactional(readOnly=true)
  public int getEventsSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Category category = get(id);
        return size(session, category.getEventCategory());
      }
    };

    return ((Integer)getHibernateTemplate().execute(callback)).intValue();
  }

  @Transactional(readOnly=true)
  public int getSize(final String id) {

    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        if (StringUtils.isNotBlank(id)) {
          Query queryObject = session.createQuery("select count(*) from Category c where c.id <> :categoryId");
          queryObject.setInteger("categoryId", new Integer(id));
          Object o = queryObject.setMaxResults(1).uniqueResult();
          return o;

        }
        Query queryObject = session.createQuery("select count(*) from Category");
        Object o = queryObject.setMaxResults(1).uniqueResult();
        return o;

      }
    };

    Integer result = (Integer)getHibernateTemplate().execute(callback);
    if (result != null) {
      return result.intValue();
    }
    return 0;
  }

  @Transactional
  public void updateTurnOffUnknownFlag(final String id) {

    List categoryList = list();
    for (Iterator it = categoryList.iterator(); it.hasNext();) {
      Category category = (Category)it.next();

      if (!category.getId().toString().equals(id)) {
        category.setUnknown(false);
      } else {
        category.setUnknown(true);
      }

    }
  }

  @Override
  @Transactional
  public boolean canDelete(String id) {
    return (getEventsSize(id) == 0);
  }

}
