// CoordinatorServer.java

import org.omg.CosNaming.*;

class CoordinatorServer
{ static public void main(String[] args)
  {
    try
    { // Initialize the ORB.
      org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

      // Create the Coordinator object.
      CoordinatorImpl coord =
                  new CoordinatorImpl("Coordinator");

      // Export the newly create object
      orb.connect(coord);

      // Get a reference to the Naming service
      org.omg.CORBA.Object nameServiceObj = 
                 orb.resolve_initial_references ("NameService");
      if (nameServiceObj == null) 
      {
        System.out.println("nameServiceObj = null");
        return;
      }

      org.omg.CosNaming.NamingContext nameService = 
                 org.omg.CosNaming.NamingContextHelper.narrow (nameServiceObj);
      if (nameService == null) 
      {
        System.out.println("nameService = null");
        return;
      }

      // bind the Count object in the Naming service
      NameComponent[] coordName = {new NameComponent("Coordinator", "Multi")};
      nameService.rebind(coordName, coord);

      // Ready to service requests
      System.out.println("Coordinator waiting for requests");

      // wait forever for current thread to die
      Thread.currentThread().join();
      }
      catch(Exception e)
      { System.err.println(e);
      }
   }
}



