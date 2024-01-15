package ch.ess.ex4u.service;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
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
import ch.ess.ex4u.entity.Role;
import ch.ess.ex4u.entity.User;
import ch.ess.ex4u.entity.Vertrag;
import ch.ess.ex4u.entity.Zeit;
import ch.ess.ex4u.entity.Zielgefaess;

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
    User normalUser = null;
    User adminUser = null;
    Vertrag derVertrag = null;
    Zielgefaess dasGefaess = null;

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
      adminUser = new User();
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
      normalUser = new User();
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

    //default vertrag
    query = entityManager.createQuery("select count(*) from Vertrag");
    if ((Long)query.getSingleResult() == 0) {
      derVertrag = new Vertrag();
      derVertrag.setVon(new GregorianCalendar(2010,  1,  1).getTime());
      derVertrag.setBis(new GregorianCalendar(2015, 12, 31).getTime());
      derVertrag.setVertragsnummer("1000000");
      derVertrag.setVertragsname("Default Vertrag");
      if (normalUser == null) {
        query = entityManager.createQuery("select u from User u where userName = :name");
        query.setParameter("name", "user");
        query.setMaxResults(1);
        normalUser = (User)query.getSingleResult();
      }
      derVertrag.addEma(normalUser);
      entityManager.persist(derVertrag);
    }

    //default zielgefäss
    query = entityManager.createQuery("select count(*) from Zielgefaess");
    if ((Long)query.getSingleResult() == 0) {
      dasGefaess = new Zielgefaess();
      dasGefaess.setBeschreibung("Default Zeitgefäss");
      int min = 1000000, max = 9999999;
      long randomNum = new Random().nextInt(max - min + 1) + min;
      dasGefaess.setNummer("" + randomNum);
      if (derVertrag == null) {
        query = entityManager.createQuery("select v from Vertrag v where vertragsnummer = :nummer");
        query.setParameter("nummer", "1000000");
        query.setMaxResults(1);
        derVertrag = (Vertrag)query.getSingleResult();
      }
      dasGefaess.addVertrag(derVertrag);
      entityManager.persist(dasGefaess);
    }
    
    //eine zeit
    query = entityManager.createQuery("select count(*) from Zeit");
    if ((Long)query.getSingleResult() == 0) {
      Zeit z = new Zeit();
      z.setBemerkung("Test Zeit");
      z.setPeriode("d"); // d == day, w == week, m == montch
      z.setVonDatum(new GregorianCalendar(2010, 4, 1).getTime());
      z.setBisDatum(new GregorianCalendar(2010, 4, 1).getTime());
      z.setVonZeit (new GregorianCalendar(2010, 4, 1,  8, 0).getTime());
      z.setBisZeit (new GregorianCalendar(2010, 4, 1, 17, 0).getTime());
      z.setSpesen(0);
      z.setStunden(9);
      if (normalUser != null) z.setUser(normalUser);
      if (derVertrag != null) z.setVertrag(derVertrag);
      if (dasGefaess != null) z.setZielgefaess(dasGefaess);
      entityManager.persist(z);
      //eine weitere zeit
      z = new Zeit();
      z.setBemerkung("Neuzeit");
      z.setPeriode("d"); // d == day, w == week, m == montch
      z.setVonDatum(new GregorianCalendar(2010, 4, 2).getTime());
      z.setBisDatum(new GregorianCalendar(2010, 4, 2).getTime());
      z.setVonZeit (new GregorianCalendar(2010, 4, 2,  8, 0).getTime());
      z.setBisZeit (new GregorianCalendar(2010, 4, 2, 16, 0).getTime());
      z.setSpesen(0);
      z.setStunden(8);
      if (normalUser != null) z.setUser(normalUser);
      if (derVertrag != null) z.setVertrag(derVertrag);
      if (dasGefaess != null) z.setZielgefaess(dasGefaess);
      entityManager.persist(z);
    }
  }
}
