package ch.ess.google;

import EDU.oswego.cs.dl.util.concurrent.*;
import EDU.oswego.cs.dl.util.concurrent.misc.*;

public class HTMLWorker extends SwingWorker {
  
  private Mediator mediator;   
     
  public HTMLWorker(Mediator mediator) {
    this.mediator = mediator;
  }
     
  protected Object construct() throws InterruptedException {   
    mediator.loadPage();   
    return "ok";     
  }
     
  protected void finished() {
    
    try {
      /*Object result = */get();
      mediator.getBlip().stop();
      mediator.setStatus("HTML geladen");
               
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