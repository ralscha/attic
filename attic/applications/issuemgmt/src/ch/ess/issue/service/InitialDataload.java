package ch.ess.issue.service;

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
import ch.ess.issue.entity.IssueType;
import ch.ess.issue.entity.PriorityLevel;
import ch.ess.issue.entity.Resolution;
import ch.ess.issue.entity.User;

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
    
    Criteria criteria = hibernateSession.createCriteria(User.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      User user = new User();

      user.setEmail("admin@ess.ch");
      user.setEnabled(true);
      user.setFirstName("admin");
      user.setLastName("admin");
      user.setPassword(DigestUtils.shaHex("admin"));
      user.setRememberMeToken(null);
      user.setRole("admin");
      user.setUsername("admin");

      hibernateSession.save(user);
    }

    criteria = hibernateSession.createCriteria(IssueType.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      String[] types = {"Bug", "Improvement", "New Feature", "Task"};
      for (String typ : types) {
        IssueType newType = new IssueType();
        newType.setName(typ);

        hibernateSession.save(newType);
      }
    }

    criteria = hibernateSession.createCriteria(PriorityLevel.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      String[] priorities = {"Blocker", "Critical", "Major", "Minor", "Trivial"};
      for (String priority : priorities) {
        PriorityLevel newPriority = new PriorityLevel();
        newPriority.setName(priority);

        hibernateSession.save(newPriority);
      }
    }

    criteria = hibernateSession.createCriteria(Resolution.class);
    if (criteria.setMaxResults(1).list().isEmpty()) {
      String[] resolutions = {"Fixed", "Won't Fix", "Duplicate", "Incomplete", "Cannot Reproduce", "Rejected"};
      for (String resolution : resolutions) {
        Resolution newResolution = new Resolution();
        newResolution.setName(resolution);

        hibernateSession.save(newResolution);
      }
    }
    
    Identity.setSecurityEnabled(true);

  }

}
