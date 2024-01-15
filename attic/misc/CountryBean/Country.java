
import java.text.*;

public class Country implements Comparable {

  private String isoCode;
  private String name;
  
  public Country() {
    isoCode = null;
    name = null;
  }

  public Country(String isoCode, String name) {
    this.isoCode = isoCode;
    this.name = name;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  public String getIsoCode() {
    return this.isoCode;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
    
  public int compareTo(Object o) {
    Country c = (Country)o;    
    Collator collate = Collator.getInstance();
    return collate.compare(name, c.getName());    
  }

  public boolean equals(Object o) {
    if (!(o instanceof Country))
      return false;

    Country c = (Country)o;
    return this.isoCode.equals(c.getIsoCode());
  }

  public int hashCode() {
    return this.isoCode.hashCode();
  }

  public String toString() {

    return  "isoCode = " + isoCode + "\n"
          + "name    = " + name + "\n";
  }
}