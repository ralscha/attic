
import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;


public class TemplateParser {

  private String template;
  
  public TemplateParser(String template) {
    this.template = template;
  }

  public List getIDList() throws JDOMException, IOException {
    
    // Request document building without validation
    SAXBuilder builder = new SAXBuilder(false);
    InputStream is = getClass().getResourceAsStream(template);
    Document doc = builder.build(is);

    // Get the root element
    Element root = doc.getRootElement();

    List idList = new ArrayList();
    getIDs(root, idList);

    is.close();
  
    return idList;
    
  }

  private boolean isStopWord(String str) {
    if ( (str.startsWith("Text")) ||
         (str.startsWith("Newline")) ||
         (str.startsWith("Section")) ||
         (str.startsWith("TextBox")) ||
         (str.startsWith("Image")) )
      return true;
    else
      return false;
  }

  private void getIDs(Element e, List l) {
    
      String id = e.getAttributeValue("ID");
      if (id != null) {
        if (!isStopWord(id))
          l.add(id);
      }

      if (e.hasChildren()) {
        List childs = e.getChildren();
        for (int i = 0; i < childs.size(); i++) {
          getIDs((Element)childs.get(i), l);
        }
      }
  }

}