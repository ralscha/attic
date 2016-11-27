package @packageProject@.service;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.granite.messaging.service.annotations.RemoteDestination;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import @packageProject@.entity.Role;
import @packageProject@.entity.User;
import @packageProject@.mail.NewPasswordMailSender;

@Service("userService")
@Transactional
@Secured("ROLE_ADMIN")
@RemoteDestination(id="userService")
public class UserService extends GenericDaoService<User, Integer> {

  @Autowired
  private NewPasswordMailSender newPasswordMailSender;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)  
  public List<Role> findAllRoles() {
    return getEntityManager().createQuery("select r from Role r").getResultList();
  }

  public User getLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User)getEntityManager().createQuery("select u from User u where u.userName = :userName").setParameter("userName",
        authentication.getName()).getSingleResult();

    return user;
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public void updateLocale(String newLocale) {
    getLoggedInUser().setLocale(newLocale);
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public void updatePassword(String newPassword) {
    getLoggedInUser().setPasswordHash(newPassword);
  }

  @Transactional(readOnly = true)
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public String getLocale() {
    return getLoggedInUser().getLocale();
  }

  @SuppressWarnings("unchecked")
  @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
  public void requestNewPassword(String userName) {
    List<User> users = getEntityManager().createQuery("select u from User u where u.userName = :userName").setParameter("userName", userName)
        .getResultList();

    if (!users.isEmpty()) {
      String newPassword = RandomStringUtils.randomAlphanumeric(10);
      users.get(0).setPasswordHash(DigestUtils.shaHex(newPassword));
      newPasswordMailSender.send(users.get(0).getEmail(), newPassword);
    }
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  @Secured({"ROLE_ADMIN"})
  public List<User> search(String queryString) throws ParseException {

    if (StringUtils.isBlank(queryString)) {      
      return getEntityManager().createQuery("select u from User u").getResultList();
    }
    
    String _queryString = queryString.trim();
    if (!_queryString.endsWith("*")) {
      _queryString += "*";
    }
    
    FullTextEntityManager em = getFullTextEntityManager();

    QueryParser parser = new MultiFieldQueryParser(new String[]{"userName", "name", "firstName", "email"}, new StandardAnalyzer());
    parser.setAllowLeadingWildcard(true); //be careful with that
    parser.setDefaultOperator(Operator.AND);
    
    Query luceneQuery = parser.parse(_queryString);

    javax.persistence.Query query = em.createFullTextQuery(luceneQuery, getEntityType());

    return query.getResultList();
  }
}
