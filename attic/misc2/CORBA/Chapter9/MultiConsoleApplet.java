// MultiConsoleApplet.java, Console for Multiclient performance tests

import org.omg.CosNaming.*;

import java.awt.*;
import java.util.*;

public class MultiConsoleApplet extends java.applet.Applet {
  private TextField statusField;
  private List clientStatusList;
  private Button run, stop;
  private MultiCoordinator.Coordinator myCoordinator;
  private long startTime, stopTime;

  public void init() {
    // Create a 3 by 2 grid of widgets.
    setLayout(new GridLayout(3, 2, 5, 5));

    // Add six widgets, initialize where necessary
    add(new Label("Status", Label.CENTER));
    add(statusField = new TextField());
    statusField.setEditable(false);
    statusField.setText("ready");
    add(new Label("Client Results", Label.CENTER));
    add(clientStatusList = new List(4, false));
    add(run = new Button("Run"));
    add(stop = new Button("Stop"));

    try
    { // Initialize the ORB.
      statusField.setText("Initializing the ORB");
      org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(this, null);

      // Get a reference to the Naming service
      statusField.setText("Finding the Name Service");
      org.omg.CORBA.Object nameServiceObj = 
                  orb.resolve_initial_references("NameService");
      if (nameServiceObj == null) 
      {
        statusField.setText("nameServiceObj = null");
        return;
      }
 
      org.omg.CosNaming.NamingContext nameService = 
                 org.omg.CosNaming.NamingContextHelper.narrow (nameServiceObj);
      if (nameService == null) 
      {
        statusField.setText("nameService = null");
        return;
      }
 
      // Resolving the Coordinator Object
      statusField.setText("Binding to the Coordinator");
      NameComponent[] countName = {new NameComponent("Coordinator", "Multi")};
      myCoordinator = MultiCoordinator.CoordinatorHelper.narrow(
                                              nameService.resolve(countName));
      if (myCoordinator == null) 
      {
        statusField.setText("Failed to resolve coordinator");
        return;
      }

      statusField.setText("Ready");
    } catch(Exception e)
    { statusField.setText("Exception" + e);
    }
  }


  public boolean action(Event ev, java.lang.Object arg)
  { if(ev.target == run)
      return startCounting();
    if(ev.target == stop)
      return stopCounting();
    return false;
  }


  private boolean startCounting()
  { statusField.setText("Running");
    clientStatusList.clear();
    try
    {
      // Calculate Start time
      startTime = System.currentTimeMillis();

      myCoordinator.start();
    } catch(org.omg.CORBA.SystemException e)
    { statusField.setText("Exception" + e);
    }
    return true;
  }


  private boolean stopCounting()
  { try
    {
      String clientStatus;
      StringTokenizer token1;
      String clientSum;

      String clientStatistics = myCoordinator.stop();
      stopTime = System.currentTimeMillis();

      StringTokenizer tokens = new StringTokenizer(clientStatistics, "\n");
      int totalSum = 0;

      while (tokens.hasMoreTokens())
      {
        // get status for one client, add to list box
        clientStatus = tokens.nextToken();
        clientStatusList.addItem(clientStatus);

        // extract sum for one client, add to total sum
        token1 = new StringTokenizer(clientStatus, " ,");
        token1.nextToken();  // Skip unwanted tokens
        token1.nextToken();
        token1.nextToken();
        token1.nextToken();
        clientSum = token1.nextToken();
        totalSum = totalSum + Integer.parseInt(clientSum);
      }

      String statistics = "Grand Total = " + totalSum +
         ", Avg Txn time = " +
         Float.toString((float)(stopTime - startTime)/(float)totalSum) +
         " msecs";
      statusField.setText(statistics);
    } catch(org.omg.CORBA.SystemException e)
    { statusField.setText("Exception" + e);
    }
    return true;
  }
}
