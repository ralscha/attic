package ch.ess.cal.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.Category;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "categoryDao", autowire = Autowire.BYTYPE)
public class CategoryDao extends AbstractDao<Category> {

  public CategoryDao() {
    super(Category.class);
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Category> find(final Locale locale, final String categoryName) {

    if (StringUtils.isNotBlank(categoryName)) {

      Query queryObject = getSession()
          .createQuery("select c from Category c inner join c.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
      queryObject.setLocale("locale", locale);
      queryObject.setString("text", categoryName + "%");
      return queryObject.list();

    }
    
    return findAll();

  }
  
  @Transactional(readOnly=true)
  public Category getDefaultCategory() {
    return findByCriteriaUnique(Restrictions.eq("defaultCategory", true));
//    
//    HibernateCallback callback = new HibernateCallback() {
//
//      public Object doInHibernate(Session session) throws HibernateException {
//
//        Criteria criteria = session.createCriteria(Category.class);
//        criteria.add(Restrictions.eq("defaultCategory", true));
//        return criteria.setMaxResults(1).uniqueResult();
//      }
//    };
//
//    return (Category)getHibernateTemplate().execute(callback);
  }

  @Transactional(readOnly = true)
  public Category findByICalKey(final String icalkey) {
    return findByCriteriaUnique(Restrictions.eq("icalKey", icalkey));
    
//    HibernateCallback callback = new HibernateCallback() {
//
//      public Object doInHibernate(Session session) throws HibernateException {
//
//        Query queryObject = session.createQuery("select c from Category c where c.icalKey = :icalkey");
//        queryObject.setString("icalkey", icalkey);
//        return queryObject.uniqueResult();
//
//      }
//    };
//
//    return (Category)getHibernateTemplate().execute(callback);
  }

  @Transactional(readOnly = true)
  public long getSize(final String id) {

    Long result = null;
    if (StringUtils.isNotBlank(id)) {
      Query queryObject = getSession().createQuery("select count(*) from Category c where c.id <> :categoryId");
      queryObject.setInteger("categoryId", new Integer(id));
      result = (Long)queryObject.setMaxResults(1).uniqueResult();
    } else {
      Query queryObject = getSession().createQuery("select count(*) from Category");
      result = (Long)queryObject.setMaxResults(1).uniqueResult();
    }
    
    if (result != null) {
      return result;
    }
    return 0;
  }

  @Transactional
  public void updateTurnOffDefaultFlag(final String id) {

    List<Category> categoryList = findAll();
    for (Iterator<Category> it = categoryList.iterator(); it.hasNext();) {
      Category category = it.next();

      if (!category.getId().toString().equals(id)) {
        category.setDefaultCategory(false);
      } else {
        category.setDefaultCategory(true);
      }

    }
  }

  @Transactional(readOnly = true)
  public List<Category> listOrderBySequence() {
    return findAllOrderBy(Order.asc("sequence"));
  }

  @Override
  @Transactional
  public boolean canDelete(final Category category) {
    return (size(category.getEventCategory()) == 0);
  }


}
