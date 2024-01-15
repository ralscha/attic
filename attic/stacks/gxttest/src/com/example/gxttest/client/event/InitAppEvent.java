package com.example.gxttest.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class InitAppEvent extends GwtEvent<InitAppEvent.Handler> {

  private static final GwtEvent.Type<Handler> TYPE = new GwtEvent.Type<Handler>();

  @Override
  protected void dispatch(Handler handler) {
    handler.onInit(this);
  }
  
  public static GwtEvent.Type<Handler> getType(){
    return TYPE;
 }
  

  @Override
  public GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  
  public interface Handler extends EventHandler {
    void onInit(InitAppEvent event);
  }
}
