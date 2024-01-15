

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import common.ui.borders.*;

public class TableCheckRenderer extends JTriStateCheckBox implements TableCellRenderer {
	
	
	public Component getTableCellRendererComponent(JTable table, Object value,
												boolean isSelected, boolean hasFocus,
												int row, int column) {
													
		FileNode node = (FileNode)value;
		//setText(node.toString());
		setSelected(node.getState());
		
		if (isSelected)
			setBackground(UIManager.getColor("textHighlight"));
		else 
			setBackground(Color.lightGray);

		return this;
	}

}