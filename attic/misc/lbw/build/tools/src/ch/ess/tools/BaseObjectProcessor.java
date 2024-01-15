package ch.ess.tools;
import java.util.ArrayList;
import java.util.List;

import ch.ess.base.annotation.HierarchyProcessor;
import ch.ess.base.model.BaseObject;


public class BaseObjectProcessor implements HierarchyProcessor {

  private List<String> classList = new ArrayList<String>();
  
  public Class assignableTo() {
    return BaseObject.class;
  }

  public void process(Class clazz) {
    classList.add(clazz.getName());
  }

  public List<String> getClassList() {
    return classList;
  }
  
  

}
