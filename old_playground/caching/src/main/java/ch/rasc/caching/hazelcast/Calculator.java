package ch.rasc.caching.hazelcast;

import java.math.BigInteger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Calculator {

	@Cacheable("calculator")
	public BigInteger factorial(long n) {
		System.out.println("calling factorial method with parameter: " + n);

		BigInteger ret = BigInteger.ONE;
		for (int i = 1; i <= n; ++i) {
			ret = ret.multiply(BigInteger.valueOf(i));
		}

		return ret;
	}

}
