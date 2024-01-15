package ch.ess.ex4u.client;

import ch.ess.ex4u.client.gui.MainLayout;
import ch.ess.ex4u.shared.PrincipalDetail;
import ch.ess.ex4u.shared.PrincipalService;
import ch.ess.ex4u.shared.PrincipalServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class Main implements EntryPoint {

  ListGrid roleList;
  ListGrid userList;
  DynamicForm boundForm;
  IButton saveBtn;
  DetailViewer boundViewer;
    

  @Override
  public void onModuleLoad() {
    
    
    PrincipalServiceAsync asyncRequestHandler = (PrincipalServiceAsync)GWT.create(PrincipalService.class);

    asyncRequestHandler.getPrincipal(new AsyncCallback<PrincipalDetail>() {
      
      @Override
      public void onSuccess(PrincipalDetail result) {
        new MainLayout(result);        
      }
      
      @Override
      public void onFailure(Throwable caught) {
        SC.say(caught.toString());
      }
    });
    
  }

  @SuppressWarnings("unused")
  private void sampleApp() {
    DataSource roleDS = DataSource.get("roleDMI");
    DataSource userDS = DataSource.get("userDMI");
    DataSource vertragDS = DataSource.get("vertragDMI");
    DataSource zeitDS = DataSource.get("zeitDMI");
    DataSource zielgefaessDS = DataSource.get("zielgefaessDMI");

    VStack vStack = new VStack();
    vStack.setLeft(175);
    vStack.setTop(75);
    vStack.setWidth("70%");
    vStack.setMembersMargin(20);

    roleList = new ListGrid();
    roleList.setHeight(100);
    roleList.setWidth("30%");
    roleList.setCanEdit(true);
    roleList.setDataSource(roleDS);

    userList = new ListGrid();
    userList.setHeight(200);
    userList.setCanEdit(true);
    userList.setDataSource(userDS);

    userList.addRecordClickHandler(new RecordClickHandler() {

      public void onRecordClick(RecordClickEvent event) {
        Record record = event.getRecord();
        boundForm.editRecord(record);
        saveBtn.enable();
        boundViewer.viewSelectedData(userList);
      }
    });
    vStack.addMember(roleList);
    vStack.addMember(userList);

    boundForm = new DynamicForm();
    boundForm.setDataSource(userDS);
    boundForm.setNumCols(6);
    boundForm.setAutoFocus(false);
    vStack.addMember(boundForm);

    ToolStrip toolbar = new ToolStrip();
    toolbar.setMembersMargin(10);
    toolbar.setHeight(22);

    saveBtn = new IButton("Save");
    saveBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        boundForm.saveData();
        if (!boundForm.hasErrors()) {
          boundForm.clearValues();
          saveBtn.disable();
        }
      }
    });
    toolbar.addMember(saveBtn);

    final IButton newBtn = new IButton("New");
    newBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        boundForm.editNewRecord();
        saveBtn.enable();
      }
    });
    toolbar.addMember(newBtn);

    IButton clearBtn = new IButton("Clear");
    clearBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        boundForm.clearValues();
        saveBtn.disable();
      }
    });
    toolbar.addMember(clearBtn);

    IButton filterBtn = new IButton("Filter");
    filterBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        userList.filterData(boundForm.getValuesAsCriteria());
        saveBtn.disable();
      }
    });
    toolbar.addMember(filterBtn);

    IButton fetchBtn = new IButton("Fetch");
    fetchBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        userList.fetchData(boundForm.getValuesAsCriteria());
        saveBtn.disable();
      }
    });
    toolbar.addMember(fetchBtn);

    IButton deleteBtn = new IButton("Delete");
    deleteBtn.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        userList.removeSelectedData();
        userList.deselectAllRecords();
      }
    });
    toolbar.addMember(deleteBtn);

    vStack.addMember(toolbar);

    boundViewer = new DetailViewer();
    boundViewer.setDataSource(userDS);
    vStack.addMember(boundViewer);

    vStack.draw();

    roleList.filterData(null);
    userList.filterData(null);
  }

}
