package common.ui.configure;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.Component;
import java.awt.BorderLayout;
import java.text.DateFormat;
import java.util.StringTokenizer;
import java.util.Properties;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Date;

import javax.swing.JTree;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import common.ui.layout.*;

public class JConfigure extends JPanel implements TreeSelectionListener {
	protected JTree tree;
	protected JLabel title;
	protected JPanel deck;
	protected String filename;
	protected DeckLayout layout = new DeckLayout();
	protected Hashtable decks = new Hashtable();
	protected ConfigureTreeModel model;
	protected Properties template = new Properties();

	public JConfigure(String filename) throws IOException {
		this.filename = filename;
		ImageIcon icon = new ImageIcon("Task.gif");
				
		UIManager.put("Tree.leafIcon", icon);
		Border border = new BevelBorder(BevelBorder.LOWERED, getBackground().brighter(),
                                		getBackground(), getBackground().darker(), getBackground());

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		model = new ConfigureTreeModel();

		deck = new JPanel();
		deck.setLayout(layout);

		File templateFile = new File(filename.substring(0, filename.lastIndexOf('.')) + ".template");
		if (templateFile.exists())
			readTemplate(templateFile.getName());
		readProperties(model, filename);

		tree = new ConfigureTree(model);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		
		tree.addTreeSelectionListener(this);
		add("West", new JScrollPane(tree));

		title = new TitleBar("Properties", icon);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add("Center", new JScrollPane(deck));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel.add("North", title);
		panel.add("Center", content);
		add("Center", panel);
	}

	public void readTemplate(String filename) throws IOException {
		InputStream input = new FileInputStream(filename);
		template.load(input);
		input.close();
	}

	public void save() throws IOException {
		String prevfile = filename.substring(0, filename.lastIndexOf('.')) + ".previous";
		File prev = new File(prevfile);
		if (prev.exists())
			prev.delete();
		File file = new File(filename);
		if (file.exists())
			file.renameTo(prev);
		writeProperties(model, filename);
	}

	public void save(String filename) throws IOException {
		writeProperties(model, filename);
	}

	private void writeProperties(ConfigureTreeModel tree, String filename) throws IOException {
		Vector paths = model.getPaths();
		int count = paths.size();

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		writer.write("# Saved by JConfigure on " +
             		DateFormat.getDateTimeInstance(DateFormat.SHORT,
                                            		DateFormat.SHORT). format(new Date(System.currentTimeMillis())));
		writer.newLine();

		for (int i = 0; i < count; i++) {
			String path = paths.elementAt(i).toString();
			if (decks.containsKey(path)) {
				FieldPanel card = (FieldPanel) decks.get(path);
				String[] names = card.getFieldNames();
				String[] values = card.getFieldValues();
				for (int j = 0; j < names.length; j++) {
					writer.write(path + "." +
             					names[j].substring(0, names[j].length() - 1) + "=" + values[j]);
					writer.newLine();
				}

			}
		}
		writer.close();
	}

	private void readProperties(ConfigureTreeModel tree, String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null) {
			processLine(tree, line);
		}
		reader.close();
	}

	private void processLine(ConfigureTreeModel tree, String line) {
		if (line.charAt(0) == '#')
			return;
		String prop = line.substring(0, line.indexOf('='));
		String path = prop.substring(0, prop.lastIndexOf('.'));
		String name = prop.substring(path.length() + 1);
		String value = line.substring(prop.length() + 1);
		tree.addPath(path);
		addField(path, name, value);
	}

	public void addField(String path, String name, String value) {
		FieldPanel card;
		if (decks.containsKey(path)) {
			card = (FieldPanel) decks.get(path);
		} else {
			card = new FieldPanel(path);
			decks.put(path, card);
			deck.add(path, card);
		}
		try {
			String className = "common.ui.configure.PropertyTextField";
			String prop = path + "." + name;
			if (template.containsKey(prop))
				className = (String) template.get(prop);
			PropertyField field =
  			(PropertyField) Class.forName(className).newInstance();
			field.setValue(value);
			card.addEntry(name + ":", (Component) field);
		} catch (InstantiationException e) {
			System.out.println(e);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}
	}

	public void valueChanged(TreeSelectionEvent event) {
		Object obj = tree.getLastSelectedPathComponent();
		if (obj == null)
			return;
		ConfigureTreeNode node = (ConfigureTreeNode) obj;
		if (node.isLeaf()) {
			title.setText(node.getName() + " properties");
			String path = treePath(tree.getSelectionPath());
			layout.show(deck, path);
			deck.repaint();
		}
	}

	public String treePath(TreePath treePath) {
		ConfigureTreeNode node;
		Object[] list = treePath.getPath();
		StringBuffer path = new StringBuffer();
		for (int i = 1; i < list.length; i++) {
			node = (ConfigureTreeNode) list[i];
			if (i > 1)
				path.append(".");
			path.append(node.getName());
		}
		return path.toString();
	}

}