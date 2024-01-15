package ch.ess.lbw.web.bewertung;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.lbw.LbwUtil;
import ch.ess.lbw.dao.BewertungDao;
import ch.ess.lbw.dao.LieferantDao;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Bewertung;
import ch.ess.lbw.model.Lieferant;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;

@Role("$bewertung")
public class BewertungListAction extends AbstractListAction {

  
  private LieferantDao lieferantDao;
  private BewertungDao bewertungDao;
  private WerkDao werkDao;
  private UserDao userDao;

  public void setLieferantDao(LieferantDao lieferantDao) {
    this.lieferantDao = lieferantDao;
  }

  public void setBewertungDao(BewertungDao bewertungDao) {
    this.bewertungDao = bewertungDao;
  }

  public void setWerkDao(WerkDao werkDao) {
    this.werkDao = werkDao;
  }
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void save_onClick(final FormActionContext ctx) {
    SimpleListControl listControl = (SimpleListControl)ctx.session().getAttribute("listControl");
    SimpleListDataModel dataModel = (SimpleListDataModel)listControl.getDataModel();
    
    for (int i=0; i < dataModel.size(); i++){
      DynaBean dyna = (DynaBean)dataModel.getElementAt(i);
      String id = (String) dyna.get("id");
      
      Bewertung bewertung = bewertungDao.findById(id);
      
      String preis = (String)dyna.get("preis");
      String service = (String)dyna.get("service");
      String teilequalitaet = (String)dyna.get("teilequalitaet");
      String mengentreue = (String)dyna.get("mengentreue");
      String liefertreue = (String)dyna.get("liefertreue");
      
      if (StringUtils.isNotBlank(preis)) {
        try {
          bewertung.setPreis(new BigDecimal(preis));
        } catch (NumberFormatException e) {
          bewertung.setPreis(null);
        }
      } else {
        bewertung.setPreis(null);
      }

      if (StringUtils.isNotBlank(service)) {
        try {
          bewertung.setService(new BigDecimal(service));
        } catch (NumberFormatException e) {
          bewertung.setService(null);
        }
      } else {
        bewertung.setService(null);
      }
      
      if (StringUtils.isNotBlank(teilequalitaet)) {
        try {
          bewertung.setTeilequalitaet(new BigDecimal(teilequalitaet));
        } catch (NumberFormatException e) {
          bewertung.setTeilequalitaet(null);
        }
      } else {
        bewertung.setTeilequalitaet(null);
      }
      
      if (StringUtils.isNotBlank(mengentreue)) {
        try {
          bewertung.setSapMengentreue(new BigDecimal(mengentreue));
        } catch (NumberFormatException e) {
          bewertung.setSapMengentreue(null);
        }
      } else {
        bewertung.setSapMengentreue(null);
      }
      
      if (StringUtils.isNotBlank(liefertreue)) {
        try {
          bewertung.setSapLiefertreue(new BigDecimal(liefertreue));
        } catch (NumberFormatException e) {
          bewertung.setSapLiefertreue(null);
        }
      } else {
        bewertung.setSapLiefertreue(null);
      }
      
      
      LbwUtil.updateNoten(bewertung);     
      bewertungDao.save(bewertung);
    }
   
  }

  
  @Override
  public boolean doPreExecute(ActionContext ctx) throws Exception {

    MapForm searchForm = (MapForm)ctx.form();

    String quartalStr = searchForm.getStringValue("quartal");
    String jahrStr = searchForm.getStringValue("jahr");
    String werkId = searchForm.getStringValue("werkId");

    Calendar today = new GregorianCalendar();

    if (StringUtils.isBlank(quartalStr)) {
      int monat = today.get(Calendar.MONTH);
      searchForm.setValue("quartal", String.valueOf((monat / 3) + 1));
    }

    if (StringUtils.isBlank(jahrStr)) {
      searchForm.setValue("jahr", String.valueOf(today.get(Calendar.YEAR)));
    }

    if (StringUtils.isBlank(werkId)) {
      List<Werk> werke = werkDao.findAll();
      if (!werke.isEmpty()) {
        searchForm.setValue("werkId", werke.iterator().next().getId().toString()); 
      }
    }

    return super.doPreExecute(ctx);
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    User user = Util.getUser(ctx.session(), userDao);
    
    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();

    String quartalStr = searchForm.getStringValue("quartal");
    String jahrStr = searchForm.getStringValue("jahr");
    String werkId = searchForm.getStringValue("werkId");
    
    Werk werk = werkDao.findById(werkId);
    
    String lieferantStr = searchForm.getStringValue("name");
    String lieferantnr = searchForm.getStringValue("nr");

    int quartal = Integer.parseInt(quartalStr);
    int jahr = Integer.parseInt(jahrStr);

    List<Lieferant> lieferanten = lieferantDao.find(lieferantStr, lieferantnr, werk);


    
    for (Lieferant lieferant : lieferanten) {

      DynaBean dynaBean = new LazyDynaBean("bewertungList");

      Bewertung bewertung = bewertungDao.find(lieferant, quartal, jahr, werkId);
      if (bewertung == null) {
        bewertung = new Bewertung();
        
        bewertung.setLieferant(lieferant);
        bewertung.setQuartal(quartal);
        bewertung.setJahr(jahr);
        bewertung.setWerk(werk);
        
        bewertungDao.save(bewertung);
      } 

      dynaBean.set("id", bewertung.getId().toString());
      
      dynaBean.set("lieferant", lieferant.getKurz());
      if (bewertung.getPreis() != null) {
        dynaBean.set("preis", Constants.ONE_DECIMAL_FORMAT.format(bewertung.getPreis()));
      } else {
        dynaBean.set("preis", null);
      }
      if (bewertung.getService() != null) {
        dynaBean.set("service", Constants.ONE_DECIMAL_FORMAT.format(bewertung.getService()));
      } else {
        dynaBean.set("service", null);
      }
      if (bewertung.getTeilequalitaet() != null) {
        dynaBean.set("teilequalitaet", Constants.ONE_DECIMAL_FORMAT.format(bewertung.getTeilequalitaet()));
      } else {
        dynaBean.set("teilequalitaet", null);
      }

      if (bewertung.getSapMengentreue() != null) {
        dynaBean.set("mengentreue", Constants.ONE_DECIMAL_FORMAT.format(bewertung.getSapMengentreue()));
      } else {
        dynaBean.set("mengentreue", null);
      }

      if (bewertung.getSapLiefertreue() != null) {
        dynaBean.set("liefertreue", Constants.ONE_DECIMAL_FORMAT.format(bewertung.getSapLiefertreue()));
      } else {
        dynaBean.set("liefertreue", null);
      }

      if ((user.getKriteriumPreis() != null) && (user.getKriteriumPreis())) {
        ctx.session().setAttribute("preisAnzeige", true);
      } else {
        ctx.session().setAttribute("preisAnzeige", false);
      }
      
      if ((user.getKriteriumService() != null) && (user.getKriteriumService())) {
        ctx.session().setAttribute("serviceAnzeige", true);
      } else {
        ctx.session().setAttribute("serviceAnzeige", false);
      }    
      
      if ((user.getKriteriumTeilequalitaet() != null) && (user.getKriteriumTeilequalitaet())) {
        ctx.session().setAttribute("teilequalitaetAnzeige", true);
      } else {
        ctx.session().setAttribute("teilequalitaetAnzeige", false);
      }  
      
      if ((user.getKriteriumSap() != null) && (user.getKriteriumSap())) {
        ctx.session().setAttribute("sapAnzeige", true);
      } else {
        ctx.session().setAttribute("sapAnzeige", false);
      }        
      
      dataModel.add(dynaBean);

    }

    return dataModel;
  }

  
  
  @Override
  protected void setListControlAttributes(SimpleListControl listControl) {
    listControl.setSortInfo("lieferant", SortOrder.ASCENDING);
  }

  @Override
  public void deleteObject(ControlActionContext ctx, String key) throws Exception {
    //not possible
    return;
  }

}
