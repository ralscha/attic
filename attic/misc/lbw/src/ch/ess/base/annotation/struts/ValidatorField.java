package ch.ess.base.annotation.struts;

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
