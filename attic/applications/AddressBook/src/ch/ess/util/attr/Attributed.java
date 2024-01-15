/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/attr/Attributed.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.attr;

/**
 * Interface Attributed
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public interface Attributed {

  Attributed add(Attr newAttr);

  Attr find(String attrName) throws AttributeNotFoundException;

  void remove(String attrName);

  void replace(Attr replAttr);

  void removeAll();

  java.util.Iterator attrs();
}
