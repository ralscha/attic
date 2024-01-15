package ch.ess.test;

// Generated 13-Jul-2006 12:08:03 by Hibernate Tools 3.1.0.beta5

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Part.
 * @see ch.ess.test.Part
 * @author Hibernate Tools
 */
public class PartHome {

  private static final Log log = LogFactory.getLog(PartHome.class);

  private final SessionFactory sessionFactory = getSessionFactory();

  protected SessionFactory getSessionFactory() {
    try {
      return (SessionFactory)new InitialContext().lookup("SessionFactory");
    } catch (Exception e) {
      log.error("Could not locate SessionFactory in JNDI", e);
      throw new IllegalStateException("Could not locate SessionFactory in JNDI");
    }
  }

  public void persist(Part transientInstance) {
    log.debug("persisting Part instance");
    try {
      sessionFactory.getCurrentSession().persist(transientInstance);
      log.debug("persist successful");
    } catch (RuntimeException re) {
      log.error("persist failed", re);
      throw re;
    }
  }

  public void attachDirty(Part instance) {
    log.debug("attaching dirty Part instance");
    try {
      sessionFactory.getCurrentSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(Part instance) {
    log.debug("attaching clean Part instance");
    try {
      sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void delete(Part persistentInstance) {
    log.debug("deleting Part instance");
    try {
      sessionFactory.getCurrentSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public Part merge(Part detachedInstance) {
    log.debug("merging Part instance");
    try {
      Part result = (Part)sessionFactory.getCurrentSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public Part findById(int id) {
    log.debug("getting Part instance with id: " + id);
    try {
      Part instance = (Part)sessionFactory.getCurrentSession().get("ch.ess.test.Part", id);
      if (instance == null) {
        log.debug("get successful, no instance found");
      } else {
        log.debug("get successful, instance found");
      }
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  public List findByExample(Part instance) {
    log.debug("finding Part instance by example");
    try {
      List results = sessionFactory.getCurrentSession().createCriteria("ch.ess.test.Part")
          .add(Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }
}
