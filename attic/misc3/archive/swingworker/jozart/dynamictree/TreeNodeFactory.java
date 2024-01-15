/*
 * TreeNodeFactory.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.dynamictree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Remote model interface for dynamic node expansion. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public interface TreeNodeFactory {
    /**
     * Creates child nodes for a newly-expanded parent node. 
     * Called on worker thread. The userObject parameter is the
     * newly-expanded node's link back to the remote model. 
     * Initially, each child node is assigned an allowsChildren
     * property and a link back to the remote model. 
     */
    DefaultMutableTreeNode[] createChildren(Object userObject)
        throws Exception;
}
