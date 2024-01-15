package ch.ess.hpadmin.model;

public class TextResource {

  private Integer fieldId;
  private String fieldName;
  private String textDe;
  private String textEn;
  private Integer textDeId;
  private Integer textEnId;

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getTextDe() {
    return textDe;
  }

  public void setTextDe(String textDe) {
    this.textDe = textDe;
  }

  public String getTextEn() {
    return textEn;
  }

  public void setTextEn(String textEn) {
    this.textEn = textEn;
  }

  
  public Integer getTextDeId() {
    return textDeId;
  }

  
  public void setTextDeId(Integer textDeId) {
    this.textDeId = textDeId;
  }

  
  public Integer getTextEnId() {
    return textEnId;
  }

  
  public void setTextEnId(Integer textEnId) {
    this.textEnId = textEnId;
  }

  
  public Integer getFieldId() {
    return fieldId;
  }

  
  public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
  }

}
