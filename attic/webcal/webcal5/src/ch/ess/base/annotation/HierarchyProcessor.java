package ch.ess.base.annotation;


public interface HierarchyProcessor {
  
  public Class< ? > assignableTo();

  public void process(Class< ? > clazz);
}
