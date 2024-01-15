package ch.ess.base.annotation;


public interface NameProcessor {
  
  public boolean matches(String className);

  public void process(Class< ? > clazz);
  
}
