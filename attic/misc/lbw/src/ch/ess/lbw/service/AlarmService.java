package ch.ess.lbw.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import ch.ess.lbw.dao.AlarmDao;
import ch.ess.lbw.model.Alarm;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "alarmService", autowire = Autowire.BYTYPE)
public class AlarmService {

  private AlarmDao alarmDao;

  public void setAlarmDao(AlarmDao alarmDao) {
    this.alarmDao = alarmDao;
  }

  public void deleteAlarm(String id, String typ) {
    alarmDao.delete(id, typ);
  }
  
  public AlarmDisplay getAlarm(String id) {
    if (StringUtils.isNotBlank(id)) {
      Alarm alarm = alarmDao.findById(id);
      if (alarm != null) {
        
        AlarmDisplay adisplay = new AlarmDisplay();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        adisplay.setAlarmDatum(df.format(alarm.getDatum()));
        adisplay.setAlarmEmpfaenger(alarm.getEmpfaenger());
        adisplay.setAlarmSubjekt(alarm.getSubjekt());
        adisplay.setAlarmText(alarm.getText());
        return adisplay;
        
      }
    }
    return null;
  }

  public String updateAlarm(String id, String typ, String datum, String empfaenger, String subjekt, String text) {
    Alarm alarm = alarmDao.find(id, typ);
    if (alarm == null) {
      alarm = new Alarm();
    }

    alarm.setTargetId(new Integer(id));
    alarm.setTyp(typ);

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    try {
      alarm.setDatum(df.parse(datum));

      alarm.setEmpfaenger(empfaenger);
      alarm.setSubjekt(subjekt);
      alarm.setText(text);

      alarmDao.save(alarm);
      
      return alarm.getId().toString();
    } catch (ParseException e) {
      return null;
    }
  }
}
