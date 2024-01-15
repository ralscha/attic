package ch.ess.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:47 $ 
  */
public class PublicMapDynaBean extends BasicDynaBean {
  
  public PublicMapDynaBean(DynaClass dynaClass) {
      super(dynaClass);      
  }
  
  public Map getMap() {
    return values;
  }
}
