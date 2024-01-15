package ch.ess.blankrc.service.impl;

import ch.ess.blankrc.model.UserGroup;
import ch.ess.blankrc.service.UserGroupService;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="userGroupService"
 * @spring.property name="dao" reflocal="userGroupDao"
 */
public class UserGroupServiceImpl extends BaseServiceImpl implements UserGroupService {

  public Object get(Integer id) {
    return (UserGroup)getDao().getAndInitCollections(id, new String[]{"permissions"});
  }

}