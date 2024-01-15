package common.ui.outlook;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.AbstractAction;

public class SelectAction extends AbstractAction {
	public static final String LARGE_ICON = "LargeIcon";

	FolderBar folder;

	public SelectAction(String name, FolderBar folder) {
		super(name);
		this.folder = folder;

		ImageIcon image = new ImageIcon(
                    		Toolkit.getDefaultToolkit().createImage(getClass().getResource("view.gif")));

		putValue(LARGE_ICON, image);
	}

	public void actionPerformed(ActionEvent event) {
		folder.setText((String) getValue(NAME));
	}
}