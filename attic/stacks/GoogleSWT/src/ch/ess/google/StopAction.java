package ch.ess.google;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StopAction extends SelectionAdapter{

  private Mediator mediator;
  
  
  public StopAction(Mediator mediator) {
    this.mediator = mediator;
  }  
  
  public void widgetSelected(SelectionEvent arg0) {
    mediator.stop();
  }
  
  



}
