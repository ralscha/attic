package ch.ess.pbroker.security;

import ch.ess.pbroker.db.*;
import com.objectmatter.bsf.*;
import java.util.*;

public final class WebSecurityManager {

	private static Acl myAcl = null;

  public WebSecurityManager() { }

  public static Benutzer retrieveBenutzer(Database db, String logonId) {
    OQuery query = new OQuery(Benutzer.class);
    query.add(logonId, "LoginId");
    Benutzer[] ben = (Benutzer[])query.execute(db);
    
    if ((ben != null) && (ben.length > 0)) {
      return ben[0];
    }

    return null;
  }

  public static Acl getBenutzerAcl(Database db, Benutzer benutzer) {
    Acl benutzerAcl = new Acl(benutzer.getLoginId()+"ACL", false);
    Principal principal = new Principal(benutzer.getLoginId());
    
    BenutzerGruppen gruppe = benutzer.getBenutzerGruppe();
    if (gruppe != null) {      
      AclEntry entry = new AclEntry(principal);
        
      BenutzerRechte[] benRechte = gruppe.getBenutzerRechte();
      if (benRechte != null) {
        for (int j = 0; j < benRechte.length; j++) {
          Rechte recht = benRechte[j].getRecht();
          if (recht != null) {
            int action = PBrokerPermission.NONE;
            if (benRechte[j].isLesen()) {
              action = action | PBrokerPermission.READ;
            }
            if (benRechte[j].isSchreiben()) {
              action = action | PBrokerPermission.WRITE;
            }
            PBrokerPermission permission 
                      = new PBrokerPermission(recht.getRecht(), action);
            entry.addPermission(permission);              
          }  
        }
      }              
      benutzerAcl.addEntry(entry);
    }    
    return benutzerAcl;
  }

  public static void init(Database db) {
    // alle Berechtigungen lesen
    myAcl = new Acl("PbrokerACL", true);

    BenutzerGruppen[] gruppen = (BenutzerGruppen[])db.get(BenutzerGruppen.class);
    if (gruppen != null) {      
      for (int i = 0; i < gruppen.length; i++) {
        PrincipalGroup group = new PrincipalGroup(gruppen[i].getBenutzerGruppe());
        
        Benutzer[] benutzer = gruppen[i].getBenutzer();
        if (benutzer != null) {
          for (int j = 0; j < benutzer.length; j++) {
             Principal principal = new Principal(benutzer[j].getLoginId());
             group.addMember(principal);
          }
        }

        AclEntry entry = new AclEntry(group);
        
        BenutzerRechte[] benRechte = gruppen[i].getBenutzerRechte();
        if (benRechte != null) {
          for (int j = 0; j < benRechte.length; j++) {
            Rechte recht = benRechte[j].getRecht();
            if (recht != null) {
              int action = PBrokerPermission.NONE;
              if (benRechte[j].isLesen()) {
                action = action | PBrokerPermission.READ;
              }
              if (benRechte[j].isSchreiben()) {
                action = action | PBrokerPermission.WRITE;
              }
              PBrokerPermission permission 
                        = new PBrokerPermission(recht.getRecht(), action);
              entry.addPermission(permission);              
            }  
          }
        }
                
        myAcl.addEntry(entry);
      }
    }

  }


  public static final boolean checkPermission(Benutzer benutzer, Permission permission) {    
    Principal principal = new Principal(benutzer.getLoginId());
    return checkPermission(principal, permission);
  }

  public static final boolean checkPermission(Principal principal, Permission permission) {
    return myAcl.checkPermission(principal, permission);
  }


}
