package com.example.gxttest.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorEvent.Handler> {

  private static final GwtEvent.Type<Handler> TYPE = new GwtEvent.Type<Handler>();

  private Object data;
  
  public ErrorEvent(Object data) {
    this.data = data;
  }
  
  @SuppressWarnings("unchecked")
  public <X> X getData() {
    return (X) data;
  }
  
  @Override
  protected void dispatch(Handler handler) {
    handler.onError(this);
  }
  
  public static GwtEvent.Type<Handler> getType(){
    return TYPE;
 }
  

  @Override
  public GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  
  public interface Handler extends EventHandler {
    void onError(ErrorEvent event);
  }
}
