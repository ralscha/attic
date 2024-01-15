package ch.ess.timetracker.web.customer;

import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.timetracker.db.Customer;

/** 
  * @struts.action path="/newCustomer" name="customerForm" input=".customer.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editCustomer" name="customerForm" input=".customer.list" scope="session" validate="false" roles="admin" parameter="edit" 
  * @struts.action path="/storeCustomer" name="customerForm" input=".customer.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteCustomer" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".customer.edit"
  * @struts.action-forward name="list" path="/listCustomer.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteCustomer.do" 
  * @struts.action-forward name="reload" path="/editCustomer.do" 
  */
public class CustomerEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Customer.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Customer.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Customer();
  }

}