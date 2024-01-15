package org.apache.struts.annotation;

public enum ActionScope {
  SESSION("session"), REQUEST("request");

  private String name;

  ActionScope(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
