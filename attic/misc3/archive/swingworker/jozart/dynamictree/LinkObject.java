/*
 * LinkObject.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.dynamictree;

/**
 * Links a local tree node to its remote counterpart.
 * Links are stored in DefaultMutableTreeNode's userObject. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class LinkObject implements java.io.Serializable {

    /** Linked object. */
    private final Object obj;

    /** Link name. */
    private final String name;

    /** Constructs a named link to <code>obj</code>. */
    public LinkObject(Object obj, String name) {
        this.obj = obj;
        this.name = name;
    }

    /** @return linked object */
    public Object getObject() {
        return obj;
    }

    /** @return link name */
    public String toString() {
        return name;
    }
}

