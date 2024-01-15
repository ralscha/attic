
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.security.*;
import com.objectmatter.bsf.*;

public class SecurityTest {
	public static void main(String[] args) {
    try {

      Principal benutzer = new Principal("101");

      PrincipalGroup group = new PrincipalGroup("ESS");
      group.addMember(benutzer);
    Acl acl = new Acl("myAcl", true);
  
/*
      AclEntry entry = new AclEntry(benutzer);
      PBrokerPermission permission = new PBrokerPermission("reporting.finanz", PBrokerPermission.READ);
      entry.addPermission(permission);
      acl.addEntry(entry);

      permission = new PBrokerPermission("reporting.finanz", PBrokerPermission.READ);  
      System.out.println(acl.checkPermission(benutzer, permission));      

*/
      AclEntry entry = new AclEntry(group);
      PBrokerPermission permission = new PBrokerPermission("reporting.finanz", PBrokerPermission.READ);
      entry.addPermission(permission);
      permission = new PBrokerPermission("reporting.*", PBrokerPermission.READ|PBrokerPermission.WRITE);
      entry.addPermission(permission);
      acl.addEntry(entry);

      permission = new PBrokerPermission("reporting.finanz", PBrokerPermission.WRITE);  
      System.out.println(acl.checkPermission(benutzer, permission));

      permission = new PBrokerPermission("reporting.personal", PBrokerPermission.READ);  
      System.out.println(acl.checkPermission(benutzer, permission));


    } catch (Exception e) {
      System.err.println(e);
    }
	
	}

}