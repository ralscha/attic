import com.sun.java.swing.*;
import java.awt.*;

class MsgDialog extends JDialog {
    private JTextField field;
       
    MsgDialog(JFrame dw, String title, String msg) {
        super(dw, title, false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", new Label(msg, Label.CENTER));
        setSize(300, 100);         
        setLocationRelativeTo(dw);    	
    }
}