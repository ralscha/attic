package ch.ess.base.annotation.option;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

import ch.ess.base.annotation.AnnotationProcessor;

public class OptionAnnotationProcessor implements AnnotationProcessor {

  private StaticApplicationContext webContext;
  
  public OptionAnnotationProcessor(StaticApplicationContext webContext) {
    this.webContext = webContext;
  }
  
  public Class<? extends Annotation> getAnnotation() {
    return Option.class;
  }

  @SuppressWarnings("unchecked")
  public void process(Class clazz) {

    Option optionAnnotation = (Option)clazz.getAnnotation(Option.class);
    
    RootBeanDefinition beanDefinition = new RootBeanDefinition(clazz);
    
    beanDefinition.setAutowireMode(optionAnnotation.autowire().getTyp());
    beanDefinition.setSingleton(optionAnnotation.singleton());
    
    webContext.registerBeanDefinition(optionAnnotation.id(), beanDefinition);

    
    
  }

}
