// CoordinatorImpl.java

class CoordinatorImpl
      extends MultiCoordinator._CoordinatorImplBase
{
  int maxClients = 20;
  public  Client.ClientControl[] thisClient =
                      new Client.ClientControl[maxClients];

  CoordinatorImpl(String name)
  { super(name);
    System.out.println("Coordinator Object Created");
    for (int i=0; i < maxClients; i++)
    {
      thisClient[i] = null;
    }
  }

  public boolean register(String clientNumber,
                          Client.ClientControl clientObjRef)
                
  {
    try
    { System.out.println("Client " + clientNumber + " Registering");
      thisClient[Integer.parseInt(clientNumber)] = clientObjRef;
      System.out.println("Client " + clientNumber + " Registered");
      return true;
    } catch(Exception e)
    { System.out.println("Exception" + e);
      return false;
    }
  }


  public  boolean start()
  { try
    { System.out.println("Start");
      for (int i=0; i < maxClients; i++)
      {
        if (thisClient[i] != null)
         thisClient[i].start();
      }
      return true;
    } catch(org.omg.CORBA.SystemException e)
    { System.out.println("Exception" + e);
      return false;
    }
  }


  public  String stop()
  { String status = "";
    try
    {
      System.out.println("Stop");
      for (int i=0; i < maxClients; i++)
      {
        if (thisClient[i] != null)
         status = status + thisClient[i].stop() + "\n";
      }
    } catch(org.omg.CORBA.SystemException e)
    { System.out.println("Exception" + e);
    }
    return status;
  }
}



