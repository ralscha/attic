package ch.rasc.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class Main {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException {
		Class<?> dynamicType = new ByteBuddy().subclass(Object.class)
				.method(ElementMatchers.named("toString"))
				.intercept(FixedValue.value("Hello World!")).make()
				.load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
				.getLoaded();

		System.out.println(dynamicType.newInstance().toString());
	}

}
