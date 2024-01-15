package ch.ess.sandbox.facelets;

// Faces imports
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.jboss.seam.annotations.Name;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * A JSF converter class that performs Joda date and time conversions.
 * Example 7-2 of the Facelets Shortcut.
 * @author Robert Swarr
 */
@Name("ch.ess.sandbox.facelets.JodaDateTimeConverter")
@org.jboss.seam.annotations.faces.Converter
public class JodaDateTimeConverter implements Converter, StateHolder {

  private String pattern;
  private boolean transientFlag;

  /**
   * default no argument constructor
   */
  public JodaDateTimeConverter() {
    super();
    transientFlag = false;

  }

  /**
   * Converts String to a Joda DateTime object. This method is required for any
   * class that implements the Converter interface.
   */
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String dtString) throws ConverterException {

    String pattern = getPattern();

    DateTime dateTime = null;
    try {
      dateTime = DateTimeFormat.forPattern(pattern).parseDateTime(dtString);
    } catch (Exception e) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date String: " + dtString
          + ", unable to convert to Joda DateTime", null);
      throw new ConverterException(message, e);
    }
    return dateTime;
  }

  /**
   * Converts Joda DateTime object to a String. This method is required for any class
   * that implements the Converter interface.
   */
  public String getAsString(FacesContext arg0, UIComponent arg1, Object dtObject) throws ConverterException {
    String dateTimeStr = null;
    try {
      dateTimeStr = DateTimeFormat.forPattern(getPattern()).print((DateTime)dtObject);
    } catch (Exception e) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid DateTime value.", null);
      throw new ConverterException(message, e);
    }
    return dateTimeStr;
  }

  // converter methods

  /**
   * Accessor method for the pattern property.
   * @return String a pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Mutator method for the pattern property.
   * @param aPattern
   */
  public void setPattern(String aPattern) {
    this.pattern = aPattern;
  }

  // stateHolder methods

  /**
   * Accessor method for the transient property.
   * This method is required for any class 
   * that implements the StateHolder interface.
   */
  public boolean isTransient() {
    return transientFlag;
  }

  /**
   * Mutator method for the transient property.
   * This method is required for any class 
   * that implements the StateHolder interface.
   */
  public void setTransient(boolean aTransientFlag) {
    this.transientFlag = aTransientFlag;
  }

  /**
   * Saves the state of this StateHolder object.
   * Required method for a class that implements the StateHolder interface.
   */
  public Object saveState(FacesContext context) {
    return getPattern();
  }

  /**
   * Restores the state of this StateHolder object.
   * Required method for a class that implements the StateHolder interface.
   */
  public void restoreState(FacesContext context, Object state) {
    setPattern((String)state);
  }

}
