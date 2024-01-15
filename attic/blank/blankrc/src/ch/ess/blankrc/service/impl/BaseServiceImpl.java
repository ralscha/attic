package ch.ess.blankrc.service.impl;

import java.util.List;

import ch.ess.blankrc.persistence.Dao;
import ch.ess.blankrc.service.BaseService;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public class BaseServiceImpl implements BaseService {

  private Dao dao;

  protected Dao getDao() {
    return dao;
  }

  public void setDao(Dao dao) {
    this.dao = dao;
  }

  public List list() {
    return dao.list();
  }

  public Object get(Integer id) {
    return dao.get(id);
  }

  public void delete(Integer id) {
    dao.delete(id);

  }

  public void save(Object obj) {
    dao.save(obj);
  }

  public int getSize(Integer id, String collection) {
    return dao.getSize(id, collection);
  }

}