package ch.rasc.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
	String value() default "";

	String idProperty() default "id";

	boolean paging() default false;

	String readMethod() default "";

	String createMethod() default "";

	String updateMethod() default "";

	String destroyMethod() default "";

}
