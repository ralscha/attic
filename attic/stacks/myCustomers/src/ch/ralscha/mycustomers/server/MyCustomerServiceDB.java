package ch.ralscha.mycustomers.server;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ralscha.mycustomers.client.MyCustomerService;
import ch.ralscha.mycustomers.data.Customer;

@Service("myCustomerService")
public class MyCustomerServiceDB extends GenericDaoService<Customer, Integer> implements MyCustomerService {

  @Override
  @Transactional(readOnly=true)
  public ArrayList<Customer> getCustomers() {
    return new ArrayList<Customer>(findAll());
  }

  @Override
  public void removeCustomer(Customer c) {
    remove(c);
  }

  @Override
  @Transactional
  public void updateSaveCustomers(ArrayList<Customer> changes) {
    for (Customer customer : changes) {
      merge(customer);
    }
  }

}
