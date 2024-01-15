import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.sun.java.swing.*;

public class lw extends JFrame {
    private LRectPanel lrp1,lrp2;

    public lw() {
	    super("test lw");
	    addWindowListener(new Exit());

        getContentPane().setLayout(new GridLayout(3,1,5,5));
        lrp1 = new LRectPanel(0,4);
        lrp2 = new LRectPanel(3,20);
        getContentPane().add(lrp1);
        getContentPane().add(lrp2);
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent a) {
                                    okAction();
                                }
                            });

        okButton.setSize(100,20);
        getContentPane().add(okButton);

        pack();
	    //setSize(140,400);
	    setVisible(true);
    }

    void okAction() {
        if (lrp1.isOK() && lrp2.isOK()) {
            System.out.println(lrp1.getNumbers());
            System.out.println(lrp2.getNumbers());
        } else {
            System.out.println("NICHT OK");
        }
    }


    public static void main(String args[]) {
    	new lw();
    }

    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            Object object = event.getSource();
			if (object == lw.this)
                System.exit(0);
        }
    }

}

