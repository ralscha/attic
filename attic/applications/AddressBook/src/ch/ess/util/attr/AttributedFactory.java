/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/attr/AttributedFactory.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.attr;

/**
 * Class AttributedFactory
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class AttributedFactory {

  private AttributedFactory() {
  }

  public static Attributed getAttributed() {
    return new AttributedImpl();
  }

  public static Attributed getAttributed(boolean synchron) {

    if (synchron) {
      return new AttributedImpl();
    } else {
      return new AttributedNotSynchronizedImpl();
    }
  }
}
