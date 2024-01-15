package ch.ralscha.mycustomers.client;

import java.util.ArrayList;
import ch.ralscha.mycustomers.data.Customer;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/myCustomerService")
public interface MyCustomerService extends RemoteService {

  public ArrayList<Customer> getCustomers();

  public void removeCustomer(Customer c);

  public void updateSaveCustomers(ArrayList<Customer> changes);
}
