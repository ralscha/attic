package @packageProject@.client.control;

import @packageProject@.client.EventBus;
import @packageProject@.client.event.ErrorEvent;


public class ErrorControl implements ErrorEvent.Handler {

  public ErrorControl() {
    EventBus.get().addHandler(ErrorEvent.getType(), this);
  }
  
  @Override
  public void onError(ErrorEvent event) {
    System.out.println("error: " + event.<Object>getData());
  }

}
