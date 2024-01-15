package ch.ess.blankrc.model;

/** A business entity class representing a Configuration
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @hibernate.class  table="Configuration"
 */
public class Configuration extends BaseObject {

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