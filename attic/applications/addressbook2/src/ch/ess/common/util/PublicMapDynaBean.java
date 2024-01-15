package ch.ess.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:30 $ 
  */
public class PublicMapDynaBean extends BasicDynaBean {
  
  public PublicMapDynaBean(DynaClass dynaClass) {
      super(dynaClass);      
  }
  
  public Map getMap() {
    return values;
  }
}
