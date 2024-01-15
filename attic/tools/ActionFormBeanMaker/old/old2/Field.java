
public class Field {

  private String fieldName;
  private String methodName;
  private String type;
  private boolean multiple;

  public Field() {
    this(null, null, false);
  }

  public Field(String name, String type) {
    this(name, type, false);
  }

  public Field(String name, String type, boolean multiple) {
    setFieldName(name);
    setMethodName(name);
    setType(type);
    setMultiple(multiple);
  }


  public void setName(String name) {
    setFieldName(name);
    setMethodName(name);
  }

  public void setType(String type) {
    this.type = type.toLowerCase();
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getMethodName() {
    return methodName;
  }

  public String getType() {
    return type;
  }

  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

  public boolean isMultiple() {
    return multiple;
  }

  private void setFieldName(String name) {
    //first letter --> lower case
    this.fieldName = name.substring(0,1).toLowerCase() + name.substring(1);
  }
	
  private void setMethodName(String name) {
    //first letter --> upper case
    this.methodName = name.substring(0,1).toUpperCase() + name.substring(1);
  }

}