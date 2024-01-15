
import com.hothouseobjects.tags.*;
import java.io.*;
import java.util.*;

public class StrutsCreator {

  public static void changeAndWriteNewPage(String fileName) throws IOException {
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName+".jsp")));
    out.println("<%@ page language=\"java\" %>");
    out.println("<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>");
    out.println();

    //Parse html document
    FileReader fr = new FileReader(fileName);
    TagTiller tiller = new TagTiller(fr);
    Tag theParsedPage = tiller.getTilledTags();

    //Look for the forms tags    
    Vector forms = theParsedPage.collectByType("FORM");

    for (int i = 0; i < forms.size(); i++) {
      Tag form = (Tag)forms.get(i);
      String nameAttr = form.getAttributeValue("NAME", true);
      String methodAttr = form.getAttributeValue("METHOD", true);
      String actionAttr = form.getAttributeValue("ACTION", true);

      out.println("<struts:form action=\""+actionAttr+"\" method=\""+methodAttr+"\" name=\""
                + nameAttr + "\" scope=\"???\" type=\"???\">");
      

      Vector inputs = form.collectByType("INPUT");  
      for (int n = 0; n < inputs.size(); n++) {
        Tag inputTag = (Tag)inputs.get(n);
        String type = inputTag.getAttributeValue("TYPE", true);
        nameAttr = inputTag.getAttributeValue("NAME", true);

        if (type.equalsIgnoreCase("hidden")) {
          out.println();
          out.println("<struts:hidden property=\""+nameAttr+"\"/>");
        } else if (type.equalsIgnoreCase("password") ||
                   type.equalsIgnoreCase("text")) {
          out.println();
          String maxlength = inputTag.getAttributeValue("MAXLENGTH", true);
          String size = inputTag.getAttributeValue("SIZE", true);
          StringBuffer attrSB = new StringBuffer();
          
          if (size != null)
            attrSB.append(" size=\"").append(size).append("\"");

          if (maxlength != null)
            attrSB.append(" maxlength=\"").append(maxlength).append("\"");
          
          if (type.equalsIgnoreCase("password"))
            out.println("<struts:password property=\""+nameAttr+"\""+attrSB.toString()+"/>");
          else
            out.println("<struts:text property=\""+nameAttr+"\""+attrSB.toString()+"/>");
        } else if (type.equalsIgnoreCase("radio")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.print("<struts:radio property=\""+nameAttr+"\" value=\""+valueAttr+"\">");
          Vector contents = inputTag.getContents();
          for (int y = 0; y < contents.size(); y++) {
            out.print(((Item)contents.get(y)).toHTML());
          }
          out.println("</struts:radio>");
        } else if (type.equalsIgnoreCase("checkbox")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.print("<struts:checkbox property=\""+nameAttr+"\" value=\""+valueAttr+"\">");
          Vector contents = inputTag.getContents();
          for (int y = 0; y < contents.size(); y++) {
            out.print(((Item)contents.get(y)).toHTML());
          }
          out.println("</struts:checkbox>");
        } else if (type.equalsIgnoreCase("submit")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.println("<struts:submit property=\""+nameAttr+"\" value=\""+valueAttr+"\"/>");        
        } 
      }        

      //Textarea Tags
      Vector textAreas = form.collectByType("TEXTAREA");  
      for (int n = 0; n < textAreas.size(); n++) {
        out.println();
        Tag textAreaTag = (Tag)textAreas.get(n);
        nameAttr = textAreaTag.getAttributeValue("NAME", true);
        String colsAttr = textAreaTag.getAttributeValue("COLS", true);
        String rowsAttr = textAreaTag.getAttributeValue("ROWS", true);
        out.println("<struts:textarea property=\""+nameAttr+"\" cols=\""+colsAttr+"\" rows=\""+rowsAttr+"\"\\>");
      }        

      //Select Tags
      Vector selects = form.collectByType("SELECT");  
      for (int n = 0; n < selects.size(); n++) {
        out.println();
        Tag selectTag = (Tag)selects.get(n);
        nameAttr = selectTag.getAttributeValue("NAME", true);
        
        out.println("<struts:select property=\""+nameAttr+"\">");  
        
        Vector options = selectTag.collectByType("OPTION");
        for (int x = 0; x < options.size(); x++) {      
          Tag optionTag = (Tag)options.get(x);
          String valueAttr = optionTag.getAttributeValue("VALUE", true);

          out.print("  <struts:option value=\""+valueAttr+"\">");
          
          Vector contents = optionTag.getContents();
          for (int y = 0; y < contents.size(); y++) {
            out.print(((Item)contents.get(y)).toHTML());
          }
          
          out.println("</struts:option>");
        }
        out.println("</struts:select>");
      } 

      out.println();
      out.println("</struts:form>");
      
    }
          
    out.close();  
  }

}