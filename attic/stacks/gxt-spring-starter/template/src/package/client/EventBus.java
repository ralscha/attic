package @packageProject@.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;

public final class EventBus {

  private HandlerManager handlerManager;
  private static EventBus instance = new EventBus();

  public static EventBus get() {
    return instance;
  }

  public <H extends EventHandler> void addHandler(GwtEvent.Type<H> type, final H handler) {
    ensureHandlers().addHandler(type, handler);
  }

  private HandlerManager ensureHandlers() {
    return handlerManager == null ? handlerManager = new HandlerManager(this) : handlerManager;
  }

  public void fireEvent(GwtEvent< ? > event) {
    handlerManager.fireEvent(event);
  }

  @SuppressWarnings("deprecation")
  public <H extends EventHandler> void removeHandler(GwtEvent.Type<H> type, final H handler) {
    handlerManager.removeHandler(type, handler);
  }

}
