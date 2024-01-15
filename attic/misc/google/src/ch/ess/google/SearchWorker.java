package ch.ess.google;

import java.rmi.*;

import ch.ess.google.wsdl.*;
import EDU.oswego.cs.dl.util.concurrent.*;
import EDU.oswego.cs.dl.util.concurrent.misc.*;

public class SearchWorker extends SwingWorker {
  
  private Mediator mediator;   
  private final static String key = "4AmzrmIvkRbJKd+mZ9+Hw/+/BC0IoKSq"; 
     
  public SearchWorker(Mediator mediator) {
    this.mediator = mediator;
  }
     
  protected Object construct() throws InterruptedException, RemoteException {   

    GoogleSearchResult r = mediator.getSearchService().doGoogleSearch(key, mediator.getQueryString(), 0, 10, true, null, true, null, "latin1", "latin1");

        
    return r;
    
        
  }
     
  protected void finished() {
    
    try {
      GoogleSearchResult result = (GoogleSearchResult)get();
      mediator.stop();
      
      mediator.getTableModel().clear();
      
      
      if (result.getEstimatedTotalResultsCount() > 0) {
        ResultElement[] re = result.getResultElements();
        
        if (re != null) {
          for (int i = 0; i < re.length; i++) {
            mediator.getTableModel().addRow("<html>"+re[i].getTitle()+"</html>", re[i].getURL());                      
          }
        }
                
        mediator.setStatus(result.getEstimatedTotalResultsCount() + " gefunden      Suchzeit: "+result.getSearchTime());
      } else {
        mediator.setStatus("nichts gefunden      Suchzeit: " + result.getSearchTime() );
      }
      
      mediator.getTableModel().fireTableDataChanged();
         
    } catch (java.lang.reflect.InvocationTargetException e) {
      Throwable ex = e.getTargetException();
      if (ex instanceof TimeoutException) {
        mediator.setStatus("Timed out.");
      } else if (ex instanceof InterruptedException) {
        mediator.stop();
        mediator.setStatus("Abgebrochen...");
      } else {
        mediator.setStatus("Exception: " + ex);
      }
    } catch (InterruptedException ex) {
      // event-dispatch thread won't be interrupted 
      throw new IllegalStateException(ex+"");
    }
  }
  
}