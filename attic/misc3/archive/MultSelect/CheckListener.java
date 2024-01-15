package CheckTree.MultSelect;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class CheckListener implements TreeSelectionListener {
    public void valueChanged(TreeSelectionEvent e) {

        JTree tree = (JTree)e.getSource ();
        SelectNode node = (SelectNode)tree.getLastSelectedPathComponent ();
        
        if (node.getChecked ())
            node.setChecked (false);
        else
            node.setChecked (true);

    }
}
