/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/attr/AttributedFactory.java,v 1.3 2002/07/22 14:42:59 sr Exp $
 * $Revision: 1.3 $
 * $Date: 2002/07/22 14:42:59 $
 */

package ch.ess.util.attr;

/**
 * Class AttributedFactory
 *
 * @version $Revision: 1.3 $ $Date: 2002/07/22 14:42:59 $
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
