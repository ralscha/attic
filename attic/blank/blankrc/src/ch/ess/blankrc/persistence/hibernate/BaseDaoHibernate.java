package ch.ess.blankrc.persistence.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import ch.ess.blankrc.persistence.Dao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public abstract class BaseDaoHibernate extends HibernateDaoSupport implements Dao {

  Class clazz;

  public Class getClazz() {
    return clazz;
  }

  public void setClazz(Class clazz) {
    this.clazz = clazz;
  }

  public void save(Object baseObject) throws DataAccessException {
    getHibernateTemplate().saveOrUpdate(baseObject);
  }

  public void delete(Object baseObject) throws DataAccessException {
    getHibernateTemplate().delete(baseObject);
  }

  public void delete(final Integer id) throws DataAccessException {

    final String deleteQuery = "from " + clazz.getName() + " as obj where obj.id = ?";

    getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        session.delete(deleteQuery, id, Hibernate.INTEGER);
        return null;
      }
    });

  }

  public List list() throws DataAccessException {
    return getHibernateTemplate().loadAll(clazz);
  }

  public List listAndInitCollections(final String[] collections) {
    return getHibernateTemplate().executeFind(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = getHibernateTemplate().createCriteria(session, clazz);
        List resultList = criteria.list();
        for (Iterator it = resultList.iterator(); it.hasNext();) {
          BaseDaoHibernate.this.initialize(it.next(), collections);
        }
        return resultList;
      }
    });
  }

  public int getSize(final Integer id, final String collection) throws DataAccessException {

    HibernateCallback callback = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {

        Object dbObj = session.get(clazz, id);

        Object collectionObj = null;

        try {
          collectionObj = PropertyUtils.getProperty(dbObj, collection);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        } catch (InvocationTargetException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        }

        Integer size = ((Integer)session.createFilter(collectionObj, "select count(*)").iterate().next());
        return size;

      }
    };

    return ((Integer)getHibernateTemplate().execute(callback)).intValue();

  }

  public Object get(Integer id) {
    return getHibernateTemplate().get(clazz, id);
  }

  public Object getAndInitCollections(final Integer id, final String[] collections) {

    return getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Object dbObj = session.get(clazz, id);
        BaseDaoHibernate.this.initialize(dbObj, collections);
        return dbObj;
      }
    });
  }

  public void deleteAll() throws DataAccessException {

    final String deleteQuery = "from " + clazz.getName();

    getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        session.delete(deleteQuery);
        return null;
      }
    });
  }

  void initialize(Object dbObj, String[] collections) throws HibernateException {
    if (collections != null) {
      for (int i = 0; i < collections.length; i++) {
        try {
          Hibernate.initialize(PropertyUtils.getProperty(dbObj, collections[i]));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        } catch (InvocationTargetException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
          throw new HibernateException(e);
        }
      }
    }
  }

}