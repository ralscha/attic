package ch.ess.sandbox;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.jboss.seam.annotations.Name;

@Name("nullableStringConverter")
@org.jboss.seam.annotations.faces.Converter
public class NullableStringConverter implements Converter {

  /**
   * Get the given value as String. In case of an empty String, null is returned.
   * 
   * @param value the value of the control
   * @param facesContext current facesContext
   * @param uiComponent the uicomponent providing the value
   * @return the given value as String. In case of an empty String, null is returned.
   * 
   * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
   */
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

    if (facesContext == null) {
      throw new NullPointerException("facesContext");
    }
    if (uiComponent == null) {
      throw new NullPointerException("uiComponent");
    }

    return stringToValue(value);
  }

  /**
   * Convert the String to value (String or null).
   * 
   * @param value the string from webcomponent
   * @return the object (null if trimmed String is Empty String)
   */
  protected Object stringToValue(String value) {

    if (value != null) {
      String val = value.trim();
      if (val.length() > 0) {
        return val;
      }
    }
    return null;
  }

  /**
   * Convert the value to String for web control.
   * 
   * @param value the value to be set
   * @param facesContext current facesContext
   * @param uiComponent the uicomponent to show the value
   * @return the String-converted parameter
   * 
   * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
   */
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

    if (facesContext == null) {
      throw new NullPointerException("facesContext");
    }
    if (uiComponent == null) {
      throw new NullPointerException("uiComponent");
    }
    return valueToString(value);
  }

  /**
   * Converts the value to HTMLized String.
   * 
   * @param value the object to be converted
   * @return String representation
   */
  protected String valueToString(Object value) {

    if (value == null) {
      return "";
    }
    if (value instanceof String) {
      return (String)value;
    }
    return value + "";
  }
}
