
import java.io.*;
import java.util.*;
import com.hothouseobjects.tags.*;

public class Test {

	public static void main(String[] args) {
    try {

      //parse jsp document
      FileReader fr = new FileReader("test.html");
      TagTiller tiller = new TagTiller(fr);
      Tag theParsedPage = tiller.getTilledTags();

      //search form tags    
      Vector forms = theParsedPage.collectByType("form");

      for (int i = 0; i < forms.size(); i++) {
        Tag form = (Tag)forms.get(i);
        Tag newForm = new Tag("struts:form");
        newForm.setContainer(true);

        Vector v = form.getContents();              
        for (int j = 0; j < v.size(); j++) {
          v.get(j);
        }
        //theParsedPage.replaceItem(form, newForm);
        //System.out.println(newForm.toHTML());
      }        

      System.out.println(theParsedPage.toHTML());

    } catch (IOException ioe) {
      System.err.println(ioe);
    }
    
	}

}