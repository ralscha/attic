
import com.hothouseobjects.tags.*;
import java.io.*;
import java.util.*;

public class ActionFormBeanMaker {


  private void start(String fileName) throws IOException {
    //Parse html document
    FileReader fr = new FileReader(fileName);
    TagTiller tiller = new TagTiller(fr);
    Tag theParsedPage = tiller.getTilledTags();

    //Look for the forms tags    
    Vector forms = theParsedPage.collectByType("FORM");

    List sourceFiles = new ArrayList();

    for (int i = 0; i < forms.size(); i++) {
      Tag form = (Tag)forms.get(i);
      
      String nameAttr = form.getAttributeValue("NAME", true);

      if (nameAttr != null) {
        SourceFile sf = new SourceFile(nameAttr);

        //Look for input tags inside the form
        Vector inputs = form.collectByType("INPUT");  
        for (int n = 0; n < inputs.size(); n++) {
          Tag inputTag = (Tag)inputs.get(n);
          String type = inputTag.getAttributeValue("TYPE", true);
          nameAttr = inputTag.getAttributeValue("NAME", true);
          sf.addField(new Field(nameAttr, type));
        }        

        //Textarea Tags
        Vector textAreas = form.collectByType("TEXTAREA");  
        for (int n = 0; n < textAreas.size(); n++) {
          Tag textAreaTag = (Tag)textAreas.get(n);
          nameAttr = textAreaTag.getAttributeValue("NAME", true);
          sf.addField(new Field(nameAttr, "textarea"));
        }        

        //Select Tags
        Vector selects = form.collectByType("SELECT");  
        for (int n = 0; n < selects.size(); n++) {
          Tag selectTag = (Tag)selects.get(n);
          nameAttr = selectTag.getAttributeValue("NAME", true);
          sf.addField(new Field(nameAttr, "select"));
        } 
        
        //Write source code
        sf.write();
               
        //Write new page
        changeAndWriteNewPage(fileName);
        
      } else {
        System.out.println("missing NAME attribute in form");
      }

    }
    
  }  

  private void changeAndWriteNewPage(String fileName) throws IOException {
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
          out.println("<struts:hidden name=\""+nameAttr+"\"/>");
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
            out.println("<struts:password name=\""+nameAttr+"\""+attrSB.toString()+"/>");
          else
            out.println("<struts:text name=\""+nameAttr+"\""+attrSB.toString()+"/>");
        } else if (type.equalsIgnoreCase("radio")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.print("<struts:radio name=\""+nameAttr+"\" value=\""+valueAttr+"\">");
          Vector contents = inputTag.getContents();
          for (int y = 0; y < contents.size(); y++) {
            out.print(((Item)contents.get(y)).toHTML());
          }
          out.println("</struts:radio>");
        } else if (type.equalsIgnoreCase("checkbox")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.print("<struts:checkbox name=\""+nameAttr+"\" value=\""+valueAttr+"\">");
          Vector contents = inputTag.getContents();
          for (int y = 0; y < contents.size(); y++) {
            out.print(((Item)contents.get(y)).toHTML());
          }
          out.println("</struts:checkbox>");
        } else if (type.equalsIgnoreCase("submit")) {
          String valueAttr = inputTag.getAttributeValue("VALUE", true);
          out.println();
          out.println("<struts:submit name=\""+nameAttr+"\" value=\""+valueAttr+"\"/>");        
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
        out.println("<struts:textarea name=\""+nameAttr+"\" cols=\""+colsAttr+"\" rows=\""+rowsAttr+"\">");
        Vector contents = textAreaTag.getContents();
        for (int x = 0; x < contents.size(); x++) {
          out.println(((Item)contents.get(x)).toHTML());
        }
        out.println("</struts:textarea>");

      }        

      //Select Tags
      Vector selects = form.collectByType("SELECT");  
      for (int n = 0; n < selects.size(); n++) {
        out.println();
        Tag selectTag = (Tag)selects.get(n);
        nameAttr = selectTag.getAttributeValue("NAME", true);
        
        out.println("<struts:select name=\""+nameAttr+"\">");  
        
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

  public static void main(String[] args) {
    if (args.length == 1) {
      try {
        new ActionFormBeanMaker().start(args[0]);
      } catch (IOException ioe) {
        System.err.println(ioe);
      }
    } else {
      System.out.println("java ActionFormBeanMaker <htmlFile>");
    }

  }

}