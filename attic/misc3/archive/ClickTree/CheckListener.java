package CheckTree.ClickTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

//Tree listener for a Mouse Press
public class CheckListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        //get the tree
        JTree tree = (JTree)e.getSource ();
        SelectNode node = (SelectNode)tree.getLastSelectedPathComponent ();

        if (node != null) {

            if (node.getChecked ())
                node.setChecked (false);
            else
                node.setChecked (true);

            tree.repaint();
        }
    }
}
