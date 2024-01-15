package ch.ess.lbw.web.lieferant;

import java.math.BigDecimal;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.PropertyCopier;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.lbw.LbwUtil;
import ch.ess.lbw.dao.AlarmDao;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Alarm;
import ch.ess.lbw.model.Bewertung;
import ch.ess.lbw.model.Lieferant;
import ch.ess.lbw.model.LieferantWerk;
import ch.ess.lbw.model.Werk;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$stammdaten")
public class LieferantEditAction extends AbstractEditAction<Lieferant> {

  private PropertyCopier propertyCopier;
  private AlarmDao alarmDao;
  private WerkDao werkDao;
  
  public LieferantEditAction() {
    propertyCopier = new PropertyCopier();
    propertyCopier.addExcludeProperty("version");
    propertyCopier.addExcludeProperty("id");
  }
  
  public void setAlarmDao(AlarmDao alarmDao) {
    this.alarmDao = alarmDao;
  }
  
  public void setWerkDao(WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  @Override
  public void formToModel(final ActionContext ctx, Lieferant lieferant) {

    LieferantForm lieferantForm = (LieferantForm)ctx.form();
    propertyCopier.copyProperties(lieferantForm, lieferant);
   
    if (StringUtils.isNotBlank(lieferant.getIso())) {
      lieferant.setIsoPunkte(new BigDecimal("5.0"));
    } else {
      lieferant.setIsoPunkte(new BigDecimal("3.0"));
    }
    lieferantForm.setIsoPunkte(lieferant.getIsoPunkte());
     
   

    if (lieferant.getVda()) {
      lieferant.setVdaPunkte(new BigDecimal("0.5"));
    } else {
      lieferant.setVdaPunkte(new BigDecimal("0.0"));
    }
    lieferantForm.setVdaPunkte(lieferant.getVdaPunkte());
    
   
    if (lieferant.getQs()) {
      lieferant.setQsPunkte(new BigDecimal("0.5"));
    } else {
      lieferant.setQsPunkte(new BigDecimal("0.0"));
    }
    lieferantForm.setQsPunkte(lieferant.getQsPunkte());
    

    if (lieferant.getPolytec()) {
      lieferant.setPolytecPunkte(new BigDecimal("5.0"));
    } else {
      lieferant.setPolytecPunkte(new BigDecimal("0.0"));
    }
    lieferantForm.setPolytecPunkte(lieferant.getPolytecPunkte());
    
    

    
    Set<Bewertung> bewertungen = lieferant.getBewertungen();
    for (Bewertung bewertung : bewertungen) {      
      LbwUtil.updateNoten(bewertung);
    }
    
    
    Set<LieferantWerk> userWerke = lieferant.getLieferantWerke();
    for (LieferantWerk uw : userWerke) {
      uw.getWerk().getLieferantWerke().remove(uw);
    }
    
    lieferant.getLieferantWerke().clear();

    String[] werkIds = lieferantForm.getWerkIds();
    if (werkIds != null) {

      for (String id : werkIds) {
        if (StringUtils.isNotBlank(id)) {
          Werk werk = werkDao.findById(id);
          if (werk != null) {

            LieferantWerk userWerk = new LieferantWerk();
            userWerk.setLieferant(lieferant);
            userWerk.setWerk(werk);

            lieferant.getLieferantWerke().add(userWerk);
            werk.getLieferantWerke().add(userWerk);

          }
        }
      }
    }
    

  }

  @Override
  public void modelToForm(final ActionContext ctx, final Lieferant lieferant) {

    LieferantForm lieferantForm = (LieferantForm)ctx.form();

    propertyCopier.copyProperties(lieferant, lieferantForm);
    
    if (lieferant.getId() != null) {
    
      Alarm alarm = alarmDao.find(lieferant.getId().toString(), "ISO");
      if (alarm != null) {
        lieferantForm.setIsoAlarmId(alarm.getId().toString());
      } else {
        lieferantForm.setIsoAlarmId(null);
      }
    } else {
      lieferantForm.setIsoAlarmId(null);
    }
    
    //Werks
    Set<LieferantWerk> userWerke = lieferant.getLieferantWerke();
    if (!userWerke.isEmpty()) {
      String[] werkIds = new String[userWerke.size()];

      int ix = 0;
      for (LieferantWerk userWerk : userWerke) {
        werkIds[ix++] = userWerk.getWerk().getId().toString();
      }
      lieferantForm.setWerkIds(werkIds);
    } else {
      lieferantForm.setWerkIds(null);
    }
    
    lieferantForm.setTabset("adresse");
    lieferantForm.setAlarmDatum(null);
    lieferantForm.setAlarmEmpfaenger(null);
    lieferantForm.setAlarmSubjekt(null);
    lieferantForm.setAlarmText(null);

    
  }

}
