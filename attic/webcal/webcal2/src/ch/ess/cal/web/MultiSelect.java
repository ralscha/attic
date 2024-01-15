package ch.ess.cal.web;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

public class MultiSelect {

  public static boolean handleRequest(
    String prefix,
    Map params,
    List availableList,
    String[] availableId,
    List selectedList,
    String[] selectedId) {
    
    Set paramKeys = params.keySet();

    if (paramKeys.contains(prefix + "add")) {

      for (ListIterator it = availableList.listIterator(); it.hasNext();) {
        LabelValueBean lb = (LabelValueBean)it.next();
        String id = lb.getValue();
        for (int i = 0; i < availableId.length; i++) {
          if (id.equals(availableId[i])) {
            selectedList.add(lb);
            it.remove();
            break;
          }
        }
      }
      Collections.sort(selectedList);
      return true;
    } else if (paramKeys.contains(prefix + "alladd")) {
      selectedList.addAll(availableList);      
      availableList.clear();
      Collections.sort(selectedList);
      return true;
    } else if (paramKeys.contains(prefix + "remove")) {

      for (ListIterator it = selectedList.listIterator(); it.hasNext();) {
        LabelValueBean lb = (LabelValueBean)it.next();
        String id = lb.getValue();
        for (int i = 0; i < selectedId.length; i++) {
          if (id.equals(selectedId[i])) {
            availableList.add(lb);
            it.remove();
            break;
          }
        }
      }
      Collections.sort(availableList);
      return true;
    } else if (paramKeys.contains(prefix + "allremove")) {
      availableList.addAll(selectedList);
      selectedList.clear();
      Collections.sort(availableList);
      return true;
    }

    return false;
  }
}
