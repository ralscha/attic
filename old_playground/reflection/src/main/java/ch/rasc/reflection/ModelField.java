package ch.rasc.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModelField {
	String value() default "";

	String type() default "auto";

	String defaultValue() default "";

	String dateFormat() default "";

	boolean useNull() default false;

}
