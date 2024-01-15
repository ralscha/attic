
import org.omg.CosNaming.*;

public class CountPortableClient {
    static public void main(String[] args) {
        try {
            System.out.println("Initializing the ORB");
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
            
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
            CounterPortable.Count counter = CounterPortable.CountHelper.narrow(nameService.resolve(countName));
                        
            System.out.println("Setting sum to 0");
            counter.sum((int)0);
            
            long startTime = System.currentTimeMillis();
            
            System.out.println("Incrementing");
            for (int i = 0; i < 1000; i++)
                counter.increment();
                
            long stopTime = System.currentTimeMillis();
            
            System.out.println("Avg Ping = " + ((stopTime - startTime)/1000f) + " msecs");
            System.out.println("Sum = " + counter.sum());
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
