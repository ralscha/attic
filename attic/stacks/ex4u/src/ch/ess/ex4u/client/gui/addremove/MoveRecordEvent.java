package ch.ess.ex4u.client.gui.addremove;

import com.google.gwt.event.shared.EventHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;


public interface MoveRecordEvent extends EventHandler {

  void onRecordMove(ListGridRecord record);

}
