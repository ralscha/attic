package ch.ralscha.mycustomers.data;

public enum MarketingTypes {

  BROCHURES(0x1, "Brochures"), 
  EVENTS(0x2, "Events"), 
  FLYERS(0x4,"Flyers");

  private int type;
  private String name;

  MarketingTypes(int type, String name) {
    this.type = type;
    this.name = name;
  }

  public int intValue() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }
}
