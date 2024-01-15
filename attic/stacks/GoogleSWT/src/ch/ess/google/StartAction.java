package ch.ess.google;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StartAction extends SelectionAdapter{

  private Mediator mediator;
  
  public StartAction(Mediator mediator) {
    this.mediator = mediator;
  }  
  
  public void widgetSelected(SelectionEvent arg0) {
    mediator.start();
  }
  
  



}
