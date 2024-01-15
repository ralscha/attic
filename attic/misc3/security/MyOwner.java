
import java.security.Principal;
import java.security.acl.*;
import java.util.Enumeration;

public class MyOwner implements Owner {

  private Group ownerGroup;


  public MyOwner(Principal principal) {
    ownerGroup = new MyGroup("AclOwners");
    ownerGroup.addMember(principal);
  }

  public synchronized boolean addOwner(Principal principal, Principal principal1)
    throws NotOwnerException {
    if (!isOwner(principal)) {
      throw new NotOwnerException();
    } else {
      ownerGroup.addMember(principal1);
      return false;
    }
  }

  public synchronized boolean deleteOwner(Principal principal, Principal principal1)
    throws NotOwnerException, LastOwnerException {
    if (!isOwner(principal))
      throw new NotOwnerException();
    Enumeration enumeration = ownerGroup.members();
    Object obj = enumeration.nextElement();
    if (enumeration.hasMoreElements())
      return ownerGroup.removeMember(principal1);
    else
      throw new LastOwnerException();
  }

  public synchronized boolean isOwner(Principal principal) {
    return ownerGroup.isMember(principal);
  }

}
