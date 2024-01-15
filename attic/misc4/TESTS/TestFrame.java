import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TestFrame extends Frame {
    public TestFrame() {
	    super("Test ImageButton");
	    setBackground(Color.lightGray);
	    
	    addWindowListener(new Exit());
        setLayout(new FlowLayout());

        MediaTracker tracker = new MediaTracker(this);
        Image img1 = Toolkit.getDefaultToolkit().getImage("image1.gif");
        Image img2 = Toolkit.getDefaultToolkit().getImage("image2.gif");

        //wait until the image is in memory
        tracker.addImage(img1, 1);
        tracker.addImage(img2, 2);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) { }
           
        ImageButton102 ib1 = new ImageButton102(img1, "Image1");
        ImageButton102 ib2 = new ImageButton102(img2, "Image2");
        
        add(ib1);
        add(ib2);
        ib1.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent a) {
                                    actionIB(a);
                                }
                              });

        ib2.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent a) {
                                    actionIB(a);
                                }
                              });


	    setSize(240,400);
        pack();
	    setVisible(true);
    }

    private void actionIB(ActionEvent e) {
        if (e.getActionCommand().equals("Image1")) 
            System.out.println("image1 action performed ");
        else if (e.getActionCommand().equals("Image2"))    
            System.out.println("image2 action performed ");
    }

    public static void main(String args[]) {
    	new TestFrame();
    }

    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            Object object = event.getSource();
			if (object == TestFrame.this)
                System.exit(0);
        }
    }

}

