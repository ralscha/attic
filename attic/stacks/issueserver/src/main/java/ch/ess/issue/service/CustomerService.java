package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.Customer;

@Service
public class CustomerService extends AbstractService<Customer> {

  public CustomerService() {
    super(Customer.class);

  }

}
