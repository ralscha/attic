package ch.ess.tools;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import ch.ess.base.annotation.AnnotationProcessor;
import ch.ess.base.annotation.option.Option;

public class OptionAnnotationProcessor implements AnnotationProcessor {

  private List<String> classList = new ArrayList<String>();
    
  public Class<? extends Annotation> getAnnotation() {
    return Option.class;
  }

  public void process(Class clazz) {
    classList.add(clazz.getName());
  }

  public List<String> getClassList() {
    return classList;
  }

}
