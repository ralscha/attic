
package checktree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import common.ui.checktree.*;
import common.swing.*;

public class JCheckTreeTest extends JPanel
{
  public JCheckTreeTest()
  {
    setLayout(new GridLayout(1, 1));
    CheckTreeNode root = new CheckTreeNode(
      BasicTreeNode.createComputer("Root"));
    root.add(makeNode("Node 1", "Child", 7));
    root.add(makeNode("Node 2", "Child", 7));
    root.add(makeNode("Node 3", "Child", 7));
    root.add(makeNode("Node 4", "Child", 7));
    root.add(makeNode("Node 5", "Child", 7));
    root.add(makeNode("Node 6", "Child", 7));
    root.add(makeNode("Node 7", "Child", 7));
    
    JCheckTree tree = new JCheckTree(root);
    tree.setCellRenderer(new BasicTreeCellRenderer());
    tree.setEditorRenderer(new BasicTreeCellRenderer());
    tree.setRootVisible(true);
    tree.setShowsRootHandles(true);
    
    add(new JScrollPane(tree));
  }

  public CheckTreeNode makeNode(
    String name, String prefix, int count)
  {
    CheckTreeNode node = new CheckTreeNode(
      BasicTreeNode.createDrive(name)); 
    for (int i = 0; i < count; i++)
    {
      node.add(new CheckTreeNode(
        BasicTreeNode.createFolder(prefix + " " + (i + 1))));
    }
    return node;
  }
  
  public static void main(String[] args)
  {
    PlafPanel.setNativeLookAndFeel(true);
    
    JFrame frame = new JFrame("JCheckTree Test");
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(new JCheckTreeTest());
    frame.setSize(200, 400);
    frame.show();
  }
}

