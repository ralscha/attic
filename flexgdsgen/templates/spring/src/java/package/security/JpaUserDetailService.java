package @packageProject@.security;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import @packageProject@.entity.User;

public class JpaUserDetailService implements UserDetailsService {

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    List<User> users = entityManager.createQuery("select u from User u where u.userName = :userName").setParameter("userName", userName)
        .getResultList();

    if (users.isEmpty()) {
      throw new UsernameNotFoundException(userName);
    }

    UserDetails userDetail = new JpaUserDetail(users.get(0));

    return userDetail;

  }

}
