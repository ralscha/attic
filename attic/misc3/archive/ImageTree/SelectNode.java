package CheckTree.ImageTree;


import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

//a tree node that remembers being selected
public class SelectNode 
    extends DefaultMutableTreeNode {
    private boolean checked;    //remembers check
    private String text;        //remembers text

        public SelectNode(String label) {
            super(label);
            text = label;
            checked = false;
        }
        public String getText()  {
            return text;
        }
        //can set and get the checked state
        public void setChecked(boolean b) {
            checked = b;
        }
        public boolean getChecked() {
            return checked;
        }
}
