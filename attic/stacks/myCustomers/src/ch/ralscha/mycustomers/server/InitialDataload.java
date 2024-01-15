package ch.ralscha.mycustomers.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ralscha.mycustomers.data.Customer;
import ch.ralscha.mycustomers.data.SizeTypes;

@Service
public class InitialDataload implements ApplicationListener<ApplicationEvent> {

  @PersistenceContext
  private EntityManager entityManager;

//  @Override
//  @Transactional
//  public void onApplicationEvent(ApplicationEvent event) {
//    if (event instanceof ContextRefreshedEvent) {
//      String[] configLocations = ((XmlWebApplicationContext)event.getSource()).getConfigLocations();
//      if (configLocations != null && configLocations[0].indexOf("applicationContext.xml") != -1) {        
//        init();
//      }
//    }
//  }

  @Override
  @Transactional
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ContextRefreshedEvent) {
      init();
    }

  }

  public void init() {

    Query query = entityManager.createQuery("select count(*) from Customer");
    if ((Long)query.getSingleResult() == 0) {
      List<Customer> loadCustomers = loadCustomers();
      for (Customer customer : loadCustomers) {
        entityManager.merge(customer);
      }
    }

  }

  private List<Customer> loadCustomers() {
    List<Customer> customers = new ArrayList<Customer>();
    try {
      InputStream is = getClass().getResourceAsStream("data.csv");
      LineNumberReader lnr = new LineNumberReader(new InputStreamReader(is));

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
        customers.add(c);
        line = lnr.readLine();
      }

      is.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return customers;
  }

}
