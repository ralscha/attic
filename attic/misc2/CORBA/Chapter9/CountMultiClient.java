// CountMultiClient.java, VisiBroker for Java App using awt

import java.awt.*;
import java.awt.event.*;
import org.omg.CosNaming.*;

public class CountMultiClient extends Frame
             implements ActionListener, WindowListener
{ public  TextField statusField;
  private Button run, stop;
  private org.omg.CosNaming.NamingContext nameService; 
  public  CounterPortable.Count counter;
  public  long clientSum;
  private long startTime, stopTime;
  private Thread countThread;
  private boolean countThreadStarted;
  private Thread controlThread;
  public  String clientNumber;

  public CountMultiClient(String[] args)
  {
    setTitle("Count Client Number " + args[0]);
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
 
      // resolve the Count object reference
      NameComponent[] countName = {new NameComponent("countName", "")};
      counter = CounterPortable.CountHelper.narrow(nameService.resolve(countName));
      if (counter == null) 
      {
        statusField.setText("Failed to resolve countName");
        return;
      }

      clientNumber = args[0];
      controlThread = new ClientControlThread(this, args, nameService);
      controlThread.start();
      controlThread.setPriority(Thread.NORM_PRIORITY + 1);

    } catch(Exception e)
    { statusField.setText("Exception" + e);
    }

    countThread = new ClientCountThread(this);

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
    statusField.setText("Incrementing");

    // Calculate Start time
    startTime = System.currentTimeMillis();

    // start the count
    if (countThreadStarted)
    { countThread.resume();
    } else
    { countThread.start();
      countThreadStarted = true;
    }
    return true;
  }


  public String stopCounting()
  { countThread.suspend();

    // Calculate stop time; show statistics
    long stopTime = System.currentTimeMillis();
    String statistics =  "Sum = " + clientSum + ", Avg Ping = " +
         Float.toString((float)(stopTime - startTime)/(float)clientSum) +
         " msecs";
    statusField.setText(statistics);
    return "Client " + clientNumber + ": " + statistics;
  }


  public static void main(String[] args)
  {
    CountMultiClient f = new CountMultiClient(args);
    f.setSize(480, 90);
    f.show();
  }
}


class ClientControlThread extends Thread
{
  private CountMultiClient thisClient;
  private MultiCoordinator.Coordinator myCoordinator;
  private String[] args;                      
  private org.omg.CosNaming.NamingContext nameService;


  ClientControlThread(CountMultiClient client, String[] myargs, 
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


class ClientCountThread extends Thread
{ CountMultiClient myClient;

  ClientCountThread(CountMultiClient client)
  { myClient = client;
  }

  public void run()
  { // increment count continually
    while (true)
    { try
      {
        myClient.clientSum++;
        myClient.counter.increment();
      } catch(org.omg.CORBA.SystemException e)
      { myClient.statusField.setText("System Exception" + e);
      }
    }
  }
}
