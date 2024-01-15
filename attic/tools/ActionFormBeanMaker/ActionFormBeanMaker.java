
import com.hothouseobjects.tags.*;
import java.io.*;
import java.util.*;

public class ActionFormBeanMaker {

  private void jsp2bean(String fileName) throws IOException {
    //parse jsp document
    FileReader fr = new FileReader(fileName);
    TagTiller tiller = new TagTiller(fr);
    Tag theParsedPage = tiller.getTilledTags();

    //search form tags    
    Vector forms = theParsedPage.collectByType("struts:form");

    for (int i = 0; i < forms.size(); i++) {
      Tag form = (Tag)forms.get(i);
      
      String typeAttr = form.getAttributeValue("type", true);

      if (typeAttr != null) {
        SourceFile sf = new SourceFile(typeAttr);

        Iterator tagIterator = SourceFile.TAG_SET.iterator();
        while(tagIterator.hasNext()) {
          String type = (String)tagIterator.next();

          Vector tags = form.collectByType(type);  
          for (int n = 0; n < tags.size(); n++) {
            Tag tag = (Tag)tags.get(n);
            String property = tag.getAttributeValue("property", true);

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

        //Write source code
        sf.write();
                       
      } else {
        System.out.println("missing TYPE attribute in struts:form");
      }
    }    
  }  

  private void html2bean(String fileName) throws IOException {
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

        //Input Tags
        Vector inputs = form.collectByType("INPUT");  
        for (int n = 0; n < inputs.size(); n++) {
          Tag inputTag = (Tag)inputs.get(n);
          String type = inputTag.getAttributeValue("TYPE", true);
          nameAttr = inputTag.getAttributeValue("NAME", true);
          String strutsType = html2struts(type);
          if (strutsType != null)
            sf.addField(new Field(nameAttr, strutsType));
        }        

        //Textarea Tags
        Vector textAreas = form.collectByType("TEXTAREA");  
        for (int n = 0; n < textAreas.size(); n++) {
          Tag textAreaTag = (Tag)textAreas.get(n);
          nameAttr = textAreaTag.getAttributeValue("NAME", true);
          sf.addField(new Field(nameAttr, SourceFile.TAG_TEXTAREA));
        }        

        //Select Tags
        Vector selects = form.collectByType("SELECT");  
        for (int n = 0; n < selects.size(); n++) {
          Tag selectTag = (Tag)selects.get(n);
          nameAttr = selectTag.getAttributeValue("NAME", true);

          String multiple = selectTag.getAttributeValue("multiple");
          if (multiple != null) {
            sf.addField(new Field(nameAttr, SourceFile.TAG_SELECT, true));
          } else {
            sf.addField(new Field(nameAttr, SourceFile.TAG_SELECT));
          }

        } 
      
        //Write source code
        sf.write();
        
               
        //Write new page
        new StrutsCreator().changeAndWriteNewPage(fileName);
        

      } else {
        System.out.println("missing NAME attribute in form");
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

  public static void main(String[] args) {
    if (args.length >= 2) {
      try {
        boolean htmlPage = false;
        String fileName = null;
         
        if (args.length == 2) {        
          fileName = args[1];
          htmlPage = "-html".equalsIgnoreCase(args[0]);
        } 

        if (htmlPage) {
          new ActionFormBeanMaker().html2bean(fileName);
        } else {
          new ActionFormBeanMaker().jsp2bean(fileName);
        }
      } catch (IOException ioe) {
        System.err.println(ioe);
      }
    } else {
      System.out.println("java ActionFormBeanMaker <-jsp|-html> <fileName>");
    }

  }

}