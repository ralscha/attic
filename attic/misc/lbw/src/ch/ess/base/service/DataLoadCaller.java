package ch.ess.base.service;

import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "dataLoadCaller", initMethod = "callDataLoad", autowire = Autowire.BYTYPE)
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
