// DebitCreditServer.java

import org.omg.CosNaming.*;

class DebitCreditServer
{ static public void main(String[] args)
  {
    int     numberInstances = 3;

    try
    {
      // Initialize the ORB.
      org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

      // Create the BankDispenser object
      DispenserImpl dispenser =
                new DispenserImpl(args, "My Dispenser", numberInstances);

      // Export the newly create object
      orb.connect(dispenser);

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

      // bind the dispenser object in the Naming service
      NameComponent[] dispenserName = {new NameComponent("DebitCredit", "Bank")};
      nameService.rebind(dispenserName, dispenser);

      // wait forever for current thread to die
      Thread.currentThread().join();

    } catch(Exception e)
    { System.err.println(e);
    }
   }
}



