package ch.ess.base.persistence.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.BaseObject;
import ch.ess.base.persistence.Dao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:22 $
 */
public abstract class AbstractDaoHibernate<T extends BaseObject> extends HibernateDaoSupport implements Dao<T> {


  public abstract Class<T> getClazz() ;

  @Transactional
  public void saveOrUpdate(final T baseObject) {
    getHibernateTemplate().saveOrUpdate(baseObject);
  }

  @Transactional
  public void delete(final T baseObject) {    
    getHibernateTemplate().delete(baseObject);
  }

  @Transactional
  public void delete(final String id) {
    delete(get(id));
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public List<T> list() {
    return getHibernateTemplate().loadAll(getClazz());
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public T get(final String id) {
    return (T)getHibernateTemplate().get(getClazz(), new Integer(id));
  }

  @Transactional
  public void deleteAll() {

    getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query deleteQuery = session.createQuery("delete from " + getClazz().getName());
        deleteQuery.executeUpdate();
        return null;
      }
    });
  }

  @Transactional
  private boolean canDelete(final T baseObject) {
    return canDelete(baseObject.getId().toString());
  }

  public boolean canDelete(String id) {
    return true;
  }

  @Transactional
  public boolean deleteCond(final T baseObject) {
    if (canDelete(baseObject)) {
      delete(baseObject);
      return true;
    }
    return false;
  }

  @Transactional
  public boolean deleteCond(final String id) {
    if (canDelete(id)) {
      delete(id);
      return true;
    }
    return false;
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked")
  public Integer size(final Session session, final Collection collection) throws HibernateException {
    return ((Integer)session.createFilter(collection, "select count(*)").iterate().next());
  }
}