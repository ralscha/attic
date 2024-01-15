package ch.ess.ex4u.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ch.ess.ex4u.shared.VertragServiceRequest;
import ch.ess.ex4u.shared.VertragServiceRequestAsync;
import ch.ess.ex4u.shared.ZeitRPC;
import ch.ess.ex4u.shared.ZielgefaessServiceRequest;
import ch.ess.ex4u.shared.ZielgefaessServiceRequestAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;

//@SuppressWarnings("unused")
public class ZeitObjConverter {

  private MainLayout mainPanel;
  private Long loginUserId;
  private boolean userIsAdmin;
  
  protected ListGridField vertragField;
  protected ListGridField zielgefaessField;
  
  protected ZielgefaessServiceRequestAsync zielgefaessRequestHandler;
  protected VertragServiceRequestAsync vertragRequestHandler;

  public ZeitObjConverter(MainLayout mainLayout) {
    mainPanel = mainLayout;

    zielgefaessRequestHandler = (ZielgefaessServiceRequestAsync)GWT.create(ZielgefaessServiceRequest.class);
    vertragRequestHandler = (VertragServiceRequestAsync)GWT.create(VertragServiceRequest.class);

    loginUserId = mainPanel.getPrincipal().getId();
    Set<String> roles = mainPanel.getPrincipal().getRoles();
    userIsAdmin = roles.contains("ROLE_ADMIN");
    
    fetchVertraege();
    fetchZielgefaesse(null);
  }

  private static final Map<String, String> periodeValueMap = new HashMap<String, String>();
  static {
    periodeValueMap.put("d", "Tag");
    periodeValueMap.put("w", "Woche");
    periodeValueMap.put("m", "Monat");
  }

  public ListGridRecord obj2Record(ZeitRPC zeit) {
    ListGridRecord listGridRecord = new ListGridRecord();

    if (zeit != null) {
      listGridRecord.setAttribute("id", zeit.getId());

      listGridRecord.setAttribute("periode", zeit.getPeriode());

      listGridRecord.setAttribute("vonDatum", zeit.getVonDatum());

      listGridRecord.setAttribute("bisDatum", zeit.getBisDatum());

      listGridRecord.setAttribute("vonZeit", zeit.getVonZeit());

      listGridRecord.setAttribute("bisZeit", zeit.getBisZeit());

      listGridRecord.setAttribute("stunden", zeit.getStunden());

      listGridRecord.setAttribute("spesen", zeit.getSpesen());

      listGridRecord.setAttribute("bemerkung", zeit.getBemerkung());

      listGridRecord.setAttribute("genehmigt", zeit.isGenehmigt());

      listGridRecord.setAttribute("userId", zeit.getUserId());

      listGridRecord.setAttribute("vertragId", zeit.getVertragId());

      listGridRecord.setAttribute("zielgefaessId", zeit.getZielgefaessId());
    }
    return listGridRecord;
  }
  
  public ZeitRPC record2Obj(ListGridRecord listGridRecord) {
    ZeitRPC zeitRPC = new ZeitRPC();

    if (listGridRecord != null) {
      zeitRPC.setId(Long.parseLong(listGridRecord.getAttribute("id")));

      zeitRPC.setPeriode(listGridRecord.getAttribute("periode"));

      zeitRPC.setVonDatum(listGridRecord.getAttributeAsDate("vonDatum"));

      zeitRPC.setBisDatum(listGridRecord.getAttributeAsDate("bisDatum"));

      zeitRPC.setVonZeit(listGridRecord.getAttributeAsDate("vonZeit"));

      zeitRPC.setBisZeit(listGridRecord.getAttributeAsDate("bisZeit"));

      zeitRPC.setStunden(Double.parseDouble(listGridRecord.getAttribute("stunden")));

      zeitRPC.setSpesen(Double.parseDouble(listGridRecord.getAttribute("spesen")));

      zeitRPC.setBemerkung(listGridRecord.getAttribute("bemerkung"));

      zeitRPC.setGenehmigt(listGridRecord.getAttributeAsBoolean("genehmigt"));

      zeitRPC.setUserId(Long.parseLong(listGridRecord.getAttribute("userId")));

      zeitRPC.setVertragId(Long.parseLong(listGridRecord.getAttribute("vertragId")));

      zeitRPC.setZielgefaessId(Long.parseLong(listGridRecord.getAttribute("zielgefaessId")));
    }
    return zeitRPC;
  }


  public RecordList getRecordList(ArrayList<ZeitRPC> zeiten) {
    RecordList recordList = new RecordList();

    if (zeiten != null) {
      for (ZeitRPC zeit : zeiten) {
        recordList.add(obj2Record(zeit));
      }
    }
    
    return recordList;
  }

  public ListGridField[] getListGridFields() {
    List<ListGridField> listGridFields = new ArrayList<ListGridField>();

    final ListGridField periode = new ListGridField("periode", "Periode");
    periode.setValueMap(periodeValueMap);
    listGridFields.add(periode);

    listGridFields.add(new DateListGridField("vonDatum", "Von"));
    listGridFields.add(new DateListGridField("bisDatum", "Bis"));

    listGridFields.add(new DateListGridField("vonZeit", "Von", DateListGridField.DEFAULT_TIME_FORMAT));
    listGridFields.add(new DateListGridField("bisZeit", "Bis", DateListGridField.DEFAULT_TIME_FORMAT));

    listGridFields.add(new NumberListGridField("stunden", "Stunden"));
    listGridFields.add(new NumberListGridField("spesen", "Spesen", NumberListGridField.SIMPLE_CURRENCY_FORMAT));

    listGridFields.add(new ListGridField("bemerkung", "Bemerkung"));

    listGridFields.add(new BooleanListGridField("genehmigt", "Genehmigt"));

    vertragField = new ListGridField("vertragId", "Vertrag");
    vertragField.addCellSavedHandler(new VertragChangedHandler());
    listGridFields.add(vertragField);

    zielgefaessField = new ListGridField("zielgefaessId", "Zielgefäss");
    listGridFields.add(zielgefaessField);

    return listGridFields.toArray(new ListGridField[0]);
  }

  protected void fetchVertraege() {
    Long userId = userIsAdmin ? null : loginUserId;
    vertragRequestHandler.fetchAsValueMap(userId, new VertragFetchCallback());
  }

  protected class VertragFetchCallback implements AsyncCallback<HashMap<String, String>> {

    @Override
    public void onFailure(Throwable caught) {
      caught.printStackTrace();
      SC.say("Server Timeout: requesting contracts failed!");
    }

    @Override
    public void onSuccess(HashMap<String, String> vertragValueMap) {
      String s = "";
      for (String vn : vertragValueMap.values()) {
        if (s.length() > 0)
          s += ", ";
        s += vn;
      }
      //SC.say("contracts: " + s);
      vertragField.setValueMap(vertragValueMap);
    }
  }

  protected class VertragChangedHandler implements CellSavedHandler {

    @Override
    public void onCellSaved(CellSavedEvent event) {

      String vertragId = (String)event.getNewValue();

      fetchZielgefaesse(Long.parseLong(vertragId));
    }
  }

  protected void fetchZielgefaesse(Long vertragId) {
    zielgefaessRequestHandler.fetchAsValueMap(vertragId, new ZielgefaessFetchCallback());
  }

  protected class ZielgefaessFetchCallback implements AsyncCallback<HashMap<String, String>> {

    @Override
    public void onFailure(Throwable caught) {
      caught.printStackTrace();
      SC.say("Server Timeout: requesting bins failed!");
    }

    @Override
    public void onSuccess(HashMap<String, String> gefaessValueMap) {
      String s = "";
      for (String zg : gefaessValueMap.values()) {
        if (s.length() > 0)
          s += ", ";
        s += zg;
      }
      //SC.say("bins: " + s);
      zielgefaessField.setValueMap(gefaessValueMap);
    }
  }
  
  public void onZeitRecordClick(RecordClickEvent event) {
    
    Record record = event.getRecord();
    String vertragId = record.getAttribute("vertragId");
    // update Zeitgefäss select options 
    fetchZielgefaesse(Long.parseLong(vertragId));
  }

}
