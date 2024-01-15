import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;

public class SmileyPlace extends Frame {
    SmileyBean smiley1 = null;
    SmileyBean smiley2 = null;
    Button button = null;
    
    SmileyPlace() {
        super("Smiley");

        try {
            smiley1 = (SmileyBean)Beans.instantiate(null, "Smiley1");
            smiley2 = (SmileyBean)Beans.instantiate(null, "Smiley2");
        } catch(Exception e) {
            
            try {
                smiley1 = (SmileyBean)Beans.instantiate(null, "SmileyBean");
                smiley2 = (SmileyBean)Beans.instantiate(null, "SmileyBean");
            } catch (Exception ex) {
                System.err.println("Exception:");
                System.err.println(ex);
            }
        }
        
        try {
            button = (Button)Beans.instantiate(null, "java.awt.Button");
        } catch (Exception e) { 
            System.err.println("Exception:");
            System.err.println(e);
        }
        button.setLabel("Toggle Smile");
        setLayout(new BorderLayout());
        
        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout());
        p1.add(smiley1, "North");
        p1.add(button, "South");
        add(p1,"West");

        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        p2.add(smiley2, "North");
        add(p2, "East");
        
        button.addActionListener(new SmileyEventAdapter(smiley1));
        
        smiley1.addPropertyChangeListener((PropertyChangeListener) new SmileyBoundAdapter(smiley2));
  }

    static public void main(String args[]) {
        final SmileyPlace smileyPlace = new SmileyPlace();
        
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                
                try {
                    // save the smiley1

                    FileOutputStream fos = new FileOutputStream("smiley1.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(smileyPlace.smiley1);
                    oos.flush();
                    oos.close();

                    // save the smiley2
                    fos = new FileOutputStream("smiley2.ser");
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(smileyPlace.smiley2);
                    oos.flush();
                    oos.close();                    
                } catch (Exception ex) {
                    System.err.println("Exception :");
                    System.err.println(ex);
                }
                System.exit(0);
            }
        };
        smileyPlace.addWindowListener(l);
        smileyPlace.pack();
        smileyPlace.setVisible(true);

        
    }


}
