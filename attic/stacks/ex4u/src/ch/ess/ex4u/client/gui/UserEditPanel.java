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
public class UserEditPanel extends BasePanel {

  protected SimpleEditPanel userEditor;
  protected SimpleEditPanel roleEditor1;
  protected SimpleEditPanel roleEditor2;
  protected DragAndDropPanel roleAssigner;

  ServiceRequestAsync asyncRequestHandler;

  public UserEditPanel(MainLayout main) {
    super(main);
    asyncRequestHandler = (ServiceRequestAsync)GWT.create(ServiceRequest.class);
  }
  
  @Override
  public String getID() {
    return "UserEditPanel";
  }

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      UserEditPanel panel = new UserEditPanel(main);
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

  public class UserRecordClickHandler implements RecordClickHandler {

    public void onRecordClick(RecordClickEvent event) {
      Record record = event.getRecord();
      if (record == null) return;

      String userIdStr = record.getAttribute("id");
      Long userId = Long.parseLong(userIdStr);

      asyncRequestHandler.getAssignedRollen(userId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: requesting assigned roles failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          roleAssigner.putLeftRecords(result);
        }
      });

      asyncRequestHandler.getUnassignedRollen(userId, new AsyncCallback<ArrayList<Map<String, String>>>() {

        @Override
        public void onFailure(Throwable caught) {
          caught.printStackTrace();
          SC.say("Server Timeout: Requesting unassigned roles failed!");
        }

        @Override
        public void onSuccess(ArrayList<Map<String, String>> result) {
          roleAssigner.putRightRecords(result);
        }
      });
    }
  }

  public class MoveToLeftEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord roleRecord) {
      
      ListGridRecord selectedUserRecord = userEditor.getListGrid().getSelectedRecord();
      Long userId = Long.parseLong(selectedUserRecord.getAttribute("id"));
      Long roleId = Long.parseLong(roleRecord.getAttribute("id"));

      asyncRequestHandler.assignRolle(userId, roleId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: z.B. links neu sortieren, Buttons anktivieren/deaktivieren
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: role assigned failed!");
        }
      });
    }
  }

  public class MoveToRightEvent implements MoveRecordEvent {

    @Override
    public void onRecordMove(ListGridRecord roleRecord) {
      
      ListGridRecord selectedUserRecord = userEditor.getListGrid().getSelectedRecord();
      Long userId = Long.parseLong(selectedUserRecord.getAttribute("id"));
      Long roleId = Long.parseLong(roleRecord.getAttribute("id"));

      asyncRequestHandler.deassignRolle(userId, roleId, new AsyncCallback<Boolean>() {

        @Override
        public void onSuccess(Boolean result) {
          // TODO: z.B. links neu sortieren, Buttons anktivieren/deaktivieren
        }

        @Override
        public void onFailure(Throwable caught) {
          SC.say("Server Timeout: role deassigned failed!");
        }
      });
    }
  }

  
  @Override
  public Canvas getViewPanel() {

    DataSource userDS = DataSource.get("userDMI");
    //roleDS = DataSource.get("roleDMI");

    VLayout canvas = new VLayout();
    canvas.setHeight100();
    canvas.setMargin(10);
    canvas.setMembersMargin(10);

    UserRecordClickHandler clickHandler = new UserRecordClickHandler();

    // ---------- user editor ----------
    userEditor = new SimpleEditPanel("Users", userDS, "60%", clickHandler);
    userEditor.getListGrid().fetchData();
    
    // ---------- role editor ----------
    MoveToLeftEvent moveToLeftEvent= new MoveToLeftEvent();
    MoveToRightEvent moveToRightEvent= new MoveToRightEvent();
    roleAssigner = new DragAndDropPanel("Roles", "100%", "40%", moveToLeftEvent, moveToRightEvent);

    // ---------- ein paar Buttons ----------
    SearchForm searchForm = new SearchForm();
    searchForm.setCellPadding(6);
    searchForm.setNumCols(5);
    searchForm.setStyleName("defaultBorder");
    searchForm.setWidth("75%");
    final TextItem searchName = new TextItem("Name");
    searchName.setDefaultValue("ROLE_ADMIN");
    ButtonItem searchButton = new ButtonItem("Filter");
    searchButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        Criteria filter = new Criteria();
        String name = (String)searchName.getValue();
        filter.addCriteria("name", name);
        roleEditor1.getListGrid().filterData(filter);
      }
    });
    searchButton.setEndRow(false);
    searchForm.setItems(searchName, searchButton);
    //canvas.addMember(searchForm);

    final TextItem filterName = new TextItem("Name");
    filterName.setDefaultValue("ROLE_ADMIN");
    DynamicForm filterForm = new DynamicForm();
    filterName.setHeight(19);
    filterForm.setFields(filterName);
    IButton filterButton = new IButton("Filter");
    filterButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      @Override
      public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
        Criteria filter = new Criteria();
        String name = (String)filterName.getValue();
        filter.addCriteria("name", name);
        roleEditor1.getListGrid().filterData(filter);
      }
    });
    filterButton.setHeight(22);
    filterButton.setIcon("silk/find.png");

    IButton serverRequestButton = new IButton("Test Request 1");
    serverRequestButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      @Override
      public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

        ListGridRecord lgrec = userEditor.getListGrid().getSelectedRecord();

        if (lgrec != null) {
          String userIdStr = lgrec.getAttribute("id");
          Long userId = Long.parseLong(userIdStr);

          asyncRequestHandler.getAssignedRollen(userId, new AsyncCallback<ArrayList<Map<String, String>>>() {

            @Override
            public void onFailure(Throwable caught) {
              caught.printStackTrace();
              SC.say("TEST Request 1 failed");
            }

            @Override
            public void onSuccess(ArrayList<Map<String, String>> result) {
              String roles = "";
              if (result != null) {
                for (Map<String, String> role : result) {
                  roles += "Id " + role.get("id") + " = " + role.get("name") + ", ";
                }
                SC.say("Response to Test Request 2:\n" + roles);
              }
            }
          });
        }
      }
    });

    IButton serverRequestButton2 = new IButton("Test Request 2");
    serverRequestButton2.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      @Override
      public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

        ListGridRecord lgrec = userEditor.getListGrid().getSelectedRecord();

        if (lgrec != null) {
          String userIdStr = lgrec.getAttribute("id");
          Long userId = Long.parseLong(userIdStr);

          asyncRequestHandler.getUnassignedRollen(userId, new AsyncCallback<ArrayList<Map<String, String>>>() {

            @Override
            public void onFailure(Throwable caught) {
              caught.printStackTrace();
              SC.say("TEST Request 2 failed");
            }

            @Override
            public void onSuccess(ArrayList<Map<String, String>> result) {
              String roles = "";
              if (result != null) {
                for (Map<String, String> role : result) {
                  roles += "Id " + role.get("id") + " = " + role.get("name") + ", ";
                }
                SC.say("Response to Test Request 2:\n" + roles);
              }
            }
          });
        }
      }
    });

    HLayout filterLayout = new HLayout();
    filterLayout.setMembersMargin(10);
    filterLayout.addMember(filterForm);
    filterLayout.addMember(filterButton);
    filterLayout.addMember(serverRequestButton);
    filterLayout.addMember(serverRequestButton2);

    
    canvas.addMember(userEditor);
    canvas.addMember(roleAssigner);
    canvas.addMember(filterLayout);
    
    return canvas;
  }

  @Override
  public String getIntro() {
    return null;
  }
}