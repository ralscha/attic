package CheckTree.ClickTree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class SelectNode extends DefaultMutableTreeNode {
    private boolean checked;
    private String text;

        public SelectNode(String label) {
            super(label);
            text = label;
            checked = false;
        }
        public String getText()  {
            return text;
        }
        public void setChecked(boolean b) {
            checked = b;
        }
        public boolean getChecked() {
            return checked;
        }

}
