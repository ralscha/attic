package oogui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

//swing classes
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
 

public class JxFrame extends JFrame
{
   public JxFrame(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLF();
   }
   //------------------------------------------
   private void setLF()
   {
   // Force SwingApp to come up in the System L&F
	String laf = UIManager.getCrossPlatformLookAndFeelClassName();
	try {
       UIManager.setLookAndFeel(laf);
   	 }
       catch (UnsupportedLookAndFeelException exc) 
         {System.err.println("Warning: UnsupportedLookAndFeel: " + laf);}
       catch (Exception exc) {System.err.println("Error loading " + laf + ": " + exc);
	   }
   }
}
