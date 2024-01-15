package ch.ess.timetracker.web.customer;

import ch.ess.common.web.PersistentForm;
import ch.ess.timetracker.db.Customer;

/** 
  * @struts.form name="customerForm"
  */
public class CustomerForm extends PersistentForm {


  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="customer.name"
   */
  public void setName(String name) {
    ((Customer)getPersistent()).setName(getTrimmedString(name));
  }

  public String getName() {
    return ((Customer)getPersistent()).getName();
  }

  
  public void setDescription(String description) {
    ((Customer)getPersistent()).setDescription(getTrimmedString(description));
  }

  public String getDescription() {
    return ((Customer)getPersistent()).getDescription();
  }
  
  
     
  protected void fromForm() {
    //do nothing
  }

  protected void toForm() {
    //do nothing    
  }




}
