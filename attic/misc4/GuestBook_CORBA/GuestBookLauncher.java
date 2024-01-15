import com.sun.java.swing.*;
import org.omg.CosNaming.*;

public class GuestBookLauncher extends JApplet {

    public IGuestBook resolveGuestBook(org.omg.CORBA.ORB orb) {
        try {
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
            if (nameServiceObj == null) {
                System.err.println("nameServiceObj = null");
                return null;
            }
            org.omg.CosNaming.NamingContext nameService = org.omg.CosNaming.NamingContextHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.err.println("nameService = null");
                return null;
            }
            NameComponent[] guestBookName = {new NameComponent("guestbook", "")};
            IGuestBook gb = IGuestBookHelper.narrow(nameService.resolve(guestBookName));

            if (gb == null) {
                System.err.println("Failed to resolve GuestBook");
                return null;
            }
            
            return gb;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
    
    public void init() {                       
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(this, null);
        IGuestBook gb = resolveGuestBook(orb);        
        if (gb != null)
            new GuestBookFrame(true, gb, orb);
    }

    public GuestBookLauncher() { }

    public GuestBookLauncher(String[] args) {
       org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
       IGuestBook gb = resolveGuestBook(orb);
       if (gb != null)
           new GuestBookFrame(false, gb, orb);
    }
    
    public static void main(String[] args) {
       new GuestBookLauncher(args);       
    }
    
}