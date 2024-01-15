package ch.ess.timetracker.web.project;

import net.sf.hibernate.HibernateException;
import ch.ess.common.web.PersistentForm;
import ch.ess.timetracker.db.Customer;
import ch.ess.timetracker.db.Project;

/** 
  * @struts.form name="projectForm"
  */
public class ProjectForm extends PersistentForm {


  private Long customerId;
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="project.name"
   */
  public void setName(String name) {
    ((Project)getPersistent()).setName(getTrimmedString(name));
  }

  public String getName() {
    return ((Project)getPersistent()).getName();
  }
  
  public void setDescription(String description) {
    ((Project)getPersistent()).setDescription(getTrimmedString(description));
  }

  public String getDescription() {
    return ((Project)getPersistent()).getDescription();
  }
    
  public Long getCustomerId() {
    return customerId;
  }
  
  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="customer"
   */  
  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
        
  protected void fromForm() throws HibernateException {
    Project project = (Project)getPersistent();            
    project.setCustomer(Customer.load(customerId));
  }

  protected void toForm() {
    customerId = null;
    
    Project project = (Project)getPersistent();
    if (project != null) {
      if (project.getCustomer() != null) {
        customerId = project.getCustomer().getId();
      }
    } 
  }


  

}
