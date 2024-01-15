
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import com.klg.jclass.swing.*;
import com.klg.jclass.util.swing.*;
import com.klg.jclass.util.treetable.*;
	
import common.swing.*;
import CheckTree.*;

public class TreeExplorer extends JFrame {

	public TreeExplorer() {
		super("File Tree");
		
		JCTreeExplorer te = new JCTreeExplorer(new FileSystemModel());
		te.setTreeIconRenderer(null);
		
		JTree tree = te.getTree();
		tree.putClientProperty("JTree.lineStyle", "Angled");

		CheckRenderer2 ckRend = new CheckRenderer2();
				
		tree.addMouseListener(new CheckMouseListener());
		tree.setCellRenderer(ckRend);
		tree.setShowsRootHandles(true);
		tree.setBackground(Color.lightGray);

		//
		// Set column sorting keys
		//
		int keys0[] = { 3, 0 };
		int keys1[] = { 3, 0 };
		int keys2[] = { 3,2,0 };
		int keys3[] = { 3, 0 };
		int keys4[] = { 4, 0 };
		
		JCSortableTable st = (JCSortableTable)te.getTable();
		st.setBackground(Color.lightGray);
		
		st.setKeyColumns(0, keys0);
		st.setKeyColumns(1, keys1);
		st.setKeyColumns(2, keys2);
		st.setKeyColumns(3, keys3);
		st.setKeyColumns(4, keys4);
		

		TableModel tableModel = st.getModel();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn col = st.getColumn(tableModel.getColumnName(i));
			if (i == 1) {
				TableCheckRenderer tcr = new TableCheckRenderer();
				tcr.setHorizontalAlignment(SwingConstants.CENTER);
				col.setCellRenderer(tcr);
			} else {
				DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
				dtcr.setHorizontalAlignment(FileSystemModel.getAlignment(i));
				col.setCellRenderer(dtcr);
				//col.setPreferredWidth(userModel.getWidth(i));
			}
		}	
		
		
		getContentPane().add(te);
	}
	
	public static void main(String args[]) {		
		PlafPanel.setNativeLookAndFeel(true);
		TreeExplorer app = new TreeExplorer();
		app.setDefaultCloseOperation(3);
		app.pack();
		app.setVisible(true);
	}

}