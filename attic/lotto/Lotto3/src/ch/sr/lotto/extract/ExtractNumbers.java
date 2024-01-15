package ch.sr.lotto.extract;

import java.io.*;
import java.util.*;

import ch.sr.lotto.util.Util;

import com.hothouseobjects.tags.*;

public class ExtractNumbers {

  public static int[] getNumbers(BufferedReader br) throws IOException {
    TagTiller myTiller = new TagTiller(br);
    myTiller.runTiller();

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
          numeros[i] = Integer.parseInt(Util.removeNonDigits(src.substring(pos1+1, pos2)));

        }
        break;
      } else {
        count++;
      }
    }
    
    return numeros;
  
  }
}
