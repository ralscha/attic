package ch.ess.addressbook.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a Configuration
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:26 $
  * @hibernate.class  table="abConfiguration"
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
