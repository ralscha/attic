package ch.ess.base.annotation.struts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StrutsAction {
  String path();

  Class< ? > form() default java.lang.Object.class;

  String name() default "";

  boolean validate() default false;

  String input() default "";

  ActionScope scope() default ActionScope.REQUEST;

  Forward[] forwards() default {};

  String parameter() default "";
  
  String roles() default "";

  boolean unknown() default false;
}
