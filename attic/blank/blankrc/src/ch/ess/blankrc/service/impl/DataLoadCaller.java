package ch.ess.blankrc.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/13 08:42:25 $ 
 * 
 * @spring.bean id="dataLoadCaller" init-method="callDataLoad"
 * @spring.property name="dataLoad" reflocal="dataLoadService"
 */
public class DataLoadCaller {
  
  
  /* diese classe ist notwendig weil die init-methode vor dem
   * BeanNameAutoProxyCreator ausgeführt wird und die 
   * Methode initDataLoad innerhalb einer Transaction ausgeführt 
   * werden muss
   */  
  
  private static final Log logger = LogFactory.getLog(DataLoadCaller.class);
  
  
  private DataLoad dataLoad;
  
  
  public void setDataLoad(DataLoad dataLoad) {
    this.dataLoad = dataLoad;
  }
  
  
  public void callDataLoad() {
    logger.info("start DataLoadCaller.callDataLoad start");
    dataLoad.initLoad();    
  }
}
