package ch.ess.cal.web.event;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.ImportanceEnum;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 17.10.2003 
  */
public class ImportanceOptions extends Options {

  public void init() {
    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(getMessage(ImportanceEnum.getKey(ImportanceEnum.NORMAL)), String.valueOf(ImportanceEnum.NORMAL.toInt())));
    getLabelValue().add(new LabelValueBean(getMessage(ImportanceEnum.getKey(ImportanceEnum.HIGH)), String.valueOf(ImportanceEnum.HIGH.toInt())));
    getLabelValue().add(new LabelValueBean(getMessage(ImportanceEnum.getKey(ImportanceEnum.LOW)), String.valueOf(ImportanceEnum.LOW.toInt())));    
  }

}
