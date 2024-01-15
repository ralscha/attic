package testset;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TestBean {
  private Long id;
  private String name;

  public TestBean() {
  }
  
  public TestBean(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(Long long1) {
    id = long1;
  }

  public void setName(String string) {
    name = string;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    } else {
      return super.hashCode();
    }
    
  }
  

  public boolean equals(Object o) {
    if (!(o instanceof TestBean)) {
      return false;
    }
    
    TestBean tb = (TestBean)o;
    if ((tb.getId() == null) || (getId() == null)) {
      return super.equals(o);
    }
    
    return tb.getId().equals(id);
    
  }

}
