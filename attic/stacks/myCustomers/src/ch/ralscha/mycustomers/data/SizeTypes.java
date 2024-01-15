package ch.ralscha.mycustomers.data;

public enum SizeTypes {

  SMALL("Small"), MEDIUM("Medium"), LARGE("Large"), XTRA_LARGE("Xtra Large");

  private String size;

  SizeTypes(String size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return size;
  }
}
