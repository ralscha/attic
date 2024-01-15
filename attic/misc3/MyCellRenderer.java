import javax.swing.*;
import java.awt.*;

class MyCellRenderer extends JLabel implements ListCellRenderer {

	public MyCellRenderer () {
		// Don't paint behind the component
		setOpaque(true);
	}

	// Set the attributes of the
	//class and return a reference
	public Component getListCellRendererComponent(JList list, Object value, // value to display
        	int index, // cell index
        	boolean iss, // is selected
        	boolean chf)// cell has focus?
	{
		// Set the text and
		//background color for rendering
		setText(((ListItem) value).getValue());
		setBackground(((ListItem) value).getColor());

		// Set a border if the
		//list item is selected
		if (iss) {
			setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		} else {
			setBorder(BorderFactory.createLineBorder(list.getBackground(), 2));
		}

		return this;
	}
}