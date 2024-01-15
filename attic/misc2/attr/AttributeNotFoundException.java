package ch.ess.util.attr;


public class AttributeNotFoundException extends Exception {
  // Construct a new instance.
  public AttributeNotFoundException(String name) {
    super("Attribute not found: " + name);
  }
}
