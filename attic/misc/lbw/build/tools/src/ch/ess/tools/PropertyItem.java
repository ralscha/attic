package ch.ess.tools;

import org.apache.commons.lang.StringUtils;


public class PropertyItem {
  private String readMethod;
  private String formReadMethod;
  private String writeMethod;
  private String name;
  private boolean required;
  private String formTyp;
  private int maxLength;
  private int size;
  private String typ;
  private boolean filter;
  private int listsequenz;
  private int editsequenz;
  private String refProperty;
  private String refReadProperty;
  private String refImport;
  private String refDaoImport;
  private String typOfSet;
  private String joinReadMethod;
  private String joinImport;
  private String joinClassName;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReadMethod() {
    return readMethod;
  }

  public void setReadMethod(String readMethod) {
    this.readMethod = readMethod;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getFormTyp() {
    return formTyp;
  }

  public void setFormTyp(String typ) {
    this.formTyp = typ;
  }

  public String getWriteMethod() {
    return writeMethod;
  }

  public void setWriteMethod(String writeMethod) {
    this.writeMethod = writeMethod;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  public String getTyp() {
    return typ;
  }

  public void setTyp(String typ) {
    this.typ = typ;
  }

  public String getFormReadMethod() {
    return formReadMethod;
  }

  public void setFormReadMethod(String formReadMethod) {
    this.formReadMethod = formReadMethod;
  }

  public boolean isFilter() {
    return filter;
  }

  public void setFilter(boolean filter) {
    this.filter = filter;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getListsequenz() {
    return listsequenz;
  }

  public void setListsequenz(int listsequenz) {
    this.listsequenz = listsequenz;
  }

  public String getRefProperty() {
    return refProperty;
  }

  public void setRefProperty(String refProperty) {
    this.refProperty = refProperty;
  }
  
  public String getRefReadProperty() {
    return refReadProperty;
  }

  public void setRefReadProperty(String refReadProperty) {
    this.refReadProperty = refReadProperty;
  }

  public boolean isReference() {
    if (StringUtils.isNotBlank(refProperty) && (StringUtils.isBlank(typOfSet)) && StringUtils.isBlank(joinReadMethod)) {
      return true;
    }
    return false;
  }
  
  public boolean isMany() {
    return (StringUtils.isNotBlank(typOfSet)) && StringUtils.isBlank(joinReadMethod);
  }

  public boolean isManymany() {
    return StringUtils.isNotBlank(joinReadMethod);
  }
  
  public String getRefImport() {
    return refImport;
  }

  public void setRefImport(String refImport) {
    this.refImport = refImport;
  }

  public String getRefDaoImport() {
    return refDaoImport;
  }

  public void setRefDaoImport(String refDaoImport) {
    this.refDaoImport = refDaoImport;
  }

  public int getEditsequenz() {
    return editsequenz;
  }

  public void setEditsequenz(int editsequenz) {
    this.editsequenz = editsequenz;
  }

  public String getTypOfSet() {
    return typOfSet;
  }

  public void setTypOfSet(String setTyp) {
    this.typOfSet = setTyp;
  }

  public String getJoinReadMethod() {
    return joinReadMethod;
  }

  public void setJoinReadMethod(String joinReadMethod) {
    this.joinReadMethod = joinReadMethod;
  }

  public String getJoinClassName() {
    return joinClassName;
  }

  public void setJoinClassName(String joinClassName) {
    this.joinClassName = joinClassName;
  }

  public String getJoinImport() {
    return joinImport;
  }

  public void setJoinImport(String joinImport) {
    this.joinImport = joinImport;
  }


}
