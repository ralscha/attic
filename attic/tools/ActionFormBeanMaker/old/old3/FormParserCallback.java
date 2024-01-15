import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class FormParserCallback extends HTMLEditorKit.ParserCallback {

  private boolean validating = false;
  private SourceFile sf = null;

  public FormParserCallback(boolean validating) {
    this.validating = validating;
  }

  public void handleText(char[] data, int pos) {
    //System.out.println(data);
  }

  public void handleComment(char[] data, int pos) {
    //System.out.println(data);
  }

  public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
    if (t == HTML.Tag.FORM) {
      String nameAttr = (String)a.getAttribute(HTML.Attribute.NAME);
      
      if (nameAttr != null) {
        sf = new SourceFile(nameAttr, validating);
      }

    } //HTML.Tag.FORM
    
    else if ((t == HTML.Tag.TEXTAREA) && (sf != null)) {
      String nameAttr = (String)a.getAttribute(HTML.Attribute.NAME);
      sf.addField(new Field(nameAttr, SourceFile.TAG_TEXTAREA));      
    }

    else if ((t == HTML.Tag.SELECT) && (sf != null)) {
      String nameAttr = (String)a.getAttribute(HTML.Attribute.NAME);
      String multiple = (String)a.getAttribute(HTML.Attribute.MULTIPLE);

      if (multiple != null) {
        sf.addField(new Field(nameAttr, SourceFile.TAG_SELECT, true));
      } else {
        sf.addField(new Field(nameAttr, SourceFile.TAG_SELECT));
      }
    }
  }

  public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
    if ((t == HTML.Tag.INPUT) && (sf != null)) {
      String type = (String)a.getAttribute(HTML.Attribute.TYPE);
      String nameAttr = (String)a.getAttribute(HTML.Attribute.NAME);

      String strutsType = html2struts(type);
      if (strutsType != null)
        sf.addField(new Field(nameAttr, strutsType));
    } //HTML.Tag.INPUT
  }

  public void handleEndTag(HTML.Tag t, int pos) {   
    if (t == HTML.Tag.FORM) {
      if (sf != null) {
        try {
          sf.write();
        } catch (IOException ioe) {
          System.err.println(ioe);
        }
        sf = null;
      }
    }

  }


  private String html2struts(String type) {
    if ("checkbox".equalsIgnoreCase(type))
      return SourceFile.TAG_CHECKBOX;
    else if ("hidden".equalsIgnoreCase(type))
      return SourceFile.TAG_HIDDEN;  
    else if ("radio".equalsIgnoreCase(type))
      return SourceFile.TAG_RADIO;
    else if ("text".equalsIgnoreCase(type) 
          || "password".equalsIgnoreCase(type))
      return SourceFile.TAG_TEXT;
    else
      return null;
  }

}
