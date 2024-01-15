package CheckTree.ImageTree;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import common.swing.*;

//swing classes
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.tree.*;

public class TreeDemo extends JExitFrame {
    private DefaultMutableTreeNode root;
    private JTree tree;
    CheckListener ckListen;

    public TreeDemo() {
        super("Image Checkbox Tree Demo");
        
        PlafPanel.setNativeLookAndFeel(true);

        
        JPanel jp = new JPanel();   // create interior panel      
        jp.setLayout(new BorderLayout());
        getContentPane().add(jp);

        //create scroll pane
        JScrollPane sp =  new JScrollPane();
        jp.add("Center", sp);

        //create root node
        root = new SelectNode("Foods");
        tree = new JTree(root);    //create tree
        sp.getViewport().add(tree);      //add to scroller

        //create 3 nodes, each with three sub nodes
        ckListen = new CheckListener();
        addNodes("Meats", "Beef", "Chicken", "Pork");
        addNodes("Vegies", "Broccolli", "Carrots", "Peas");
        addNodes("Desserts","Charlotte Russe", "Bananas Flambe","Peach Melba"); 
        CheckRenderer ckRend = new CheckRenderer();
        tree.addTreeSelectionListener (ckListen);
        tree.setCellRenderer(ckRend);

        //turn on root handle
        tree.setShowsRootHandles (true) ;
        tree.setBackground(Color.lightGray );
        setSize(200, 300);
        setVisible(true);
    }
    //----------------------------------------
    private void addNodes(String b, String n1, String n2, String n3) {
        SelectNode base = new SelectNode(b);
        root.add(base);
        SelectNode node =  new SelectNode(n1);
        base.add( node);
        node = new SelectNode(n2);
        base.add(node);
        node = new SelectNode(n3);
        base.add(node);
    }
    //----------------------------------------
    static public void main(String[] argv) {
        new TreeDemo();
    }
}
