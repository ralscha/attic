package ch.ess.ex4u.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ch.ess.ex4u.client.PanelFactory;
import ch.ess.ex4u.client.gui.addremove.DragAndDropPanel;
import ch.ess.ex4u.client.gui.addremove.MoveRecordEvent;
import ch.ess.ex4u.shared.ServiceRequest;
import ch.ess.ex4u.shared.ServiceRequestAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@SuppressWarnings("unused")
public class VertragEditPanel extends BasePanel {

  protected SimpleEditPanel vertragEditor;
  protected SimpleEditPanel zielgefaessEditor;
  protected DragAndDropPanel userAssigner;
  protected DragAndDropPanel binAssigner;

  ServiceRequestAsync asyncRequestHandler;

  public VertragEditPanel(MainLayout main) {
    super(main);
    asyncRequestHandler = (ServiceRequestAsync)GWT.create(ServiceRequest.class);
  }

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      VertragEditPanel panel = new VertragEditPanel(main);
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

    DataSource vertragDS = DataSource.get("vertragDMI");
    DataSource zielgefaessDS = DataSource.get("zielgefaessDMI");

    VLayout vLayout = new VLayout();
    vLayout.setHeight100();
    vLayout.setMargin(10);
    vLayout.setMembersMargin(10);

    HLayout editLayout = new HLayout();
    editLayout.setHeight("60%");
    editLayout.setMembersMargin(10);

    HLayout assignLayout = new HLayout();
    assignLayout.setHeight("40%");
    assignLayout.setMembersMargin(10);

    // ---------- vertrag editor ----------
    vertragEditor = new SimpleEditPanel("Verträge", vertragDS, "100%", new VertragRecordClickHandler());
    vertragEditor.getListGrid().fetchData();

    // ---------- zielgefäss editor ----------
    zielgefaessEditor = new SimpleEditPanel("Zielgefässe", zielgefaessDS, "100%", null);
    zielgefaessEditor.getListGrid().fetchData();

    // ---------- user assigner ----------
    userAssigner = new DragAndDropPanel("EMAs", "50%", "100%", new MoveUserToLeftEvent(), new MoveUserToRightEvent());

    // ---------- bin assigner ----------
    binAssigner = new DragAndDropPanel("Zielgefässe", "50%", "100%", new MoveBinToLeftEvent(), new MoveBinToRightEvent());

    editLayout.addMember(vertragEditor);
    editLayout.addMember(zielgefaessEditor);
    
    assignLayout.addMember(userAssigner);
    assignLayout.addMember(binAssigner);
    
    vLayout.addMember(editLayout);
    vLayout.addMember(assignLayout);

    return vLayout;
  }

  @Override
  public String getIntro() {
    return null;
  }

  public class VertragRecordClickHandler implements RecordClickHandler {

    public void onRecordClick(RecordClickEvent event) {
      Record record = event.getRecord();
      if (record == null) return;
      
      String idStr = record.getAttribute("id");
      Long vId = Long.parseLong(idStr);

      asyncRequestHandler.getAssignedVertragEmas(vId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: requesting assigned contract users failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          userAssigner.putLeftRecords(result);
        }
      });

      asyncRequestHandler.getAssignedVertragGefaesse(vId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: requesting assigned contract bins failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          binAssigner.putLeftRecords(result);
        }
      });

      asyncRequestHandler.getUnassignedVertragEmas(vId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: Requesting unassigned contract users failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          userAssigner.putRightRecords(result);
        }
      });

      asyncRequestHandler.getUnassignedVertragGefaesse(vId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: Requesting unassigned contract bins failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          binAssigner.putRightRecords(result);
        }
      });
    }
  }

  public class MoveUserToLeftEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord userRecord) {

      ListGridRecord selectedVertragRecord = vertragEditor.getListGrid().getSelectedRecord();
      Long vId = Long.parseLong(selectedVertragRecord.getAttribute("id"));
      Long uId = Long.parseLong(userRecord.getAttribute("id"));

      asyncRequestHandler.assignVertragEma(vId, uId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: do something
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: assign EMA to contract failed!");
        }
      });
    }
  }

  public class MoveUserToRightEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord userRecord) {

      ListGridRecord selectedVertragRecord = vertragEditor.getListGrid().getSelectedRecord();
      Long vId = Long.parseLong(selectedVertragRecord.getAttribute("id"));
      Long uId = Long.parseLong(userRecord.getAttribute("id"));

      asyncRequestHandler.deassignVertragEma(vId, uId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: do something
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: deassign EMA from contract failed!");
        }
      });
    }
  }

  public class MoveBinToLeftEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord binRecord) {

      ListGridRecord selectedVertragRecord = vertragEditor.getListGrid().getSelectedRecord();
      Long vId = Long.parseLong(selectedVertragRecord.getAttribute("id"));
      Long gId = Long.parseLong(binRecord.getAttribute("id"));

      asyncRequestHandler.assignVertragGefaess(vId, gId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: do something
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: assign bin to contract failed!");
        }
      });
    }
  }

  public class MoveBinToRightEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord binRecord) {

      ListGridRecord selectedVertragRecord = vertragEditor.getListGrid().getSelectedRecord();
      Long vId = Long.parseLong(selectedVertragRecord.getAttribute("id"));
      Long gId = Long.parseLong(binRecord.getAttribute("id"));

      asyncRequestHandler.deassignVertragGefaess(vId, gId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: do something
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: deassign bin from contract failed!");
        }
      });
    }
  }
}