
package fileexplorer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class FileExplorer {
	public static void main(String[] argv) {
		JFrame frame = new JFrame("File Explorer");
		frame.setDefaultCloseOperation(3);

		FileSystemModel model = new FileSystemModel();
		DirectoryModel directoryModel = new DirectoryModel((File) model.getRoot());
		JTable table = new JTable(directoryModel);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);

		table.setIntercellSpacing(new Dimension(0, 2));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumn("Type").setCellRenderer(new DirectoryRenderer());
		table.getColumn("Type").setMaxWidth(32);
		table.getColumn("Type").setMinWidth(32);

		FileSystemTreePanel fileTree = new FileSystemTreePanel(model);
		fileTree.getTree().addTreeSelectionListener(new TreeListener(directoryModel));

		JScrollPane treeScroller = new JScrollPane(fileTree);
		JScrollPane tableScroller = new JScrollPane(table);

		treeScroller.setMinimumSize(new Dimension(0, 0));
		tableScroller.setMinimumSize(new Dimension(0, 0));
		tableScroller.setBackground(Color.white);
		JSplitPane splitPane =
  		new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroller, tableScroller);
		splitPane.setContinuousLayout(true);

		frame.getContentPane().add(splitPane);

		frame.setSize(400, 200);
		frame.pack();
		frame.show();
	}

	protected static class TreeListener implements TreeSelectionListener {
		DirectoryModel model;

		public TreeListener(DirectoryModel mdl) {
			model = mdl;
		}
		public void valueChanged(TreeSelectionEvent e) {
			File fileSysEntity = (File) e.getPath().getLastPathComponent();
			if (fileSysEntity.isDirectory()) {
				model.setDirectory(fileSysEntity);
			} else {
				model.setDirectory(null);
			}
		}
	}
}