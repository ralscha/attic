package ch.ess.tools.schemacompare.dbobj;

/**
 * @author sr
 */
public class DbObject {
  private String name;

  public DbObject(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}