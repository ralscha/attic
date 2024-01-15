package ch.ess.base.annotation;
import java.lang.annotation.Annotation;

public interface AnnotationProcessor {

  public Class<? extends Annotation> getAnnotation();

  public void process(Class clazz);

}