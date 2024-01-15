package ch.ess.base.annotation.struts;

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
