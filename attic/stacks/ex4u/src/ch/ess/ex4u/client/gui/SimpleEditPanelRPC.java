package ch.ess.ex4u.client.gui;

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

public class SimpleEditPanelRPC extends VLayout {

  protected ToolStripButton newButton;
  protected ToolStripButton deleteButton;
  protected ToolStripButton fetchButton;
  protected RecordClickHandler callbackHandler;
  final protected ListGrid listGrid;
  final protected ToolStrip toolBar;

  public SimpleEditPanelRPC(String title, String height, RecordClickHandler callback) {

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
    listGrid.setShowAllRecords(true);

    listGrid.setShowFilterEditor(true);
    listGrid.setFilterOnKeypress(true);
    listGrid.setFetchDelay(500);
    listGrid.setCanEdit(true);
    listGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
    listGrid.setListEndEditAction(RowEndEditAction.NEXT);
    listGrid.setEditByCell(false);

    listGrid.addRecordClickHandler(new RecordClickHandler() {

      public void onRecordClick(RecordClickEvent event) {
        deleteButton.enable();
        if (callbackHandler != null)
          callbackHandler.onRecordClick(event);
      }
    });

    addMember(listGrid);
  }


  public ListGrid getListGrid() {
    return listGrid;
  }

  public ToolStrip getToolBar() {
    return toolBar;
  }

}
