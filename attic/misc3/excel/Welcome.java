
import java.awt.*;
import java.awt.event.*;
import com.f1j.swing.*;
import javax.swing.*;

public class Welcome extends JApplet {
    
    static JBook jBook1 = new JBook();
    public void Welcome1() {
    }

    //Initialize the applet
    public void init() {
        this.setSize(new Dimension(400,300));
        try {
            // Call the Formula One setText API to place text into a cell
            jBook1.setText(2, 0, "Welcome to Formula One for Java: The Internet Spreadsheet");
            
            // Add a format to the cell
            com.f1j.ss.CellFormat CellFormat1;
            CellFormat1 = jBook1.getCellFormat();
            CellFormat1.setHorizontalAlignment(CellFormat1.eHorizontalAlignmentCenterAcrossCells);
            CellFormat1.setFontBold(true);
            CellFormat1.setFontItalic(true);
            CellFormat1.setFontSize(210);
            jBook1.setCellFormat(CellFormat1,2,0,2,6);
        } 
        catch (com.f1j.util.F1Exception e) {
            // catch any exceptions in case something fails setting the text 
            System.out.println(e.getMessage()); 
        }
        this.getContentPane().add(jBook1, BorderLayout.CENTER);
    }

    public static void main(String args[]){
        // Create a JFrame to place our application in. 
        JFrame myFrame = new JFrame("Getting Started");

        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        // Create an instance of the Welcome class
        Welcome myApp = new Welcome();
        myFrame.getContentPane().add("Center", myApp);
        myApp.init();

        // Resize the JFrame to the desired size, and make it visible 
        myFrame.setSize(600,400);
        myFrame.show();

        // Run the methods the browser normally would
        myApp.start();
    }
}