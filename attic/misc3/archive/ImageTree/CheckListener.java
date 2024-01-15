package CheckTree.ImageTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

//Listens for tree selections and
//inverts state of the selected node
public class CheckListener 
    implements TreeSelectionListener {
    public void valueChanged(TreeSelectionEvent e) {
        //Get the tree
        JTree tree = (JTree)e.getSource ();
        
        //get the specific node
        SelectNode node = (SelectNode)
            tree.getLastSelectedPathComponent ();
        
        //change its check state
        if (node.getChecked ())
            node.setChecked (false);
        else
            node.setChecked (true);
    }
}
