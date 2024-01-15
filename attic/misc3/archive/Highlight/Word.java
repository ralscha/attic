import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

//This is an encapsulation of a string to be displayed in a
//styled document. It contains the highlight information
//as well as the cumulated offset of that word in the text.
public class Word {
  private String word;
  private SimpleAttributeSet attr;
  private boolean hilite;
  private int offset;

  public Word(String w) {
      word = w + " ";
      //create a simple attribute and set its font and size
      attr = new SimpleAttributeSet();
      StyleConstants.setFontFamily(attr, "SansSerif");
      StyleConstants.setFontSize(attr, 9);  //default size

      //look for underscores to indicate highlighted words
      int i = word.indexOf ("_");
      //if highlighted, make the font bugger and red
      if (i > 0)   {
        setColor(Color.red);    // color
        setFontSize(12);        //and size
        hilite = true;
        setBold(true);          //make it bold
      }
      //Remove the underscores
      while (i > 0) {
        StringBuffer buf = new StringBuffer(word);
        buf.setCharAt (i, ' ');
        word = buf.toString ();
        i = word.indexOf ("_");
      }
  }
  //save the offset for fast recovery
  public void setOffset(int offs) {
      offset = offs;
  }
  //return the stored offset
  public int getOffset() {
      return offset;
  }
  //return whether word is to be highlighted
  public boolean isHighlighted() {
      return hilite;
  }
  //get the word
  public String getWord() {
      return word;
  }
  //get the wird kength
  public int length() {
      return word.length ();
  }
  //set the font size
  public void setFontSize(int fsz) {
      StyleConstants.setFontSize(attr, fsz);
  }
  //set the color
  public void setColor(Color c) {
      StyleConstants.setForeground(attr, c);
  }
  //set whether italic
  public void setItalic(boolean b) {
      StyleConstants.setItalic(attr, b);
  }
  //set if bold
  public void setBold(boolean b) {
      StyleConstants.setBold(attr, b);
  }
  //get the attribute as it is currently set
  public SimpleAttributeSet getAttributes() {
      return attr;
  }
}
