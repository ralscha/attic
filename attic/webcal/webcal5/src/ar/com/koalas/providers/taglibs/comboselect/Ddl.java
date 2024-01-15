package ar.com.koalas.providers.taglibs.comboselect;

public class Ddl {
  private String name = "";
  private String value = "";
  private String onchange = "";
  private String property = null;

  public Ddl(String name, String value, String onchange, String property) {
    this.name = name;
    this.value = value;
    this.onchange = onchange;
    this.property = property;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getOnChange() {
    return onchange;
  }

  public String getProperty() {
    return property;
  }

}
