package com.example.gxttest.client;

import com.example.gxttest.client.control.AppControl;
import com.example.gxttest.client.control.ErrorControl;
import com.example.gxttest.client.event.InitAppEvent;
import com.example.gxttest.share.GreetingService;
import com.example.gxttest.share.GreetingServiceAsync;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Main implements EntryPoint {

  public static final String GREETING_SERVICE = "greetingService";
  
  @Override
  public void onModuleLoad() {

    GXT.setDefaultTheme(Theme.GRAY, true);

    GreetingServiceAsync service = (GreetingServiceAsync)GWT.create(GreetingService.class);
    Registry.register(GREETING_SERVICE, service);
    
    new ErrorControl();
    new AppControl();
    
    EventBus.get().fireEvent(new InitAppEvent());

    GXT.hideLoadingPanel("loading");

  }

}
