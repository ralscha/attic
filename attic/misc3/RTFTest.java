
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.rtf.*;
import java.io.*;

public class RTFTest {


	public static void main(String[] args) {
      HTMLEditorKit myHTML = new HTMLEditorKit() ;
      RTFEditorKit myRTF = new RTFEditorKit() ;
      DefaultStyledDocument mySDoc = new DefaultStyledDocument();

//      Document myDoc = myRTF.createDefaultDocument () ;
//      mySDoc = (DefaultStyledDocument) myDoc;
      SimpleAttributeSet TabAttSet = new SimpleAttributeSet() ;

      TabStop first  = new TabStop( 50, TabStop.ALIGN_LEFT,TabStop.LEAD_NONE ) ;
      TabStop second = new TabStop( 100, TabStop.ALIGN_LEFT,TabStop.LEAD_NONE ) ;
      TabStop third  = new TabStop( 250, TabStop.ALIGN_LEFT,TabStop.LEAD_NONE ) ;

      TabSet tabs = new TabSet( new TabStop[] { first, second,third} ) ;

      SimpleAttributeSet Tabs = new SimpleAttributeSet() ;
      StyleConstants.setTabSet (Tabs, tabs);
      int offset ;
      try
      {
         mySDoc.insertString(mySDoc.getLength (),"\t1.Tab\t2.Tab\t3.Tab", null );
         mySDoc.setParagraphAttributes (0,mySDoc.getLength(),Tabs,false);
      }
      catch (Exception Ex)
      {
         Ex.printStackTrace ();
      }
      //jTextPane1.setDocument (mySDoc);
      try
      {
         FileOutputStream ofs = new FileOutputStream("rtfoutput.rtf" ) ;
         myRTF.write( ofs, mySDoc, 0, mySDoc.getLength ()) ;
         ofs.close();
         ofs = new FileOutputStream("htmloutput.htm" ) ;
         myHTML.write( ofs, mySDoc, 0, mySDoc.getLength ()) ;
         ofs.close();

      }
      catch ( Exception Ex )
      {
         Ex.printStackTrace ();
      }

    System.exit(0);

}

}