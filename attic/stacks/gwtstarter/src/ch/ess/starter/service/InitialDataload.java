package ch.ess.starter.service;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.XmlWebApplicationContext;
import ch.ess.starter.entity.Role;
import ch.ess.starter.entity.User;

@Service
public class InitialDataload implements ApplicationListener<ApplicationEvent> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ContextRefreshedEvent) {
      String[] configLocations = ((XmlWebApplicationContext)event.getSource()).getConfigLocations();
      if (configLocations != null && configLocations[0].indexOf("applicationContext.xml") != -1) {        
        init();
      }
    }
  }
  
  private void init() {
    Role adminRole = null;
    Role userRole = null;

    Query query = entityManager.createQuery("select count(*) from Role");
    if ((Long)query.getSingleResult() == 0) {
      Role role = new Role();
      role.setName("ROLE_ADMIN");
      entityManager.persist(role);
      adminRole = role;

      role = new Role();
      role.setName("ROLE_USER");
      entityManager.persist(role);
      userRole = role;
    }

    query = entityManager.createQuery("select count(*) from User");
    if ((Long)query.getSingleResult() == 0) {
      //admin user
      User adminUser = new User();
      adminUser.setUserName("admin");
      adminUser.setEmail("test@test.ch");
      adminUser.setFirstName("admin");
      adminUser.setName("admin");
      adminUser.setPasswordHash(DigestUtils.shaHex("admin"));
      adminUser.setEnabled(true);
      adminUser.setLocale("en_US");

      if (adminRole == null) {
        query = entityManager.createQuery("select r from Role r where name = :name");
        query.setParameter("name", "ROLE_ADMIN");
        query.setMaxResults(1);
        adminRole = (Role)query.getSingleResult();
      }

      Set<Role> roles = new HashSet<Role>();
      roles.add(adminRole);
      adminUser.setRoles(roles);

      entityManager.persist(adminUser);

      //normal user
      User normalUser = new User();
      normalUser.setUserName("user");
      normalUser.setEmail("user@test.ch");
      normalUser.setFirstName("user");
      normalUser.setName("user");
      normalUser.setPasswordHash(DigestUtils.shaHex("user"));
      normalUser.setEnabled(true);
      normalUser.setLocale("de_CH");

      if (userRole == null) {
        query = entityManager.createQuery("select r from Role r where name = :name");
        query.setParameter("name", "ROLE_USER");
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
