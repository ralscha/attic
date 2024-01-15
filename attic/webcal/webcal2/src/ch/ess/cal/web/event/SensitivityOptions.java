package ch.ess.cal.web.event;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.SensitivityEnum;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 17.10.2003 
  */
public class SensitivityOptions extends Options {

  public void init() {
    getLabelValue().clear();
    getLabelValue().clear();
    getLabelValue().add(new LabelValueBean(getMessage(SensitivityEnum.getKey(SensitivityEnum.NO_SENSITIVITY)), String.valueOf(SensitivityEnum.NO_SENSITIVITY.toInt())));
    getLabelValue().add(new LabelValueBean(getMessage(SensitivityEnum.getKey(SensitivityEnum.PERSONAL)), String.valueOf(SensitivityEnum.PERSONAL.toInt())));
    getLabelValue().add(new LabelValueBean(getMessage(SensitivityEnum.getKey(SensitivityEnum.PRIVATE)), String.valueOf(SensitivityEnum.PRIVATE.toInt())));
    getLabelValue().add(new LabelValueBean(getMessage(SensitivityEnum.getKey(SensitivityEnum.CONFIDENTIAL)), String.valueOf(SensitivityEnum.CONFIDENTIAL.toInt())));


  }

}
