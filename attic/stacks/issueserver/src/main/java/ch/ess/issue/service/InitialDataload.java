package ch.ess.issue.service;

import javax.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.ess.issue.entity.IssueType;
import ch.ess.issue.entity.PriorityLevel;
import ch.ess.issue.entity.Resolution;
import ch.ess.issue.entity.User;

@Service
public class InitialDataload {

  @Autowired
  private UserService userService;
  @Autowired
  private IssueTypeService issueTypeService;
  @Autowired
  private PriorityLevelService priorityLevelService;
  @Autowired
  private ResolutionService resolutionService;

  @PostConstruct
  public void init() {

    if (userService.findAll().isEmpty()) {
      User user = new User();

      user.setEmail("admin@ess.ch");
      user.setEnabled(true);
      user.setFirstName("admin");
      user.setLastName("admin");
      user.setPassword(DigestUtils.shaHex("admin"));
      user.setRememberMeToken(null);
      user.setRole("admin");
      user.setUsername("admin");

      userService.save(user);
    }
    

    if (issueTypeService.findAll().isEmpty()) {
      String[] types = {"Bug", "Improvement", "New Feature", "Task"};
      for (String typ : types) {
        IssueType newType = new IssueType();
        newType.setName(typ);
        issueTypeService.save(newType);        
      }
    }
           
    if (priorityLevelService.findAll().isEmpty()) {
      String[] priorities = {"Blocker", "Critical", "Major", "Minor", "Trivial"};
      for (String priority : priorities) {
        PriorityLevel newPriority = new PriorityLevel();
        newPriority.setName(priority);
        priorityLevelService.save(newPriority);        
      }
    }
    
    if (resolutionService.findAll().isEmpty()) {
      String[] resolutions = {"Fixed", "Won't Fix", "Duplicate", "Incomplete", "Cannot Reproduce", "Rejected"};
      for (String resolution : resolutions) {
        Resolution newResolution = new Resolution();
        newResolution.setName(resolution);
        resolutionService.save(newResolution);        
      }
    }
    
  }

}
