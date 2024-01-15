/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/attr/AttributeNotFoundException.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.attr;

/**
 * Class AttributeNotFoundException
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class AttributeNotFoundException extends Exception {

  // Construct a new instance.
  public AttributeNotFoundException(String name) {
    super("Attribute not found: " + name);
  }
}
