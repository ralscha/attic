package ch.ess.lbw.web.reports;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.JRPropertyDataSource;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.BigDecimalNoDecimalDigitsConverter;
import ch.ess.base.web.ExportPropertyDescription;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.lbw.dao.BewertungDao;
import ch.ess.lbw.dao.LieferantDao;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Bewertung;
import ch.ess.lbw.model.Lieferant;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.taglib.TagHelp;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ControlButton;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ColumnDesignModel;
import com.cc.framework.ui.model.ColumnImageDesignModel;
import com.cc.framework.ui.model.DesignRule;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.ListDesignModel;
import com.cc.framework.ui.model.imp.ColumnImageDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnTextDesignModelImp;
import com.cc.framework.ui.model.imp.DesignRuleImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.ListDesignModelImp;

@Role("$bewertung")
public class BewertungReportListAction extends AbstractListAction {

  
  private LieferantDao lieferantDao;
  private BewertungDao bewertungDao;
  private WerkDao werkDao;

  public void setLieferantDao(LieferantDao lieferantDao) {
    this.lieferantDao = lieferantDao;
  }

  public void setBewertungDao(BewertungDao bewertungDao) {
    this.bewertungDao = bewertungDao;
  }

  public void setWerkDao(WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  @Override
  public boolean doPreExecute(ActionContext ctx) throws Exception {

    MapForm searchForm = (MapForm)ctx.form();

    String quartalStr = searchForm.getStringValue("quartal");
    String jahrStr = searchForm.getStringValue("jahr");

    Calendar today = new GregorianCalendar();

    if (StringUtils.isBlank(quartalStr)) {
      int monat = today.get(Calendar.MONTH);
      searchForm.setValue("quartal", String.valueOf((monat / 3) + 1));
    }

    if (StringUtils.isBlank(jahrStr)) {
      searchForm.setValue("jahr", String.valueOf(today.get(Calendar.YEAR)));
    }


    return super.doPreExecute(ctx);
  }

  
  @Override
  public void doExecute(final ActionContext ctx) throws Exception {
    if (ctx.request().getParameter("nolist") == null) {

      SimpleListControl listControl = new SimpleListControl();

      ListDesignModel designModel = new ListDesignModelImp();
      designModel.setLocaleName("false");
      designModel.setName("listControl");
      designModel.setTitle(Util.translate(ctx.request(), "reports.bewertungen"));
      designModel.setRowCount(Integer.parseInt((String)ctx.session().getAttribute("noRows")));
      designModel.setButtonPermission(ControlButton.REFRESH, TagHelp.toPermission("true"));
      designModel.setButtonPermission(ControlButton.CREATE, TagHelp.toPermission("false"));
      designModel.setButtonPermission(ControlButton.PRINTLIST, TagHelp.toPermission("true"));
      designModel.setButtonPermission(ControlButton.EXPORTLIST, TagHelp.toPermission("true"));
      designModel.setId("bewertungReportList");

      ColumnDesignModel columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle(Util.translate(ctx.request(), "lieferant.nr"));
      columnDesignModel.setProperty("nr");
      columnDesignModel.setWidth(70);
      columnDesignModel.setSortable(true);
      designModel.addColumn(columnDesignModel);

      columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle(Util.translate(ctx.request(), "lieferant"));
      columnDesignModel.setProperty("lieferant");
      columnDesignModel.setWidth(200);
      columnDesignModel.setMaxLength(38);
      columnDesignModel.setSortable(true);
      designModel.addColumn(columnDesignModel);
      
      List<Werk> werke = werkDao.findAll();
      for (Werk werk : werke) {
        
        columnDesignModel = new ColumnTextDesignModelImp();
        columnDesignModel.setTitle(werk.getName());
        columnDesignModel.setProperty("W"+werk.getId().toString());
        columnDesignModel.setWidth(90);
        columnDesignModel.setAlignment(AlignmentType.RIGHT);
        columnDesignModel.setSortable(true);        
        columnDesignModel.setConverter(new BigDecimalNoDecimalDigitsConverter());
        designModel.addColumn(columnDesignModel);      
      }
 
      columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle(Util.translate(ctx.request(), "bewertung.gesamtnote"));
      columnDesignModel.setProperty("gesamtnote");
      columnDesignModel.setWidth(90);
      columnDesignModel.setAlignment(AlignmentType.RIGHT);
      columnDesignModel.setSortable(true);
      columnDesignModel.setConverter(new BigDecimalNoDecimalDigitsConverter());
      designModel.addColumn(columnDesignModel);
      
      columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle(Util.translate(ctx.request(), "bewertung.einstufung"));
      columnDesignModel.setProperty("einstufung");
      columnDesignModel.setWidth(90);
      columnDesignModel.setSortable(true);
      columnDesignModel.setAlignment(AlignmentType.CENTER);
      
      DesignRule rule = new DesignRuleImp();
      rule.setRule("@{bean.map.einstufung == 'C'}");
      rule.setStyle("background-color: red;");
      columnDesignModel.addDesignRule(rule);
      
      rule = new DesignRuleImp();
      rule.setRule("@{bean.map.einstufung == 'B'}");
      rule.setStyle("background-color: yellow;");
      columnDesignModel.addDesignRule(rule);
      
      rule = new DesignRuleImp();
      rule.setRule("@{bean.map.einstufung == 'AB'}");
      rule.setStyle("background-color: yellow;");
      columnDesignModel.addDesignRule(rule);
      
      rule = new DesignRuleImp();
      rule.setRule("@{bean.map.einstufung == 'A'}");
      rule.setStyle("background-color: lime;");
      columnDesignModel.addDesignRule(rule);
      
      designModel.addColumn(columnDesignModel);
      
      
      
      columnDesignModel = new ColumnTextDesignModelImp();
      columnDesignModel.setTitle(Util.translate(ctx.request(), "bewertung.letztesQuartal"));
      columnDesignModel.setProperty("letztesQuartal");
      columnDesignModel.setWidth(120);
      columnDesignModel.setConverter(new BigDecimalNoDecimalDigitsConverter());
      columnDesignModel.setAlignment(AlignmentType.RIGHT);
      columnDesignModel.setSortable(true);
      designModel.addColumn(columnDesignModel);

      
      
      ImageMap imageMap = new ImageMap();
      ImageModelImp tief = new ImageModelImp();
      tief.setHeight(11);
      tief.setWidth(11);
      tief.setTooltip(Util.translate(ctx.request(), "bewertung.tendenz.tiefer"));
      tief.setSource("images/tendenztief.gif");

      ImageModelImp gleich = new ImageModelImp();
      gleich.setHeight(11);
      gleich.setWidth(11);
      gleich.setTooltip(Util.translate(ctx.request(), "bewertung.tendenz.gleich"));
      gleich.setSource("images/tendenzgleich.gif");

      ImageModelImp hoch = new ImageModelImp();
      hoch.setHeight(11);
      hoch.setWidth(11);
      hoch.setTooltip(Util.translate(ctx.request(), "bewertung.tendenz.hoeher"));
      hoch.setSource("images/tendenzhoch.gif");
      
      imageMap.addImage("T", tief);
      imageMap.addImage("G", gleich);
      imageMap.addImage("H", hoch);
      
      
      ColumnImageDesignModel columnImageDesignModel = new ColumnImageDesignModelImp();
      //columnImageDesignModel.setTitle(Util.translate(ctx.request(), "bewertung.tendenz"));
      columnImageDesignModel.setProperty("tendenz");
      columnImageDesignModel.setAlignment(AlignmentType.CENTER);
      columnImageDesignModel.setSortable(true);
      columnImageDesignModel.setImageMap(imageMap);
      
      designModel.addColumn(columnImageDesignModel);
      
      
      listControl.setDesignModel(designModel);
      listControl.setDataModel(getDataModel(ctx));

      listControl.setSortInfo("lieferant", SortOrder.ASCENDING);
   
      
      ctx.session().setAttribute(getListAttributeName(), listControl);

    }
    ctx.forwardToInput();
  }
  
  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    String quartalStr = searchForm.getStringValue("quartal");
    String jahrStr = searchForm.getStringValue("jahr");
    String einstufungFilter = searchForm.getStringValue("einstufung");
    
    int quartal = Integer.parseInt(quartalStr);
    int jahr = Integer.parseInt(jahrStr);

    List<Werk> werke = werkDao.findAll();
    List<Lieferant> lieferanten = lieferantDao.findAllOrderBy(Order.asc("kurz"));
    
    for (Lieferant lieferant : lieferanten) {

      DynaBean dynaBean = new LazyDynaBean("bewertungList");

      dynaBean.set("id", lieferant.getId().toString());      
      dynaBean.set("nr", lieferant.getNr());
      dynaBean.set("lieferant", lieferant.getKurz());
      
      int count = 0;
      BigDecimal total = Constants.ZERO;
      
      for (Werk werk : werke) {
        
        Bewertung bewertung = bewertungDao.find(lieferant, quartal, jahr, werk.getId().toString());
        if ((bewertung != null) && (bewertung.getGesamtNote() != null) && (bewertung.getGesamtNote().compareTo(Constants.ZERO) != 0)) {
          dynaBean.set("W"+werk.getId(), bewertung.getGesamtNote());
          count++;
          total = total.add(bewertung.getGesamtNote());
        } else {
          dynaBean.set("W"+werk.getId(), null);
        }
      }
      
      BigDecimal note = null;
      if (count > 0) {
        note = total.divide(new BigDecimal(count), 0, BigDecimal.ROUND_HALF_UP);
        dynaBean.set("gesamtnote", note);
      } 

      
      String einstufung = null;
      
      if (total.compareTo(Constants.ZERO) != 0) {
        if (total.compareTo(new BigDecimal(90)) >= 0) {
          einstufung = "A";
        } else if (total.compareTo(new BigDecimal(80)) >= 0) {
          einstufung = "AB";
        } else if (total.compareTo(new BigDecimal(60)) >= 0) {
          einstufung = "B";
        } else {
          einstufung = "C";
        }
        
      }
      
      if (StringUtils.isNotBlank(einstufungFilter)) {
        if (einstufung != null) {
          if (!einstufungFilter.equals(einstufung)) {
            continue;
          }
        }
      }
      
      dynaBean.set("einstufung", einstufung);
      
      
      int vorquartal = quartal - 1;
      int vorjahr = jahr;
      if (vorquartal == 0) {
        vorquartal = 4;
        vorjahr--;
      }
      
      count = 0;
      BigDecimal vortotal = Constants.ZERO;
      
      for (Werk werk : werke) {
        
        Bewertung bewertung = bewertungDao.find(lieferant, vorquartal, vorjahr, werk.getId().toString());
        if ((bewertung != null) && (bewertung.getGesamtNote() != null) && (bewertung.getGesamtNote().compareTo(Constants.ZERO) != 0)) {
          count++;
          vortotal = vortotal.add(bewertung.getGesamtNote());
        } 
      }
      
      if (count > 0) {        
        BigDecimal vorNote = vortotal.divide(new BigDecimal(count), 0, BigDecimal.ROUND_HALF_UP);
        dynaBean.set("letztesQuartal", vorNote);
        
        if (note != null) { 
          String tendenz;
          
          if (note.intValue() > vorNote.intValue()) {
            tendenz = "H";
          } else if (note.intValue() < vorNote.intValue()) {
            tendenz = "T";
          } else {
            tendenz = "G";
          }
          dynaBean.set("tendenz", tendenz);
        }
      } 
      
      
      dataModel.add(dynaBean);

    }
    
    
    
    dataModel.setExportFileName("bewertung");
    dataModel.addExportProperty("lieferant.nr", "nr");
    dataModel.addExportProperty("lieferant", "lieferant");
    
    for (Werk werk : werke) {
      ExportPropertyDescription descr = new ExportPropertyDescription(werk.getName(), "W"+werk.getId().toString(), 3700, AlignmentType.RIGHT);
      dataModel.addExportProperty(descr);
    }
    
    dataModel.addExportProperty("bewertung.gesamtnote", "gesamtnote");
    dataModel.addExportProperty("bewertung.einstufung", "einstufung");
    
    dataModel.addExportProperty("bewertung.letztesQuartal", "letztesQuartal");

    
    return dataModel;
  }


  
  @Override
  public void onPrintList(ControlActionContext ctx) throws Exception {
    
    MapForm searchForm = (MapForm)ctx.form();

    String quartal = searchForm.getStringValue("quartal");
    String jahr = searchForm.getStringValue("jahr");

    String quartalStr = "";
    if ("1".equals(quartal)) {
      quartalStr = Util.translate(ctx.request(), "quartal.1") + " " + jahr;
    } else if ("2".equals(quartal)) {
      quartalStr = Util.translate(ctx.request(), "quartal.2") + " " + jahr;
    } else if ("3".equals(quartal)) {
      quartalStr = Util.translate(ctx.request(), "quartal.3") + " " + jahr;
    } else if ("4".equals(quartal)) {
      quartalStr = Util.translate(ctx.request(), "quartal.4") + " " + jahr;
    }
    
    String reportTemplate = "/WEB-INF/reports/bewertung.jasper";
    
    String filename = "bewertung.pdf";
    Util.setExportHeader(ctx.response(), "application/pdf", filename);        
    ctx.response().setHeader("extension", "pdf");

    
    Map<String,Object> parameters = new HashMap<String,Object>();
    parameters.put("REPORT_LOCALE", getLocale(ctx.request())); 
    System.out.println(quartalStr);
    parameters.put("quartal", quartalStr);
    
    SimpleListDataModel dataModel = (SimpleListDataModel)ctx.control().getDataModel();
    

    JRPropertyDataSource ds = new JRPropertyDataSource(dataModel.getObjectList());

    BigDecimalNoDecimalDigitsConverter conv = new BigDecimalNoDecimalDigitsConverter(); 
    ds.addConverter("gesamtnote", conv);
    
    List<Werk> werke = werkDao.findAll();
    for (Werk werk : werke) {      
      ds.addConverter("W"+werk.getId().toString(), conv);            
    }    
            
    JasperPrint print = JasperFillManager.fillReport(ctx.session().getServletContext().getResourceAsStream(reportTemplate),
        parameters, ds);
    
    
    OutputStream out = ctx.response().getOutputStream();
    JasperExportManager.exportReportToPdfStream(print, out);
    out.close();
    
    ctx.forwardToResponse();
    
    
  }

  @Override
  public void deleteObject(ControlActionContext ctx, String key) throws Exception {
    //not possible
    return;
  }

}
