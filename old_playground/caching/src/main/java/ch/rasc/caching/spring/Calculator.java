package ch.rasc.caching.spring;

import java.math.BigInteger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Calculator {

	public BigInteger callFromInside() {
		// Map<String, String> test = new HashMap<>();
		System.out.println("calling factorial from inside Calculator");
		return factorial(69);
	}

	@Cacheable("calculator")
	public BigInteger factorial(long n) {
		System.out.println("calling factorial method with parameter: " + n);

		BigInteger ret = BigInteger.ONE;
		for (int i = 1; i <= n; ++i) {
			ret = ret.multiply(BigInteger.valueOf(i));
		}

		return ret;
	}

	@Cacheable(value = "calculator", key = "#p0")
	public BigInteger factorial(long n, String user) {
		System.out.println(
				"calling factorial method with parameter: " + n + " and user: " + user);
		return factorial(n);
	}

	@Cacheable(value = "calculator", condition = "#n > 10")
	public BigInteger factorialWithACondition(long n) {
		System.out.println("calling condition factorial method with parameter: " + n);
		return factorial(n);
	}

	@CacheEvict(value = { "calculator", "anotherCache" }, allEntries = true)
	public void clearCache() {
		// nothing here
	}

	@CacheEvict(value = "calculator", key = "#n")
	public void clearCache(@SuppressWarnings("unused") long n) {
		// nothing here
	}
}
