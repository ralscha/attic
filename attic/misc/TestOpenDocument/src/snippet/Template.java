package snippet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.JDOMException;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.jopendocument.dom.template.TemplateException;

public class Template {

  public static void main(String[] args) throws IOException, TemplateException, JDOMException {
     File templateFile = new File("template/test.odt");
      File outFile = new File("out.odt");
      // Load the template.
      // Java 5 users will have to use RhinoFileTemplate instead
      JavaScriptFileTemplate template = new JavaScriptFileTemplate(templateFile);
    
      // Fill with sample values.
      template.setField("toto", "value set using setField()");
      List<Map<String, String>> months = new ArrayList<Map<String, String>>();
      months.add(createMap("January", "-12", "3"));
      months.add(createMap("February", "-8", "5"));
      months.add(createMap("March", "-5", "12"));
      months.add(createMap("April", "-1", "15"));
      months.add(createMap("May", "3", "21"));
      template.setField("months", months);
    
      template.hideParagraph("p1");
    
      // Save to file.
      template.saveAs(outFile);
    
      // Open the document with OpenOffice.org !
      OOUtils.open(outFile);
  }
  
  private static Map<String, String> createMap(String n, String min, String max) {
    final Map<String, String> res = new HashMap<String, String>();
    res.put("name", n);
    res.put("min", min);
    res.put("max", max);
    return res;
}
}

