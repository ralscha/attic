

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

//import com.klg.jclass.util.swing.*;

import common.swing.*;


public class FileExplorer extends JExitFrame {
   
	private JTable table;
	private JTree tree;
	private DirectoryModel directoryModel;
	private TreePath selectedPath;
	private TreeTableMediator mediator;
	private Map rootMap;
	
	private JPanel jPanel;
	private JLabel jlblStatus;
	private Blip blip;
	private transient SwingWorker worker;		
	
	private static final Font menuFont = new Font("SansSerif", Font.BOLD, 10);
	
	public FileExplorer() {

		super("File Explorer");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		mediator = new TreeTableMediator();	
		
	
		rootMap = new TreeMap();
		
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			rootMap.put(roots[i].getPath(), new FileNode(roots[i], mediator));
		}
		
		Iterator it = rootMap.values().iterator();		
		//FileNode model = (FileNode)it.next();
		
		FileNode model = (FileNode)rootMap.get("D:\\");
		
		directoryModel = new DirectoryModel(model, mediator);

		//Table
		table = new JTable(directoryModel);
		
		mediator.setTable(table);
		mediator.setDirectoryModel(directoryModel);
		mediator.setFileNode(model);
		
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);

		table.setIntercellSpacing(new Dimension(0, 2));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumn(DirectoryModel.COL_TYPE).setCellRenderer(new DirectoryRenderer());
		table.getColumn(DirectoryModel.COL_TYPE).setMaxWidth(32);
		table.getColumn(DirectoryModel.COL_TYPE).setMinWidth(32);

		TableCheckRenderer tcr = new TableCheckRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		TableCheckEditor tce = new TableCheckEditor();

		table.getColumn(DirectoryModel.COL_SELECTED).setCellRenderer(tcr);
		table.getColumn(DirectoryModel.COL_SELECTED).setCellEditor(tce);
		table.getColumn(DirectoryModel.COL_SELECTED).setPreferredWidth(50);

		table.setBackground(Color.lightGray);
	

		TableColumn col = table.getColumn(DirectoryModel.COL_NAME);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.LEFT);
		col.setCellRenderer(dtcr);
		col.setPreferredWidth(200);

		col = table.getColumn(DirectoryModel.COL_SIZE);
		dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		col.setCellRenderer(dtcr);
		col.setPreferredWidth(100);
		
		col = table.getColumn(DirectoryModel.COL_MODIFIED);
		dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		col.setCellRenderer(dtcr);
		col.setPreferredWidth(150);
		
		tree = new JTree(model);
		mediator.setTree(tree);
		tree.addTreeSelectionListener(new TreeListener(directoryModel));

		selectedPath = new TreePath(model);
		
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		
		tree.setBackground(Color.lightGray);
		
		CheckRenderer ckRend = new CheckRenderer();
				
		tree.addMouseListener(new CheckMouseListener(mediator));
		
		tree.setCellRenderer(ckRend);
		
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

		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		
		JComboBox jcb = new JComboBox();
		it = rootMap.keySet().iterator();
		while(it.hasNext()) {
			jcb.addItem((String)it.next());	
		}
		jcb.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								JComboBox tmp = (JComboBox)ae.getSource();
								FileNode newModel = (FileNode)rootMap.get(tmp.getSelectedItem());
								if (newModel != null) {
									setModel(newModel);
								}
							}
					});
		
		
		treePanel.add(jcb, BorderLayout.NORTH);
		treePanel.add(treeScroller, BorderLayout.CENTER);
		
		treePanel.setPreferredSize(new Dimension(200,100));
		treeScroller.setPreferredSize(new Dimension(200,100));
		
		tableScroller.setPreferredSize(new Dimension(400, 100));
		//tableScroller.setBackground(Color.white);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, tableScroller);
  			
		splitPane.setContinuousLayout(false);

		getContentPane().add(splitPane, BorderLayout.CENTER);

		
		//Status
		
		blip = new Blip ();
		jlblStatus = new JLabel();

		
		jPanel = new JPanel();

		jPanel.setLayout(new BorderLayout());


		jlblStatus.setToolTipText("Status");
		jlblStatus.setBorder(new CompoundBorder(
                        new CompoundBorder(new EmptyBorder(new Insets(1, 1, 1, 1)),
                        new LineBorder(Color.gray)), new EmptyBorder(new Insets(0, 2, 0, 0))));
		jlblStatus.setText("Ready");
		jPanel.add(jlblStatus, BorderLayout.CENTER);

		
		blip.setToolTipText("Activity Indicator");
		blip.setPreferredSize(new Dimension(23, 23));
		blip.setOpaque(true);
		blip.setBorder(new CompoundBorder (
		    new CompoundBorder (
		    new EmptyBorder(new Insets(1, 1, 1, 1)),
		    new LineBorder(Color.gray)),
		    new EmptyBorder(new Insets(1, 1, 1, 1))));
		blip.setForeground(new Color(153, 153, 204));
		
		jPanel.add(blip, BorderLayout.EAST);
		
		getContentPane().add(jPanel, BorderLayout.SOUTH);

		//Menu
		addMenu();
		
		setSize(700, 500);
		//pack();
		setVisible(true);

	}

	private void setModel(FileNode model) {
		mediator.setFileNode(model);
		tree.setModel(new DefaultTreeModel(model, false));
		selectedPath = new TreePath(model);
		directoryModel.setDirectory(model);
	}
	
	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");		
		fileMenu.setActionCommand("File");
		fileMenu.setMnemonic('F');

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.setMnemonic('x');		
		exitMenuItem.setFont(menuFont);
		exitMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								doExit();
							}
						});
		
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	protected void doExit() {
		
		int n = JOptionPane.showConfirmDialog(this, 
					"Are you sure?",
					"Exit Application",
					JOptionPane.YES_NO_OPTION);
		
		if (n == JOptionPane.YES_OPTION)
			super.doExit();
	}

	
	
	
	public static void main(String[] argv) {
		//PlafPanel.setNativeLookAndFeel(false);
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
			mediator.setFileNode(node);
		}
	}
}