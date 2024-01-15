package ch.ess.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:14:33 $ 
  */
public class PublicMapDynaBean extends BasicDynaBean {
  
  public PublicMapDynaBean(DynaClass dynaClass) {
      super(dynaClass);      
  }
  
  public Map getMap() {
    return values;
  }
}
