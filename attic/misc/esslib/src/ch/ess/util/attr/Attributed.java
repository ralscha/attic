/*
 * $Header: /home/cvsroot/pbroker/src/ch/ess/util/attr/Attributed.java,v 1.3 2002/07/22 14:42:59 sr Exp $
 * $Revision: 1.3 $
 * $Date: 2002/07/22 14:42:59 $
 */

package ch.ess.util.attr;

/**
 * Interface Attributed
 *
 * @version $Revision: 1.3 $ $Date: 2002/07/22 14:42:59 $
 */
public interface Attributed {

  Attributed add(Attr newAttr);

  Attr find(String attrName);

  void remove(String attrName);

  void replace(Attr replAttr);

  void removeAll();

  java.util.Iterator attrs();
}
