package org.apache.struts.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Validator {
  String name();

  String value() default "";

  String key() default "";

  boolean resource() default true;

  boolean arg() default true;

  Variable[] vars() default {};
}
