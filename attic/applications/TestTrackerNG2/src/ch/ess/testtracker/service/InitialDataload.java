package ch.ess.testtracker.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.security.Identity;
import ch.ess.testtracker.entity.Principal;

@Startup
@Scope(ScopeType.APPLICATION)
@Name("initialDataload")
public class InitialDataload {

  @In
  private Session hibernateSession;

  @Create
  @Transactional
  public void init() {

    Identity.setSecurityEnabled(false);

    Criteria criteria = hibernateSession.createCriteria(Principal.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      Principal principal = new Principal();
      principal.setEmail("admin@ess.ch");
      principal.setEnabled(true);
      principal.setFirstName("admin");
      principal.setName("admin");
      principal.setLocale("de");
      principal.setPasswordHash(DigestUtils.shaHex("admin"));
      principal.setUserName("admin");
      hibernateSession.save(principal);
    }
    
    Identity.setSecurityEnabled(true);
  }
}
