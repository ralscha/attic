package ch.ess.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sr
 */
public class ClassDescription {
  private String packageName;
  private String name;
  private List<PropertyDescription> properties;
  private String sizeProperty;
  private String confirmProperty;
  private String focusProperty;

  public String getSizeProperty() {
    return sizeProperty;
  }

  public String getSizePropertyFirstUpperCase() {
    return StringUtils.capitalize(sizeProperty);
  }

  public void setSizeProperty(String sizeProperty) {
    this.sizeProperty = sizeProperty;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public List getProperties() {
    return properties;
  }

  public void setProperties(List<PropertyDescription> properties) {
    this.properties = properties;
  }

  public String getNameSmall() {
    return StringUtils.uncapitalize(name);
  }

  public String getNameAllSmall() {
    return name.toLowerCase();
  }

  public String getConfirmProperty() {
    return confirmProperty;
  }

  public void setConfirmProperty(String confirmProperty) {
    this.confirmProperty = confirmProperty;
  }

  public String getFocusProperty() {
    return focusProperty;
  }

  public void setFocusProperty(String focusProperty) {
    this.focusProperty = focusProperty;
  }

  public boolean isContainsDateProperties() {
    for (PropertyDescription prop : properties) {
      if (prop.isModelDate()) {
        return true;
      }
    }
    return false;
  }

  public boolean isContainsArrayProperties() {
    for (PropertyDescription prop : properties) {
      if (prop.isArray()) {
        return true;
      }
    }
    return false;
  }

  public boolean isContainsResetProperties() {
    for (PropertyDescription prop : properties) {
      if (prop.isModelBoolean()) {
        return true;
      }
      if (prop.isArray()) {
        return true;
      }
    }
    return false;
  }

  public String getFinderSignature() {
    String sig = null;
    for (PropertyDescription prop : properties) {
      if (prop.isFinder()) {
        if ((sig != null) && (sig.length() > 0)) {
          sig += ", ";
        } else {
          sig = "";
        }
        sig += "String " + prop.getName();
      }

    }
    return sig;
  }

  public String getFinderFinalSignature() {
    String sig = null;
    for (PropertyDescription prop : properties) {
      if (prop.isFinder()) {
        if ((sig != null) && (sig.length() > 0)) {
          sig += ", ";
        } else {
          sig = "";
        }
        sig += "final String " + prop.getName();
      }

    }
    return sig;
  }

  public String getFinderMethodParameter() {
    String params = null;
    for (PropertyDescription prop : properties) {
      if (prop.isFinder()) {
        if ((params != null) && (params.length() > 0)) {
          params += ", ";
        } else {
          params = "";
        }
        params += prop.getName();
      }

    }
    return params;
  }

  public String getFinderIf() {
    List<String> ifs = new ArrayList<String>();
    for (PropertyDescription prop : properties) {
      if (prop.isFinder()) {
        ifs.add("(" + prop.getName() + " != null)");
      }
    }

    if (ifs.size() == 1) {
      return ifs.get(0);
    } else if (ifs.size() > 1) {
      String ifstr = "(";

      boolean first = true;
      for (String ifstmt : ifs) {
        if (!first) {
          ifstr += " || ";
        } else {
          first = false;
        }
        ifstr += ifstmt;
      }

      ifstr += ")";
      return ifstr;
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}