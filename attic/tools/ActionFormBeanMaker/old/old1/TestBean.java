
import com.hothouseobjects.tags.*;
import java.io.*;
import java.util.*;

public class TestBean {

	public Enumeration getCollection() {
		Vector v = new Vector(3);
		v.addElement("one");
		v.addElement("two");
		v.addElement("three");
		return v.elements();
	}

  public List getList() {
    List l = new ArrayList();
    l.add("eins");
    l.add("zwei");
    l.add("drei");
    l.add("vier");
    return l;
  }



  public static void main(String[] args) {
    try {
      //Parse html document
      FileReader fr = new FileReader("logon.jsp");
      TagTiller tiller = new TagTiller(fr);
      Tag theParsedPage = tiller.getTilledTags();

      //Look for the forms tags    
      Vector forms = theParsedPage.collectByType("struts:form");
      for (int i = 0; i < forms.size(); i++) {
        Tag form = (Tag)forms.get(i);
        String nameAttr = form.getAttributeValue("NAME", true);
        System.out.println(nameAttr);
      }
    } catch (Exception e) {
      System.err.println(e);
    }
  }

}