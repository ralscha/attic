package CheckTree.SubClick;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

//Tree listener for a Mouse Press
public class CheckListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        //get the tree
        JTree tree = (JTree)e.getSource ();
        SelectNode node = (SelectNode)tree.getLastSelectedPathComponent ();

        if (node != null) {

            if (node.getChecked ())
                subChecks(node, false);
            else
                subChecks(node, true);

            tree.repaint();
        }
    }
    //check or uncheck the current node
    //and all of its child nodes
    private void subChecks(SelectNode node, boolean state) {
        node.setChecked (state);    //current node
        
        //get any child nodes
        Enumeration e = node.children ();
        
        //call this routine recursively 
        //until no more childrn
        if (e.hasMoreElements ()) {
            while (e.hasMoreElements ()) {
                SelectNode nd = (SelectNode)e.nextElement ();
                subChecks(nd, state);
            }
        } else
            node.setChecked (state); //last node
    }
}
