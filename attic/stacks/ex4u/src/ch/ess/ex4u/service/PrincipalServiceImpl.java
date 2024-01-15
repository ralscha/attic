package ch.ess.ex4u.service;

import java.util.HashSet;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.ex4u.entity.User;
import ch.ess.ex4u.shared.PrincipalDetail;
import ch.ess.ex4u.shared.PrincipalService;
import com.google.common.collect.Sets;


@Named
public class PrincipalServiceImpl implements PrincipalService {

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  @Override
  public PrincipalDetail getPrincipal() {
    
    PrincipalDetail pd = new PrincipalDetail();
    
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      UserDetails pr = (UserDetails)principal;
      
      pd.setName(pr.getUsername());
      
      if (pr.getUsername() != null) {
        Session hibernateSession = (Session)entityManager.getDelegate();
        Criteria criteria = hibernateSession.createCriteria(User.class);
        Criterion userNameRestriction = Restrictions.eq("userName", pr.getUsername());
        criteria.add(userNameRestriction);
        List<User> users = criteria.list();
        if (users != null && users.size() > 0) {
          pd.setFullName(users.get(0).getPrinzipalDetail().getFullName());
          pd.setId(users.get(0).getPrinzipalDetail().getId());
        }
      }
      
      HashSet<String> roles = Sets.newHashSet();
      for (GrantedAuthority auth : pr.getAuthorities()) {
        roles.add(auth.getAuthority());
      }
      pd.setRoles(roles);
    } 
    
    return pd;
    
  }

}
