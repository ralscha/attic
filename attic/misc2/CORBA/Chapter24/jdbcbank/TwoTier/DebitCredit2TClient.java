// DebitCredit2TClient.java

package TwoTier;
import java.awt.*;
import java.awt.event.*;

import org.omg.CosNaming.*;

public class DebitCredit2TClient extends Frame
             implements ActionListener, WindowListener
{ public  TextField statusField;
  private Button run, stop;
  private org.omg.CosNaming.NamingContext nameService; 
  public  DCBank.BankDB myBankDB;
  public  long clientSum;
  private long startTime, stopTime;
  private Thread debitCreditThread;
  private Thread controlThread;
  public  String clientNumber;
  boolean runTest = false;
  public  boolean staticTxn = false;

  public DebitCredit2TClient(String[] args)
  {
    if ((args.length == 3) && args[2].equals("static"))
    { staticTxn = true;
      setTitle("DebitCredit 2-Tier Client " + args[0] + " (Static)");
    }
    else
    { staticTxn = false;
      setTitle("DebitCredit 2-Tier Client " + args[0] + " (Dynamic)");
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
    {
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
 

      clientNumber = args[0];
      controlThread = new ClientControlThread(this, args, nameService);
      controlThread.start();
      controlThread.setPriority(Thread.NORM_PRIORITY + 1);

      myBankDB = new DCBank.BankDB();
      myBankDB.connect(args[1], "bank", "sa", "");
      System.out.println("BankDB Object Created");

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

    // start test
    runTest = true;
    debitCreditThread.resume();
    return true;
  }


  public String stopCounting()
  {
    runTest = false;

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
    DebitCredit2TClient f = new DebitCredit2TClient(args);
    f.setSize(480, 90);
    f.show();
  }
}


class ClientControlThread extends Thread
{
  private DebitCredit2TClient thisClient;
  private MultiCoordinator.Coordinator myCoordinator;
  private String[] args;
  private org.omg.CosNaming.NamingContext nameService;

  ClientControlThread(DebitCredit2TClient client, String[] myargs,
                      org.omg.CosNaming.NamingContext naming)
  { thisClient = client;
    myargs = args;
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
    { System.err.println(e);
    }
   }
}


class ClientControlImpl extends Client._ClientControlImplBase implements Client.ClientControl
{
  private DebitCredit2TClient thisClient;

  ClientControlImpl(Object client)
  {
    super();
    thisClient = (DebitCredit2TClient)client;
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
{ DebitCredit2TClient myClient;

  ClientDebitCreditThread(DebitCredit2TClient client)
  { myClient = client;
  }

  public void run()
  { // run debit/credit transactions
    while (true)
    { try
      {
        if (!myClient.runTest) this.suspend();
        myClient.myBankDB.debitCreditTransaction(
                                    Math.random(),
                                    Math.random(),
                                    myClient.staticTxn
                                    );
        myClient.clientSum++;
     } catch(Exception e)
      { myClient.statusField.setText("System Exception" + e);
        this.suspend();
      }
    }
  }
}



