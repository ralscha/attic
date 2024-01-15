package ar.com.koalas.providers;

/**
 * @author DZ156H
 */
public class ProviderException extends Exception {

  /** The root cause of this exception */
  protected Throwable cause;

  //~ Constructors -------------------------------------------------------------------------------
  /**
   * Construct a <tt>ConfigurationException</tt>.
   *
   * @param message The exception detail message.
   */
  public ProviderException(final String message) {
    super(message);
  }

  /**
   * Construct a <tt>ConfigurationException</tt>.
   *
   * @param message The exception detail message.
   * @param cause The detail cause of the exception.
   */
  public ProviderException(final String message, final Throwable cause) {
    super(message);
    this.cause = cause;
  }

  /**
   * Construct a <tt>ConfigurationException</tt>.
   *
   * @param cause The detail cause of the exception.
   */
  public ProviderException(final Throwable cause) {
    super(cause.getMessage());
    this.cause = cause;
  }

  //~ Methods ------------------------------------------------------------------------------------
  /**
   * Get the cause of the exception.
   *
   * @return The cause of the exception or null if there is none.
   */
  @Override
  public Throwable getCause() {
    return cause;
  }

  /**
   * Return a string representation of the exception.
   *
   * @return A string representation.
   */
  @Override
  public String toString() {
    return (cause == null) ? super.toString() : (super.toString() + ", Cause: " + cause);
  }
}
