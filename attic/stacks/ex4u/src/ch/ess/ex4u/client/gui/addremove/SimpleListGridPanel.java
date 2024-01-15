package ch.ess.ex4u.client.gui.addremove;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class SimpleListGridPanel extends VLayout {

  ToolStripButton newButton;
  ToolStripButton deleteButton;
  ToolStripButton fetchButton;
  RecordClickHandler callbackHandler;
  final ListGrid listGrid;
  final ToolStrip toolBar;

  public SimpleListGridPanel(String title, String height) {

    setMembersMargin(10);
    setHeight(height);
    setWidth("50%");

    toolBar = new ToolStrip();
    toolBar.setWidth100();

    toolBar.addSpacer(6);
    Label titleLabel = new Label(title);
    titleLabel.setStyleName("sgwtSubTitle");
    titleLabel.setWrap(false);
    toolBar.addMember(titleLabel);
    toolBar.addFill();

    addMember(toolBar);

    listGrid = new ListGrid();
    listGrid.setWidth100();
    listGrid.setShowAllRecords(true);
    listGrid.setCanEdit(false);

    listGrid.setCanDragRecordsOut(true);
    listGrid.setCanAcceptDroppedRecords(true);
    listGrid.setDragDataAction(DragDataAction.MOVE);
    listGrid.setSelectionType(SelectionStyle.MULTIPLE);

    addMember(listGrid);
  }

  public ListGrid getListGrid() {
    return listGrid;
  }

  public ToolStrip getToolBar() {
    return toolBar;
  }
}
