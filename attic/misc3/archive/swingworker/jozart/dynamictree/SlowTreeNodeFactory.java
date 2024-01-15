/*
 * SlowTreeNodeFactory.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.dynamictree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Adds random delays to a TreeNodeFactory.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class SlowTreeNodeFactory implements TreeNodeFactory {

    /** Wrapped factory. */
    private final TreeNodeFactory factory;

    /** Maximum delay per child. */
    private final int delay;

    /** Constructs a slow factory. */
    public SlowTreeNodeFactory(TreeNodeFactory factory, int delay) {
        this.factory = factory;
        this.delay = delay;
    }

    /**
     * Creates children using the wrapped factory and then inserts
     * a random delay per child. 
     */
    public DefaultMutableTreeNode[] createChildren(Object userObject)
            throws Exception {
        DefaultMutableTreeNode[] children = factory.createChildren(userObject);
        // simulate a delay per child
        Thread.sleep((long)(1000 + Math.random() * children.length * delay));
        return children;
    }
}

