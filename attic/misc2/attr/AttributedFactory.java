package ch.ess.util.attr;

public class AttributedFactory {
  private AttributedFactory() {}

  public static Attributed getAttributed() {
    return new AttributedImpl();
  }

  public static Attributed getAttributed(boolean synchron) {
    if (synchron)
      return new AttributedImpl();
    else
      return new AttributedNotSynchronizedImpl();
  }
}
