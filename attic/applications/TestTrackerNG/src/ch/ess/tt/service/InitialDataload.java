package ch.ess.tt.service;

import javax.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.ess.tt.model.Principal;


@Service
public class InitialDataload {

  @Autowired
  private PrincipalService principalService;
  
  
  @PostConstruct
  public void init() {

    if (principalService.getCount() == 0) {
      Principal principal = new Principal();
      principal.setEmail("admin@ess.ch");
      principal.setEnabled(true);
      principal.setFirstName("admin");
      principal.setName("admin");
      principal.setLocale("de");
      principal.setPasswordHash(DigestUtils.shaHex("admin"));
      principal.setUserName("admin");

      principalService.insert(principal);
      
      System.out.println("IDIDIDIDID: " + principal.getId());
    }
  
  }
}
