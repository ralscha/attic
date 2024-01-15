import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;

public class TreeDemo extends JExitFrame {

DefaultTreeModel treeModel;
DefaultMutableTreeNode top;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
       private int newNodeSuffix = 1;
     final JTree tree;  
       
	public TreeDemo() {
		new PlafPanel(this);
		top = new DefaultMutableTreeNode("Cars");
		createNodes(top);
		treeModel = new DefaultTreeModel(top);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setEditable(true);
		tree.setShowsRootHandles(true);
		//tree.putClientProperty("JTree.lineStyle", "Horizontal");
       tree.putClientProperty("JTree.lineStyle", "Angled");
                  
       tree.addTreeSelectionListener(new TreeSelectionListener() {
              public void valueChanged(TreeSelectionEvent e) {
                  DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                     tree.getLastSelectedPathComponent();
                  
                  if (node == null) return;

                  if (node.isLeaf()) {
                      //System.out.println(node);
                  } else {
                  //    System.out.println("you clicked on a node");
                  }
              }
          });           
                  
           
		JScrollPane treeView = new JScrollPane(tree);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(treeView, BorderLayout.CENTER);
		
		
		
		JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addObject("New Node " + newNodeSuffix++);
            }
        });

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCurrentNode();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);
        getContentPane().add(panel, BorderLayout.EAST);

		
	}

   public void clear() {
        top.removeAllChildren();
        treeModel.reload();
    }

	public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }
    
    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = top;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = 
                new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = top;
        }

        treeModel.insertNodeInto(childNode, parent, 
                                 parent.getChildCount());

        // Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }



	class MyTreeModelListener implements TreeModelListener {
              public void treeNodesChanged(TreeModelEvent e) {
                  DefaultMutableTreeNode node;
                  node = (DefaultMutableTreeNode)
                           (e.getTreePath().getLastPathComponent());

                  /*
                   * If the event lists children, then the changed
                   * node is the child of the node we've already
                   * gotten.  Otherwise, the changed node and the
                   * specified node are the same.
                   */
                  try {
                      int index = e.getChildIndices()[0];
                      node = (DefaultMutableTreeNode)
                             (node.getChildAt(index));
                  } catch (NullPointerException exc) {}

                  System.out.println("The user has finished editing the node.");
                  System.out.println("New value: " + node.getUserObject());
              }
              public void treeNodesInserted(TreeModelEvent e) {
              }
              public void treeNodesRemoved(TreeModelEvent e) {
              }
              public void treeStructureChanged(TreeModelEvent e) {
              }
          }

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode car = null;

		category = new DefaultMutableTreeNode("Cars for Java Programmers");
		top.add(category);

		car = new DefaultMutableTreeNode("Volvo");
		category.add(car);
		car = new DefaultMutableTreeNode("Audi");
		category.add(car);

		category = new DefaultMutableTreeNode("Cars for Java Implementers");
		top.add(category);
		
		car = new DefaultMutableTreeNode("Jugular");
		category.add(car);
	}

	public static void main(String[] args) {
		JFrame frame = new TreeDemo();

		frame.pack();
		frame.setVisible(true);
	}
}