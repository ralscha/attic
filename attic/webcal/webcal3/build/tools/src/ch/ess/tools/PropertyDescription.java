package ch.ess.tools;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sr
 */
public class PropertyDescription {
  private String name;
  private String type = "String";
  private String modelType;
  private String listReferenceProperty;
  private String validator;
  private String dao;
  private String sort;
  private boolean list = false;
  private boolean finder = false;
  private boolean option = false;

  public boolean isOption() {
    return option;
  }

  public void setOption(boolean option) {
    this.option = option;
  }

  public String getDao() {
    return StringUtils.uncapitalize(dao);
  }

  public String getDaoFirstUpperCase() {
    return StringUtils.capitalize(dao);
  }

  public void setDao(String dao) {
    this.dao = dao;
  }

  public boolean isList() {
    return list;
  }

  public void setList(boolean list) {
    this.list = list;
  }

  public String getName() {
    return name;
  }

  public String getNameFirstUpperCase() {
    return StringUtils.capitalize(name);
  }

  public String getNameWithoutId() {
    if (name == null) {
      return null;
    }
    if (name.endsWith("Id")) {
      return name.substring(0, name.length() - 2);
    }
    if (name.endsWith("Ids")) {
      return name.substring(0, name.length() - 3);
    }
    return name;
  }

  public String getNameFirstUpperCaseWithoutId() {
    String tmp = getNameFirstUpperCase();
    if (tmp == null) {
      return null;
    }

    if (tmp.endsWith("Id")) {
      return tmp.substring(0, tmp.length() - 2);
    }
    return tmp;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    if (isModelBoolean()) {
      return "boolean";
    }

    if (type == null) {
      return "String";
    }

    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isArray() {
    return getType().endsWith("[]");
  }

  public boolean getNameEndsWithId() {
    return name.endsWith("Id");
  }

  public boolean isClone() {
    return "Date".equals(getType());
  }

  public String getValidator() {
    return validator;
  }

  public boolean isDateValidator() {
    if (validator == null) {
      return false;
    }
    return validator.indexOf("date") != -1;
  }

  public boolean isRequiredValidator() {
    if (validator == null) {
      return false;
    }
    return validator.indexOf("required") != -1;
  }

  public void setValidator(String validator) {
    this.validator = validator;
  }

  public String getModelType() {
    return modelType;
  }

  public String getModelTypeFirstLowerCase() {
    return StringUtils.uncapitalize(modelType);
  }

  public void setModelType(String modelType) {
    this.modelType = modelType;
  }

  public boolean isModelBigDecimal() {
    return "BigDecimal".equals(modelType);
  }

  public boolean isModelDate() {
    return "Date".equals(modelType);
  }

  public boolean isModelInteger() {
    return "Integer".equals(modelType);
  }

  public boolean isModelBoolean() {
    return "Boolean".equals(modelType);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public String getListReferenceProperty() {
    return StringUtils.capitalize(listReferenceProperty);
  }

  public void setListReferenceProperty(String referenceProperty) {
    this.listReferenceProperty = referenceProperty;

  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public boolean isFinder() {
    return finder;
  }

  public void setFinder(boolean finder) {
    this.finder = finder;
  }
}