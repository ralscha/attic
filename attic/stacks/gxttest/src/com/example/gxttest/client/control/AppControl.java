package com.example.gxttest.client.control;

import com.example.gxttest.client.EventBus;
import com.example.gxttest.client.Main;
import com.example.gxttest.client.event.ErrorEvent;
import com.example.gxttest.client.event.InitAppEvent;
import com.example.gxttest.share.GreetingServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppControl implements InitAppEvent.Handler {

  AppView appView;

  public AppControl() {
    EventBus.get().addHandler(InitAppEvent.getType(), this);
  }

  @Override
  public void onInit(InitAppEvent event) {
    appView = new AppView(this);
  }

  public void sendMessage(String message) {
    GreetingServiceAsync greetingService = (GreetingServiceAsync)Registry.get(Main.GREETING_SERVICE);
    greetingService.greetServer(message, new AsyncCallback<String>() {

      @Override
      public void onSuccess(String result) {
        appView.updateGreeting(result);
      }

      @Override
      public void onFailure(Throwable caught) {
        EventBus.get().fireEvent(new ErrorEvent(caught));
      }
    });
  }
}
