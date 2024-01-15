package ch.ess.base.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.model.UserUserGroup;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "userDao", autowire = Autowire.BYTYPE)
public class UserDao extends AbstractDao<User> {

  public UserDao() {
    super(User.class);
  }
  
  @Transactional(readOnly = true)
  public User findWithLoginToken(final String token) {
    return findByCriteriaUnique(Restrictions.eq("loginToken", token));
  }

  @Transactional(readOnly = true)
  public User findByNameAndToken(final String userName, final String token) {
    return findByCriteriaUnique(Restrictions.eq("userName", userName), Restrictions.eq("passwordHash", token));
  }

  @Transactional(readOnly = true)
  public User findByName(final String userName) {
    return findByCriteriaUnique(Restrictions.eq("userName", userName));
  }

  @Transactional(readOnly = true)
  public User findExcludeId(final String userName, final String id) {
    Criteria criteria = getSession().createCriteria(User.class);
  
    criteria.add(Restrictions.eq("userName", userName));
  
    if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
      criteria.add(Restrictions.not(Restrictions.eq("id", new Integer(id))));
    }
  
    return (User)criteria.setMaxResults(1).uniqueResult();

  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<User> findWithSearchText(final String searchText, final String userGroupId) {
    Criteria criteria = getSession().createCriteria(User.class);
    
    if (StringUtils.isNotBlank(searchText)) {
      String str = searchText.trim();
      criteria.add(Restrictions.or(Restrictions.like("name", str, MatchMode.START), Restrictions.like("userName",
          str, MatchMode.START)));
    }
    
    if (StringUtils.isNotBlank(userGroupId)) {
      criteria.createCriteria("userUserGroups").createCriteria("userGroup").add(
          Restrictions.eq("id", new Integer(userGroupId)));
    }
    return criteria.list();


  }

  @Transactional(readOnly = true)
  public Set<String> getPermission(final String id) {
    User user = findById(id);

    Set<String> permissionsSet = new HashSet<String>();

    Set<UserUserGroup> userUserGroups = user.getUserUserGroups();
    for (UserUserGroup userUserGroup : userUserGroups) {

      UserGroup userGroup = userUserGroup.getUserGroup();
      Set<UserGroupPermission> userGroupPermissions = userGroup.getUserGroupPermissions();

      for (UserGroupPermission userGroupPermission : userGroupPermissions) {
        permissionsSet.add(userGroupPermission.getPermission().getPermission());
      }

    }

    return permissionsSet;
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public Integer getAdminUserCount(User user, UserGroup userGroup) {
    
    String queryString = "select count(*) from User user " + 
    "inner join user.userUserGroups uug " +
    "inner join uug.userGroup ug " +
    "inner join ug.userGroupPermissions ugp " +
    "inner join ugp.permission p " +
    "where p.permission = 'admin' ";
    
    if (user != null) {
      queryString += " and user <> :exceptUser";
    }
    
    if (userGroup != null) {
      queryString += " and ug <> :exceptUserGroup";
    }
      
    Query query = getSession().createQuery(queryString);    
    
    if (user != null) {
      query.setEntity("exceptUser", user);
    }
    
    if (userGroup != null) {
      query.setEntity("exceptUserGroup", userGroup);
    }
        
    query.setMaxResults(1);
    List<Integer> result = query.list();
    if (!result.isEmpty()) {
      return result.get(0);
    }
    return 0;
  }

  @Override
  public boolean canDelete(User user) {
    return (getAdminUserCount(user, null) > 0);  
  }
  
  

}