/*
 * $Header: c:/cvs/pbroker/src/ch/ess/util/attr/AttributedNotSynchronizedImpl.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.util.attr;

import java.util.*;


/**
 * Class AttributedNotSynchronizedImpl
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class AttributedNotSynchronizedImpl implements Attributed {

  protected Map attrs = new HashMap(11);

  public Attributed add(Attr attr) {

    attrs.put(attr.getName(), attr);

    return this;
  }

  public Attr find(String attrName) {

    Attr attr = (Attr)attrs.get(attrName);

    return attr;
  }

  public void remove(String attrName) {
    attrs.remove(attrName);
  }

  public void replace(Attr replAttr) {
    remove(replAttr.getName());
    add(replAttr);
  }

  public void removeAll() {
    attrs.clear();
  }

  public Iterator attrs() {
    return attrs.values().iterator();
  }
}
