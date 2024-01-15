package ch.ess.cal.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.model.Directory;
import ch.ess.cal.model.DirectoryPermission;
import ch.ess.cal.model.Group;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "directoryDao", autowire = Autowire.BYTYPE)
public class DirectoryDao extends AbstractDao<Directory> {

  public DirectoryDao() {
    super(Directory.class);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Directory> findAllRootDirectories(String directoryName) {
    Criteria criteria = getSession().createCriteria(Directory.class);
    criteria.add(Restrictions.isNull("parent"));
    
    if (StringUtils.isNotEmpty(directoryName)) {
      criteria.add(Restrictions.like("name", directoryName, MatchMode.ANYWHERE));
    }
    
    return criteria.list();
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Directory> findDirectories(String directoryName) {
    Criteria criteria = getSession().createCriteria(Directory.class);
    
    if (StringUtils.isNotEmpty(directoryName)) {
      criteria.add(Restrictions.like("name", directoryName, MatchMode.ANYWHERE));
    }
    
    return criteria.list();
  }
  
  public boolean hasWritePermission(Directory directory, User user) {
    Set<DirectoryPermission> permissions = directory.getDirectoryPermission();  
    for (DirectoryPermission permission : permissions) {
      if (permission.getUser() != null) {
        if (permission.getWritePermission()) {
          if (permission.getUser().equals(user)) {          
            return true;
          }
        }
      } else {
        if (permission.getWritePermission()) {
          Group group = permission.getGroup();        
          if (group.getUsers().contains(user)) {
            return true; 
          }
        }
      }
      
    }
    
    return false;
  }
  
  public boolean hasReadOrWritePermission(Directory directory, User user) {
    Set<DirectoryPermission> permissions = directory.getDirectoryPermission();  
    for (DirectoryPermission permission : permissions) {
      if (permission.getUser() != null) {        
        if (permission.getUser().equals(user)) {          
          return true;
        }        
      } else {
        Group group = permission.getGroup();        
        if (group.getUsers().contains(user)) {
          return true; 
        }
      }
    }
    
    return false;
  }
  
}
