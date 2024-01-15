package ch.ess.ex4u.client.gui.addremove;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DragAndDropPanel extends HLayout {

  protected SimpleListGridPanel leftGrid;
  protected SimpleListGridPanel rightGrid;
  protected MoveRecordEvent assignHandler;
  protected MoveRecordEvent deassignHandler;
  protected MoveMultipleRecordsEvent multipleAssignHandler;
  protected MoveMultipleRecordsEvent multipleDeassignHandler;
  protected TransferImgButton arrowLeftBut;
  protected TransferImgButton arrowRightBut;
  protected TransferImgButton leftAllBut;
  protected TransferImgButton rightAllBut;
  private List<String> columnTitles;
  private List<String> fieldNames;

  public DragAndDropPanel(String title, String width, String height, MoveRecordEvent assign, MoveRecordEvent deassign) {
    assignHandler = assign;
    deassignHandler = deassign;

    setMembersMargin(10);
    if (height == null) {
      setHeight("40%");
    } else {
      setHeight(height);
    }
    if (width == null) {
      setWidth100();
    } else {
      setWidth(width);
    }

    // no datasource assigned
    leftGrid = new SimpleListGridPanel("Assigned " + title, "100%");

    leftGrid.getListGrid().addRecordDropHandler(new RecordDropHandler() {

      @Override
      public void onRecordDrop(RecordDropEvent event) {
        ListGridRecord[] droppedRecords = event.getDropRecords();
        // !!! multiple select funktioniert nicht eigentlich !!!
        moveToLeftGrid(droppedRecords);
      }
    });

    leftGrid.getListGrid().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        arrowRightBut.enable();
      }
    });

    rightGrid = new SimpleListGridPanel("Unassigned " + title, "100%");

    rightGrid.getListGrid().addRecordDropHandler(new RecordDropHandler() {

      @Override
      public void onRecordDrop(RecordDropEvent event) {
        ListGridRecord[] droppedRecords = event.getDropRecords();
        // !!! multiple select funktioniert nicht eigentlich !!!
        moveToRightGrid(droppedRecords);
      }
    });

    rightGrid.getListGrid().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        arrowLeftBut.enable();
      }
    });

    arrowRightBut = new TransferImgButton(TransferImgButton.RIGHT);
    arrowRightBut.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        ListGridRecord[] selectedRecords = leftGrid.getListGrid().getSelection();

        rightGrid.getListGrid().transferSelectedData(leftGrid.getListGrid());
        leftGrid.getListGrid().redraw();
        rightGrid.getListGrid().redraw();

        moveToRightGrid(selectedRecords);
      }
    });

    arrowLeftBut = new TransferImgButton(TransferImgButton.LEFT);
    arrowLeftBut.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        ListGridRecord[] selectedRecords = rightGrid.getListGrid().getSelection();

        leftGrid.getListGrid().transferSelectedData(rightGrid.getListGrid());
        leftGrid.getListGrid().redraw();
        rightGrid.getListGrid().redraw();

        moveToLeftGrid(selectedRecords);
      }
    });

    rightAllBut = new TransferImgButton(TransferImgButton.RIGHT_ALL);
    rightAllBut.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        ListGridRecord[] records = leftGrid.getListGrid().getRecords();
        
        leftGrid.getListGrid().selectAllRecords();
        rightGrid.getListGrid().transferSelectedData(leftGrid.getListGrid());
        leftGrid.getListGrid().redraw();
        rightGrid.getListGrid().redraw();

        moveToRightGrid(records);
      }
    });

    leftAllBut = new TransferImgButton(TransferImgButton.LEFT_ALL);
    leftAllBut.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") com.smartgwt.client.widgets.events.ClickEvent event) {
        ListGridRecord[] records = rightGrid.getListGrid().getRecords();
        
        rightGrid.getListGrid().selectAllRecords();
        leftGrid.getListGrid().transferSelectedData(rightGrid.getListGrid());
        leftGrid.getListGrid().redraw();
        rightGrid.getListGrid().redraw();

        moveToLeftGrid(records);
      }
    });

    VLayout transferButtonsLayout = new VLayout(5);
    transferButtonsLayout.addMember(leftAllBut);
    transferButtonsLayout.addMember(arrowLeftBut);
    transferButtonsLayout.addMember(arrowRightBut);
    transferButtonsLayout.addMember(rightAllBut);
    transferButtonsLayout.setAlign(VerticalAlignment.CENTER);

    // adding main members
    addMember(leftGrid);
    addMember(transferButtonsLayout);
    addMember(rightGrid);

    widgetStateHandler();
  }

  public void putLeftRecords(ArrayList<Map<String, String>> records) {
    putRecords(leftGrid.getListGrid(), records);
  }

  public void putRightRecords(ArrayList<Map<String, String>> records) {
    putRecords(rightGrid.getListGrid(), records);
  }

  private void putRecords(ListGrid list, ArrayList<Map<String, String>> records) {
    if (records == null || records.size() == 0) {
      list.setData(new RecordList());
      return;
    }

    RecordList recordList = new RecordList();
    
    List<String> keyList = null;
    if (fieldNames != null) {
      keyList = fieldNames;
    } else {
      keyList = new ArrayList<String>(records.get(0).keySet());
    }
    
    List<String> titleList = null;
    if (columnTitles != null) {
      titleList = columnTitles;
    } else {
      titleList = new ArrayList<String>(records.get(0).keySet());
    }

    for (Map<String, String> record : records) {
      ListGridRecord listGridRecord = new ListGridRecord();
      for (String key : keyList) {
        listGridRecord.setAttribute(key, record.get(key));
      }
      recordList.add(listGridRecord);
    }

    List<ListGridField> listGridFields = new ArrayList<ListGridField>();
    for (String title : titleList) {
      listGridFields.add(new ListGridField(title, title));
    }

    list.setFields(listGridFields.toArray(new ListGridField[0]));
    list.setData(recordList);
    
    widgetStateHandler();
  }

  protected void moveToLeftGrid(ListGridRecord[] selectedRecords) {
    if (multipleAssignHandler != null) {
      multipleAssignHandler.onRecordsMove(selectedRecords);
      widgetStateHandler();
    } else {
      for (ListGridRecord selectedRecord : selectedRecords) {
        moveToLeftGrid(selectedRecord);
      }
    }
  }

  protected void moveToLeftGrid(ListGridRecord selectedRecord) {
    if (assignHandler != null) {
      assignHandler.onRecordMove(selectedRecord);
    }
    widgetStateHandler();
  }

  protected void moveToRightGrid(ListGridRecord[] selectedRecords) {
    if (multipleDeassignHandler != null) {
      multipleDeassignHandler.onRecordsMove(selectedRecords);
      widgetStateHandler();
    } else {
      for (ListGridRecord selectedRecord : selectedRecords) {
        moveToRightGrid(selectedRecord);
      }
    }
  }

  protected void moveToRightGrid(ListGridRecord selectedRecord) {
    if (deassignHandler != null) {
      deassignHandler.onRecordMove(selectedRecord);
    }
    widgetStateHandler();
  }

  public SimpleListGridPanel getLeftGrid() {
    return leftGrid;
  }

  public SimpleListGridPanel getRightGrid() {
    return rightGrid;
  }

  public MoveRecordEvent getAssignHandler() {
    return assignHandler;
  }

  public void setAssignHandler(MoveRecordEvent assignHandler) {
    this.assignHandler = assignHandler;
  }

  public MoveRecordEvent getDeassignHandler() {
    return deassignHandler;
  }

  public void setDeassignHandler(MoveRecordEvent deassignHandler) {
    this.deassignHandler = deassignHandler;
  }

  public void setMultipleAssignHandler(MoveMultipleRecordsEvent multipleAssignHandler) {
    this.multipleAssignHandler = multipleAssignHandler;
  }

  public void setMultipleDeassignHandler(MoveMultipleRecordsEvent multipleDeassignHandler) {
    this.multipleDeassignHandler = multipleDeassignHandler;
  }

  private void widgetStateHandler() {
    leftGrid.getListGrid().deselectAllRecords();
    rightGrid.getListGrid().deselectAllRecords();

    arrowLeftBut.disable();
    arrowRightBut.disable();

    rightAllBut.setDisabled(leftGrid.getListGrid().getRecordList().getLength() == 0);
    leftAllBut.setDisabled(rightGrid.getListGrid().getRecordList().getLength() == 0);
  }

  public List<String> getColumnTitles() {
    return columnTitles;
  }

  public List<String> getFieldNames() {
    return fieldNames;
  }

  public void setColumnTitles(List<String> columnTitles) {
    this.columnTitles = columnTitles;
  }

  public void setFieldNames(List<String> fieldNames) {
    this.fieldNames = fieldNames;
  }

}
