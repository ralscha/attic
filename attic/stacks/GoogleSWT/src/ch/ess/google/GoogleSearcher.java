package ch.ess.google;

import javax.xml.rpc.ServiceException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ch.ess.google.wsdl.GoogleSearchPort;
import ch.ess.google.wsdl.GoogleSearchServiceLocator;

public class GoogleSearcher {

  public static void main(String[] args) {

    try {
      GoogleSearchServiceLocator service = new GoogleSearchServiceLocator();      
      GoogleSearchPort port = service.getGoogleSearchPort();      
      
      
      Mediator mediator = new Mediator();
      
      mediator.setSearchService(port);
      
      GoogleSearcherUI ui = new GoogleSearcherUI();
      ui.setMediator(mediator);
      
      Shell shell = ui.buildUI();
      Display display = shell.getDisplay();
          
      shell.open();
      while (!shell.isDisposed()) {
        if (!display.readAndDispatch())
          display.sleep();
      }
    } catch (ServiceException e) {      
      e.printStackTrace();
    }
    
    
  }

}
