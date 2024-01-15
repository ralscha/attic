import org.omg.CosNaming.*;

class CountClientDii {
 static public void main(String[] args) {
    
        boolean loop_all = false;
        long startTime, stopTime;
        org.omg.CORBA.Request request;
    
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
            
            
            if ((args.length != 0) && (args[0].compareTo("loop_all") == 0))
                loop_all = true;
                
            if (loop_all) {
                System.out.println("Starting IR lookup + invoke test");
                
                startTime = System.currentTimeMillis();
                
                for (int i = 0; i < 1000; i++) {
                    request = buildRequest(counter);
                    request.invoke();
                }
            } else {
                System.out.println("Starting invoke only test");
                request = buildRequest(counter);
                
                startTime = System.currentTimeMillis();
                
                for (int i = 0; i < 1000; i++) {
                    request.invoke();
                }
            }
                                                  
            stopTime = System.currentTimeMillis();
                                    
            System.out.println("Avg Ping = " + ((stopTime - startTime)/1000f) + " msecs");
            System.out.println("Sum = " + counter.sum());
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static org.omg.CORBA.Request buildRequest(CounterPortable.Count counter) {
        org.omg.CORBA.InterfaceDef CountInterface = counter._get_interface();
        org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription intDesc = CountInterface.describe_interface();
        
        if (intDesc.operations[0].name.compareTo("increment") == 0) {
            org.omg.CORBA.Request request = counter._request("increment");
            request.result().value().insert_long(0);
            return request;
        } else {
            System.out.println("Unknown method");
        }
        return null;
    }
    
}
    