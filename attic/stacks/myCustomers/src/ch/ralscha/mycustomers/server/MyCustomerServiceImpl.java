package ch.ralscha.mycustomers.server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ch.ralscha.mycustomers.data.Customer;
import ch.ralscha.mycustomers.data.SizeTypes;

public class MyCustomerServiceImpl {

  private static final String FILENAME = "D:/gwt/projekte/MyCustomers/data.csv";

  private Map<String, Customer> customers;

  public List<Customer> getCustomers() {
    if (customers == null) {
      loadCustomers();
    }
    return new ArrayList<Customer>(customers.values());
  }

  public Boolean removeCustomer(Customer c) {
    if (customers.remove(c.getEmail()) != null) {
      return saveCustomers();
    }
    return false;
  }

  public Boolean updateSaveCustomers(Map<String, Customer> changes) {

    for (String origEmail : changes.keySet()) {
      customers.remove(origEmail);
      Customer newUpdated = changes.get(origEmail);
      customers.put(newUpdated.getEmail(), newUpdated);
    }
    return saveCustomers();
  }

  private void loadCustomers() {
    customers = new HashMap<String, Customer>();
    try {
      LineNumberReader lnr = new LineNumberReader(new FileReader(FILENAME));
      String line = lnr.readLine();
      while (line != null) {
        String[] parts = line.split(",");
        Customer c = new Customer();
        c.setFirstname(parts[0]);
        c.setLastname(parts[1]);
        c.setEmail(parts[2]);
        c.setAddress(parts[3]);
        c.setMale(Boolean.parseBoolean(parts[4]));
        c.setShirt(SizeTypes.values()[Integer.parseInt(parts[5]) - 1]);
        c.setSubs(Integer.parseInt(parts[6]));
        customers.put(c.getEmail(), c);
        line = lnr.readLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean saveCustomers() {
    StringBuffer sb = null;
    try {
      Writer w = new BufferedWriter(new FileWriter(FILENAME));
      PrintWriter pw = new PrintWriter(w);
      for (Iterator<Customer> it = customers.values().iterator(); it.hasNext();) {
        Customer c = it.next();
        sb = new StringBuffer();
        sb.append(c.getFirstname());
        sb.append(',');
        sb.append(c.getLastname());
        sb.append(',');
        sb.append(c.getEmail());
        sb.append(',');
        sb.append(c.getAddress());
        sb.append(',');
        sb.append(c.isMale());
        sb.append(',');
        sb.append(c.getShirt().ordinal() + 1);
        sb.append(',');
        sb.append(c.getSubs());
        pw.println(sb.toString());
      }
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
