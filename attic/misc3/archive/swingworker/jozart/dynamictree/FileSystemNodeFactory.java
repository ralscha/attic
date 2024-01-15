/*
 * FileSystemNodeFactory.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package jozart.dynamictree;

import java.io.File;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Implements TreeNodeFactory for a file system. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class FileSystemNodeFactory implements TreeNodeFactory {

    /** Absolute path to root file. */
    private final String rootPath;

    /** Create factory rooted at <code>rootPath</code>. */
    public FileSystemNodeFactory(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Creates child nodes for a newly-expanded parent node. 
     * Called on worker thread. The userObject parameter is the
     * newly-expanded node's link back to the remote model. 
     * Initially, each child node is assigned an allowsChildren
     * property and a link back to the remote model. 
     */
    public DefaultMutableTreeNode[] createChildren(Object userObject) {
        /* Find parent directory. */
        File parent;
        if (userObject instanceof LinkObject) {
            // file link
            LinkObject link = (LinkObject) userObject;
            parent = (File) link.getObject();
        } else {
            // root link
            parent = new File(rootPath);
        }
        /* Create children. */
        String[] names = parent.isDirectory()
            ? parent.list() : new String[0];
        int count = names.length;
        DefaultMutableTreeNode[] children =
            new DefaultMutableTreeNode[count];
        for (int i = 0; i < count; i++) {
            File child = new File(parent, names[i]);
            LinkObject link = new LinkObject(child, names[i]);
            boolean leaf = child.isFile();
            children[i] = new DefaultMutableTreeNode(link, !leaf);
        }
        return children;
    }

}

