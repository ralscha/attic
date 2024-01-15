package ch.ess.cal.web.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import ch.ess.base.Constants;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.cal.enums.StatusEnum;

@RemoteProxy(name = "taskUpdater")
public class TaskUpdater {
  
  @RemoteMethod
  public Map<String,String> updateCompletePercent(String statusValue, String complete) {
    
    Map<String,String> resultMap = new HashMap<String, String>();
    resultMap.put("complete", complete);
    resultMap.put("completeDate", "");
    
    StatusEnum status = StringValuedEnumReflect.getEnum(StatusEnum.class, statusValue);
    if (status == StatusEnum.COMPLETED) {
      resultMap.put("complete", "100");
      
      DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
      resultMap.put("completeDate", df.format(new Date()));

    } else if (status == StatusEnum.NOTSTARTED) {
      resultMap.put("complete", "0");
    } else {
      if (StringUtils.isNumeric(complete)) {
        int completePercent = Integer.parseInt(complete);
        if (completePercent >= 100) {
          resultMap.put("complete", "75");
        }
      }
    }
    
    return resultMap;
    
  }
  
  @RemoteMethod
  public Map<String,String> updateStatus(String complete, String statusValue) {
    
    Map<String,String> resultMap = new HashMap<String, String>();
    resultMap.put("status", statusValue);
    resultMap.put("completeDate", "");
    
    if (StringUtils.isNumeric(complete)) {
      int completePercent = Integer.parseInt(complete);
      if (completePercent >= 100) {
        resultMap.put("status", StatusEnum.COMPLETED.getValue());
        DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
        resultMap.put("completeDate", df.format(new Date()));
      } else if (completePercent == 0) {
        resultMap.put("status", StatusEnum.NOTSTARTED.getValue());
      } else {
        StatusEnum status = StringValuedEnumReflect.getEnum(StatusEnum.class, statusValue);
        if (status == StatusEnum.COMPLETED) {
          resultMap.put("status", StatusEnum.INPROCESS.getValue());
        }
      }
    }
    
    return resultMap;
  }
}
