
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PlainList {
	// Create a window
	public static void main(String args[]) {
		JFrame frame = new JFrame("Plain List Demo");
		frame.setDefaultCloseOperation(3);

		// Use a list model that
		//allows for updates
		DefaultListModel model = new DefaultListModel();
		JList statusList = new JList(model);

		// Create some dummy list items
		model.addElement("test line one");
		model.addElement("foo foo foo");
		model.addElement("quick brown fox");

		// Display the list
		JPanel panel = new JPanel();
		panel.add(statusList);
		frame.getContentPane().add("Center", panel);
		frame.pack();
		frame.setVisible(true);
	}
}
