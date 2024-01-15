package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;



public class CheesrApplication extends WebApplication {
  private List<Cheese> cheeses = new ArrayList<Cheese>();


  @Override
  public Class<Index> getHomePage() {
    return Index.class;
  }
  
  @Override
  protected void init() {
    super.init();

    // read the list of cheeses from a properties file
    readCheeses();
  }

  public static CheesrApplication get() {
    return (CheesrApplication) Application.get();
  }

  @Override
  public Session newSession(Request request, Response response) {
    return new CheesrSession(request);
  }

  /*
   * Removed the getHomePage() override, as this application does not match
   * the cheese store 100% to fit the overall examples.
   */

  public List<Cheese> getCheeses() {
    return Collections.unmodifiableList(cheeses);
  }

  /**
   * Reads the list of cheeses from a properties file.
   */
  private void readCheeses() {
    Properties props = new Properties();
    try {
      props.load(CheesrApplication.class
          .getResourceAsStream("cheeses.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (Object obj : props.keySet()) {
      String key = obj.toString();

      // only process a cheese once (identified by its name)
      if (!key.endsWith(".name"))
        continue;
      key = key.substring(0, key.indexOf("."));

      // retrieve each property value
      String name = props.getProperty(key + ".name");
      String description = props.getProperty(key + ".description");
      double price = Double.valueOf(props.getProperty(key + ".price"));

      cheeses.add(new Cheese(name, description, price));
    }
  }
}
