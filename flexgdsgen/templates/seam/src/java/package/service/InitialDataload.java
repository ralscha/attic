package @packageProject@.service;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Transactional;
import @packageProject@.entity.User;
import @packageProject@.entity.Role;

@Name("initialDataload")
public class InitialDataload {

  @In
  private EntityManager entityManager;

  @Observer("org.jboss.seam.postInitialization")
  @Transactional
  public void init() {

    Role adminRole = null;
    Role userRole = null;
    
    Query query = entityManager.createQuery("select count(*) from Role");
    if ((Long)query.getSingleResult() == 0) {
      Role role = new Role();
      role.setName("admin");
      entityManager.persist(role);
      adminRole = role;
      
      role = new Role();
      role.setName("user");
      entityManager.persist(role);
      userRole = role;
    }     
    
    query = entityManager.createQuery("select count(*) from User");
    if ((Long)query.getSingleResult() == 0) {
      //admin user
      User adminUser = new User();
      adminUser.setEmail("test@test.ch");
      adminUser.setUserName("admin");
      adminUser.setFirstName("admin");
      adminUser.setName("admin");
      adminUser.setPasswordHash(DigestUtils.shaHex("admin"));
      adminUser.setEnabled(true);
      adminUser.setLocale("en_US");
      
      if (adminRole == null) {
        query = entityManager.createQuery("select r from Role r where name = :name");
        query.setParameter("name", "admin"); 
        query.setMaxResults(1);
        adminRole = (Role)query.getSingleResult();      
      }
      
      Set<Role> roles = new HashSet<Role>();
      roles.add(adminRole);
      adminUser.setRoles(roles);
      
      entityManager.persist(adminUser);
      
      //normal user
      User normalUser = new User();
      normalUser.setEmail("user@test.ch");
      normalUser.setUserName("user");
      normalUser.setFirstName("user");
      normalUser.setName("user");
      normalUser.setPasswordHash(DigestUtils.shaHex("user"));
      normalUser.setEnabled(true);
      normalUser.setLocale("de_CH");
      
      if (userRole == null) {
        query = entityManager.createQuery("select r from Role r where name = :name");
        query.setParameter("name", "user"); 
        query.setMaxResults(1);
        userRole = (Role)query.getSingleResult();      
      }
      
      roles = new HashSet<Role>();
      roles.add(userRole);
      normalUser.setRoles(roles);
      
      entityManager.persist(normalUser);
    }

  }

}
