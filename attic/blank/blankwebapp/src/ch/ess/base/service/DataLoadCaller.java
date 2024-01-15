package ch.ess.base.service;


/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean id="dataLoadCaller" init-method="callDataLoad" autowire="byType"
 */
public class DataLoadCaller {

  /* diese classe ist notwendig weil auf dem Dataload keine 
   * Init-Methode ausgeführt werden kann
   * durch die Verwendung des TransactionProxy
   */
  private DataLoad dataLoad;

  public void setDataLoad(final DataLoad dataLoad) {
    this.dataLoad = dataLoad;
  }

  public void callDataLoad() {
    dataLoad.initLoad();
  }
}
