import org.omg.CosNaming.*;

public class GuestBookServer {

    public static void main(String args[]) {
        try {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
            GuestBook gb = new GuestBook();
            orb.connect(gb);

            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }
            org.omg.CosNaming.NamingContext nameService = org.omg.CosNaming.NamingContextHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }
            NameComponent[] guestBookName = {new NameComponent("guestbook", "")};
            nameService.rebind(guestBookName, gb);
            
            System.out.println("GuestBook Object bound");
            // wait forever for current thread to die
            Thread.currentThread().join();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

}