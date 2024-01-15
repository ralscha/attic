// DebitCreditMultiClient.java

package ThreeTier;
import java.awt.*;
import java.awt.event.*;

import org.omg.CosNaming.*;

public class DebitCreditMultiClient extends Frame
             implements ActionListener, WindowListener
{ public  TextField statusField;
  private Button run, stop;
  private org.omg.CosNaming.NamingContext nameService; 
  public  Bank.DebitCredit myDebitCredit;
  public  Bank.Dispenser   myDispenser;
  public  long clientSum;
  private long startTime, stopTime;
  private Thread debitCreditThread;
  private Thread controlThread;
  public  String clientNumber;
  boolean runTest = false;
  public  boolean staticTxn = false;

  public DebitCreditMultiClient(String[] args)
  {

    if ((args.length == 2) && args[1].equals("static"))
    { staticTxn = true;
      setTitle("DebitCredit Client Number " + args[0] + " (Static)");
    }
    else
    { staticTxn = false;
      setTitle("DebitCredit Client Number " + args[0] + " (dynamic)");
    }

    // Create a 2 by 2 grid of widgets.
    setLayout(new GridLayout(2, 2, 5, 5));

    // Add the four widgets, initialize where necessary
    add(new Label("Status", Label.CENTER));
    add(statusField = new TextField());
    statusField.setEditable(false);
    statusField.setText("ready");
    add(run = new Button("Run"));
    run.addActionListener(this);
    add(stop = new Button("Stop"));
    stop.addActionListener(this);
    addWindowListener(this);

    try
    { // Initialize the ORB
      org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

      // Get a reference to the Naming service
      org.omg.CORBA.Object nameServiceObj = 
                  orb.resolve_initial_references ("NameService");
      if (nameServiceObj == null) 
      {
        statusField.setText("nameServiceObj = null");
        return;
      }
 
      nameService = 
             org.omg.CosNaming.NamingContextHelper.narrow (nameServiceObj);
      if (nameService == null) 
      {
        statusField.setText("nameService = null");
        return;
      }
 
      // resolve the Dispenser object reference
      NameComponent[] countName = {new NameComponent("DebitCredit", "Bank")};
      myDispenser   = Bank.DispenserHelper.narrow(nameService.resolve(countName));
      if (myDispenser == null) 
      {
        statusField.setText("Failed to resolve DebitCredit");
        return;
      }

      clientNumber = args[0];
      controlThread = new ClientControlThread(this, args, nameService);
      controlThread.start();
      controlThread.setPriority(Thread.NORM_PRIORITY + 1);

    } catch(Exception e)
    { statusField.setText("Exception" + e);
      System.err.println(e);
    }

    debitCreditThread = new ClientDebitCreditThread(this);
    debitCreditThread.start();

  }


  public void windowClosing(WindowEvent event)
  { System.exit(0);
  }

  public void windowClosed(WindowEvent event) {}
  public void windowDeiconified(WindowEvent event) {}
  public void windowIconified(WindowEvent event) {}
  public void windowActivated(WindowEvent event) {}
  public void windowDeactivated(WindowEvent event) {}
  public void windowOpened(WindowEvent event) {}


  public void actionPerformed(ActionEvent ev)
  {
    String arg = ev.getActionCommand();
    if ("Run".equals(arg))
       startCounting();
    if ("Stop".equals(arg))
       stopCounting();
  }


  public boolean startCounting()
  { // initialize client sum, update status
    clientSum = 0;
    statusField.setText("Running");

    // Calculate Start time
    startTime = System.currentTimeMillis();

    try
    {
      myDebitCredit = myDispenser.reserveDebitCreditObject();
    } catch(Exception e)
    { System.out.println(e);
    }

    // start test
    runTest = true;
    debitCreditThread.resume();
    return true;
  }


  public String stopCounting()
  {
    runTest = false;

    try
    {
      myDispenser.releaseDebitCreditObject(myDebitCredit);
    } catch(Exception e)
    { System.out.println(e);
    }

    // Calculate stop time; show statistics
    long stopTime = System.currentTimeMillis();
    String statistics =  "Sum = " + clientSum + ", Avg Txn = " +
         Float.toString((float)(stopTime - startTime)/(float)clientSum) +
         " msecs";
    statusField.setText(statistics);
    return "Client " + clientNumber + ": " + statistics;
  }


  public static void main(String[] args)
  {
    DebitCreditMultiClient f = new DebitCreditMultiClient(args);
    f.setSize(480, 90);
    f.show();
  }
}


class ClientControlThread extends Thread
{
  private DebitCreditMultiClient thisClient;
  private MultiCoordinator.Coordinator myCoordinator;
  private String[] args;
  private org.omg.CosNaming.NamingContext nameService;

  ClientControlThread(DebitCreditMultiClient client, String[] myargs,
                      org.omg.CosNaming.NamingContext naming)
  { thisClient = client;
    args = myargs;
    nameService = naming;
  }        

  public void run()
  { try
     { // Initialize the ORB
       org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
 
       // Create the ClientControl object
       ClientControlImpl control =
                  new ClientControlImpl(thisClient);
 
       // Export the newly created object
       orb.connect(control);
 
       // resolve the Coordinator object reference
       NameComponent[] coordName = {new NameComponent("Coordinator", "Multi")};
       myCoordinator = MultiCoordinator.CoordinatorHelper.narrow(
                                       nameService.resolve(coordName));
 
      // Register with Coordinator
      if (myCoordinator.register(thisClient.clientNumber,
                                 (Client.ClientControl)control))
         {
          System.out.println("Registration Succeeded");
          thisClient.statusField.setText("Ready");
         }
      else
         {
          System.out.println("Registration Failed");
          thisClient.statusField.setText("Registration Failed");
         }

      // Ready to service requests
      System.out.println("Control: Waiting for requests");
      Thread.currentThread().join();
    }
    catch(Exception e)
    { System.err.println("Exception in ClientControl: " + e);
    }
   }
}



class ClientControlImpl extends Client._ClientControlImplBase implements Client.ClientControl
{
  private DebitCreditMultiClient thisClient;

  ClientControlImpl(Object client)
  {
    super();
    thisClient = (DebitCreditMultiClient)client;
    System.out.println("Client Control Object Created as client"
                       + thisClient.clientNumber);
  }

  public  boolean start()
  { return thisClient.startCounting();
  }

  public  String stop()
  { return thisClient.stopCounting();}


}



class ClientDebitCreditThread extends Thread
{ DebitCreditMultiClient myClient;
  
  ClientDebitCreditThread(DebitCreditMultiClient client)
  { myClient = client;
  }

  public void run()
  { // run debit/credit transactions
    while (true)
    { try
      {
        if (!myClient.runTest) this.suspend();
        myClient.myDebitCredit.debitCreditTransaction(
                                    Math.random(),
                                    Math.random(),
                                    myClient.staticTxn
                                    );
        myClient.clientSum++;
     } catch(org.omg.CORBA.SystemException e)
      { myClient.statusField.setText("System Exception" + e);
        this.suspend();
      } catch(org.omg.CORBA.UserException e)
      { myClient.statusField.setText("User Exception" + e);
        this.suspend();
      }
    }
  }
}



