package ch.ess.hgtracker.dao;

import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.test.hibernate.BaseObject;

public abstract class AbstractDao<T extends BaseObject> {

  private SessionFactory sessionFactory;
  private Class<T> persistentClass;

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public AbstractDao(final Class<T> persistentClass) {
    this.persistentClass = persistentClass;
  }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public T newInstance() throws InstantiationException, IllegalAccessException {
    return persistentClass.newInstance();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findByCriteria(final Criterion... criterion) {
    Criteria crit = createCriteria(criterion);
    return crit.list();
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<T> findByCriteriaOrderBy(final Order order, final Criterion... criterion) {
    Criteria crit = createCriteria(criterion);

    if (order != null) {
      crit.addOrder(order);
    }

    return crit.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public T findByCriteriaUnique(final Criterion... criterion) {
    Criteria crit = createCriteria(criterion);
    return (T)crit.setMaxResults(1).uniqueResult();
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public T findByCriteriaOrderByUnique(final Order order, final Criterion... criterion) {
    Criteria crit = createCriteria(criterion);

    if (order != null) {
      crit.addOrder(order);
    }

    return (T)crit.setMaxResults(1).uniqueResult();
  }

  private Criteria createCriteria(final Criterion... criterion) {
    Criteria crit = getSession().createCriteria(persistentClass);
    for (Criterion c : criterion) {
      crit.add(c);
    }
    return crit;
  }

  @Transactional(readOnly = true)
  public List<T> findAll() {
    return findByCriteria();
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<T> findAllOrderBy(Order order) {
    Criteria crit = getSession().createCriteria(persistentClass);
    crit.addOrder(order);
    return crit.list();
  }

  @Transactional(readOnly = true)
  public List<T> findByExample(final T exampleInstance) {
    return findByCriteria(Example.create(exampleInstance));
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public T findById(final String id) {
    return (T)getSession().load(persistentClass, new Integer(id));
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public T getById(final String id) {
    return (T)getSession().get(persistentClass, new Integer(id));
  }

  @Transactional
  public void save(final T baseObject) {
    getSession().saveOrUpdate(baseObject);
  }

  @Transactional
  public void delete(final T baseObject) {
    getSession().delete(baseObject);
  }

  @Transactional
  public void delete(final String id) {
    delete(findById(id));
  }

  @Transactional
  public void deleteAll() {
    Query deleteQuery = getSession().createQuery("delete from " + persistentClass.getName());
    deleteQuery.executeUpdate();
  }

  @Transactional
  public boolean canDelete(@SuppressWarnings("unused")
  final T baseObject) {
    return true;
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public Long size(final Collection collection) {
    return ((Long)getSession().createFilter(collection, "select count(*)").iterate().next());
  }

}
