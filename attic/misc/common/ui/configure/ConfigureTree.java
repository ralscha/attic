package common.ui.configure;


import java.awt.Dimension;
import javax.swing.JTree;

public class ConfigureTree extends JTree {
	public ConfigureTree(ConfigureTreeModel model) {
		setPreferredSize(new Dimension(150, 150));
		setShowsRootHandles(true);
		setRootVisible(false);
		setModel(model);
	}

}