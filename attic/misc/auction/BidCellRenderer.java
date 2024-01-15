
import javax.swing.*;

/**
 * Custom list cell renderer to allow a JList to display information about
 * Bid objects.
 */
public class BidCellRenderer extends DefaultListCellRenderer {
	public java.awt.Component getListCellRendererComponent(JList list, Object value,
        	int index, boolean isSelected, boolean cellHasFocus) {
		//parent class version sets up colors for if selected or not
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		//set the state using description and icon of the bid
		Bid b = (Bid) value;
		setText(b.getDescription());
		System.out.println(b.getIcon());
		setIcon(b.getIcon());

		return this;
	}
}