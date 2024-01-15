package common.ui.tips;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class JTips extends JFrame implements ActionListener {
	protected String filename;
	protected Properties properties;
	protected Vector tips;

	protected JButton next, close;
	protected JCheckBox show;
	protected JTextArea text;

	public JTips(String filename, Properties properties) {
		super("Tip of the Day");
		int w = 425;
		int h = 280;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - w) / 2;
		int y = (screen.height - h) / 2;
		setBounds(x, y, w, h);

		this.filename = filename;
		this.properties = properties;

		getContentPane().setLayout(new BorderLayout());
		tips = new Vector();
		readTipFile();

		JPanel iconPanel = new JPanel();
		iconPanel.setLayout(new BorderLayout());
		iconPanel.setBackground(Color.gray);
		iconPanel.setPreferredSize(new Dimension(53, 53));
		
		ImageIcon image = new ImageIcon( Toolkit.getDefaultToolkit().createImage(
	                         getClass().getResource("tips.gif")));		
		JLabel icon = new JLabel(image);
		
		
		icon.setVerticalAlignment(JLabel.CENTER);
		icon.setHorizontalAlignment(JLabel.CENTER);
		icon.setPreferredSize(new Dimension(53, 53));
		iconPanel.add("North", icon);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Did you know...");
		title.setBorder(new EmptyBorder(10, 10, 0, 0));
		title.setFont(new Font("Helvetica", Font.PLAIN, 18));
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBorder(new EdgeBorder(EdgeBorder.SOUTH));
		titlePanel.setPreferredSize(new Dimension(46, 46));
		titlePanel.add("Center", title);

		text = new TipTextArea();
		text.setBackground(getBackground());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add("North", titlePanel);
		centerPanel.add("Center", text);

		JPanel tipsPanel = new JPanel();
		tipsPanel.setLayout(new BorderLayout());
		tipsPanel.setBorder( new CompoundBorder(new EmptyBorder(10, 10, 0, 10),
                                        		new BevelBorder(BevelBorder.LOWERED)));
		tipsPanel.add("Center", centerPanel);
		tipsPanel.add("West", iconPanel);

		getContentPane().add("Center", tipsPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonPanel.add(next = new JButton("Next Tip"));
		buttonPanel.add(close = new JButton("Close"));

		JPanel showPanel = new JPanel();
		showPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		showPanel.add(show = new JCheckBox("Show tips at startup", getShow()));

		JPanel navPanel = new JPanel();
		navPanel.setLayout(new BorderLayout());
		navPanel.add("East", buttonPanel);
		navPanel.add("West", showPanel);

		getContentPane().add("South", navPanel);

		next.addActionListener(this);
		close.addActionListener(this);
		show.addActionListener(this);

		nextTip();
	}

	private void increment() {
		int current = getNext() + 1;
		if (current >= tips.size())
			current = 0;
		setNext(current);
	}

	private void nextTip() {
		text.setText((String) tips.elementAt(getNext()));
		increment();
	}

	private int getNext() {
		String prop = properties.getProperty("tips.index");
		if (prop == null) {
			setNext(0);
			return 0;
		}
		int next = Integer.parseInt(prop);
		return next;
	}

	private void setNext(int next) {
		properties.put("tips.index", "" + next);
	}

	public boolean getShow() {
		String prop = properties.getProperty("tips.show");
		if (prop == null) {
			setShow(true);
			return true;
		}
		return prop.equalsIgnoreCase("true");
	}

	public void setShow(boolean show) {
		properties.put("tips.show", "" + show);
	}

	public void readTipFile() {
		tips.removeAllElements();
		try {
			FileReader file = new FileReader(filename);
			BufferedReader input = new BufferedReader(file);

			String line;
			while ((line = input.readLine()) != null) {
				line = replaceParagraphMarkers(line);
				tips.addElement(line);
			}

			input.close();
			file.close();
		} catch (FileNotFoundException e) {
			tips.addElement("Tip file '" + filename + "' not found!");
		}
		catch (IOException e) {
			tips.addElement("Error reading '" + filename + "'");
		}
	}

	public String replaceParagraphMarkers(String line) {
		StringBuffer buffer = new StringBuffer(line);
		int pos;
		while ((pos = line.indexOf("\\p")) > -1) {
			buffer.setCharAt(pos, '\n');
			buffer.setCharAt(pos + 1, '\n');
			line = buffer.toString();
		}
		return line;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == close) {
			setVisible(false);
		}
		if (source == next) {
			nextTip();
		}
		if (source == show) {
			setShow(show.isSelected());
		}
	}

	public void startup() {
		if (getShow())
			setVisible(true);
	}
}