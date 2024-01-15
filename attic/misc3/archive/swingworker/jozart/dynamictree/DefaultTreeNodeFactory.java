/*
 * DefaultTreeNodeFactory.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.dynamictree;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * Adapts any TreeModel to the TreeNodeFactory interface.
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class DefaultTreeNodeFactory implements TreeNodeFactory {

    /** Adaptee. */
    private final TreeModel model;

    /** Adapts JTree's default tree model. */
    public DefaultTreeNodeFactory() {
        this.model = new JTree().getModel();
    }

    /** Adapts the given tree model. */
    public DefaultTreeNodeFactory(TreeModel model) {
        this.model = model;
    }

    /**
     * Creates child nodes for a newly-expanded parent node. 
     * Called on worker thread. The userObject parameter is the
     * newly-expanded node's link back to the remote model. 
     * Initially, each child node is assigned an allowsChildren
     * property and a link back to the remote model. 
     */
    public DefaultMutableTreeNode[] createChildren(Object userObject) {
        /* Find parent node in model. */
        Object parent;
        if (userObject instanceof LinkObject) {
            // node link
            LinkObject link = (LinkObject) userObject;
            parent = link.getObject();
        } else {
            // root link
            parent = model.getRoot();
        }
        /* Create children. */
        int count = (parent != null) ? model.getChildCount(parent) : 0;
        DefaultMutableTreeNode[] children =
            new DefaultMutableTreeNode[count];
        for (int i = 0; i < count; i++) {
            Object child = model.getChild(parent, i);
            LinkObject link = new LinkObject(child, child.toString());
            boolean leaf = model.isLeaf(child);
            children[i] = new DefaultMutableTreeNode(link, !leaf);
        }
        return children;
    }

}
