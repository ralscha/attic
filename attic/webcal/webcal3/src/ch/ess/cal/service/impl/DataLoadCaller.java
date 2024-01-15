package ch.ess.cal.service.impl;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:12 $ 
 * 
 * @spring.bean id="dataLoadCaller" init-method="callDataLoad"
 */
public class DataLoadCaller {

  /* diese classe ist notwendig weil auf dem Dataload keine 
   * Init-Methode ausgeführt werden kann
   * durch die Verwendung des TransactionProxy
   */
  private DataLoad dataLoad;

  /**
   * @spring.property reflocal="dataLoadService"
   */
  public void setDataLoad(final DataLoad dataLoad) {
    this.dataLoad = dataLoad;
  }

  public void callDataLoad() {
    dataLoad.initLoad();
  }
}
