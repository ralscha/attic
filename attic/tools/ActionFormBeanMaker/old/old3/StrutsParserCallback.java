import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class StrutsParserCallback extends HTMLEditorKit.ParserCallback {

  private boolean validating = false;
  private SourceFile sf = null;

  public StrutsParserCallback(boolean validating) {
    this.validating = validating;
  }

  public void handleText(char[] data, int pos) {
    //System.out.println(data);
  }

  public void handleComment(char[] data, int pos) {
    //System.out.println(data);
  }

  public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
    if (!a.containsAttribute(HTML.Attribute.ENDTAG, "true")) {
      if (t.toString().equals("struts:form")) {
        String typeAttr = (String)a.getAttribute(HTML.Attribute.TYPE);
        if (typeAttr != null) {
          sf = new SourceFile(typeAttr, validating);
        }
      } //struts:form

      if (sf != null) {
        if (SourceFile.TAG_SET.contains(t.toString())) {
          //String property = (String)a.getAttribute(HTML.Attribute.TYPE);
          Enumeration e = a.getAttributeNames();
          while(e.hasMoreElements()) {
            System.out.println((String)e.nextElement());
          }
        }
      }
            
/*
            if (type.equals(SourceFile.TAG_SELECT)) {
              String multiple = tag.getAttributeValue("multiple");
              if (multiple != null) {
                sf.addField(new Field(property, type, true));
              } else {
                sf.addField(new Field(property, type));
              }
            } else {
              sf.addField(new Field(property, type));
            }
          }                  
        }

*/
    } else { //END TAGS
      System.out.println(t);   
      if (t.toString().equals("struts:form")) {
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


  }

}
