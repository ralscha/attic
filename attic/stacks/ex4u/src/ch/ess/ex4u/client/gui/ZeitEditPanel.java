package ch.ess.ex4u.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ch.ess.ex4u.client.PanelFactory;
import ch.ess.ex4u.shared.VertragServiceRequest;
import ch.ess.ex4u.shared.VertragServiceRequestAsync;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.ex4u.shared.ZeitServiceRequest;
import ch.ess.ex4u.shared.ZeitServiceRequestAsync;
import ch.ess.ex4u.shared.ZielgefaessServiceRequest;
import ch.ess.ex4u.shared.ZielgefaessServiceRequestAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.events.DataChangedEvent;
import com.smartgwt.client.data.events.DataChangedHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

@SuppressWarnings("unused")
public class ZeitEditPanel extends BasePanel {

  protected SimpleEditPanelRPC zeitEditor;
  protected ZeitObjConverter converter;
  protected ZeitServiceRequestAsync zeitRequestHandler;
  protected ZielgefaessServiceRequestAsync zielgefaessRequestHandler;
  protected VertragServiceRequestAsync vertragRequestHandler;
  protected ListGridRecord lastClickedRecord;
  private Long loginUserId;
  private boolean userIsAdmin;

  private static final Map<String, String> periodeValueMap = new HashMap<String, String>();
  static {
    periodeValueMap.put("d", "Tag");
    periodeValueMap.put("w", "Woche");
    periodeValueMap.put("m", "Monat");
  }

  public ZeitEditPanel(MainLayout main) {
    super(main);
    init();
  }

  private void init() {
    if (zeitRequestHandler == null) {
      zeitRequestHandler = (ZeitServiceRequestAsync)GWT.create(ZeitServiceRequest.class);
      zielgefaessRequestHandler = (ZielgefaessServiceRequestAsync)GWT.create(ZielgefaessServiceRequest.class);
      vertragRequestHandler = (VertragServiceRequestAsync)GWT.create(VertragServiceRequest.class);
      loginUserId = getMainPanel().getPrincipal().getId();
      Set<String> roles = getMainPanel().getPrincipal().getRoles();
      userIsAdmin = roles.contains("ROLE_ADMIN");
    }
  }

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      ZeitEditPanel panel = new ZeitEditPanel(main);
      id = panel.getID();
      return panel;
    }

    public String getID() {
      return id;
    }

    public String getDescription() {
      return null;
    }
  }

  @Override
  public Canvas getViewPanel() {

    init();

    VLayout vLayout = new VLayout();
    vLayout.setHeight100();
    vLayout.setMargin(10);
    vLayout.setMembersMargin(10);

    // ---------- zeit editor ----------
    zeitEditor = new SimpleEditPanelRPC("Zeiterfassung", "60%", new ZeitRecordClickHandler());

    converter = new ZeitObjConverter(getMainPanel());

    zeitEditor.getListGrid().setFields(converter.getListGridFields());
    
    //zeitEditor.getListGrid().addDoubleClickHandler(new ZeitDoubleClickHandler());

    vLayout.addMember(zeitEditor);

    fetchZeiten();

    return vLayout;
  }

  @Override
  public String getIntro() {
    return null;
  }

  protected void fetchZeiten() {
    zeitRequestHandler.fetch(loginUserId, new ZeitFetchCallback());
  }

  protected class ZeitFetchCallback implements AsyncCallback<ArrayList<ZeitRPC>> {

    @Override
    public void onFailure(Throwable caught) {
      caught.printStackTrace();
      SC.say("Server Timeout: requesting times failed!");
    }

    @Override
    public void onSuccess(ArrayList<ZeitRPC> result) {
      String s = "";
      for (ZeitRPC z : result) {
        if (s.length() > 0)
          s += ", ";
        s += z.getBemerkung();
      }
      //SC.say("times: " + s);
      
      RecordList zeitList = converter.getRecordList(result);
      zeitEditor.getListGrid().setData(zeitList);

      zeitList.addDataChangedHandler(new ZeitChangedHandler());

      zeitEditor.getListGrid().redraw();
    }
  }
  
  public class ZeitRecordClickHandler implements RecordClickHandler {

    public void onRecordClick(RecordClickEvent event) {
      lastClickedRecord = (ListGridRecord)event.getRecord();
      if (lastClickedRecord == null) return;

      converter.onZeitRecordClick(event);
    }
  }

 protected class ZeitDoubleClickHandler implements DoubleClickHandler {
    
    @Override
    public void onDoubleClick(DoubleClickEvent event) {
      SC.say("DoubleClickHandler");
    }
  }

  protected class ZeitChangedHandler implements DataChangedHandler {
    
    @Override
    public void onDataChanged(DataChangedEvent event) {
      //SC.say("DataChangedHandler");
      
      ZeitRPC zeitRPC = converter.record2Obj(lastClickedRecord);
      
      zeitRequestHandler.update(zeitRPC, new AsyncCallback<Boolean>() {
        
        @Override
        public void onSuccess(Boolean result) {
          SC.say("updating times succeeded!");
        }
        
        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: updating times failed!");
        }
      });
      
    }
  }
  
}