package ch.ess.sandbox.facelets;

// Joda imports
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.joda.time.DateTime;

/**
 * JSF backing bean for the view document homepage_chp3.xhtml.
 * @author Robert Swarr
 */
@Name("exampleBean")
public class ExampleBean {

  @Logger
  private Log log;

  private DateTime dateTime;

  /**
   * Accessor method for the dateTime property.
   * @return DateTime.
   */
  public DateTime getDateTime() {
    log.debug("<<< entered getDateTime() >>>");
    if (dateTime != null) {
      log.debug(" dateTime: " + dateTime.toString("MM/dd/yyyy"));
    }
    return dateTime;
  }

  /**
   * Mutator method for the dateTime property.
   * @param aDateTime The dateTime to set.
   */
  public void setDateTime(DateTime aDateTime) {
    log.debug("<<< entered setDateTime() >>>");
    if (aDateTime != null) {
      log.debug(" aDateTime: " + aDateTime.toString("MM/dd/yyyy"));
    }
    this.dateTime = aDateTime;
  }

  public String submitAction() {
    log.debug("<<< entered submitAction() >>>");
    if (dateTime != null) {
      log.debug(" dateTime: " + dateTime.toString("MM/dd/yyyy"));
    }
    return "";
  }

  public String exampleMethod(String param1, String param2, String param3) {
    System.out.println("<<< entered exampleMethod() >>>");
    System.out.println(" parameters: " + param1 + param2 + param3);
    return param1 + param2 + param3;
  }

  /**
   * Perorms that Math.max() function. This method is used by example 7.8.
   * @param intStr1
   * @param intStr2
   * @return String
   */
  public String max(String intStr1, String intStr2) {
    int i = Integer.parseInt(intStr1);
    int j = Integer.parseInt(intStr2);
    return Integer.toString(Math.max(i, j));
  }
}
