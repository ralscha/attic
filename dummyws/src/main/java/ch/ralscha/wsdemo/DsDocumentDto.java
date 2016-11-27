package ch.ralscha.wsdemo;

import java.util.List;

public class DsDocumentDto {
  private String id;
  private List<DsPropertyDto> properties;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<DsPropertyDto> getProperties() {
    return properties;
  }

  public void setProperties(List<DsPropertyDto> properties) {
    this.properties = properties;
  }

}
