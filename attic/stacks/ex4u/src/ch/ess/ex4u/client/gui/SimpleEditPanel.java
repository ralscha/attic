package ch.ess.ex4u.client.gui;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class SimpleEditPanel extends VLayout {

  ToolStripButton newButton;
  ToolStripButton deleteButton;
  ToolStripButton fetchButton;
  RecordClickHandler callbackHandler;
  final ListGrid listGrid;
  final ToolStrip toolBar;

  public SimpleEditPanel(String title, DataSource ds, String height, RecordClickHandler callback) {

    this.callbackHandler = callback;

    setMembersMargin(10);
    setHeight(height);

    toolBar = new ToolStrip();
    toolBar.setWidth100();

    toolBar.addSpacer(6);
    Label titleLabel = new Label(title);
    titleLabel.setStyleName("sgwtSubTitle");
    titleLabel.setWrap(false);
    toolBar.addMember(titleLabel);

    toolBar.addFill();

    toolBar.addSeparator();

    newButton = new ToolStripButton();
    newButton.setTitle("New");
    newButton.setIcon("demoApp/icon_add.png");
    newButton.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        // listGrid.fetchData();
        listGrid.deselectAllRecords();
        listGrid.startEditingNew();
      }
    });
    toolBar.addMember(newButton);

    deleteButton = new ToolStripButton();
    deleteButton.setTitle("Delete");
    deleteButton.setIcon("demoApp/icon_delete.png");
    deleteButton.setDisabled(true);
    deleteButton.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        listGrid.removeSelectedData();
        listGrid.deselectAllRecords();
      }
    });
    toolBar.addMember(deleteButton);

    fetchButton = new ToolStripButton();
    fetchButton.setTitle("Reload");
    fetchButton.setIcon("silk/arrow_refresh_small.png");
    fetchButton.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        listGrid.fetchData();
        listGrid.deselectAllRecords();
      }
    });
    toolBar.addMember(fetchButton);
    toolBar.addSpacer(6);

    addMember(toolBar);

    listGrid = new ListGrid();
    listGrid.setWidth100();
    //listGrid.setHeight("100%");
    listGrid.setShowAllRecords(true);
    if (ds != null) {
      listGrid.setDataSource(ds);
    }
    //listGrid.setAutoFetchData(true);
    listGrid.setShowFilterEditor(true);
    listGrid.setFilterOnKeypress(true);
    listGrid.setFetchDelay(500);
    listGrid.setCanEdit(true);
    listGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
    listGrid.setListEndEditAction(RowEndEditAction.NEXT);
    listGrid.setEditByCell(true);

    listGrid.addRecordClickHandler(new RecordClickHandler() {

      public void onRecordClick(RecordClickEvent event) {
        //Record record = event.getRecord();
        //boundForm.editRecord(record);
        //boundViewer.viewSelectedData(userList);
        deleteButton.enable();
        if (callbackHandler != null)
          callbackHandler.onRecordClick(event);
      }
    });

    /*
    ListGridField countryCodeField = new ListGridField("countryCode", "Flag", 40);
    countryCodeField.setAlign(Alignment.CENTER);
    countryCodeField.setType(ListGridFieldType.IMAGE);
    countryCodeField.setImageURLPrefix("flags/16/");
    countryCodeField.setImageURLSuffix(".png");
    countryCodeField.setCanEdit(false);

    ListGridField nameField = new ListGridField("countryName", "Country");
    ListGridField continentField = new ListGridField("continent", "Continent");
    ListGridField memberG8Field = new ListGridField("member_g8", "Member G8");
    ListGridField populationField = new ListGridField("population", "Population");
    populationField.setType(ListGridFieldType.INTEGER);
    populationField.setCellFormatter(new CellFormatter() {

      public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
        if (value != null) {
          NumberFormat nf = NumberFormat.getFormat("0,000");
          try {
            return nf.format(((Number)value).longValue());
          } catch (Exception e) {
            return value.toString();
          }
        } else {
          return null;
        }
      }
    });
    ListGridField independenceField = new ListGridField("independence", "Independence");
    userGrid.setFields(countryCodeField, nameField, continentField, memberG8Field, populationField, independenceField);
    */

    addMember(listGrid);
  }

  public ListGrid getListGrid() {
    return listGrid;
  }

  public ToolStrip getToolBar() {
    return toolBar;
  }
}
