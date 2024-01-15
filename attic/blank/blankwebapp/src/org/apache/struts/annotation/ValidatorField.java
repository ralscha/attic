package org.apache.struts.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface ValidatorField {
  String key() default "";

  boolean resource() default true;

  boolean required() default false;

  Validator[] validators() default {};
}
