import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hothouseobjects.tags.Inspector;
import com.hothouseobjects.tags.Tag;
import com.hothouseobjects.tags.TagTiller;

public class TestTags {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("C:\\Documents and Settings\\sr\\Desktop\\lotto.html"));
    TagTiller myTiller = new TagTiller(br);
    myTiller.runTiller();
    br.close();

    Tag thePage = myTiller.getTilledTags();
    List imgTags = Inspector.collectByType(thePage, "img");
    List newList = new ArrayList();
    
    Iterator it = imgTags.iterator();
    while (it.hasNext()) {
      Tag element = (Tag)it.next();
      String src = element.getAttributeValue("src");
      
      if (src != null) {        
        if (src.indexOf("numeros") != -1) {
          newList.add(element);
        }
      }      
    }

    it = newList.iterator();

    int[] numeros = new int[7];

    int count = 0;
    while (it.hasNext()) {
      Tag element = (Tag)it.next();
      String src = element.getAttributeValue("src");
      
          
      if (count == 44) {
        for (int i = 0; i < 7; i++) {            
          element = (Tag)it.next();
          src = element.getAttributeValue("src");          
          int pos1 = src.lastIndexOf("/");
          int pos2 = src.indexOf(".gif");
          numeros[i] = Integer.parseInt(src.substring(pos1+1, pos2));

        }
        break;
        
      } else {
        count++;
      }
          
                    
    }
    
    for (int i = 0; i < numeros.length; i++) {
      System.out.println(numeros[i]);
    }
    
  }

}
