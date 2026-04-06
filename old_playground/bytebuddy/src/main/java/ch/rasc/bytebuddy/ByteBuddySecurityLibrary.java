package ch.rasc.bytebuddy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

class ByteBuddySecurityLibrary {

	public static String currentUser = "admin";

	public <T> Class<? extends T> secure(Class<T> type) {
		return new ByteBuddy().subclass(type)
				.method(ElementMatchers.isAnnotatedWith(Secured.class))
				.intercept(MethodDelegation.to(ByteBuddySecurityLibrary.class)).make()
				.load(type.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}

	@RuntimeType
	public static Object intercept(@SuperCall Callable<?> superMethod,
			@Origin Method method) throws Exception {
		if (!method.getAnnotation(Secured.class).requiredUser().equals(currentUser)) {
			throw new IllegalStateException(method + " requires appropriate login");
		}
		return superMethod.call();
	}

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException {
		ByteBuddySecurityLibrary l = new ByteBuddySecurityLibrary();
		Class<? extends TestService> t = l.secure(TestService.class);
		TestService ts = t.newInstance();
		System.out.println(ts);
		ts.publicMethod();
		ts.securedMethod();
		ts.securedUserMethod();
	}
}