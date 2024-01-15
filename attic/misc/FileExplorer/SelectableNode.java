

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public interface SelectableNode extends TreeNode {
	public void setState(int state);
	public int getState();

}