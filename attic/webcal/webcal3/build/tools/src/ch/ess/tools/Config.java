package ch.ess.tools;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sr
 */
public class Config {

  private String javaOutputPath;
  private String jspOutputPath;
  private String dropDownMenu;
  private List<ClassDescription> classDescriptions;

  public String getJavaOutputPath() {
    return javaOutputPath;
  }

  public void setJavaOutputPath(String javaOutputPath) {
    this.javaOutputPath = javaOutputPath;
  }

  public String getJspOutputPath() {
    return jspOutputPath;
  }

  public void setJspOutputPath(String jspOutputPath) {
    this.jspOutputPath = jspOutputPath;
  }

  public List<ClassDescription> getClassDescriptions() {
    return classDescriptions;
  }

  public void setClassDescriptions(List<ClassDescription> classDescription) {
    this.classDescriptions = classDescription;
  }

  public String getDropDownMenu() {
    return dropDownMenu;
  }

  public void setDropDownMenu(String dropDownMenu) {
    this.dropDownMenu = dropDownMenu;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}