package ch.ess.base.annotation.option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.ess.base.annotation.spring.Autowire;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
  String id();

  boolean singleton() default false;

  Autowire autowire() default Autowire.BYNAME;
}
