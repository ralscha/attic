package ch.ess.cal.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a Configuration
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:46 $
  * @hibernate.class  table="calConfiguration"
  */
public class Configuration extends Persistent {

  private String name;
  private String propValue;


  /** 
  * @hibernate.property length="100" not-null="true" unique="true"
  */
  public String getName() {
    return name;
  }

  /** 
  * @hibernate.property length="255"
  */
  public String getPropValue() {
    return propValue;
  }

  public void setName(String string) {
    name = string;
  }

  public void setPropValue(String string) {
    propValue = string;
  }

}
