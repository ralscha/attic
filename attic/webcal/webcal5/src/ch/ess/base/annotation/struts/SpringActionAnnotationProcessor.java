package ch.ess.base.annotation.struts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

import ch.ess.base.annotation.AnnotationProcessor;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.web.AbstractEditAction;

public class SpringActionAnnotationProcessor implements AnnotationProcessor {

  private static final Log log = LogFactory.getLog(SpringActionAnnotationProcessor.class);
  
  private StaticApplicationContext webContext;

  public SpringActionAnnotationProcessor(StaticApplicationContext webContext) {
    this.webContext = webContext;
  }

  public Class< ? extends Annotation> getAnnotation() {
    return SpringAction.class;
  }

  @SuppressWarnings("unchecked")
  public void process(Class clazz) {

    if (clazz.isInterface() || (Modifier.isAbstract(clazz.getModifiers()))) {
      return;
    }
    
    log.info("SPRING ACTION : " + clazz.getName());


    RootBeanDefinition beanDef = new RootBeanDefinition(clazz);

    SpringAction sa = (SpringAction)clazz.getAnnotation(SpringAction.class);
    beanDef.setAutowireMode(sa.autowire().getTyp());

    beanDef.setLazyInit(true);
    String name = ProcessorUtil.getPath(clazz);

    if (ClassUtils.isAssignable(clazz, AbstractEditAction.class)) {

      String daoName = name.substring(0, name.length() - "Edit".length()) + "Dao";
      MutablePropertyValues mpv = new MutablePropertyValues();
      mpv.addPropertyValue("dao", webContext.getBean(daoName));

      beanDef.setPropertyValues(mpv);
    }
    String beanName;

    List<StrutsAction> salist = ProcessorUtil.extractStrutsActionAnnotation(clazz);

    if (salist.isEmpty()) {
      beanName = "/" + name;
    } else {
      beanName = salist.get(0).path();
    }
    webContext.registerBeanDefinition(beanName, beanDef);

    for (int i = 1; i < salist.size(); i++) {
      webContext.registerAlias(beanName, salist.get(i).path());
    }

  }

}
