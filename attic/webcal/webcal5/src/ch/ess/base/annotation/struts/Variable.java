package ch.ess.base.annotation.struts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Variable {
  String name();

  String value();

  boolean arg() default true;

  String key() default "";

  boolean resource() default true;
}
