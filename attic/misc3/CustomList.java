import javax.swing.*;
import java.awt.*;

public class CustomList {
	// Create a window
	public static void main(String args[]) {
		JFrame frame = new JFrame("Custom List Demo");
		frame.setDefaultCloseOperation(3);

		// Use a list model that
		//allows for updates
		DefaultListModel model = new DefaultListModel();
		JList statusList = new JList(model);
		statusList.setCellRenderer(new MyCellRenderer());
		// Create some dummy list items.
		ListItem li = new ListItem(Color.cyan, "test line one");
		model.addElement(li);
		li = new ListItem(Color.yellow, "foo foo foo");
		model.addElement(li);
		li = new ListItem(Color.green, "quick brown fox");
		model.addElement(li);

		// Display the list
		JPanel panel = new JPanel();
		panel.add(statusList);
		frame.getContentPane().add("Center", panel);
		frame.pack();
		frame.setVisible(true);
	}
}