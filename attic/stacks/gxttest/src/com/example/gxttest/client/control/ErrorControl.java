package com.example.gxttest.client.control;

import com.example.gxttest.client.EventBus;
import com.example.gxttest.client.event.ErrorEvent;


public class ErrorControl implements ErrorEvent.Handler {

  public ErrorControl() {
    EventBus.get().addHandler(ErrorEvent.getType(), this);
  }
  
  @Override
  public void onError(ErrorEvent event) {
    System.out.println("error: " + event.<Object>getData());
  }

}
