package simpletree;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;


public class FileExplorer extends JFrame {
   
	private JTable table;
	private JTree tree;
	private DirectoryModel directoryModel;
	private TreePath selectedPath;
	
	public FileExplorer() {
		JFrame frame = new JFrame("File Explorer");
		frame.setDefaultCloseOperation(3);

		FileNode model = new FileNode(new File("D:\\"));
		directoryModel = new DirectoryModel(model);

		table = new JTable(directoryModel);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);

		table.setIntercellSpacing(new Dimension(0, 2));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumn("Type").setCellRenderer(new DirectoryRenderer());
		table.getColumn("Type").setMaxWidth(32);
		table.getColumn("Type").setMinWidth(32);
	
		tree = new JTree(model);
		tree.addTreeSelectionListener(new TreeListener(directoryModel));

		selectedPath = new TreePath(model);
		
		tree.setRootVisible( false );
		tree.setShowsRootHandles( true );
		tree.putClientProperty( "JTree.lineStyle", "Angled" );
		
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		       if (e.getClickCount() == 2) {
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					
					directoryModel.setDirectory(row);
					selectedPath = selectedPath.pathByAddingChild(directoryModel.getNode());
					
					tree.expandPath(selectedPath);
					tree.scrollPathToVisible(selectedPath);
		       }
			}	
		});
		
		
		
		JScrollPane treeScroller = new JScrollPane(tree);
		JScrollPane tableScroller = new JScrollPane(table);

		JSplitPane splitPane =
  			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroller, tableScroller);
		splitPane.setContinuousLayout(true);

		frame.getContentPane().add(splitPane);

		frame.setSize(400, 200);
		frame.pack();
		frame.setVisible(true);

	}
	
	public static void main(String[] argv) {
		new FileExplorer();
	}

	
	
	private class TreeListener implements TreeSelectionListener {
		DirectoryModel model;

		public TreeListener(DirectoryModel mdl) {
			model = mdl;
		}
		public void valueChanged(TreeSelectionEvent e) {
			selectedPath = e.getPath();			
			FileNode node = (FileNode)e.getPath().getLastPathComponent();
			model.setDirectory(node);
		}
	}
}