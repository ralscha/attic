package ch.ess.ex4u.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.Role;
import ch.ess.ex4u.entity.User;
import ch.ess.ex4u.entity.Vertrag;
import ch.ess.ex4u.entity.Zielgefaess;
import ch.ess.ex4u.shared.ServiceRequest;
import com.isomorphic.log.Logger;

// server data request handler

@SuppressWarnings("unchecked")
@Named
public class ServiceRequestImpl implements ServiceRequest {

  Logger log = new Logger(RoleService.class.getName());

  @PersistenceContext
  private EntityManager entityManager;

  // ---------- user/roles ----------

  @Override
  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getAssignedRollen(Long userId) {
//    System.out.println("ServiceRequestImpl.getAssignedRollen() !!!" + " userId = " + userId);

    List<Role> roles = queryAssignedUserRoles(userId);

    if (roles == null)
      return null;

    return roles2maps(roles);
  }

  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getUnassignedRollen(Long userId) {
//    System.out.println("ServiceRequestImpl.getUnassignedRollen() !!!" + " userId = " + userId);

    List<Role> roles = queryUnassignedRoles(userId);

    if (roles == null)
      return null;

    return roles2maps(roles);
  }

  private List<Role> queryAssignedUserRoles(Long userId) {
//    log.info("procesing ServiceRequestImpl.queryAssignedRoles");

    Session hibernateSession = (Session)entityManager.getDelegate();
    System.out.println(hibernateSession.isOpen());
    Criteria criteria = hibernateSession.createCriteria(Role.class);
    criteria.createCriteria("users").add(Restrictions.eq("id", userId));
    List<Role> matchingItems = criteria.list();

//    TypedQuery<Role> q = entityManager.createQuery("select r from Role r left join r.users u where u.id = :id", Role.class);    
//    q.setParameter("id", Long.valueOf(userId));
//    List<Role> matchingItems = q.getResultList();
    return matchingItems;
  }

  private List<Role> queryUnassignedRoles(Long userId) {
//    log.info("procesing ServiceRequestImpl.queryUnassignedRoles");

    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Role.class);
    criteria.add(Restrictions.sqlRestriction("not exists (select * from UserRoles where roleId = this_.id and userId = " + userId + ")"));
    List<Role> matchingItems = criteria.list();

    return matchingItems;
  }

  @Override
  @Transactional
  public Boolean assignRolle(Long userId, Long roleId) {
//    System.out.println("ServiceRequestImpl.assignRolle() !!!");
    Session hibernateSession = (Session)entityManager.getDelegate();
    Role role = (Role)hibernateSession.get(Role.class, roleId);
    User user = (User)hibernateSession.get(User.class, userId);
    user.addRole(role);
    hibernateSession.saveOrUpdate(user);
    return Boolean.TRUE;
  }

  @Override
  @Transactional
  public Boolean deassignRolle(Long userId, Long roleId) {
//    System.out.println("ServiceRequestImpl.deassignRolle() !!!");
    Session hibernateSession = (Session)entityManager.getDelegate();
    Role role = (Role)hibernateSession.get(Role.class, roleId);
    User user = (User)hibernateSession.get(User.class, userId);
    Set<Role> roles = user.getRoles();
    Boolean success = roles.remove(role);
    user.setRoles(roles);
    hibernateSession.saveOrUpdate(user);
    return success;
  }

  @Override
  @Transactional
  public Boolean setAssignedRollen(Long userId, ArrayList<Map<String, String>> rolesList) {
    Set<Role> roles = new HashSet<Role>();
    Session hibernateSession = (Session)entityManager.getDelegate();
    User user = (User)hibernateSession.get(User.class, userId);
    for (Map<String, String> roleMap : rolesList) {
      Long roleId = Long.parseLong(roleMap.get("id"));
      Role role = (Role)hibernateSession.get(Role.class, roleId);
      roles.add(role);
    }
    user.setRoles(roles);
    return true;
  }

  // ---------- contract/users ----------

  @Override
  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getAssignedVertragEmas(Long vId) {

    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(User.class);
    criteria.createCriteria("vertraege").add(Restrictions.eq("id", vId));
    List<User> matchingItems = criteria.list();

    if (matchingItems == null)
      return null;

    return users2maps(matchingItems);
  }

  @Override
  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getUnassignedVertragEmas(Long vId) {

    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(User.class);
    criteria.add(Restrictions.sqlRestriction("not exists (select * from VertragEma where userId = this_.id and vertragId = " + vId + ")"));
    List<User> matchingItems = criteria.list();

    if (matchingItems == null)
      return null;

    return users2maps(matchingItems);
  }

  @Override
  @Transactional
  public Boolean assignVertragEma(Long vId, Long uId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Vertrag vertrag = (Vertrag)hibernateSession.get(Vertrag.class, vId);
    User user = (User)hibernateSession.get(User.class, uId);
    vertrag.addEma(user);
    hibernateSession.saveOrUpdate(vertrag);
    return Boolean.TRUE;
  }
  
  @Override
  @Transactional
  public Boolean deassignVertragEma(Long vId, Long uId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Vertrag vertrag = (Vertrag)hibernateSession.get(Vertrag.class, vId);
    User user = (User)hibernateSession.get(User.class, uId);
    Set<User> users = vertrag.getEmas();
    Boolean success = users.remove(user);
    vertrag.setEmas(users);
    hibernateSession.saveOrUpdate(vertrag);
    return success;
  }
  
  // ---------- contract/bins ----------
  
  @Override
  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getAssignedVertragGefaesse(Long vId) {

    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Zielgefaess.class);
    criteria.createCriteria("vertraege").add(Restrictions.eq("id", vId));
    List<Zielgefaess> matchingItems = criteria.list();

    if (matchingItems == null)
      return null;

    return bins2maps(matchingItems);
  }

  @Override
  @Transactional(readOnly = true)
  public ArrayList<Map<String, String>> getUnassignedVertragGefaesse(Long vId) {

    Session hibernateSession = (Session)entityManager.getDelegate();
    Criteria criteria = hibernateSession.createCriteria(Zielgefaess.class);
    criteria.add(Restrictions.sqlRestriction("not exists (select * from VertragZielgefaess where zielgefaessId = this_.id and vertragId = " + vId + ")"));
    List<Zielgefaess> matchingItems = criteria.list();

    if (matchingItems == null)
      return null;

    return bins2maps(matchingItems);
  }

  @Override
  @Transactional
  public Boolean assignVertragGefaess(Long vId, Long gId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Vertrag vertrag = (Vertrag)hibernateSession.get(Vertrag.class, vId);
    Zielgefaess gefaess = (Zielgefaess)hibernateSession.get(Zielgefaess.class, gId);
    vertrag.addZielgefaess(gefaess);
    hibernateSession.saveOrUpdate(vertrag);
    return Boolean.TRUE;
  }

  @Override
  @Transactional
  public Boolean deassignVertragGefaess(Long vId, Long gId) {
    Session hibernateSession = (Session)entityManager.getDelegate();
    Vertrag vertrag = (Vertrag)hibernateSession.get(Vertrag.class, vId);
    Zielgefaess gefaess = (Zielgefaess)hibernateSession.get(Zielgefaess.class, gId);
    Set<Zielgefaess> gefaesse = vertrag.getZielgefaesse();
    Boolean success = gefaesse.remove(gefaess);
    vertrag.setZielgefaesse(gefaesse);
    hibernateSession.saveOrUpdate(vertrag);
    return success;
  }

  // ---------- conversion methods ----------
  
  private ArrayList<Map<String, String>> roles2maps(List<Role> roles) {
    ArrayList<Map<String, String>> rollmops = new ArrayList<Map<String, String>>();
    for (Role role : roles) {
      rollmops.add(role2map(role));
    }
    return rollmops;
  }

  private Map<String, String> role2map(Role role) {
    Map<String, String> roleMap = new HashMap<String, String>();
    roleMap.put("id", role.getId().toString());
    roleMap.put("name", role.getName());
    return roleMap;
  }

  private ArrayList<Map<String, String>> users2maps(List<User> users) {
    ArrayList<Map<String, String>> usermops = new ArrayList<Map<String, String>>();
    for (User user : users) {
      usermops.add(user2map(user));
    }
    return usermops;
  }

  private Map<String, String> user2map(User user) {
    Map<String, String> userMap = new HashMap<String, String>();
    userMap.put("id", user.getId().toString());
    userMap.put("username", user.getUserName() + "(" + user.getFirstName() + " " + user.getName() + ")");
    userMap.put("name", user.getFirstName() + " " + user.getName());
    return userMap;
  }

  private ArrayList<Map<String, String>> bins2maps(List<Zielgefaess> bins) {
    ArrayList<Map<String, String>> binmops = new ArrayList<Map<String, String>>();
    for (Zielgefaess bin : bins) {
      binmops.add(bin2map(bin));
    }
    return binmops;
  }

  private Map<String, String> bin2map(Zielgefaess bin) {
    Map<String, String> binMap = new HashMap<String, String>();
    binMap.put("id", bin.getId().toString());
    binMap.put("nummer", bin.getNummer());
    binMap.put("bezeichnung", bin.getBeschreibung());
    return binMap;
  }
}
