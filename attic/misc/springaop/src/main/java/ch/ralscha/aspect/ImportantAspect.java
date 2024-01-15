package ch.ralscha.aspect;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Named
public class ImportantAspect {

	@SuppressWarnings("unused")
	@Pointcut("@annotation(Important)")
	private void importantAnnotatedMethods() {
	}

	@Around("importantAnnotatedMethods()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("START");
		Object retVal = pjp.proceed();
		System.out.println("END");
		return retVal + "X";
	}

}
