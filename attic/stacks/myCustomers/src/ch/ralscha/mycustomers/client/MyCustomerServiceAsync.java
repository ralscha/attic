package ch.ralscha.mycustomers.client;

import java.util.ArrayList;
import ch.ralscha.mycustomers.data.Customer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyCustomerServiceAsync {

  void getCustomers(AsyncCallback<ArrayList<Customer>> callback);

  void removeCustomer(Customer c, AsyncCallback<Void> callback);

  void updateSaveCustomers(ArrayList<Customer> changes, AsyncCallback<Void> callback);

}
