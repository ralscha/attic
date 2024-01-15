package ch.ess.issue.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.issue.entity.User;

@Service
public class UserService extends AbstractService<User> {

  public UserService() {
    super(User.class);
  }
  
  @Transactional(readOnly=true)
  public User getAdminUser() {    
    return findByCriteriaUnique(Restrictions.eq("username", "admin"));
  }
  
  @Transactional(readOnly=true)
  public User login(String username, String password) {    
    return findByCriteriaUnique(Restrictions.eq("username", username),
        Restrictions.eq("password", DigestUtils.shaHex(password)));
  }
  
  @Transactional(readOnly=true)
  public User loginWithRememberMe(String username, String rememberMeToken) {
    return findByCriteriaUnique(Restrictions.eq("username", username),
        Restrictions.eq("rememberMeToken", rememberMeToken));
  }
  
  @Transactional
  public String createRememberMeToken(User user) {
    StringBuilder sb = new StringBuilder(100);
    sb.append(user.getUsername());
    sb.append(String.valueOf(System.currentTimeMillis()));

    user.setRememberMeToken(DigestUtils.shaHex(sb.toString()));
    getSession().merge(user);
    return user.getRememberMeToken();
  }

}
