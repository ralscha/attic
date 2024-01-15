
public class CountClient {
    static public void main(String[] args) {
        try {
            System.out.println("Initializing the ORB");
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
            System.out.println("Binding the Count Object");
            Counter.Count counter = Counter.CountHelper.bind(orb, "My Count");
            
            System.out.println("Setting sum to 0");
            counter.sum((int)0);
            
            long startTime = System.currentTimeMillis();
            
            System.out.println("Incrementing");
            for (int i = 0; i < 1000; i++)
                counter.increment();
                
            long stopTime = System.currentTimeMillis();
            
            System.out.println("Avg Ping = " + ((stopTime - startTime)/1000f) + " msecs");
            System.out.println("Sum = " + counter.sum());
            
        } catch (org.omg.CORBA.SystemException e) {
            System.err.println(e);
        }
    }
    
}
