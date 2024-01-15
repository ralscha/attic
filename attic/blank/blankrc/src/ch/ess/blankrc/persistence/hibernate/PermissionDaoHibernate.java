package ch.ess.blankrc.persistence.hibernate;

import org.springframework.dao.DataAccessException;

import ch.ess.blankrc.persistence.PermissionDao;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="permissionDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.blankrc.model.Permission"
 */
public class PermissionDaoHibernate extends BaseDaoHibernate implements PermissionDao {

  public void delete(Integer id) throws DataAccessException {
    throw new UnsupportedOperationException();
  }

}