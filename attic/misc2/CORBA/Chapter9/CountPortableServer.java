
import org.omg.CosNaming.*;

public class CountPortableServer {
    static public void main(String[] args) {
        try {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

            CountPortableImpl count = new CountPortableImpl();            
            orb.connect(count);

            
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
            
            NameComponent[] countName = {new NameComponent("countName", "")};
            nameService.rebind(countName, count);
            
            Thread.currentThread().join();
                                    
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
