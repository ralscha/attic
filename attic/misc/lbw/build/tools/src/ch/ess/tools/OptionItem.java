package ch.ess.tools;

public class OptionItem {
  private String packageName;
  private String className;
  private String refReadMethod;

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getRefReadMethod() {
    return refReadMethod;
  }

  public void setRefReadMethod(String refReadMethod) {
    this.refReadMethod = refReadMethod;
  }

}
