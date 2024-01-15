package ch.ess.base.annotation.spring;

import org.springframework.beans.factory.support.AbstractBeanDefinition;


public enum Autowire {  
  NO(AbstractBeanDefinition.AUTOWIRE_NO),
  BYNAME(AbstractBeanDefinition.AUTOWIRE_BY_NAME),
  BYTYPE(AbstractBeanDefinition.AUTOWIRE_BY_TYPE), 
  CONSTRUCTOR(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR),
  AUTODETECT(AbstractBeanDefinition.AUTOWIRE_AUTODETECT);
    
  private int typ;

  Autowire(int typ) {
    this.typ = typ;
  }

  public int getTyp() {
    return typ;
  }
}


