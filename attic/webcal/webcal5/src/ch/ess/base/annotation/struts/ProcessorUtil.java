package ch.ess.base.annotation.struts;

import java.util.ArrayList;
import java.util.List;

public class ProcessorUtil {

  public static final String ACTION = "Action";
  
  public static String getClassName(String fqn) {
    String className = fqn;
    if (className.indexOf('.') > 0) {
      className = className.substring(className.lastIndexOf('.') + 1);
    }
    return className;
  }
  
  public static String getPath(Class< ? > clazz) {
    String name = getClassName(clazz.getName());
    return name.substring(0, 1).toLowerCase() + name.substring(1, name.length() - ACTION.length());
    
  }
  
  @SuppressWarnings("unchecked")
  public static List<StrutsAction> extractStrutsActionAnnotation(Class clazz) {
    List<StrutsAction> salist = new ArrayList<StrutsAction>();
    StrutsActions sas = (StrutsActions)clazz.getAnnotation(StrutsActions.class);
    if (sas != null) {
      StrutsAction[] saa = sas.actions();
      if (saa != null) {
        for (StrutsAction sa : saa) {
          salist.add(sa);
        }
      }
    }
    StrutsAction sac = (StrutsAction)clazz.getAnnotation(StrutsAction.class);
    if (sac != null) {
      salist.add(sac);
    }
    return salist;
  }

}
