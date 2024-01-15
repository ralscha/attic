package ch.ess.blankrc.service;

import java.util.List;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public interface BaseService {

  public Object get(Integer id);

  public List list();

  public void save(Object user);

  public void delete(Integer id);

  public int getSize(Integer id, String collection);
}